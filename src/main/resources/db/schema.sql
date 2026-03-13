CREATE TABLE IF NOT EXISTS quantity_measurements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_type VARCHAR(50),
    measurement_type VARCHAR(50),
    input_value DOUBLE,
    input_unit VARCHAR(50),
    result_value DOUBLE,
    result_unit VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);