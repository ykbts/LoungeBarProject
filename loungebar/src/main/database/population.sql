-- ##########################################################
--      SUPPLY POPULATION
-- #########################################################
INSERT INTO lounge.Supply (name, units_available, type) VALUES
('Coca Cola', 100, 'DISCRETE'),
('Coca Cola Zero', 100, 'DISCRETE'),
('Tonic Water', 90, 'DISCRETE'),
('Lemon Soda', 90, 'DISCRETE'),
('Soda Water', 80, 'DISCRETE'),
('Sparkling Water', 60, 'DISCRETE'),
('Still Water', 70, 'DISCRETE'),
('Lemon Juice Bottle', 40, 'BOTTLE_1L'),
('Strawberry Juice Bottle', 35, 'BOTTLE_1L'),
('Orange Juice Bottle', 55, 'BOTTLE_1L'),
('Pineapple Juice Bottle', 45, 'BOTTLE_1L'),
('Gin Mare', 100, 'BOTTLE_0_7L'),
('Gin Malfy', 50, 'BOTTLE_0_7L'),
('Gin Hendricks', 50, 'BOTTLE_0_7L'),
('Gin Bombay', 50, 'BOTTLE_0_7L'),
('Vodka Beluga', 50, 'BOTTLE_0_7L'),
('Vodka Belvedere', 50, 'BOTTLE_0_7L'),
('Vodka Grey Goose', 50, 'BOTTLE_0_7L'),
('Vodka Keglevich', 50, 'BOTTLE_0_7L'),
('Rum Don Papa', 50, 'BOTTLE_0_7L'),
('Rum Ron Zacapa', 50, 'BOTTLE_0_7L'),
('Rum Diplomatico', 50, 'BOTTLE_0_7L'),
('Tequila', 50, 'BOTTLE_0_7L'),
('Amaro del Capo', 50, 'BOTTLE_0_7L'),
('Amaro Jefferson', 50, 'BOTTLE_0_7L'),
('Amaro Jagermeister', 50, 'BOTTLE_0_7L'),
('Whiskey Oban', 50, 'BOTTLE_0_7L'),
('Whiskey Jack Daniels', 50, 'BOTTLE_0_7L'),
('Whiskey Jim Beam', 50, 'BOTTLE_0_7L'),
('Red Wine', 100, 'BOTTLE_0_7L'),
('White Wine', 100, 'BOTTLE_0_7L'),
('Prosecco', 100, 'BOTTLE_0_7L'),
('Beer Lager Draft', 50, 'DISCRETE'),
('Beer IPA Draft', 50, 'DISCRETE'),
('Coffee Classic', 200, 'DISCRETE'),
('Coffee Decaffeinated', 200, 'DISCRETE'),
('Sugar Sachet', 300, 'DISCRETE'),
('Straws', 500, 'DISCRETE'),
('Napkins', 400, 'DISCRETE'),
('Ice Cubes Bag', 20, 'DISCRETE');
-- ##########################################################
--      DRINK POPULATION
-- ##########################################################
INSERT INTO lounge.Drink (name, price, is_alcoholic) VALUES
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
INSERT INTO lounge.Composition (drink_name, supply_name, amount) VALUES
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
('Lager Beer Draft', 'Beer Lager Draft', 1), ('IPA Beer Draft', 'Beer IPA Draft', 1), ('Orange Juice', 'Orange Juice Bottle', 1), ('Orange Juice', 'Straws', 1),
('Old Fashioned', 'Whiskey Jack Daniels', 2), ('Old Fashioned', 'Ice Cubes Bag', 1),
('Amaretto Sour', 'Amaro Jefferson', 2), ('Amaretto Sour', 'Ice Cubes Bag', 1),
('Negroni', 'Gin Mare', 2), ('Negroni', 'Ice Cubes Bag', 1),
('Americano', 'Amaro del Capo', 2), ('Americano', 'Ice Cubes Bag', 1),
('Paloma', 'Tequila', 2), ('Paloma', 'Ice Cubes Bag', 1),
('Moscow Mule', 'Vodka Belvedere', 2), ('Moscow Mule', 'Ice Cubes Bag', 1),
('Gin Fizz', 'Gin Hendricks', 2), ('Gin Fizz', 'Ice Cubes Bag', 1),
('Long Island Iced Tea', 'Vodka Belvedere', 1), ('Long Island Iced Tea', 'Ice Cubes Bag', 1),
('Sex on the Beach', 'Vodka Grey Goose', 2), ('Sex on the Beach', 'Ice Cubes Bag', 1),
('Tequila Sunrise', 'Tequila', 2), ('Tequila Sunrise', 'Ice Cubes Bag', 1),
('Caipirinha', 'Tequila', 2), ('Caipirinha', 'Ice Cubes Bag', 1);;

