
-- Insert data into product_category
insert into "product_category" (name, description,updated_at) values ('Herbal tea', 'odio curabitur convallis duis consequat dui nec nisi volutpat eleifend donec ut dolor morbi vel lectus in', '2021-09-26 07:49:49');
insert into "product_category" (name, description,updated_at) values ('Black tea', 'orci mauris lacinia sapien quis libero nullam sit amet turpis elementum ligula vehicula consequat morbi', '2021-11-04 12:46:39');
insert into "product_category" (name, description,updated_at) values ('Green tea', 'lectus vestibulum quam sapien varius ut blandit non interdum in ante vestibulum', '2021-04-19 15:26:05');
insert into "product_category" (name, description,updated_at) values ('Merchendise', 'pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est et tempus semper est quam pharetra magna ac', '2021-05-02 06:18:44');

-- Insert data into product_inventory
insert into "product_inventory" (quantity, updated_at) values (13, '2021-03-20 11:53:27');
insert into "product_inventory" (quantity, updated_at) values (97, '2021-06-18 11:40:30');
insert into "product_inventory" (quantity, updated_at) values (17, '2021-08-28 15:13:07');
insert into "product_inventory" (quantity, updated_at) values (16, '2021-10-16 02:49:37');
insert into "product_inventory" (quantity, updated_at) values (16, '2021-10-16 02:49:37');

-- Insert data into product
insert into "product" (name, description, origin, price, fk_category_id, fk_inventory_id, updated_at) values ('Heather tea', 'drive killer portals', 'Norwegian mountains', 200, 3, 1, '2021-10-10 08:25:52');
insert into "product" (name, description, origin, price, fk_category_id, fk_inventory_id, updated_at) values ('Linden blossom tea', 'morph killer e-markets', 'Classic Latvian tea', 200, 2, 2, '2021-03-28 15:00:22');
insert into "product" (name, description, origin, price, fk_category_id, fk_inventory_id, updated_at) values ('Sencha 50g', 'synergize leading-edge technologies', 'Japanese green tea', 100, 1, 3, '2021-12-19 02:10:45');
insert into "product" (name, description, origin, price, fk_category_id, fk_inventory_id, updated_at) values ('Sencha 500g', 'aggregate out-of-the-box users', 'Japanese green tea', 800, 1, 4, '2021-07-08 09:41:24');
insert into "product" (name, description, origin, price, fk_category_id, fk_inventory_id, updated_at) values ('Mug', 'implement real-time methodologies', 'Brazilian clay', 120, 4, 5, '2021-12-10 02:33:30');

-- Insert data into permission_level
insert into "permission_level" (admin_type, permissions, updated_at) values ('user', 1,  '2021-04-25 18:23:26');
insert into "permission_level" (admin_type, permissions, updated_at) values ('admin', 2, '2021-09-02 20:33:59');
insert into "permission_level" (admin_type, permissions, updated_at) values ('owner', 3, '2021-09-29 01:32:00');

-- Insert data into user
INSERT INTO "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Espen', 'Otlo', 'espenotl@stud.ntnu.no', 'dugjetterdetaldri69', '2017-03-31 9:30:20', 3, 'true');
INSERT INTO "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Sakarias', 'Sæterstøl', 'sakkis@stud.ntnu.no', 'hemmelig', '2017-03-31 9:30:20', 2, 'true');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Aline', 'Durante', 'adurante0@wisc.edu', '0P5I0in2tJ', '2021-11-22 09:28:43', 1, 'true');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Valery', 'Stening', 'vstening1@goodreads.com', 'yAx5n1', '2021-12-24 16:19:07', 1, 'true');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Stepha', 'Hyland', 'shyland2@arstechnica.com', 'exFhkcV', '2022-02-07 21:36:25', 1, 'true');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Lowe', 'Tolson', 'ltolson3@paypal.com', 'DqFbgV56', '2021-07-26 18:06:00', 1, 'true');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Kenneth', 'Dallimare', 'kdallimare4@hostgator.com', 'mF3AucpXq', '2022-01-30 15:50:01', 1, 'true');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Boyce', 'Fidgett', 'bfidgett5@seesaa.net', 'IK5Cu1', '2021-08-31 02:42:21', 1, 'false');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Bennett', 'Choules', 'bchoules6@mapy.cz', '3wL1w9gu', '2021-03-07 07:31:21', 1, 'false');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Ode', 'Fergie', 'ofergie7@boston.com', 'UJMNcX6', '2021-07-25 19:05:35', 1, 'false');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Nadya', 'Bransdon', 'nbransdon8@about.com', '6wJE68B5l18', '2021-04-30 01:44:45', 1, 'true');
insert into "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Glenna', 'Baden', 'gbaden9@prlog.org', 'zlnK0CI', '2021-07-18 17:11:29', 1, 'true');
INSERT INTO "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Janita', 'Røyseth', 'janita.royseth@stud.ntnu.no', 'temmelighemmelig',  '2017-03-31 9:30:20', 2, 'true');
INSERT INTO "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Nina', 'Marstrander', 'ninmar@stud.ntnu.no', 'ikkehemmelig','2017-03-31 9:30:20', 2, 'true');
INSERT INTO "users" (first_name, last_name, email, password, updated_at, fk_permission_id, enabled) VALUES ('Didrik', 'Eilertsen', 'dideil@stud.ntnu.no', 'ikketemmelighemmelig','2017-03-31 9:30:20', 2, 'true');

