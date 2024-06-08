-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS banco_db;
USE banco_db;

-- Crear la tabla Persona
CREATE TABLE IF NOT EXISTS persona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    genero VARCHAR(50),
    edad INT,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(50)
);

-- Crear la tabla Cliente que hereda de Persona
CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    persona_id BIGINT NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    FOREIGN KEY (persona_id) REFERENCES persona(id)
);

-- Crear la tabla Cuenta
CREATE TABLE IF NOT EXISTS cuenta (
    numeroCuenta BIGINT UNIQUE NOT NULL PRIMARY KEY,
    tipoCuenta VARCHAR(50) NOT NULL,
    saldoInicial DOUBLE NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Crear la tabla Movimientos
CREATE TABLE IF NOT EXISTS movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    tipoMovimiento VARCHAR(50) NOT NULL,
    valor DOUBLE NOT NULL,
    saldo DOUBLE NOT NULL,
    cuenta_id BIGINT NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(numeroCuenta)
);

-- Insertar datos iniciales en Persona
INSERT INTO persona (nombre, genero, edad, identificacion, direccion, telefono) VALUES 
('Jose Lema', 'Masculino', 30, '1234567890', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'Femenino', 28, '0987654321', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'Masculino', 35, '1122334455', '13 junio y Equinoccial', '098874587');

-- Insertar datos iniciales en Cliente
INSERT INTO cliente (persona_id, id, contrasena, estado) VALUES 
((SELECT id FROM persona WHERE identificacion='1234567890'), '1234567890', '1234', TRUE),
((SELECT id FROM persona WHERE identificacion='0987654321'), '0987654321', '5678', TRUE),
((SELECT id FROM persona WHERE identificacion='1122334455'), '1122334455', '1245', TRUE);

-- Insertar datos en Cuenta
INSERT INTO cuenta (numeroCuenta, tipoCuenta, saldoInicial, estado, cliente_id) VALUES 
('478758', 'Ahorros', 2000, TRUE, (SELECT id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='1234567890'))),
('225487', 'Corriente', 100, TRUE, (SELECT id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='0987654321'))),
('495878', 'Ahorros', 0, TRUE, (SELECT id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='1122334455'))),
('496825', 'Ahorros', 540, TRUE, (SELECT id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='0987654321'))),
('585545', 'Corriente', 1000, TRUE, (SELECT id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='1234567890')));

-- Insertar datos en Movimientos
INSERT INTO movimientos (fecha, tipoMovimiento, valor, saldo, cuenta_id) VALUES 
('2022-02-10', 'Retiro', -575, 1425, (SELECT numeroCuenta FROM cuenta WHERE numeroCuenta='478758')),
('2022-02-08', 'Depósito', 600, 700, (SELECT numeroCuenta FROM cuenta WHERE numeroCuenta='225487')),
('2022-02-08', 'Depósito', 150, 150, (SELECT numeroCuenta FROM cuenta WHERE numeroCuenta='495878')),
('2022-02-08', 'Retiro', -540, 0, (SELECT numeroCuenta FROM cuenta WHERE numeroCuenta='496825'));


