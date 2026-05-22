CREATE TABLE items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE variants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_id UUID NOT NULL,
    sku VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock_on_hand INT NOT NULL DEFAULT 0,
    stock_allocated INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_variants_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE
);