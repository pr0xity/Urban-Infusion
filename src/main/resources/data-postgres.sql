
-- Insert data into product_category
insert into "product_category" (id, name, description, created_at, updated_at, deleted_at) values (1, 'Herbal tea', 'odio curabitur convallis duis consequat dui nec nisi volutpat eleifend donec ut dolor morbi vel lectus in', '2022-01-16 10:49:45', '2021-09-26 07:49:49', null);
insert into "product_category" (id, name, description, created_at, updated_at, deleted_at) values (2, 'Black tea', 'orci mauris lacinia sapien quis libero nullam sit amet turpis elementum ligula vehicula consequat morbi', '2021-08-03 00:13:52', '2021-11-04 12:46:39', null);
insert into "product_category" (id, name, description, created_at, updated_at, deleted_at) values (3, 'Green tea', 'lectus vestibulum quam sapien varius ut blandit non interdum in ante vestibulum', '2021-03-24 02:50:44', '2021-04-19 15:26:05', null);
insert into "product_category" (id, name, description, created_at, updated_at, deleted_at) values (4, 'Merchendise', 'pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est et tempus semper est quam pharetra magna ac', '2021-11-28 14:24:12', '2021-05-02 06:18:44', null);

-- Insert data into product_inventory
insert into "product_inventory" (id, quantity, created_at, updated_at, deleted_at) values (1, 13, '2021-05-23 21:39:18', '2021-03-20 11:53:27', null);
insert into "product_inventory" (id, quantity, created_at, updated_at, deleted_at) values (2, 97, '2021-10-20 13:32:24', '2021-06-18 11:40:30', null);
insert into "product_inventory" (id, quantity, created_at, updated_at, deleted_at) values (3, 17, '2021-04-13 06:44:11', '2021-08-28 15:13:07', null);
insert into "product_inventory" (id, quantity, created_at, updated_at, deleted_at) values (4, 16, '2021-04-07 13:02:16', '2021-10-16 02:49:37', null);
insert into "product_inventory" (id, quantity, created_at, updated_at, deleted_at) values (5, 16, '2021-04-07 13:02:16', '2021-10-16 02:49:37', null);

-- Insert data into product
insert into "product" (id, name, description, origin, price, fk_category_id, fk_inventory_id, created_at, updated_at, deleted_at) values (1, 'Heather tea', 'drive killer portals', 'Norwegian mountains', 200, 3, 1, '2021-10-10 08:25:52', '2021-12-23 03:17:08', null);
insert into "product" (id, name, description, origin, price, fk_category_id, fk_inventory_id, created_at, updated_at, deleted_at) values (2, 'Linden blossom tea', 'morph killer e-markets', 'Classic Latvian tea', 200, 2, 2, '2021-03-28 15:00:22', '2021-04-18 08:22:03', null);
insert into "product" (id, name, description, origin, price, fk_category_id, fk_inventory_id, created_at, updated_at, deleted_at) values (3, 'Sencha 50g', 'synergize leading-edge technologies', 'Japanese green tea', 100, 1, 3, '2021-12-19 02:10:45', '2021-05-27 15:12:10', null);
insert into "product" (id, name, description, origin, price, fk_category_id, fk_inventory_id, created_at, updated_at, deleted_at) values (4, 'Sencha 500g', 'aggregate out-of-the-box users', 'Japanese green tea', 800, 1, 4, '2021-07-08 09:41:24', '2022-02-16 16:52:03', null);
insert into "product" (id, name, description, origin, price, fk_category_id, fk_inventory_id, created_at, updated_at, deleted_at) values (5, 'Mug', 'implement real-time methodologies', 'Brazilian clay', 120, 4, 5, '2021-12-10 02:33:30', '2021-03-26 09:33:31', null);

-- Insert data into permission_level
insert into "permission_level" (id, admin_type, permissions, created_at, updated_at) values (1, 'owner', 3, '2021-07-30 19:37:45', '2021-09-29 01:32:00');
insert into "permission_level" (id, admin_type, permissions, created_at, updated_at) values (2, 'admin', 2, '2021-06-16 08:41:39', '2021-09-02 20:33:59');
insert into "permission_level" (id, admin_type, permissions, created_at, updated_at) values (3, 'user', 1, '2021-03-29 18:49:48', '2021-04-25 18:23:26');

