-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS banco_db;
USE banco_db;

-- Crear la tabla Persona
CREATE TABLE Persona (
    PersonaID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Genero CHAR(1) NOT NULL,
    Edad INT NOT NULL,
    Identificacion VARCHAR(50) NOT NULL UNIQUE,
    Direccion VARCHAR(255),
    Telefono VARCHAR(20)
);

-- Crear la tabla Cliente que hereda de Persona
CREATE TABLE Cliente (
    ClienteID INT AUTO_INCREMENT PRIMARY KEY,
    PersonaID INT NOT NULL,
    Contraseña VARCHAR(255) NOT NULL,
    Estado VARCHAR(50) NOT NULL,
    CONSTRAINT FK_Cliente_Persona FOREIGN KEY (PersonaID) REFERENCES Persona(PersonaID)
);

-- Crear la tabla Cuenta
CREATE TABLE Cuenta (
    CuentaID INT AUTO_INCREMENT PRIMARY KEY,
    NumeroCuenta VARCHAR(50) NOT NULL UNIQUE,
    TipoCuenta VARCHAR(50) NOT NULL,
    SaldoInicial DECIMAL(10, 2) NOT NULL,
    Estado VARCHAR(50) NOT NULL
);

-- Crear la tabla Movimientos
CREATE TABLE Movimientos (
    MovimientoID INT AUTO_INCREMENT PRIMARY KEY,
    CuentaID INT NOT NULL,
    Fecha DATE NOT NULL,
    TipoMovimiento VARCHAR(50) NOT NULL,
    Valor DECIMAL(10, 2) NOT NULL,
    Saldo DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_Movimientos_Cuenta FOREIGN KEY (CuentaID) REFERENCES Cuenta(CuentaID)
);

CREATE TABLE ClienteCuenta (
    ClienteID INT NOT NULL,
    CuentaID INT NOT NULL,
    CONSTRAINT FK_ClienteCuenta_Cliente FOREIGN KEY (ClienteID) REFERENCES Cliente(ClienteID),
    CONSTRAINT FK_ClienteCuenta_Cuenta FOREIGN KEY (CuentaID) REFERENCES Cuenta(CuentaID)
);

-- Insertar datos iniciales en Persona
INSERT INTO Persona (Nombre, Genero, Edad, Identificacion, Direccion, Telefono) VALUES 
('Jose Lema', 'M', 30, '1234567890', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'F', 28, '0987654321', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'M', 35, '1122334455', '13 junio y Equinoccial', '098874587');

-- Insertar datos iniciales en Cliente
INSERT INTO Cliente (PersonaID, Contraseña, Estado) VALUES 
((SELECT PersonaID FROM Persona WHERE Identificacion='1234567890'), '1234', 'Activo'),
((SELECT PersonaID FROM Persona WHERE Identificacion='0987654321'), '5678', 'Activo'),
((SELECT PersonaID FROM Persona WHERE Identificacion='1122334455'), '1245', 'Activo');

-- Insertar datos iniciales en Cuenta
INSERT INTO Cuenta (NumeroCuenta, TipoCuenta, SaldoInicial, Estado) VALUES 
('478758', 'Ahorros', 2000, 'Activo'),
('225487', 'Corriente', 100, 'Activo'),
('495878', 'Ahorros', 0, 'Activo'),
('496825', 'Ahorros', 540, 'Activo'),
('585545', 'Corriente', 1000, 'Activo');

-- Relacionar Cuentas con Clientes (asumiendo que una tabla intermedia maneja la relación)
INSERT INTO ClienteCuenta (ClienteID, CuentaID) VALUES
((SELECT ClienteID FROM Cliente WHERE PersonaID=(SELECT PersonaID FROM Persona WHERE Identificacion='1234567890')), 
 (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='478758')),
((SELECT ClienteID FROM Cliente WHERE PersonaID=(SELECT PersonaID FROM Persona WHERE Identificacion='0987654321')), 
 (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='225487')),
((SELECT ClienteID FROM Cliente WHERE PersonaID=(SELECT PersonaID FROM Persona WHERE Identificacion='1122334455')), 
 (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='495878')),
((SELECT ClienteID FROM Cliente WHERE PersonaID=(SELECT PersonaID FROM Persona WHERE Identificacion='0987654321')), 
 (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='496825')),
((SELECT ClienteID FROM Cliente WHERE PersonaID=(SELECT PersonaID FROM Persona WHERE Identificacion='1234567890')), 
 (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='585545'));

-- Insertar datos iniciales en Movimientos
INSERT INTO Movimientos (Fecha, TipoMovimiento, Valor, Saldo, CuentaID) VALUES 
('2022-02-10', 'Retiro', -575, 1425, (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='478758')),
('2022-02-08', 'Depósito', 600, 700, (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='225487')),
('2022-02-08', 'Depósito', 150, 150, (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='495878')),
('2022-02-08', 'Retiro', -540, 0, (SELECT CuentaID FROM Cuenta WHERE NumeroCuenta='496825'));

