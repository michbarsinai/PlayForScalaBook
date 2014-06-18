# --- !Ups
INSERT INTO products (ean, name, description) VALUES (1000000000001, 'Pay-per-Clip', 'Novel clipping key');
INSERT INTO products (ean, name, description) VALUES (1000000000002, 'Pepper Clip', 'Hot clip for pita breads');
INSERT INTO products (ean, name, description) VALUES (1000000000003, 'Normal Clip', 'A clip that is pretty normal');
INSERT INTO products (ean, name, description) VALUES (1000000000004, 'Big Clip', 'A clip that is big');

INSERT INTO warehouses (name) VALUES ('Boston');
INSERT INTO warehouses (name) VALUES ('New-York');
INSERT INTO warehouses (name) VALUES ('LA');
INSERT INTO warehouses (name) VALUES ('Tel-Aviv');

INSERT INTO stock_items (product_id, warehouse_id, quantity)
  VALUES ( (select id from products where ean=1000000000001), (select id from warehouses where name='Boston'), 1000 );
INSERT INTO stock_items (product_id, warehouse_id, quantity)
  VALUES ( (select id from products where ean=1000000000001), (select id from warehouses where name='New-York'), 1050 );
INSERT INTO stock_items (product_id, warehouse_id, quantity)
  VALUES ( (select id from products where ean=1000000000001), (select id from warehouses where name='Tel-Aviv'), 1200 );
INSERT INTO stock_items (product_id, warehouse_id, quantity)
  VALUES ( (select id from products where ean=1000000000002), (select id from warehouses where name='Boston'), 1002 );
INSERT INTO stock_items (product_id, warehouse_id, quantity)
  VALUES ( (select id from products where ean=1000000000002), (select id from warehouses where name='Boston'), 1003 );
INSERT INTO stock_items (product_id, warehouse_id, quantity)
  VALUES ( (select id from products where ean=1000000000003), (select id from warehouses where name='Boston'), 1004 );


# --- !Downs
DELETE FROM stock_items;
DELETE FROM warehouses;
DELETE FROM products;