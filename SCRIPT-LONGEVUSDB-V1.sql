//CREATE DATABASE Longevusdb;

USE LongevusDB;

CREATE TABLE room (
    id INT PRIMARY KEY UNIQUE,
    statusRoom NVARCHAR(50) CHECK (statusRoom IN ('Disponible', 'No disponible')),
    roomType NVARCHAR(50) ,
    bedCount INT,
    isActive BOOLEAN
);

CREATE TABLE resident (
    id INT PRIMARY KEY AUTO_INCREMENT,
    identification NVARCHAR(20),
    name NVARCHAR(100),
    age INT,
    healthStatus NVARCHAR(100),
    numberRoom INT,
    photo NVARCHAR(200),
    isActive BOOLEAN,
    FOREIGN KEY (numberRoom) REFERENCES room(id)
);

CREATE TABLE schedule (
    id INT PRIMARY KEY AUTO_INCREMENT,
    day VARCHAR(10),
    entryTime1 VARCHAR(20),
    exitTime1 VARCHAR(20),
    entryTime2 VARCHAR(20),
    exitTime2 VARCHAR(20),
    isActive BOOLEAN
);

CREATE TABLE person (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    identification NVARCHAR(20),
    salary DECIMAL(10, 2),
    photoSchedule NVARCHAR(255),
    email VARCHAR(100),
    password NVARCHAR(100),
    scheduleID INT,
    isActive BOOLEAN,
    FOREIGN KEY (scheduleID) REFERENCES schedule(id)
);

CREATE TABLE administrator (
    id INT PRIMARY KEY AUTO_INCREMENT,
    officeContact VARCHAR(100),
    FOREIGN KEY (id) REFERENCES person(id)
);
 
CREATE TABLE caregiver (
    id INT PRIMARY KEY AUTO_INCREMENT,
    shift VARCHAR(50),
    residentId INT,
    FOREIGN KEY (id) REFERENCES person(id),
    FOREIGN KEY (residentId) REFERENCES resident(id) -- Assumes Resident table exists
);

CREATE TABLE errand (
    id INT PRIMARY KEY AUTO_INCREMENT,
    caregiverId INT,
    description TEXT,
    FOREIGN KEY (caregiverId) REFERENCES caregiver(id)
);

CREATE TABLE billing (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sequence VARCHAR(50),
    date DATE,
    amount DECIMAL(10, 2),
    period VARCHAR(50),
    paymentMethod VARCHAR(50),
    administratorId INT,
    residentId INT,
    isActive BOOLEAN,
    FOREIGN KEY (administratorId) REFERENCES administrator(id),
    FOREIGN KEY (residentId) REFERENCES resident(id)
);

CREATE TABLE visit (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    visitDate DATE,
    contact VARCHAR(100),
    relationship VARCHAR(50),
    photo NVARCHAR(255),
    idresident INT,
    isActive BOOLEAN,
    FOREIGN KEY (idresident) REFERENCES resident(id)
);

CREATE TABLE activity (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    description TEXT,
    type VARCHAR(50),
    date DATE,
    startTime TIME,
    endTime TIME,
    location VARCHAR(100),
    status VARCHAR(50),
    responsibleId INT,
    isActive BOOLEAN,
    FOREIGN KEY (responsibleId) REFERENCES person(id)
);

CREATE TABLE resident_activity (
    resident_id INT,
    activity_id INT,
    PRIMARY KEY (resident_id, activity_id),
    FOREIGN KEY (resident_id) REFERENCES resident(id),
    FOREIGN KEY (activity_id) REFERENCES activity(id)
);

CREATE TABLE caregiver_resident (
    caregiver_id INT,
    resident_id INT,
    PRIMARY KEY (caregiver_id, resident_id),
    FOREIGN KEY (caregiver_id) REFERENCES caregiver(id),
    FOREIGN KEY (resident_id) REFERENCES resident(id)
);

CREATE TABLE supplier (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    phoneNumber VARCHAR(50),
    email VARCHAR(100),
    address VARCHAR(300),
    photo NVARCHAR(100),
    isActive BOOLEAN
);

CREATE TABLE unit (
    id INT PRIMARY KEY AUTO_INCREMENT,
    unit_type VARCHAR(50),
    isActive BOOLEAN
);

CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    price DECIMAL(10, 2),
    category NVARCHAR(30),
    expirationDate DATE,
    photoURL NVARCHAR(200),
    unitId INT,
    supplierId INT,
    isActive BOOLEAN,
    FOREIGN KEY (unitId) REFERENCES unit(id),
    FOREIGN KEY (supplierId) REFERENCES supplier(id)
);

CREATE TABLE purchase (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE,
    idProduct int,
     idAdministrator INT,
    amount DECIMAL(10, 2),
    isActive BOOLEAN,
    FOREIGN KEY (idAdministrator) REFERENCES administrator(id),
    FOREIGN KEY (idProduct) REFERENCES product(id)
);


CREATE TABLE inventory (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quantity INT,
    category VARCHAR(100),
    photo Text,
    productId INT,
    purchaseId INT,
    isActive BOOLEAN,
    FOREIGN KEY (productId) REFERENCES product(id),
    FOREIGN KEY (purchaseId) REFERENCES purchase(id)
);

CREATE TABLE purchase_product (
    idPurchase INT,
    idProduct INT,
    quantity INT,
    PRIMARY KEY (idPurchase, idProduct),
    FOREIGN KEY (idPurchase) REFERENCES purchase(id),
    FOREIGN KEY (idProduct) REFERENCES product(id)
);

SELECT * FROM PRODUCT
SELECT * FROM PURCHASE
SELECT * FROM PURCHASE_PRODUCT

SELECT * FROM INVENTORY


