-- Database: LoungeBar
--Triggers

--Overlapping between reservations
CREATE OR REPLACE FUNCTION check_reservation_overlap()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 
        FROM Reservation
        WHERE tableNumber = NEW.tableNumber
          AND reservation_date = NEW.reservation_date
          AND id != NEW.id 
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
BEFORE INSERT OR UPDATE ON Reservation
FOR EACH ROW
EXECUTE FUNCTION check_reservation_overlap();


--Checking of the table's capacity
CREATE OR REPLACE FUNCTION check_table_capacity()
RETURNS TRIGGER AS $$
DECLARE
    v_capacity SMALLINT;
BEGIN
    SELECT capacity INTO v_capacity FROM Tables WHERE number = NEW.tableNumber;

    IF NEW.n_people > v_capacity THEN
        RAISE EXCEPTION 'Table % capacity exceeded. Max capacity: %, requested: %.', 
            NEW.tableNumber, v_capacity, NEW.n_people;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_table_capacity
BEFORE INSERT OR UPDATE ON Reservation
FOR EACH ROW
EXECUTE FUNCTION check_table_capacity();
