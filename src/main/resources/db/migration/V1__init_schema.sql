CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NULL,
    role VARCHAR(255) NOT NULL,
    provider VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
);

CREATE TABLE IF NOT EXISTS quantity_measurements (
    id BIGINT NOT NULL AUTO_INCREMENT,
    operation VARCHAR(50) NOT NULL,
    operand1 VARCHAR(255) NULL,
    operand2 VARCHAR(255) NULL,
    result TEXT NULL,
    error TEXT NULL,
    measurement_type VARCHAR(50) NULL,
    user_email VARCHAR(255) NULL,
    error_flag BIT NOT NULL DEFAULT b'0',
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NULL,
    PRIMARY KEY (id),
    KEY idx_operation (operation),
    KEY idx_measurement_type (measurement_type),
    KEY idx_user_email (user_email),
    KEY idx_created_at (created_at)
);