SELECT * FROM inventory WHERE isActive = 1;
CALL get_all_inventory_full();


SHOW COLUMNS FROM inventory;


DESCRIBE INVENTORY;
CALL get_all_inventory();
DROP PROCEDURE IF EXISTS get_all_inventory;


ALTER TABLE inventory
MODIFY COLUMN isActive TINYINT(1) DEFAULT 1;














-- Compra 1 tiene 2 productos
INSERT INTO purchase_product (idPurchase, idProduct, quantity) VALUES (1, 2, 10);
INSERT INTO purchase_product (idPurchase, idProduct, quantity) VALUES (1, 3, 5);

-- Producto 2 se compró en 2 compras diferentes
INSERT INTO purchase_product (idPurchase, idProduct, quantity) VALUES (2, 2, 7);


//Inserts
INSERT INTO schedule (day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES
('Lunes', '08:00', '12:00', '13:00', '17:00', TRUE),
('Martes', '08:30', '12:30', '14:00', '18:00', TRUE),
('Miércoles', '09:00', '13:00', '14:00', '18:00', TRUE);

INSERT INTO person (name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES
('Carlos Pérez', 'PER1001', 1200.00, 'horario1.jpg', 'carlos@mail.com', 'pass1', 1, TRUE),
('Lucía Fernández', 'PER1002', 1400.50, 'horario2.jpg', 'lucia@mail.com', 'pass2', 2, TRUE),
('Juan Solís', 'PER1003', 1350.75, 'horario3.jpg', 'juan@mail.com', 'pass3', 3, TRUE);

INSERT INTO administrator (id, officeContact) VALUES
(1, 'Oficina 101'),
(2, 'Oficina 102'),
(3, 'Oficina 103');
 

INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES
(1, 'Disponible', 'Individual', 1, TRUE),
(2, 'No disponible', 'Compartida', 2, TRUE),
(3, 'Disponible', 'Individual', 1, TRUE);

INSERT INTO resident (identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES
('ID101', 'Ana Soto', 75, 'Estable', 1, 'foto1.jpg', TRUE),
('ID102', 'Luis Gómez', 82, 'Mejorando', 2, 'foto2.jpg', TRUE),
('ID103', 'Elena Ruiz', 68, 'Crítico', 3, 'foto3.jpg', TRUE);

INSERT INTO caregiver (id, shift, residentId) VALUES
(1, 'Mañana', 1),
(2, 'Tarde', 2),
(3, 'Noche', 3);

INSERT INTO errand (caregiverId, description) VALUES
(1, 'Toma de presión'),
(2, 'Asistencia al baño'),
(3, 'Entrega de medicamentos');

INSERT INTO visit (name, visitDate, contact, relationship, photo, idresident, isActive) VALUES
('Pedro Morales', '2023-01-05', '88881234', 'Hijo', 'visita1.jpg', 1, TRUE),
('María Jiménez', '2023-02-10', '88885678', 'Nieta', 'visita2.jpg', 2, TRUE),
('Laura Vargas', '2023-03-15', '88889999', 'Amiga', 'visita3.jpg', 3, TRUE);

INSERT INTO activity (name, description, type, date, startTime, endTime, location, status, responsibleId, isActive) VALUES
('Taller de pintura', 'Actividad artística', 'Recreativa', '2023-04-01', '10:00', '12:00', 'Sala común', 'Activa', 1, TRUE),
('Clases de yoga', 'Relajación y ejercicio', 'Física', '2023-04-02', '09:00', '10:30', 'Patio', 'Activa', 2, TRUE),
('Cine foro', 'Película y discusión', 'Cultural', '2023-04-03', '15:00', '17:00', 'Sala TV', 'Activa', 3, TRUE);

INSERT INTO resident_activity (resident_id, activity_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO caregiver_resident (caregiver_id, resident_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO supplier (name, phoneNumber, email, address, photo, isActive) VALUES
('Proveedor A', '80000001', 'a@correo.com', 'San José', 'proveedorA.jpg', TRUE),
('Proveedor B', '80000002', 'b@correo.com', 'Heredia', 'proveedorB.jpg', TRUE),
('Proveedor C', '80000003', 'c@correo.com', 'Cartago', 'proveedorC.jpg', TRUE);

INSERT INTO unit (unit_type, isActive) VALUES
('ml', TRUE),
('g', TRUE),
('unidades', TRUE);

INSERT INTO product (name, price, expirationDate, category, photoURL, unitId, supplierId, isActive) VALUES
('Paracetamol', 200.00, '2024-12-31', 'Medicamento', 'paracetamol.jpg', 1, 1, TRUE),
('Vitamina C', 150.00, '2025-01-15', 'Suplemento', 'vitaminac.jpg', 2, 2, TRUE),
('Algodón', 50.00, '2026-06-10', 'Insumo', 'algodon.jpg', 3, 3, TRUE);

INSERT INTO purchase (id, date, amount, idAdministrator, idProduct, isActive) VALUES
('0001-20230501','2023-05-01', 200.00, 1, 1, TRUE),
('0002-20230502','2023-05-02', 150.00, 2, 2, TRUE),
('0003-20230503', '2023-05-03', 50.00, 3, 3, TRUE);

INSERT INTO inventory (quantity, category, photo, productId, purchaseId, isActive) VALUES
(100, 'Medicamento', 'inv1.jpg', 1, '0001-20230501', TRUE),
(50, 'Suplemento', 'inv2.jpg', 2, '0002-20230502', TRUE),
(75, 'Insumo', 'inv3.jpg', 3, '0003-20230503', TRUE);

SELECT * FROM inventory WHERE isActive = 1;



SHOW COLUMNS FROM purchase_product;

SHOW COLUMNS FROM purchase;


SELECT * FROM product