-- Insert data into user_payment
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (1, 'Visa', 'Stripe', 40045, '19-09-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (2, 'PayPal', 'PayPal', 99150, '02-12-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (3, 'Stripe', 'Visa', 6447, '11-04-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (4, 'Stripe', 'PayPal', 80852, '01-07-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (5, 'Stripe', 'PayPal', 74562, '22-07-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (6, 'PayPal', 'Visa', 57541, '23-03-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (7, 'Stripe', 'PayPal', 41966, '22-06-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (8, 'Stripe', 'PayPal', 57651, '31-03-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (9, 'Visa', 'Stripe', 67479, '07-11-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (10, 'Stripe', 'PayPal', 53554, '30-07-2024');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (11, 'Stripe', 'PayPal', 12395, '31-01-2025');
insert into "user_payment" (fk_user_id, payment_type, provider, account_no, expiry) values (12, 'PayPal', 'Stripe', 2756, '28-10-2024');

-- Insert data into user_address
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (1, '148 Sachtjen Center', null, 'Vishnyeva', '56470-000', 'Belarus', '1538349718');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (2, '03 Prairieview Hill', null, 'Nonoichi', '793-0066', 'Japan', '6521299834');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (3, '6 Arapahoe Avenue', null, 'Itabaiana', '49500-000', 'Brazil', '5147610567');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (14, '4860 Rigney Circle', null, 'Shaoha', '66276', 'China', '5765621435');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (5, '57 Mayer Lane', null, 'Puerto Padre', '431783', 'Cuba', '9494081516');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (6, '6403 Londonderry Street', null, 'Sơn Tây', '32-080', 'Vietnam', '6482510014');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (7, '380 Lindbergh Plaza', null, 'Baoli', '43133', 'China', '9265054423');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (8, '273 Badeau Point', null, 'Vanderbijlpark', '1916', 'South Africa', '4741880543');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (9, '06 Kenwood Drive', null, 'Dowr-e Rabāţ', '32213', 'Afghanistan', '8973271305');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (10, '1475 Old Shore Road', null, 'Palmerston North', '4820', 'New Zealand', '6277787425');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (11, '9 Rieder Trail', null, 'Changliu', '789 01', 'China', '2915684287');
insert into "user_address" (fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (12, '81 Crescent Oaks Drive', null, 'Sison', '8404', 'Philippines', '3232257095');

-- Insert data into fav_list
insert into "fav_list" (fk_user_id, fk_product_id) values (1, 4);
insert into "fav_list" (fk_user_id, fk_product_id) values (2, 4);
insert into "fav_list" (fk_user_id, fk_product_id) values (3, 2);
insert into "fav_list" (fk_user_id, fk_product_id) values (4, 1);
insert into "fav_list" (fk_user_id, fk_product_id) values (5, 2);
insert into "fav_list" (fk_user_id, fk_product_id) values (6, 3);
insert into "fav_list" (fk_user_id, fk_product_id) values (4, 4);
insert into "fav_list" (fk_user_id, fk_product_id) values (1, 5);
insert into "fav_list" (fk_user_id, fk_product_id) values (12, 3);
insert into "fav_list" (fk_user_id, fk_product_id) values (7, 5);

-- Insert data into shopping_session
insert into "shopping_session" (fk_user_id, total, updated_at) values (1, 23, '2021-11-13 14:46:00');
insert into "shopping_session" (fk_user_id, total, updated_at) values (2, 37, '2021-03-22 07:52:39');
insert into "shopping_session" (fk_user_id, total, updated_at) values (3, 99, '2021-06-26 14:36:46');
insert into "shopping_session" (fk_user_id, total, updated_at) values (4, 10, '2021-05-19 11:44:58');
insert into "shopping_session" (fk_user_id, total, updated_at) values (5, 54, '2021-10-25 00:08:31');

-- Insert data into cart_item
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (4, 1, 6, '2021-09-23 23:42:42');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (1, 4, 4, '2021-11-09 22:55:22');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (3, 2, 1, '2021-03-30 02:29:56');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (5, 4, 4, '2022-01-19 09:47:03');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (3, 3, 5, '2022-01-03 15:15:38');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (5, 5, 1, '2022-01-18 20:27:08');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (3, 3, 7, '2021-12-17 04:37:54');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (4, 4, 5, '2021-07-16 18:43:27');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (1, 2, 4, '2021-10-18 19:19:05');
insert into "cart_item" (fk_session_id, fk_product_id, quantity, updated_at) values (5, 1, 2, '2021-10-19 23:16:01');

-- Insert data into order_details
insert into "order_details" (fk_user_id, total, quantity, status, updated_at) values (4, 24.23, 17, 'Canceled', '2021-05-19 09:48:25');
insert into "order_details" (fk_user_id, total, quantity, status, updated_at) values (11, 48.48, 92, 'Shipped', '2021-12-27 08:47:00');
insert into "order_details" (fk_user_id, total, quantity, status, updated_at) values (6, 18.23, 42, 'Shipped', '2022-02-02 09:28:22');
insert into "order_details" (fk_user_id, total, quantity, status, updated_at) values (3, 65.53, 89, 'New', '2021-11-06 01:43:17');

-- Insert data into order_item
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (1, 5, 1, '2021-11-29 16:50:34');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (1, 1, 5, '2021-11-29 06:11:13');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (1, 1, 4, '2021-08-29 23:35:56');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (3, 4, 6, '2022-02-05 01:12:38');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (3, 2, 5, '2022-01-20 22:16:22');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (2, 5, 2, '2021-09-22 11:06:59');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (2, 4, 6, '2021-05-10 09:30:28');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (2, 1, 4, '2021-08-08 23:32:55');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (2, 2, 2, '2021-04-07 13:23:46');
insert into "order_item" (fk_order_id, fk_product_id, quantity, updated_at) values (2, 4, 5, '2021-11-11 02:31:20');

-- Insert data into payment_details
insert into "payment_details" (fk_order_id, amount, provider, status, updated_at) values (1, 25, 'Visa', 'On hold', '2022-01-16 16:54:19');
insert into "payment_details" (fk_order_id, amount, provider, status, updated_at) values (2, 63, 'Stripe', 'OK', '2021-11-13 22:20:26');
insert into "payment_details" (fk_order_id, amount, provider, status, updated_at) values (3, 32, 'Visa', 'OK', '2021-09-04 06:27:51');
insert into "payment_details" (fk_order_id, amount, provider, status, updated_at) values (4, 45, 'Visa', 'OK', '2021-12-29 04:16:33');

-- Insert data into product_ratings
insert into "product_rating" (fk_user_id, fk_product_id, rating, comment,updated_at) values (1, 1, 5, 'Love it!', '2022-01-16 16:54:19');
insert into "product_rating" (fk_user_id, fk_product_id, rating, comment,updated_at) values (2, 2, 2, 'It is okay.', '2021-09-04 06:27:51');
insert into "product_rating" (fk_user_id, fk_product_id, rating, comment,updated_at) values (1, 3, 3, 'Was hoping for something better.', '2022-04-02 09:28:22');
insert into "product_rating" (fk_user_id, fk_product_id, rating, comment,updated_at) values (3, 4, 5, 'Lovely!.', '2022-04-02 09:28:22');
insert into "product_rating" (fk_user_id, fk_product_id, rating, comment,updated_at) values (3, 5, 1, 'Not the best..', '2022-05-02 09:28:22');
insert into "product_rating" (fk_user_id, fk_product_id, rating, comment,updated_at) values (2, 5, 5, 'Lovely!.', '2022-05-02 09:28:22');

