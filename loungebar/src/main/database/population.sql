-- Database: LoungeBar


-- Population of the bar database

-- population of SUPPLY table


-- NOTE: the quantities inserted are "per units SELLABLE" = 1 unit of GIN is not a bottle of gin, 
--          but 0.5 oz (~ 15 ml). This way you consider 1 bottle = 25 units;
--          same for COCA COLA: 1 unit = 1 can

-- ##########################################################
--      SUPPLY POPULATION
-- ##########################################################
INSERT INTO Supply (name, units_available) VALUES
('Coca Cola', 100), ('Coca Cola Zero', 100), ('Tonic Water', 90),
('Lemon Soda', 90), ('Soda Water', 80), ('Sparkling Water', 60),
('Still Water', 70), ('Lemon Juice Bottle', 40), ('Strawberry Juice Bottle', 35),
('Orange Juice Bottle', 55), ('Pineapple Juice Bottle', 45), ('Gin Mare', 1),
('Gin Malfy', 50), ('Gin Hendricks', 50), ('Gin Bombay', 50),
('Vodka Beluga', 50), ('Vodka Belvedere', 50), ('Vodka Grey Goose', 50),
('Vodka Keglevich', 50), ('Rum Don Papa', 50), ('Rum Ron Zacapa', 50),
('Rum Diplomatico', 50), ('Tequila', 50), ('Amaro del Capo', 50),
('Amaro Jefferson', 50), ('Amaro Jagermeister', 50), ('Whiskey Oban', 50),
('Whiskey Jack Daniels', 50), ('Whiskey Jim Beam', 50), ('Red Wine', 100),
('White Wine', 100), ('Prosecco', 100), ('Beer Lager Draft', 50),
('Beer IPA Draft', 50), ('Coffee Classic', 200), ('Coffee Decaffeinated', 200),
('Sugar Sachet', 300), ('Straws', 500), ('Napkins', 400), ('Ice Cubes Bag', 20);

-- ##########################################################
--      DRINK POPULATION
-- ##########################################################
INSERT INTO Drink (name, price, is_alcoholic) VALUES
('Gin Tonic', 8.50, TRUE), ('Vodka Lemon', 7.50, TRUE), ('Cuba Libre', 8.00, TRUE),
('Mojito', 9.00, TRUE), ('Pina Colada', 9.00, TRUE), ('Daiquiri', 8.50, TRUE),
('Margarita', 8.50, TRUE), ('Old Fashioned', 10.00, TRUE), ('Whiskey Sour', 9.00, TRUE),
('Amaretto Sour', 8.00, TRUE), ('Negroni', 9.00, TRUE), ('Spritz Aperol', 7.00, TRUE),
('Americano', 8.00, TRUE), ('Espresso Martini', 9.50, TRUE), ('Paloma', 8.50, TRUE),
('Dark and Stormy', 9.00, TRUE), ('Bloody Mary', 9.00, TRUE), ('Moscow Mule', 8.50, TRUE),
('Gin Fizz', 8.00, TRUE), ('Cosmopolitan', 9.00, TRUE), ('Long Island Iced Tea', 10.00, TRUE),
('Sex on the Beach', 9.00, TRUE), ('Tequila Sunrise', 8.00, TRUE), ('Caipirinha', 9.00, TRUE),
('Caffè', 1.50, FALSE), ('Caffè Decaffeinato', 1.50, FALSE), ('Acqua Naturale', 2.00, FALSE),
('Acqua Frizzante', 2.00, FALSE), ('Coca Cola Can', 3.50, FALSE), ('Red Wine Glass', 4.00, TRUE),
('White Wine Glass', 4.00, TRUE), ('Prosecco Glass', 5.00, TRUE), ('Lager Beer Draft', 4.50, TRUE),
('IPA Beer Draft', 5.50, TRUE), ('Orange Juice', 3.00, FALSE);

