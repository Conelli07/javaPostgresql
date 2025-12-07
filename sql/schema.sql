CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150),
    price NUMERIC,
    creation_datetime TIMESTAMP
);

CREATE TABLE product_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150),
    product_id INT REFERENCES product(id)
);
