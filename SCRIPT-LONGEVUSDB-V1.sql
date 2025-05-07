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
    category NVARCHAR(30),
    expirationDate DATE,
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
    idProduct int,
    amount DECIMAL(10, 2),
    idAdministrator INT,
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