-- ##########################################################
--      COMPOSITION POPULATION
-- ##########################################################
INSERT INTO Composition (drink_name, supply_name, amount) VALUES
('Gin Tonic', 'Gin Mare', 2), ('Gin Tonic', 'Tonic Water', 4), ('Gin Tonic', 'Ice Cubes Bag', 1), ('Gin Tonic', 'Straws', 1),
('Spritz Aperol', 'Prosecco', 3), ('Spritz Aperol', 'Soda Water', 1), ('Spritz Aperol', 'Ice Cubes Bag', 1),
('Vodka Lemon', 'Vodka Belvedere', 2), ('Vodka Lemon', 'Lemon Soda', 4), ('Vodka Lemon', 'Ice Cubes Bag', 1), ('Vodka Lemon', 'Straws', 1),
('Cuba Libre', 'Rum Don Papa', 2), ('Cuba Libre', 'Coca Cola', 4), ('Cuba Libre', 'Lemon Juice Bottle', 1), ('Cuba Libre', 'Ice Cubes Bag', 1), ('Cuba Libre', 'Straws', 1),
('Mojito', 'Rum Ron Zacapa', 2), ('Mojito', 'Soda Water', 3), ('Mojito', 'Lemon Juice Bottle', 2), ('Mojito', 'Sugar Sachet', 2), ('Mojito', 'Ice Cubes Bag', 1), ('Mojito', 'Straws', 1),
('Pina Colada', 'Rum Diplomatico', 2), ('Pina Colada', 'Pineapple Juice Bottle', 4), ('Pina Colada', 'Ice Cubes Bag', 1), ('Pina Colada', 'Straws', 1),
('Daiquiri', 'Rum Don Papa', 2), ('Daiquiri', 'Lemon Juice Bottle', 1), ('Daiquiri', 'Ice Cubes Bag', 1), ('Daiquiri', 'Straws', 1),
('Margarita', 'Tequila', 2), ('Margarita', 'Lemon Juice Bottle', 1), ('Margarita', 'Ice Cubes Bag', 1), ('Margarita', 'Straws', 1),
('Whiskey Sour', 'Whiskey Jack Daniels', 2), ('Whiskey Sour', 'Lemon Juice Bottle', 1), ('Whiskey Sour', 'Ice Cubes Bag', 1), ('Whiskey Sour', 'Straws', 1),
('Espresso Martini', 'Vodka Grey Goose', 2), ('Espresso Martini', 'Coffee Classic', 1), ('Espresso Martini', 'Sugar Sachet', 1),
('Dark and Stormy', 'Rum Ron Zacapa', 2), ('Dark and Stormy', 'Lemon Juice Bottle', 1), ('Dark and Stormy', 'Ice Cubes Bag', 1), ('Dark and Stormy', 'Straws', 1),
('Bloody Mary', 'Vodka Keglevich', 2), ('Bloody Mary', 'Strawberry Juice Bottle', 4), ('Bloody Mary', 'Lemon Juice Bottle', 1), ('Bloody Mary', 'Ice Cubes Bag', 1), ('Bloody Mary', 'Straws', 1),
('Cosmopolitan', 'Vodka Belvedere', 2), ('Cosmopolitan', 'Lemon Juice Bottle', 1), ('Cosmopolitan', 'Orange Juice Bottle', 1), ('Cosmopolitan', 'Ice Cubes Bag', 1), ('Cosmopolitan', 'Straws', 1),
('Caffè', 'Coffee Classic', 1), ('Caffè', 'Sugar Sachet', 1), ('Caffè', 'Napkins', 1),
('Caffè Decaffeinato', 'Coffee Decaffeinated', 1), ('Caffè Decaffeinato', 'Sugar Sachet', 1), ('Caffè Decaffeinato', 'Napkins', 1),
('Acqua Naturale', 'Still Water', 1), ('Acqua Frizzante', 'Sparkling Water', 1), ('Coca Cola Can', 'Coca Cola', 1),
('Red Wine Glass', 'Red Wine', 1), ('White Wine Glass', 'White Wine', 1), ('Prosecco Glass', 'Prosecco', 1),
('Lager Beer Draft', 'Beer Lager Draft', 1), ('IPA Beer Draft', 'Beer IPA Draft', 1), ('Orange Juice', 'Orange Juice Bottle', 1), ('Orange Juice', 'Straws', 1);