-- Insert data into user
INSERT INTO "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) VALUES (1, 'Espen', 'Otlo', 'espenotl@stud.ntnu.no', 'dugjetterdetaldri69', '2017-03-31 9:30:20', '2017-03-31 9:30:20', 3, 'true');
INSERT INTO "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) VALUES (2,'Sakarias', 'Sæterstøl', 'sakkis@stud.ntnu.no', 'hemmelig', '2017-03-31 9:30:20', '2017-03-31 9:30:20', 2, 'true');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (3, 'Aline', 'Durante', 'adurante0@wisc.edu', '0P5I0in2tJ', '2021-08-27 03:58:57', '2021-11-22 09:28:43', 1, 'true');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (4, 'Valery', 'Stening', 'vstening1@goodreads.com', 'yAx5n1', '2021-03-28 22:19:22', '2021-12-24 16:19:07', 1, 'true');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (5, 'Stepha', 'Hyland', 'shyland2@arstechnica.com', 'exFhkcV', '2021-11-14 18:10:49', '2022-02-07 21:36:25', 1, 'true');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (6, 'Lowe', 'Tolson', 'ltolson3@paypal.com', 'DqFbgV56', '2021-09-20 13:23:46', '2021-07-26 18:06:00', 1, 'true');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (7, 'Kenneth', 'Dallimare', 'kdallimare4@hostgator.com', 'mF3AucpXq', '2021-12-22 15:08:03', '2022-01-30 15:50:01', 1, 'true');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (8, 'Boyce', 'Fidgett', 'bfidgett5@seesaa.net', 'IK5Cu1', '2021-04-15 04:52:53', '2021-08-31 02:42:21', 1, 'false');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (9, 'Bennett', 'Choules', 'bchoules6@mapy.cz', '3wL1w9gu', '2021-08-19 09:53:31', '2021-03-07 07:31:21', 1, 'false');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (10, 'Ode', 'Fergie', 'ofergie7@boston.com', 'UJMNcX6', '2021-10-29 13:01:27', '2021-07-25 19:05:35', 1, 'false');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (11, 'Nadya', 'Bransdon', 'nbransdon8@about.com', '6wJE68B5l18', '2021-04-01 03:10:02', '2021-04-30 01:44:45', 1, 'true');
insert into "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) values (12, 'Glenna', 'Baden', 'gbaden9@prlog.org', 'zlnK0CI', '2021-03-11 22:22:39', '2021-07-18 17:11:29', 1, 'true');
INSERT INTO "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) VALUES (13,'Janita', 'Røyseth', 'janita.royseth@stud.ntnu.no', 'temmelighemmelig', '2017-03-31 9:30:20', '2017-03-31 9:30:20', 2, 'true');
INSERT INTO "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) VALUES (14,'Nina', 'Marstrander', 'ninmar@stud.ntnu.no', 'ikkehemmelig', '2017-03-31 9:30:20', '2017-03-31 9:30:20', 2, 'true');
INSERT INTO "users" (id, first_name, last_name, email, password, created_at, updated_at, fk_permission_id, enabled) VALUES (15,'Didrik', 'Eilertsen', 'dideil@stud.ntnu.no', 'ikketemmelighemmelig', '2017-03-31 9:30:20', '2017-03-31 9:30:20', 2, 'true');

