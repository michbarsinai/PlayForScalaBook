# --- !Ups
CREATE TABLE products (
  id serial PRIMARY KEY,
  ean bigint,
  name varchar,
  description varchar
);

CREATE TABLE warehouses (
  id serial PRIMARY KEY,
  name varchar
);

CREATE TABLE stock_items (
  id serial PRIMARY KEY,
  product_id bigint references products(id),
  warehouse_id bigint references warehouses(id),
  quantity bigint
);


# --- !Downs
DROP TABLE IF EXISTS stock_items;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS warehouses;