-- ##########################################################
--      TABLES POPULATION
-- ##########################################################
INSERT INTO Tables (number, capacity, is_available) VALUES
(1, 2, TRUE), (2, 2, FALSE), (3, 4, TRUE), (4, 4, FALSE), (5, 6, TRUE), (6, 6, FALSE),
(7, 2, TRUE), (8, 2, TRUE), (9, 4, TRUE), (10, 4, FALSE), (11, 6, TRUE), (12, 6, TRUE),
(13, 2, FALSE), (14, 2, TRUE), (15, 4, TRUE), (16, 4, TRUE), (17, 6, FALSE), (18, 6, TRUE),
(19, 8, TRUE), (20, 8, TRUE);

-- ##########################################################
--      WAITER POPULATION
-- ##########################################################
INSERT INTO Waiter (phone, name, surname) VALUES
('+393401234567', 'Ecaterina', 'Iacobet'), ('+393409876543', 'Claudia', 'Foffano'),
('+393412345678', 'Fulvio', 'Bruzzese'), ('+393418765432', 'Irene', 'Bernad de Olano'),
('+393421234567', 'Luis', 'Nunez'), ('+393428765432', 'Sondos', 'Halawani'),
('+393431234567', 'Matteo', 'Galli'), ('+393438765432', 'Francesca', 'Fontana');

