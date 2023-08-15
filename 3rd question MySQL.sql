use firstdb;
CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    added_by VARCHAR(100)
);

select * from product;

insert into product (name, category, added_by)
 values( 'iphone', 'electronics', 'Manju'),
 ('eggs', ' Grocery', ' sri'),
 ('Balloons','Decor', 'MS') ;
 select * from product;
 
CREATE TABLE product_price (
    price_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    price DECIMAL(10, 2) NOT NULL,
    discount_percent DECIMAL(5, 2) DEFAULT 0,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

select * from product_price;

INSERT INTO product_price (product_id, price, discount_percent, updated_by)
VALUES
    (1, 599.99, 10.00, 'Manju'),
    (2, 39.99, 5.00, 'Sri'),
    (3, 249.00, 0.00, 'ms'),
    (3, 599.99, 10.00, 'Manju');
  
select * from product_price;
INSERT INTO product_price (product_id, price, discount_percent, updated_by)
VALUES
    (3, 599.99, 10.00, 'Manju');
    select * from product_price;
    
    CREATE TABLE product_price_change_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    old_price DECIMAL(10, 2),
    new_price DECIMAL(10, 2),
    operation_type ENUM('insert', 'update', 'delete'),
    operation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operation_by VARCHAR(100),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

select * from product_price_change_log;

INSERT INTO product_price_change_log (product_id, old_price, new_price, operation_type, operation_by)
VALUES
    (1, 599.99, 549.99, 'update', 'Manju'),
    (2, 39.99, 37.99, 'update', 'Ms'),
    (3, 249.00, 269.00, 'update', 'sri');
    
    select * from product;
    select * from product_price;
    
    
select * from product_price_change_log;

SELECT
    p.name,
    p.category,
    pp.price,
    pp.updated_by,
    pp.updated_time
FROM product p
JOIN product_price pp ON p.product_id = pp.product_id;