-- ##########################################################
--      TABLES POPULATION
-- ##########################################################
INSERT INTO lounge.Tables (number, capacity, is_available) VALUES
(1, 2, TRUE), (2, 2, FALSE), (3, 4, TRUE), (4, 4, FALSE), (5, 6, TRUE), (6, 6, FALSE),
(7, 2, TRUE), (8, 2, TRUE), (9, 4, TRUE), (10, 4, FALSE), (11, 6, TRUE), (12, 6, TRUE),
(13, 2, FALSE), (14, 2, TRUE), (15, 4, TRUE), (16, 4, TRUE), (17, 6, FALSE), (18, 6, TRUE),
(19, 8, TRUE), (20, 8, TRUE);

-- ##########################################################
--      USER POPULATION
-- ##########################################################
INSERT INTO lounge."user" (username, password, name, surname, phone, role) VALUES
-- Waiters
('ecaterina_i', 'hash1', 'Ecaterina', 'Iacobet', '+393401234567', 'WAITER'),
('claudia_f', 'hash2', 'Claudia', 'Foffano', '+393409876543', 'WAITER'),
('fulvio_b', 'hash3', 'Fulvio', 'Bruzzese', '+393412345678', 'WAITER'),
('irene_b', 'hash4', 'Irene', 'Bernad', '+393418765432', 'WAITER'),
-- Customers
('nicola_ferro', 'hash5', 'Nicola', 'Ferro', '+393401112233', 'CUSTOMER'),
('luca_b', 'hash6', 'Luca', 'Bianchi', '+393402223344', 'CUSTOMER'),
('giulia_f', 'hash7', 'Giulia', 'Ferrari', '+393403334455', 'CUSTOMER'),
('marta_ferri', 'hash8', 'Marta', 'Ferri', '+393407890333', 'CUSTOMER'),
('admin_user', 'adminhash', 'Boss', 'Admin', '+390000000000', 'ADMIN');

-- ##########################################################
--      ORDERS POPULATION
-- ##########################################################
INSERT INTO lounge.Orders (date_time, status, pay_method, user_waiterID, tableID) VALUES
('2026-04-12 12:00:00', 'PAID', 'CASH', 'ecaterina_i', 1),
('2026-04-12 12:15:00', 'PAID', 'DEBIT_CARD', 'claudia_f', 2),
('2026-04-12 12:30:00', 'PENDING', NULL, 'fulvio_b', 3),
('2026-04-12 13:00:00', 'PAID', 'BANCOMAT', 'ecaterina_i', 5);

-- ##########################################################
--      ORDERITEM POPULATION
-- ##########################################################
INSERT INTO lounge.OrderItem (drink_name, orderID, quantity) VALUES
('Gin Tonic', 1, 2), ('Acqua Naturale', 1, 2),
('Coca Cola Can', 2, 2), ('Mojito', 2, 5),
('Negroni', 3, 1), ('IPA Beer Draft', 3, 2);

-- ##########################################################
--      RESERVATION POPULATION
-- ##########################################################
INSERT INTO lounge.Reservation (start_time, end_time, n_people, tableNumber, user_customerID) VALUES
('2026-04-21 12:00:00', '2026-04-21 14:00:00', 2, 1, 'nicola_ferro'),
('2026-04-21 18:00:00', '2026-04-21 19:30:00', 2, 7, 'giulia_f'),
('2026-04-22 19:00:00', '2026-04-22 21:00:00', 4, 9, 'luca_b');

-- ##########################################################
--      REVIEW POPULATION
-- ##########################################################
INSERT INTO lounge.Review (user_waiterID, user_customerID, score, comment) VALUES
('ecaterina_i', 'nicola_ferro', 5, 'Excellent service, very friendly.'),
('claudia_f', 'luca_b', 4, 'Good service, a bit slow during rush hour.');

-- ##########################################################
--      SHIFT POPULATION
-- ##########################################################
INSERT INTO lounge.Shift (start_time, end_time) VALUES
('2026-04-13 09:00:00', '2026-04-13 13:00:00'),
('2026-04-13 13:00:00', '2026-04-13 17:00:00'),
('2026-04-13 17:00:00', '2026-04-13 22:00:00');

-- ##########################################################
--      WORK POPULATION
-- ##########################################################
INSERT INTO lounge.Work (user_waiterID, shiftID) VALUES
('ecaterina_i', 1),
('claudia_f', 2),
('fulvio_b', 3);