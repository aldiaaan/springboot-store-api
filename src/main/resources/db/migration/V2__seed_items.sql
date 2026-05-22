WITH inserted_shirt AS (
    INSERT INTO items (name, description)
    VALUES ('Classic Cotton T-Shirt', 'A premium, ultra-soft cotton t-shirt.')
    RETURNING id
)
INSERT INTO variants (item_id, sku, name, price, stock_on_hand, stock_allocated)
SELECT id, 'TSHIRT-BLK-SM', 'Black - Small', 19.99, 100, 0 FROM inserted_shirt UNION ALL
SELECT id, 'TSHIRT-BLK-MD', 'Black - Medium', 19.99, 150, 0 FROM inserted_shirt UNION ALL
SELECT id, 'TSHIRT-BLK-LG', 'Black - Large', 19.99, 50, 0 FROM inserted_shirt;

WITH inserted_mug AS (
    INSERT INTO items (name, description)
    VALUES ('Ceramic Coffee Mug', 'Holds 12oz of your favorite beverage.')
    RETURNING id
)
INSERT INTO variants (item_id, sku, name, price, stock_on_hand, stock_allocated)
SELECT id, 'MUG-WHT', 'Glossy White', 12.50, 200, 0 FROM inserted_mug UNION ALL
SELECT id, 'MUG-MATTE-BLK', 'Matte Black', 14.50, 0, 0 FROM inserted_mug;