-- ##########################################################
--      ORDERS POPULATION
-- ##########################################################
INSERT INTO Orders (date_time, status, pay_method, waiterID, tableID) VALUES
('2025-12-12 12:00:00', 'PAID', 'CASH', '+393401234567', 1),
('2025-12-12 12:15:00', 'PAID', 'DEBIT CARD', '+393409876543', 2),
('2025-12-12 12:30:00', 'PENDING', NULL, '+393412345678', 3),
('2025-12-12 12:45:00', 'CANCELED', NULL, '+393418765432', 4),
('2025-12-12 13:00:00', 'PAID', 'BANCOMAT', '+393421234567', 5),
('2025-12-12 13:15:00', 'PAID', 'CASH', '+393428765432', 6),
('2025-12-12 13:30:00', 'PENDING', NULL, '+393431234567', 7),
('2025-12-12 13:45:00', 'PAID', 'DEBIT CARD', '+393438765432', 8),
('2025-12-12 14:00:00', 'CANCELED', NULL, '+393401234567', 1),
('2025-12-12 14:15:00', 'PAID', 'BANCOMAT', '+393409876543', 2),
('2025-12-12 14:30:00', 'PAID', 'CASH', '+393412345678', 3),
('2025-12-12 14:45:00', 'PENDING', NULL, '+393418765432', 4),
('2025-12-12 15:00:00', 'PAID', 'DEBIT CARD', '+393421234567', 5),
('2025-12-12 15:15:00', 'PAID', 'CASH', '+393428765432', 6),
('2025-12-12 15:30:00', 'CANCELED', NULL, '+393431234567', 7),
('2025-12-12 15:45:00', 'PAID', 'BANCOMAT', '+393438765432', 8),
('2025-12-12 16:00:00', 'PAID', 'CASH', '+393401234567', 1),
('2025-12-12 16:15:00', 'PENDING', NULL, '+393409876543', 2),
('2025-12-12 16:30:00', 'PAID', 'DEBIT CARD', '+393412345678', 3),
('2025-12-12 16:45:00', 'CANCELED', NULL, '+393418765432', 4),
('2025-12-12 17:00:00', 'PAID', 'BANCOMAT', '+393421234567', 5),
('2025-12-12 17:15:00', 'PAID', 'CASH', '+393428765432', 6),
('2025-12-12 17:30:00', 'PENDING', NULL, '+393431234567', 7),
('2025-12-12 17:45:00', 'PAID', 'DEBIT CARD', '+393438765432', 8),
('2025-12-12 18:00:00', 'CANCELED', NULL, '+393401234567', 1),
('2025-12-12 18:15:00', 'PAID', 'BANCOMAT', '+393409876543', 2),
('2025-12-12 18:30:00', 'PAID', 'CASH', '+393412345678', 3),
('2025-12-12 18:45:00', 'PENDING', NULL, '+393418765432', 4),
('2025-12-12 19:00:00', 'PAID', 'DEBIT CARD', '+393421234567', 5),
('2025-12-12 19:15:00', 'PAID', 'CASH', '+393428765432', 6),
('2025-12-12 19:30:00', 'CANCELED', NULL, '+393431234567', 7),
('2025-12-12 19:45:00', 'PAID', 'BANCOMAT', '+393438765432', 8),
('2025-12-12 20:00:00', 'PAID', 'CASH', '+393401234567', 1),
('2025-12-12 20:15:00', 'PENDING', NULL, '+393409876543', 2),
('2025-12-12 20:30:00', 'PAID', 'DEBIT CARD', '+393412345678', 3),
('2025-12-12 20:45:00', 'CANCELED', NULL, '+393418765432', 4),
('2025-12-12 21:00:00', 'PAID', 'BANCOMAT', '+393421234567', 5),
('2025-12-12 21:15:00', 'PAID', 'CASH', '+393428765432', 6),
('2025-12-12 21:30:00', 'PENDING', NULL, '+393431234567', 7),
('2025-12-12 21:45:00', 'PAID', 'DEBIT CARD', '+393438765432', 8),
('2025-12-12 22:00:00', 'CANCELED', NULL, '+393401234567', 1),
('2025-12-12 22:15:00', 'PAID', 'CASH', '+393409876543', 2),
('2025-12-12 22:30:00', 'PAID', 'DEBIT CARD', '+393412345678', 3),
('2025-12-12 22:45:00', 'CANCELED', NULL, '+393418765432', 4);

-- ##########################################################
--      ORDERITEM POPULATION
-- ##########################################################
INSERT INTO OrderItem (drink_name, orderID, quantity) VALUES
('Gin Tonic', 1, 2), ('Acqua Naturale', 1, 2), ('Caffè', 1, 2),
('Coca Cola Can', 2, 2), ('Mojito', 2, 5),
('Acqua Naturale', 3, 2), ('Negroni', 3, 1), ('IPA Beer Draft', 3, 2), ('Orange Juice', 3, 1), ('Prosecco Glass', 3, 3),
('Coca Cola Can', 4, 2), ('Caffè', 4, 1), ('Acqua Frizzante', 4, 1), ('Red Wine Glass', 4, 1),
('Mojito', 5, 1), ('Caipirinha', 5, 1), ('Orange Juice', 5, 1), ('Gin Tonic', 5, 1), ('Acqua Naturale', 5, 1),
('Acqua Naturale', 6, 2),
('Whiskey Sour', 7, 2), ('Caffè Decaffeinato', 7, 1), ('Espresso Martini', 7, 2),
('Acqua Frizzante', 8, 2), ('Cuba Libre', 8, 1), ('Lager Beer Draft', 8, 3), ('Americano', 8, 1), ('Coca Cola Can', 8, 2),
('Margarita', 9, 2), ('Orange Juice', 9, 1), ('Paloma', 9, 1),
('Red Wine Glass', 10, 2), ('Gin Fizz', 10, 1), ('Acqua Naturale', 10, 3), ('Tequila Sunrise', 10, 2), ('White Wine Glass', 10, 2),
('Caffè', 11, 1), ('Vodka Lemon', 11, 2), ('Caipirinha', 11, 1), ('Coca Cola Can', 11, 2),
('Caffè Decaffeinato', 12, 1), ('Acqua Naturale', 12, 1), ('Orange Juice', 12, 1),
('Espresso Martini', 13, 2), ('Americano', 13, 1), ('Caffè', 13, 1), ('Coca Cola Can', 13, 1), ('Prosecco Glass', 13, 1),
('Americano', 14, 1), ('Red Wine Glass', 14, 1),
('Cuba Libre', 15, 1), ('Prosecco Glass', 15, 2), ('Acqua Naturale', 15, 1), ('Vodka Lemon', 15, 1),
('Acqua Frizzante', 16, 2), ('Long Island Iced Tea', 16, 1), ('IPA Beer Draft', 16, 2),
('Caffè Decaffeinato', 17, 1), ('Cosmopolitan', 17, 2), ('Orange Juice', 17, 1), ('Lager Beer Draft', 17, 3),
('Bloody Mary', 18, 1), ('Red Wine Glass', 18, 2),
('Gin Tonic', 19, 1), ('Acqua Naturale', 19, 2), ('Old Fashioned', 19, 1),
('Prosecco Glass', 20, 2), ('Caffè', 20, 1), ('Whiskey Sour', 20, 2), ('Moscow Mule', 20, 1), ('Coca Cola Can', 20, 2);

