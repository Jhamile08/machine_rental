CREATE DATABASE RENTAL_MACHINE;

CREATE TABLE clients (
    client_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE machine (
    machine_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    serial_number VARCHAR(50) NOT NULL UNIQUE,
    state BOOLEAN NOT NULL DEFAULT TRUE 
);

CREATE TABLE rental (
    rental_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    machine_id BIGINT NOT NULL,
    lease_start_date DATE NOT NULL,
    lease_end_date DATE NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE, 
    FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE,
    FOREIGN KEY (machine_id) REFERENCES machine(machine_id) ON DELETE CASCADE
);