-- Insert data into user_payment
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (1, 1, 'Visa', 'Stripe', 40045, '19-09-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (2, 2, 'PayPal', 'PayPal', 99150, '02-12-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (3, 3, 'Stripe', 'Visa', 6447, '11-04-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (4, 4, 'Stripe', 'PayPal', 80852, '01-07-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (5, 5, 'Stripe', 'PayPal', 74562, '22-07-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (6, 6, 'PayPal', 'Visa', 57541, '23-03-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (7, 7, 'Stripe', 'PayPal', 41966, '22-06-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (8, 8, 'Stripe', 'PayPal', 57651, '31-03-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (9, 9, 'Visa', 'Stripe', 67479, '07-11-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (10, 10, 'Stripe', 'PayPal', 53554, '30-07-2024');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (11, 11, 'Stripe', 'PayPal', 12395, '31-01-2025');
insert into "user_payment" (id, fk_user_id, payment_type, provider, account_no, expiry) values (12, 12, 'PayPal', 'Stripe', 2756, '28-10-2024');

-- Insert data into user_address
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (1, 1, '148 Sachtjen Center', null, 'Vishnyeva', '56470-000', 'Belarus', '1538349718');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (2, 2, '03 Prairieview Hill', null, 'Nonoichi', '793-0066', 'Japan', '6521299834');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (3, 3, '6 Arapahoe Avenue', null, 'Itabaiana', '49500-000', 'Brazil', '5147610567');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (4, 4, '4860 Rigney Circle', null, 'Shaoha', '66276', 'China', '5765621435');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (5, 5, '57 Mayer Lane', null, 'Puerto Padre', '431783', 'Cuba', '9494081516');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (6, 6, '6403 Londonderry Street', null, 'Sơn Tây', '32-080', 'Vietnam', '6482510014');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (7, 7, '380 Lindbergh Plaza', null, 'Baoli', '43133', 'China', '9265054423');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (8, 8, '273 Badeau Point', null, 'Vanderbijlpark', '1916', 'South Africa', '4741880543');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (9, 9, '06 Kenwood Drive', null, 'Dowr-e Rabāţ', '32213', 'Afghanistan', '8973271305');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (10, 10, '1475 Old Shore Road', null, 'Palmerston North', '4820', 'New Zealand', '6277787425');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (11, 11, '9 Rieder Trail', null, 'Changliu', '789 01', 'China', '2915684287');
insert into "user_address" (id, fk_user_id, address_line1, address_line2, city, postal_code, country, telephone) values (12, 12, '81 Crescent Oaks Drive', null, 'Sison', '8404', 'Philippines', '3232257095');

-- Insert data into fav_list
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (1, 1, 4, '2022-02-23 12:39:05', '2022-01-29 14:07:12');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (2, 2, 4, '2021-11-13 01:34:02', '2021-08-07 18:56:20');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (3, 3, 2, '2021-07-04 14:26:03', '2021-04-13 11:48:41');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (4, 4, 1, '2022-01-25 18:04:58', '2021-06-10 00:04:00');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (5, 5, 2, '2021-04-15 23:20:58', '2021-11-28 00:54:30');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (6, 6, 3, '2022-01-05 03:54:16', '2022-02-06 21:11:03');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (7, 4, 4, '2021-07-07 04:55:47', '2021-05-29 05:04:43');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (8, 1, 5, '2021-08-23 19:48:33', '2021-08-14 13:23:38');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (9, 12, 3, '2021-09-20 16:37:57', '2021-05-02 17:39:58');
insert into "fav_list" (id, fk_user_id, fk_product_id, created_at, updated_at) values (10, 7, 5, '2021-11-15 13:34:14', '2021-12-21 15:16:10');

-- Insert data into shopping_session
insert into "shopping_session" (id, fk_user_id, total, created_at, updated_at) values (1, 1, 23, '2021-08-15 01:52:39', '2021-11-13 14:46:00');
insert into "shopping_session" (id, fk_user_id, total, created_at, updated_at) values (2, 2, 37, '2021-05-24 10:17:06', '2021-03-22 07:52:39');
insert into "shopping_session" (id, fk_user_id, total, created_at, updated_at) values (3, 3, 99, '2021-09-01 02:42:21', '2021-06-26 14:36:46');
insert into "shopping_session" (id, fk_user_id, total, created_at, updated_at) values (4, 4, 10, '2022-02-19 04:03:24', '2021-05-19 11:44:58');
insert into "shopping_session" (id, fk_user_id, total, created_at, updated_at) values (5, 5, 54, '2021-03-28 11:43:26', '2021-10-25 00:08:31');

-- Insert data into cart_item
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (1, 4, 1, 6, '2021-04-08 13:28:19', '2021-09-23 23:42:42');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (2, 1, 4, 4, '2021-04-05 01:59:31', '2021-11-09 22:55:22');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (3, 3, 2, 1, '2021-10-25 08:53:54', '2021-03-30 02:29:56');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (4, 5, 4, 4, '2021-10-02 03:37:31', '2022-01-19 09:47:03');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (5, 3, 3, 5, '2021-03-10 06:00:32', '2022-01-03 15:15:38');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (6, 5, 5, 1, '2021-09-09 01:27:38', '2022-01-18 20:27:08');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (7, 3, 3, 7, '2021-05-25 08:07:58', '2021-12-17 04:37:54');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (8, 4, 4, 5, '2021-03-17 21:18:21', '2021-07-16 18:43:27');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (9, 1, 2, 4, '2021-03-02 08:22:28', '2021-10-18 19:19:05');
insert into "cart_item" (id, fk_session_id, fk_product_id, quantity, created_at, updated_at) values (10, 5, 1, 2, '2021-02-28 21:33:31', '2021-10-19 23:16:01');

-- Insert data into order_details
insert into "order_details" (id, fk_user_id, total, quantity, status, created_at, updated_at) values (1, 4, 24.23, 17, 'Canceled', '2021-05-27 19:11:59', '2021-05-19 09:48:25');
insert into "order_details" (id, fk_user_id, total, quantity, status, created_at, updated_at) values (2, 11, 48.48, 92, 'Shipped', '2022-01-27 02:27:28', '2021-12-27 08:47:00');
insert into "order_details" (id, fk_user_id, total, quantity, status, created_at, updated_at) values (3, 6, 18.23, 42, 'Shipped', '2022-02-20 22:42:06', '2022-02-02 09:28:22');
insert into "order_details" (id, fk_user_id, total, quantity, status, created_at, updated_at) values (4, 3, 65.53, 89, 'New', '2021-10-12 17:23:46', '2021-11-06 01:43:17');

-- Insert data into order_item
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (1, 4, 5, 1, '2022-01-21 18:26:33', '2021-11-29 16:50:34');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (2, 3, 1, 5, '2021-03-29 00:36:25', '2021-11-29 06:11:13');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (3, 3, 1, 4, '2021-08-19 23:59:24', '2021-08-29 23:35:56');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (4, 4, 4, 6, '2021-12-24 13:34:38', '2022-02-05 01:12:38');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (5, 3, 2, 5, '2022-02-08 06:44:06', '2022-01-20 22:16:22');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (6, 4, 5, 2, '2021-10-28 04:01:56', '2021-09-22 11:06:59');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (7, 2, 4, 6, '2021-07-15 12:26:58', '2021-05-10 09:30:28');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (8, 2, 1, 4, '2021-08-18 19:57:16', '2021-08-08 23:32:55');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (9, 3, 2, 2, '2021-06-02 19:19:16', '2021-04-07 13:23:46');
insert into "order_item" (id, fk_order_id, fk_product_id, quantity, created_at, updated_at) values (10, 4, 4, 5, '2021-05-27 16:10:46', '2021-11-11 02:31:20');

-- Insert data into payment_details
insert into "payment_details" (id, fk_order_id, amount, provider, status, created_at, updated_at) values (1, 1, 25, 'Visa', 'On hold', '2021-10-17 22:53:50', '2022-01-16 16:54:19');
insert into "payment_details" (id, fk_order_id, amount, provider, status, created_at, updated_at) values (2, 2, 63, 'Stripe', 'OK', '2021-10-06 00:12:11', '2021-11-13 22:20:26');
insert into "payment_details" (id, fk_order_id, amount, provider, status, created_at, updated_at) values (3, 3, 32, 'Visa', 'OK', '2021-07-16 08:48:32', '2021-09-04 06:27:51');
insert into "payment_details" (id, fk_order_id, amount, provider, status, created_at, updated_at) values (4, 4, 45, 'Visa', 'OK', '2021-05-25 11:24:38', '2021-12-29 04:16:33');

-- Insert data into product_ratings
insert into "product_rating" (id, fk_user_id, fk_product_id, rating, comment, created_at, updated_at) values (1, 1, 1, 5, 'Love it!', '2021-10-17 22:53:50', '2022-01-16 16:54:19');
insert into "product_rating" (id, fk_user_id, fk_product_id, rating, comment, created_at, updated_at) values (2, 2, 2, 2, 'It is okay.', '2021-07-16 08:48:32', '2021-09-04 06:27:51');
insert into "product_rating" (id, fk_user_id, fk_product_id, rating, comment, created_at, updated_at) values (3, 1, 3, 3, 'Was hoping for something better.', '2022-02-20 22:42:06', '2022-02-02 09:28:22');
insert into "product_rating" (id, fk_user_id, fk_product_id, rating, comment, created_at, updated_at) values (4, 3, 4, 5, 'Lovely!.', '2022-02-20 22:42:06', '2022-02-02 09:28:22');
insert into "product_rating" (id, fk_user_id, fk_product_id, rating, comment, created_at, updated_at) values (5, 3, 5, 1, 'Not the best..', '2022-02-20 22:42:06', '2022-02-02 09:28:22');
insert into "product_rating" (id, fk_user_id, fk_product_id, rating, comment, created_at, updated_at) values (6, 2, 5, 5, 'Lovely!.', '2022-02-20 22:42:06', '2022-02-02 09:28:22');