-- ##########################################################
--      CUSTOMER POPULATION
-- ##########################################################
INSERT INTO Customer (phone, name, surname) VALUES
('+393401112233', 'Nicola', 'Ferro'), ('+393402223344', 'Luca', 'Bianchi'),
('+393403334455', 'Giulia', 'Ferrari'), ('+393404445566', 'Francesca', 'Romano'),
('+393405556677', 'Matteo', 'Colombo'), ('+393406667788', 'Chiara', 'Galli'),
('+393407778899', 'Alessandro', 'Costa'), ('+393408889900', 'Sara', 'Fontana'),
('+393409990011', 'Davide', 'Greco'), ('+393401234999', 'Elena', 'Martini'),
('+393402345888', 'Simone', 'De Luca'), ('+393403456777', 'Valentina', 'Rizzo'),
('+393404567666', 'Giorgio', 'Barbieri'), ('+393405678555', 'Ilaria', 'Sartori'),
('+393406789444', 'Federico', 'Parisi'), ('+393407890333', 'Marta', 'Ferri'),
('+393408901222', 'Tommaso', 'Longo'), ('+393409012111', 'Beatrice', 'Sanna'),
('+393401019876', 'Paolo', 'Testa'), ('+393402018765', 'Anna', 'Grimaldi');

-- ##########################################################
--      RESERVATION POPULATION
-- ##########################################################
INSERT INTO Reservation (reservation_date, start_time, end_time, n_people, tableNumber, customerID) VALUES
('2025-12-21', '12:00', '14:00', 2, 1, '+393401112233'),
('2025-12-21', '13:00', '15:30', 4, 3, '+393403334455'),
('2025-12-21', '18:00', '19:30', 2, 7, '+393406667788'),
('2025-12-22', '11:30', '13:00', 6, 5, '+393405556677'),
('2025-12-22', '19:00', '21:00', 4, 9, '+393409990011'),
('2025-12-23', '12:00', '14:30', 2, 8, '+393408889900'),
('2025-12-23', '17:00', '19:00', 6, 11, '+393406789444'),
('2025-12-24', '13:00', '15:00', 4, 15, '+393404567666'),
('2025-12-24', '18:00', '20:00', 6, 12, '+393407890333'),
('2025-12-25', '12:00', '13:30', 2, 14, '+393408901222'),
('2025-12-25', '13:30', '15:00', 2, 2, '+393402223344'),
('2025-12-25', '15:00', '16:30', 4, 4, '+393404445566'),
('2025-12-26', '12:00', '14:00', 2, 13, '+393407778899'),
('2025-12-26', '14:30', '16:30', 2, 6, '+393401234999'),
('2025-12-26', '17:00', '19:00', 4, 10, '+393402345888'),
('2025-12-27', '12:00', '13:30', 2, 16, '+393403456777'),
('2025-12-27', '13:30', '15:00', 6, 17, '+393405678555'),
('2025-12-27', '15:30', '17:00', 6, 18, '+393409012111'),
('2025-12-28', '12:00', '14:00', 2, 19, '+393401019876'),
('2025-12-28', '14:30', '16:00', 2, 20, '+393402018765'),
('2025-12-30', '18:00', '20:00', 2, 7, '+393401112233'),
('2025-12-31', '12:00', '14:00', 4, 4, '+393401112233');

