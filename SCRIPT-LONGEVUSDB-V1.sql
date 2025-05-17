CREATE DATABASE LongevusDB;

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
	expirationDate DATE,
    category NVARCHAR(30),
    photo NVARCHAR(200),
    unitId INT,
    supplierId INT,
    isActive BOOLEAN,
    FOREIGN KEY (unitId) REFERENCES unit(id),
    FOREIGN KEY (supplierId) REFERENCES supplier(id)
);

CREATE TABLE purchase (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE,
    amount DECIMAL(10, 2),
    idAdministrator INT,
    idProduct int,
    isActive BOOLEAN,
    FOREIGN KEY (idAdministrator) REFERENCES administrator(id),
    FOREIGN KEY (idProduct) REFERENCES product(id)
);


CREATE TABLE inventory (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quantity INT,
    category VARCHAR(100),
    photo VARCHAR(255),
    productId INT,
    supplierId INT,
    isActive BOOLEAN,
    FOREIGN KEY (productId) REFERENCES product(id),
    FOREIGN KEY (supplierId) REFERENCES supplier(id)
);


//INSERTS
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (1, 'Disponible', 'Individual', 1, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (2, 'No disponible', 'Compartida', 3, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (3, 'Disponible', 'Compartida', 2, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (4, 'No disponible', 'Individual', 3, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (5, 'Disponible', 'Individual', 2, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (6, 'No disponible', 'Compartida', 3, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (7, 'Disponible', 'Compartida', 4, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (8, 'Disponible', 'Compartida', 1, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (9, 'Disponible', 'Compartida', 2, TRUE);
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive) VALUES (10, 'No disponible', 'Individual', 4, TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (1, 'ID1000', 'Residente 1', 75, 'Estable', 9, 'foto_residente_1.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (2, 'ID1001', 'Residente 2', 86, 'Estable', 4, 'foto_residente_2.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (3, 'ID1002', 'Residente 3', 81, 'Mejorando', 7, 'foto_residente_3.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (4, 'ID1003', 'Residente 4', 78, 'Crítico', 6, 'foto_residente_4.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (5, 'ID1004', 'Residente 5', 72, 'Mejorando', 2, 'foto_residente_5.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (6, 'ID1005', 'Residente 6', 95, 'Mejorando', 3, 'foto_residente_6.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (7, 'ID1006', 'Residente 7', 65, 'Estable', 3, 'foto_residente_7.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (8, 'ID1007', 'Residente 8', 93, 'Estable', 6, 'foto_residente_8.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (9, 'ID1008', 'Residente 9', 84, 'Crítico', 8, 'foto_residente_9.jpg', TRUE);
INSERT INTO resident (id, identification, name, age, healthStatus, numberRoom, photo, isActive) VALUES (10, 'ID1009', 'Residente 10', 87, 'Mejorando', 6, 'foto_residente_10.jpg', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (1, 'Viernes', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (2, 'Martes', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (3, 'Viernes', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (4, 'Viernes', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (5, 'Viernes', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (6, 'Martes', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (7, 'Viernes', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (8, 'Miércoles', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (9, 'Miércoles', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO schedule (id, day, entryTime1, exitTime1, entryTime2, exitTime2, isActive) VALUES (10, 'Miércoles', '08:00', '12:00', '13:00', '17:00', TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (1, 'Persona 1', 'PERS1000', 1045.53, 'foto_horario_1.jpg', 'persona1@correo.com', 'password123', 7, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (2, 'Persona 2', 'PERS1001', 999.04, 'foto_horario_2.jpg', 'persona2@correo.com', 'password123', 10, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (3, 'Persona 3', 'PERS1002', 1836.86, 'foto_horario_3.jpg', 'persona3@correo.com', 'password123', 6, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (4, 'Persona 4', 'PERS1003', 1132.98, 'foto_horario_4.jpg', 'persona4@correo.com', 'password123', 2, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (5, 'Persona 5', 'PERS1004', 1344.24, 'foto_horario_5.jpg', 'persona5@correo.com', 'password123', 7, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (6, 'Persona 6', 'PERS1005', 1992.7, 'foto_horario_6.jpg', 'persona6@correo.com', 'password123', 2, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (7, 'Persona 7', 'PERS1006', 575.91, 'foto_horario_7.jpg', 'persona7@correo.com', 'password123', 2, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (8, 'Persona 8', 'PERS1007', 1669.65, 'foto_horario_8.jpg', 'persona8@correo.com', 'password123', 1, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (9, 'Persona 9', 'PERS1008', 1440.23, 'foto_horario_9.jpg', 'persona9@correo.com', 'password123', 9, TRUE);
INSERT INTO person (id, name, identification, salary, photoSchedule, email, password, scheduleID, isActive) VALUES (10, 'Persona 10', 'PERS1009', 1585.0, 'foto_horario_10.jpg', 'persona10@correo.com', 'password123', 3, TRUE);
INSERT INTO administrator (id, officeContact) VALUES (1, 'Oficina 1');
INSERT INTO administrator (id, officeContact) VALUES (2, 'Oficina 2');
INSERT INTO administrator (id, officeContact) VALUES (3, 'Oficina 3');
INSERT INTO administrator (id, officeContact) VALUES (4, 'Oficina 4');
INSERT INTO administrator (id, officeContact) VALUES (5, 'Oficina 5');
INSERT INTO administrator (id, officeContact) VALUES (6, 'Oficina 6');
INSERT INTO administrator (id, officeContact) VALUES (7, 'Oficina 7');
INSERT INTO administrator (id, officeContact) VALUES (8, 'Oficina 8');
INSERT INTO administrator (id, officeContact) VALUES (9, 'Oficina 9');
INSERT INTO administrator (id, officeContact) VALUES (10, 'Oficina 10');
INSERT INTO caregiver (id, shift, residentId) VALUES (1, 'Mañana', 10);
INSERT INTO caregiver (id, shift, residentId) VALUES (2, 'Tarde', 1);
INSERT INTO caregiver (id, shift, residentId) VALUES (3, 'Noche', 8);
INSERT INTO caregiver (id, shift, residentId) VALUES (4, 'Tarde', 7);
INSERT INTO caregiver (id, shift, residentId) VALUES (5, 'Tarde', 3);
INSERT INTO caregiver (id, shift, residentId) VALUES (6, 'Mañana', 5);
INSERT INTO caregiver (id, shift, residentId) VALUES (7, 'Tarde', 3);
INSERT INTO caregiver (id, shift, residentId) VALUES (8, 'Tarde', 8);
INSERT INTO caregiver (id, shift, residentId) VALUES (9, 'Tarde', 7);
INSERT INTO caregiver (id, shift, residentId) VALUES (10, 'Tarde', 9);
INSERT INTO errand (id, caregiverId, description) VALUES (1, 1, 'Tarea 1');
INSERT INTO errand (id, caregiverId, description) VALUES (2, 2, 'Tarea 2');
INSERT INTO errand (id, caregiverId, description) VALUES (3, 7, 'Tarea 3');
INSERT INTO errand (id, caregiverId, description) VALUES (4, 8, 'Tarea 4');
INSERT INTO errand (id, caregiverId, description) VALUES (5, 6, 'Tarea 5');
INSERT INTO errand (id, caregiverId, description) VALUES (6, 6, 'Tarea 6');
INSERT INTO errand (id, caregiverId, description) VALUES (7, 3, 'Tarea 7');
INSERT INTO errand (id, caregiverId, description) VALUES (8, 9, 'Tarea 8');
INSERT INTO errand (id, caregiverId, description) VALUES (9, 4, 'Tarea 9');
INSERT INTO errand (id, caregiverId, description) VALUES (10, 7, 'Tarea 10');
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (1, 'SEQ100', '2023-03-12', 866.22, 'Trimestral', 'Efectivo', 8, 2, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (2, 'SEQ101', '2023-03-08', 769.85, 'Trimestral', 'Efectivo', 1, 4, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (3, 'SEQ102', '2023-04-30', 312.85, 'Trimestral', 'Transferencia', 10, 3, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (4, 'SEQ103', '2023-07-04', 870.01, 'Mensual', 'Transferencia', 3, 1, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (5, 'SEQ104', '2023-01-13', 460.3, 'Mensual', 'Transferencia', 7, 2, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (6, 'SEQ105', '2023-12-16', 210.95, 'Trimestral', 'Efectivo', 5, 3, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (7, 'SEQ106', '2023-05-27', 958.8, 'Mensual', 'Transferencia', 2, 10, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (8, 'SEQ107', '2023-10-07', 864.48, 'Trimestral', 'Transferencia', 9, 2, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (9, 'SEQ108', '2023-11-04', 278.42, 'Mensual', 'Efectivo', 9, 5, TRUE);
INSERT INTO billing (id, sequence, date, amount, period, paymentMethod, administratorId, residentId, isActive) VALUES (10, 'SEQ109', '2023-07-30', 919.24, 'Trimestral', 'Transferencia', 4, 8, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (1, 'Visitante 1', '2023-02-16', '88880001', 'Amigo', 'foto_visita_1.jpg', 4, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (2, 'Visitante 2', '2023-07-18', '88880002', 'Amigo', 'foto_visita_2.jpg', 6, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (3, 'Visitante 3', '2023-09-07', '88880003', 'Hijo', 'foto_visita_3.jpg', 7, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (4, 'Visitante 4', '2023-08-13', '88880004', 'Hijo', 'foto_visita_4.jpg', 9, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (5, 'Visitante 5', '2023-06-22', '88880005', 'Nieto', 'foto_visita_5.jpg', 1, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (6, 'Visitante 6', '2023-06-10', '88880006', 'Hijo', 'foto_visita_6.jpg', 6, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (7, 'Visitante 7', '2023-07-19', '88880007', 'Amigo', 'foto_visita_7.jpg', 3, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (8, 'Visitante 8', '2023-09-02', '88880008', 'Nieto', 'foto_visita_8.jpg', 4, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (9, 'Visitante 9', '2023-12-13', '88880009', 'Amigo', 'foto_visita_9.jpg', 7, TRUE);
INSERT INTO visit (id, name, visitDate, contact, relationship, photo, idresident, isActive) VALUES (10, 'Visitante 10', '2023-05-06', '88880010', 'Hijo', 'foto_visita_10.jpg', 1, TRUE);
