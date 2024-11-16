CREATE TABLE ecommerce_category (
    id          INT,
    name        VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce_product (
    id            INT NOT NULL AUTO_INCREMENT,
    sku           VARCHAR(8),
    price         DECIMAL(19, 2) DEFAULT 0 NOT NULL,
    price_discount DECIMAL(19, 2) DEFAULT 0 NOT NULL,
    description   VARCHAR(75),
    category      INT,
    PRIMARY KEY (id),
    FOREIGN KEY (category) REFERENCES ecommerce_category(id)
);

INSERT INTO ecommerce_category (id, name) VALUES
    (1, 'Accessories'),
    (2, 'Clothing'),
    (3, 'Electronics'),
    (4, 'Footwear'),
    (5, 'Home Appliances'),
    (6, 'Home & Kitchen'),
    (7, 'Sports'),
    (8, 'Stationery'),
    (9, 'Toys & Games');

INSERT INTO ecommerce_product (id, sku, price, price_discount, description, category)
VALUES
    (1, 'SKU0001', 19.99, 0.00, 'Wireless Mouse with ergonomic design', 3),
    (2, 'SKU0002', 499.00, 0.00, '4K Ultra HD Smart TV, 55 inches', 3),
    (3, 'SKU0003', 29.50, 0.00, 'Stainless Steel Water Bottle, 1L', 6),
    (4, 'SKU0004', 15.00, 0.00, 'Cotton T-Shirt, Unisex, Size M', 2),
    (5, 'SKU0005', 120.00, 0.00, 'Noise-Cancelling Over-Ear Headphones', 3),
    (6, 'SKU0006', 9.99, 0.00, 'USB-C to USB Adapter', 3),
    (7, 'SKU0007', 75.00, 0.00, 'Leather Wallet with RFID Protection', 1),
    (8, 'SKU0008', 35.00, 0.00, 'Yoga Mat with Non-Slip Surface', 7),
    (9, 'SKU0009', 220.00, 0.00, 'Smartwatch with Heart Rate Monitor', 3),
    (10, 'SKU0010', 12.50, 0.00, 'Ceramic Coffee Mug, 350ml', 6),
    (11, 'SKU0011', 60.00, 0.00, 'Bluetooth Portable Speaker', 3),
    (12, 'SKU0012', 85.00, 0.00, 'Backpack with Laptop Compartment', 1),
    (13, 'SKU0013', 18.00, 0.00, 'Stainless Steel Cutlery Set, 24 Pieces', 6),
    (14, 'SKU0014', 250.00, 0.00, 'Electric Guitar Starter Pack', 9),
    (15, 'SKU0015', 42.00, 0.00, 'Running Shoes, Men''s Size 42', 4),
    (16, 'SKU0016', 27.99, 0.00, 'Digital Bathroom Scale with Body Fat Analyzer', 5),
    (17, 'SKU0017', 14.99, 0.00, 'Set of 6 Organic Cotton Socks', 2),
    (18, 'SKU0018', 300.00, 0.00, 'DSLR Camera with 18-55mm Lens', 3),
    (19, 'SKU0019', 8.99, 0.00, 'Hardcover Notebook, A5, 200 Pages', 8),
    (20, 'SKU0020', 65.00, 0.00, 'Microwave Oven, 20L Capacity', 5),
    (21, 'SKU0021', 23.50, 0.00, 'LED Desk Lamp with Adjustable Brightness', 6),
    (22, 'SKU0022', 19.00, 0.00, 'Wireless Charger Pad for Smartphones', 3),
    (23, 'SKU0023', 55.00, 0.00, 'Men''s Quartz Analog Watch with Leather Strap', 1),
    (24, 'SKU0024', 30.00, 0.00, 'Wooden Chess Set with Folding Board', 9),
    (25, 'SKU0025', 99.00, 0.00, 'Home Security Camera with Night Vision', 3),
    (26, 'SKU0026', 16.50, 0.00, 'Aromatherapy Essential Oil Diffuser', 6),
    (27, 'SKU0027', 40.00, 0.00, 'Professional Blender with 2L Jar', 5),
    (28, 'SKU0028', 22.00, 0.00, 'Kids'' Educational Tablet Toy', 9),
    (29, 'SKU0029', 110.00, 0.00, 'Mechanical Gaming Keyboard with RGB Lighting', 3),
    (30, 'SKU0030', 7.50, 0.00, 'Pack of 10 Ballpoint Pens, Blue Ink', 8);