-- ##########################################################
--      REVIEW POPULATION
-- ##########################################################
INSERT INTO Review (waiterID, customerID, score, comment) VALUES
('+393401234567', '+393401112233', 5, 'Excellent service, very friendly.'),
('+393409876543', '+393402223344', 4, 'Good service, a bit slow during rush hour.'),
('+393412345678', '+393403334455', 5, 'Amazing experience!'),
('+393418765432', '+393404445566', 3, 'Average service, could be more attentive.'),
('+393421234567', '+393405556677', 4, 'Polite and quick.'),
('+393428765432', '+393406667788', 5, 'Super friendly and helpful.'),
('+393431234567', '+393407778899', 2, 'Forgot our order twice.'),
('+393438765432', '+393408889900', 4, 'Nice and cheerful service.'),
('+393401234567', '+393409990011', 5, 'Perfect service, nothing to improve.'),
('+393409876543', '+393401234999', 3, 'Good but not great.'),
('+393412345678', '+393402345888', 4, 'Smooth and professional.'),
('+393418765432', '+393403456777', 5, 'Very kind and fast.'),
('+393421234567', '+393404567666', 4, 'Attentive and courteous.'),
('+393428765432', '+393405678555', 3, 'Could be better prepared.'),
('+393431234567', '+393406789444', 5, 'Fantastic atmosphere thanks to him!'),
('+393438765432', '+393407890333', 4, 'Good overall experience.'),
('+393401234567', '+393408901222', 5, 'Perfect timing and great attitude.'),
('+393409876543', '+393409012111', 2, 'Not very polite.'),
('+393412345678', '+393401019876', 4, 'Friendly and nice.'),
('+393418765432', '+393402018765', 5, 'One of the best waiters here!');

-- ##########################################################
--      SHIFT POPULATION
-- ##########################################################
INSERT INTO Shift (shift_date, start_time, end_time) VALUES
('2025-12-10', '09:00', '13:00'), ('2025-12-10', '13:00', '17:00'), ('2025-12-10', '17:00', '22:00'),
('2025-12-11', '09:00', '13:00'), ('2025-12-11', '13:00', '17:00'), ('2025-12-11', '17:00', '22:00'),
('2025-12-12', '09:00', '13:00'), ('2025-12-12', '13:00', '17:00'), ('2025-12-12', '17:00', '22:00'),
('2025-12-13', '09:00', '13:00'), ('2025-12-13', '13:00', '17:00'), ('2025-12-13', '17:00', '22:00'),
('2025-12-14', '09:00', '13:00'), ('2025-12-14', '13:00', '17:00'), ('2025-12-14', '17:00', '22:00'),
('2025-12-15', '09:00', '13:00'), ('2025-12-15', '13:00', '17:00'), ('2025-12-15', '17:00', '22:00'),
('2025-12-16', '10:00', '16:00'), ('2025-12-16', '16:00', '22:00');

-- ##########################################################
--      WORK POPULATION
-- ##########################################################
INSERT INTO Work (waiterID, shiftID)
    SELECT w.phone, s.id
    FROM Waiter w
    CROSS JOIN Shift s
    ORDER BY RANDOM()
    LIMIT 50;