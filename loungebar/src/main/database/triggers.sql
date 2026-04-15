--Triggers

--Overlapping between reservations
CREATE OR REPLACE FUNCTION lounge.check_reservation_overlap()
    RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM lounge.Reservation
        WHERE tableNumber = NEW.tableNumber
          AND reservation_date = NEW.reservation_date
          AND (NEW.id IS NULL OR id != NEW.id)
          AND (
            (NEW.start_time, NEW.end_time) OVERLAPS (start_time, end_time)
            )
    ) THEN
        RAISE EXCEPTION 'Table % is already reserved for the selected time slot.', NEW.tableNumber;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_reservation_overlap
    BEFORE INSERT OR UPDATE ON lounge.Reservation
    FOR EACH ROW
EXECUTE FUNCTION lounge.check_reservation_overlap();


--Checking of the table's capacity
CREATE OR REPLACE FUNCTION lounge.check_table_capacity()
    RETURNS TRIGGER AS $$
DECLARE
    v_capacity SMALLINT;
BEGIN
    SELECT capacity INTO v_capacity
    FROM lounge.Tables
    WHERE number = NEW.tableNumber;

    IF NEW.n_people > v_capacity THEN
        RAISE EXCEPTION 'Table % capacity exceeded. Max capacity: %, requested: %.',
            NEW.tableNumber, v_capacity, NEW.n_people;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_table_capacity
    BEFORE INSERT OR UPDATE ON lounge.Reservation
    FOR EACH ROW
EXECUTE FUNCTION lounge.check_table_capacity();

--Supply management
CREATE OR REPLACE FUNCTION lounge.check_and_update_supply()
    RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM lounge.Composition c
                          JOIN lounge.Supply s ON c.supply_name = s.name
        WHERE c.drink_name = NEW.drink_name
          AND s.units_available < (c.amount * NEW.quantity)
    ) THEN
        RAISE EXCEPTION 'Insufficient supply stocks for drink: %', NEW.drink_name;
    END IF;

    UPDATE lounge.Supply s
    SET units_available = s.units_available - (c.amount * NEW.quantity)
    FROM lounge.Composition c
    WHERE c.supply_name = s.name AND c.drink_name = NEW.drink_name;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_and_update_supply
    BEFORE INSERT ON lounge.OrderItem
    FOR EACH ROW EXECUTE FUNCTION lounge.check_and_update_supply();

--Automatic table availability check
CREATE OR REPLACE FUNCTION lounge.sync_table_status()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'PENDING' THEN
        UPDATE lounge.Tables SET is_available = FALSE WHERE number = NEW.tableID;
    ELSIF NEW.status IN ('PAID', 'CANCELED') THEN
        UPDATE lounge.Tables SET is_available = TRUE WHERE number = NEW.tableID;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_sync_table_status
    AFTER INSERT OR UPDATE OF status ON lounge.Orders
    FOR EACH ROW EXECUTE FUNCTION lounge.sync_table_status();