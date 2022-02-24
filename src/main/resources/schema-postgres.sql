CREATE TABLE IF NOT EXISTS product_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_inventory (
    id SERIAL PRIMARY KEY,
    quantity INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    origin VARCHAR(255),
    price DOUBLE PRECISION,
    fk_category_id INTEGER REFERENCES product_category (id),
    fk_inventory_id INTEGER REFERENCES product_inventory (id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS permission_level (
    id SERIAL PRIMARY KEY,
    admin_type VARCHAR(255),
    permissions INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    fk_permission_id INTEGER REFERENCES permission_level (id),
    enabled BOOLEAN
    );