CREATE TABLE IF NOT EXISTS quantity_measurements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation VARCHAR(50) NOT NULL,
    operand1 VARCHAR(255),
    operand2 VARCHAR(255),
    result TEXT,
    error TEXT,
    measurement_type VARCHAR(50),
    error_flag BIT(1) NOT NULL DEFAULT b'0',
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6)
);
