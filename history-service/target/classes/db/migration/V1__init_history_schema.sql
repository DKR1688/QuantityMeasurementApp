CREATE TABLE quantity_measurements (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    operation VARCHAR(50) NOT NULL,
    operand1 VARCHAR(255),
    operand2 VARCHAR(255),
    result TEXT,
    error TEXT,
    measurement_type VARCHAR(50),
    user_email VARCHAR(255),
    error_flag BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE INDEX idx_operation ON quantity_measurements (operation);
CREATE INDEX idx_measurement_type ON quantity_measurements (measurement_type);
CREATE INDEX idx_user_email ON quantity_measurements (user_email);
CREATE INDEX idx_created_at ON quantity_measurements (created_at);