CREATE TABLE IF NOT EXISTS "product_category" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "product_inventory" (
    id SERIAL PRIMARY KEY,
    quantity INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "product" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    origin VARCHAR(255),
    price DOUBLE PRECISION NOT NULL,
    fk_category_id INTEGER REFERENCES "product_category" (id) NOT NULL,
    fk_inventory_id INTEGER REFERENCES "product_inventory" (id) UNIQUE NOT NULL,
    average_rating DOUBLE PRECISION,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "permission_level" (
    id SERIAL PRIMARY KEY,
    admin_type VARCHAR(255) UNIQUE NOT NULL,
    permissions INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP  NOT NULL,
    updated_at TIMESTAMP,
    fk_permission_id INTEGER REFERENCES "permission_level" (id) NOT NULL,
    enabled BOOLEAN
);

CREATE TABLE IF NOT EXISTS "user_payment" (
    id SERIAL PRIMARY KEY,
    fk_user_id INTEGER REFERENCES "user" (id) UNIQUE NOT NULL,
    payment_type VARCHAR(255) NOT NULL,
    provider VARCHAR(255) NOT NULL,
    account_no VARCHAR(255) NOT NULL,
    expiry VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "user_address"(
    id SERIAL PRIMARY KEY,
    fk_user_id INTEGER REFERENCES "user" (id) UNIQUE NOT NULL,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    telephone VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "fav_list"(
    id SERIAL PRIMARY KEY,
    fk_user_id INTEGER REFERENCES "user" (id) NOT NULL,
    fk_product_id INTEGER REFERENCES "product" (id) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "shopping_session"(
    id SERIAL PRIMARY KEY,
    fk_user_id INTEGER REFERENCES "user" (id) UNIQUE NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "cart_item"(
    id SERIAL PRIMARY KEY,
    fk_session_id INTEGER REFERENCES "shopping_session" (id) NOT NULL,
    fk_product_id INTEGER REFERENCES "product" (id) NOT NULL,
    quantity INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "order_details"(
    id SERIAL PRIMARY KEY,
    fk_user_id INTEGER REFERENCES "user" (id) NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    quantity INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "order_item"(
    id SERIAL PRIMARY KEY,
    fk_order_id INTEGER REFERENCES "order_details" (id) NOT NULL,
    fk_product_id INTEGER REFERENCES "product" (id) NOT NULL,
    quantity INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "payment_details"(
    id SERIAL PRIMARY KEY,
    fk_order_id INTEGER REFERENCES "order_details" (id) UNIQUE NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    provider VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "product_rating" (
    id SERIAL PRIMARY KEY,
    fk_user_id INTEGER REFERENCES "user" (id) NOT NULL,
    fk_product_id INTEGER REFERENCES "product" (id) NOT NULL,
    rating INTEGER NOT NULL,
    CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE OR REPLACE FUNCTION public.set_average_rating()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
BEGIN
    UPDATE product
        SET average_rating = (
            SELECT AVG(rating)
            FROM product_rating
            WHERE product.id = NEW.fk_product_id);
    RETURN NEW;

END;
$function$
;

CREATE OR REPLACE TRIGGER update_average_ratings AFTER
INSERT OR UPDATE ON public.product_rating
FOR EACH ROW EXECUTE FUNCTION set_average_rating();
