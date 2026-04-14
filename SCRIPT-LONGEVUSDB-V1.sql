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

//ejecucion

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
    category VARCHAR(30),
    expirationDate DATE,
    photoURL TEXT,
    unitId INT,
    supplierId INT,
    isActive BOOLEAN,
    FOREIGN KEY (unitId) REFERENCES unit(id),
    FOREIGN KEY (supplierId) REFERENCES supplier(id)
);

CREATE TABLE purchase (
    id VARCHAR(20) PRIMARY KEY,
    date DATE,
    idAdministrator INT,
    amount DECIMAL(10, 2),
    isActive TINYINT(1),
    FOREIGN KEY (idAdministrator) REFERENCES administrator(id)
);

CREATE TABLE inventory (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quantity INT,
    category VARCHAR(100),
    photo TEXT,
    productId INT,
    purchaseId VARCHAR(20),
    isActive BOOLEAN,
    FOREIGN KEY (productId) REFERENCES product(id),
    FOREIGN KEY (purchaseId) REFERENCES purchase(id)
);

CREATE TABLE purchase_product (
    idPurchase VARCHAR(20),
    idProduct INT,
    quantity INT,
    expirationDate DATE,
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



//Procedimientos almacenados
DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE delete_inventory_logically_by_id(IN inventoryId INT)
BEGIN
    UPDATE inventory
    SET isActive = 0
    WHERE id = inventoryId;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE delete_product(
    IN p_id INT
)
BEGIN
    UPDATE product
    SET isActive = 0
    WHERE id = p_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE delete_purchase_by_id(
    IN p_purchase_id VARCHAR(20)
)
BEGIN
    UPDATE purchase
    SET isActive = 0
    WHERE id = p_purchase_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE delete_purchase_products(
     IN p_idPurchase VARCHAR(20)
)
BEGIN
     DELETE FROM purchase_product
    WHERE idPurchase = p_idPurchase;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_all_inventory()
BEGIN
    SELECT 
        i.id AS inventory_id,
        i.quantity,
        i.category,
        i.photo AS photo_url,
        i.isActive,

        p.id AS product_id,
        p.name AS product_name,
        p.expirationDate AS expiration_date, -- ✅ alias correcto

        s.id AS supplier_id,
        s.name AS supplier_name,

        pu.id AS purchase_id,
        pu.date AS purchase_date

    FROM inventory i
    LEFT JOIN product p ON i.productId = p.id
    LEFT JOIN supplier s ON p.supplierId = s.id
    LEFT JOIN purchase pu ON i.purchaseId = pu.id
    WHERE i.isActive = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_all_inventory_full()
BEGIN
    SELECT 
        i.id AS inventory_id,
        i.quantity AS total_quantity,
        i.category,
        i.photo AS photo_url,

        p.id AS product_id,
        p.name AS product_name,
        p.expirationDate AS expiration_date,

        s.id AS supplier_id,
        s.name AS supplier_name,

        pu.id AS purchase_id

    FROM 
        inventory i
    JOIN product p ON i.productId = p.id
    JOIN supplier s ON p.supplierId = s.id
    JOIN purchase pu ON i.purchaseId = pu.id
    WHERE 
        pu.isActive = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_all_products()
BEGIN
    SELECT 
        p.id,
        p.name,
        p.price,
        p.category,
        p.expirationDate,
        p.photoURL,
        p.unitId,       
        p.supplierId AS supplier_id,
        u.unit_type,
        s.name AS supplier_name,
        s.phoneNumber,
        s.email,
        s.address,
        s.photo AS supplier_photo
    FROM 
        product p
    JOIN unit u ON p.unitId = u.id
    JOIN supplier s ON p.supplierId = s.id
    WHERE 
        p.isActive = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_all_purchases()
BEGIN
    SELECT 
        pu.id AS purchase_id,              -- ✅ Corrección aquí
        pu.date AS purchase_date,          -- (asumo que es date, cámbialo si el nombre es otro)
        pu.amount AS purchase_amount,      -- (asumo que es amount)
        pp.idProduct,
        p.name AS product_name,
        pp.quantity,
        p.price AS product_price,
        pp.expirationDate  
    FROM 
        purchase pu
    JOIN purchase_product pp ON pu.id = pp.idPurchase   -- ✅ También aquí
    JOIN product p ON pp.idProduct = p.id
    WHERE pu.isActive = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_inventory_by_expiration(IN exp_date DATE)
BEGIN
    SELECT 
        i.id AS inventory_id,
        i.quantity,
        i.category,
        i.photo AS photo_url,
        i.isActive,

        p.id AS product_id,
        p.name AS product_name,
        p.supplierId AS supplier_id, -- ¡IMPORTANTE!
        
        s.name AS supplier_name,     -- ¡IMPORTANTE!
        
        IFNULL(pp.expirationDate, NULL) AS expiration_date,

        pu.id AS purchase_id,
        pu.date AS purchase_date,
        pu.amount
    FROM inventory i
    LEFT JOIN product p ON i.productId = p.id
    LEFT JOIN supplier s ON p.supplierId = s.id  -- ← JOIN necesario
    LEFT JOIN purchase pu ON i.purchaseId = pu.id
    LEFT JOIN purchase_product pp ON pp.idPurchase = pu.id AND pp.idProduct = p.id
    WHERE i.isActive = 1 AND pp.expirationDate = exp_date;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_inventory_by_product_unit()
BEGIN
    SELECT 
        i.id AS inventory_id,
        i.category,
        i.photo AS photo_url,
        i.quantity,
        
        p.id AS product_id,
        p.name AS product_name,
        pp.expirationDate AS expiration_date,
        
        s.id AS supplier_id,
        s.name AS supplier_name,
        
        pu.id AS purchase_id

 FROM 
        purchase_product pp
    JOIN product p ON pp.idProduct = p.id
    JOIN purchase pu ON pp.idPurchase = pu.id
    JOIN supplier s ON p.supplierId = s.id
    JOIN inventory i ON p.id = i.productId AND pu.id = i.purchaseId
    WHERE pu.isActive = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_inventory_by_unit()
BEGIN
    SELECT 
        i.id AS inventory_id,
        i.category,
        i.photo AS photo_url,

        p.id AS product_id,
        p.name AS product_name,
        p.expirationDate AS expiration_date,

        s.id AS supplier_id,
        s.name AS supplier_name,

        pu.id AS purchase_id

    FROM 
        inventory i
    JOIN product p ON i.productId = p.id
    JOIN supplier s ON p.supplierId = s.id
    JOIN purchase pu ON i.purchaseId = pu.id
    JOIN (
        -- Esta subconsulta "descompone" por unidad usando RECURSIVE
        SELECT 
            ip.inventory_id,
            ip.product_id,
            ip.purchase_id
        FROM (
            SELECT 
                i.id AS inventory_id,
                i.productId AS product_id,
                i.purchaseId AS purchase_id,
                i.quantity
            FROM inventory i
            JOIN purchase pu ON i.purchaseId = pu.id
            WHERE pu.isActive = 1
        ) AS inv
        JOIN (
            SELECT a.n
            FROM (
                SELECT 1 AS n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL 
                SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL 
                SELECT 9 UNION ALL SELECT 10
            ) a
        ) AS nums
        ON nums.n <= inv.quantity
    ) AS unit_list
    ON i.id = unit_list.inventory_id
    WHERE pu.isActive = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_inventory_units()
BEGIN
    -- CTE para generar números del 1 al 100 (puedes ampliar según tus cantidades máximas)
    WITH RECURSIVE numbers AS (
        SELECT 1 AS n
        UNION ALL
        SELECT n + 1 FROM numbers WHERE n < 100
    )

    SELECT 
        p.id AS product_id,
        p.name AS product_name,
        p.photo AS photo_url,
        s.name AS supplier_name,
        pp.expirationDate,
        pu.id AS purchase_id
    FROM purchase_product pp
    JOIN product p ON pp.idProduct = p.id
    JOIN supplier s ON p.supplierId = s.id
    JOIN purchase pu ON pp.idPurchase = pu.id
    JOIN numbers n ON n.n <= pp.quantity
    WHERE pu.isActive = 1
    ORDER BY pp.idPurchase, p.name;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_product_by_id(
    IN p_id INT
)
BEGIN
    SELECT 
        id,
        name,
        price,
        expirationDate,
        category,
        photoURL AS photo
    FROM 
        product
    WHERE id = p_id AND isActive = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE get_purchase_with_details_by_id(
    IN p_purchase_id VARCHAR(20)
)
BEGIN
    SELECT 
        p.id AS purchase_id,
        p.date AS purchase_date,
        p.amount AS purchase_amount,
        pr.id AS idProduct,
        pr.name AS product_name,
        pr.price AS product_price,
        pp.quantity
    FROM 
        purchase p
    INNER JOIN purchase_product pp ON p.id = pp.idPurchase
    INNER JOIN product pr ON pp.idProduct = pr.id
    WHERE 
        p.id = p_purchase_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE insert_inventory(
    IN p_product_id INT,
    IN p_category VARCHAR(50),
    IN p_photo VARCHAR(255),
    IN p_quantity INT,
    IN p_purchase_id VARCHAR(36)
)
BEGIN
    INSERT INTO inventory (
        quantity,
        category,
        photo,
        productId,
        purchaseId,
        isActive
    )
    VALUES (
        p_quantity,
        p_category,
        p_photo,
        p_product_id,
        p_purchase_id,
        1
    );
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE insert_product(
    IN p_name VARCHAR(100),
    IN p_price DECIMAL(10,2),
    IN p_expiration DATE,
    IN p_category VARCHAR(50),
    IN p_photoURL VARCHAR(255),
    IN p_unit_id INT,
    IN p_supplier_id INT
)
BEGIN
    INSERT INTO product (
        name, price, expirationDate, category, photoURL,
        unit_id, supplier_id, isActive
    )
    VALUES (
        p_name, p_price, p_expiration, p_category, p_photoURL,
        p_unit_id, p_supplier_id, 1
    );
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS insert_purchase;
CREATE PROCEDURE insert_purchase(
    IN p_date DATE,
    IN p_amount DECIMAL(10,2),
    IN p_admin_id INT,
    OUT p_new_id VARCHAR(20)  -- ← parámetro de salida
)
BEGIN
    DECLARE v_count INT;
    DECLARE v_id VARCHAR(20);
    DECLARE v_date_str VARCHAR(8);

    SET v_date_str = DATE_FORMAT(p_date, '%Y%m%d');

    SELECT COUNT(*) + 1 INTO v_count
    FROM purchase
    WHERE DATE(date) = p_date;

    SET v_id = CONCAT(LPAD(v_count, 4, '0'), '-', v_date_str);

    INSERT INTO purchase (id, date, amount, idAdministrator, isActive)
    VALUES (v_id, p_date, p_amount, p_admin_id, 1);

    SET p_new_id = v_id;  -- ← retorna el valor por OUT
END;
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE insert_purchase_product(
    IN p_idPurchase VARCHAR(20),
    IN p_idProduct INT,
    IN p_quantity INT,
    IN p_expiration_date DATE
)
BEGIN
    INSERT INTO purchase_product (idPurchase, idProduct, quantity, expirationDate)
    VALUES (p_idPurchase, p_idProduct, p_quantity, p_expiration_date);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE update_inventory(
    IN p_inventory_id INT,
    IN p_quantity INT,
    IN p_category VARCHAR(50),
    IN p_photo VARCHAR(255)
)
BEGIN
    UPDATE inventory
    SET quantity = p_quantity,
        category = p_category,
        photo = p_photo
    WHERE inventory_id = p_inventory_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE update_inventory_by_id(
    IN p_inventory_id INT,
    IN p_quantity INT,
    IN p_category VARCHAR(100)
)
BEGIN
    UPDATE inventory
    SET 
        quantity = p_quantity,
        category = p_category
    WHERE 
        inventory_id = p_inventory_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE update_product(
    IN p_id INT,
    IN p_name VARCHAR(100),
    IN p_price DECIMAL(10,2),
    IN p_expiration DATE,
    IN p_category VARCHAR(50),
    IN p_photoURL VARCHAR(255),
    IN p_unit_id INT,
    IN p_supplier_id INT
)
BEGIN
    UPDATE product
    SET 
        name = p_name,
        price = p_price,
        expirationDate = p_expiration,
        category = p_category,
        photoURL = p_photoURL,
        unit_id = p_unit_id,
        supplier_id = p_supplier_id
    WHERE id = p_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=root@localhost PROCEDURE update_purchase(
    IN p_purchase_id VARCHAR(20),
    IN p_date DATE,
    IN p_amount DECIMAL(10,2)
)
BEGIN
    UPDATE purchase
    SET 
        date = p_date,
        amount = p_amount
    WHERE 
        id = p_purchase_id;
END$$
DELIMITER ;

DELIMITER $$

DROP PROCEDURE IF EXISTS insert_purchase$$

CREATE PROCEDURE insert_purchase(
    IN p_date DATE,
    IN p_amount DECIMAL(10,2),
    IN p_admin_id INT,
    OUT p_new_id VARCHAR(20)
)
BEGIN
    DECLARE v_count INT;
    DECLARE v_id VARCHAR(20);
    DECLARE v_date_str VARCHAR(8);

    SET v_date_str = DATE_FORMAT(p_date, '%Y%m%d');

    SELECT COUNT(*) + 1 INTO v_count
    FROM purchase
    WHERE DATE(date) = p_date;

    SET v_id = CONCAT(LPAD(v_count, 4, '0'), '-', v_date_str);

    INSERT INTO purchase (id, date, amount, idAdministrator, isActive)
    VALUES (v_id, p_date, p_amount, p_admin_id, 1);

    SET p_new_id = v_id;
END$$

DELIMITER ;


//Procedimeintos de la segunda entrega
DELIMITER $$

CREATE PROCEDURE delete_billing_logically(IN billingId INT)
BEGIN
    UPDATE billing
    SET isActive = 0
    WHERE id = billingId;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE insert_billing(
    IN p_date DATE,
    IN p_amount DECIMAL(10, 2),
    IN p_period VARCHAR(50),
    IN p_payment_method VARCHAR(50),
    IN p_admin_id INT,
    IN p_resident_id INT,
    OUT p_new_consecutive VARCHAR(20)
)
BEGIN
    DECLARE new_id INT;

    -- Insertar la nueva factura
    INSERT INTO billing (
        date,
        amount,
        period,
        paymentMethod,  -- corregido aquí
        administratorId,
        residentId,
        isActive
    ) VALUES (
        p_date,
        p_amount,
        p_period,
        p_payment_method,  -- corregido aquí
        p_admin_id,
        p_resident_id,
        TRUE
    );

    -- Obtener el ID generado
    SET new_id = LAST_INSERT_ID();

    -- Generar el consecutivo
    SET p_new_consecutive = CONCAT('BILL-', LPAD(new_id, 5, '0'));

    -- Guardar el consecutivo en la fila
    UPDATE billing
    SET sequence = p_new_consecutive
    WHERE id = new_id;
END $$

DELIMITER ;





--PRUEBAS
-- 1. room
INSERT INTO room (id, statusRoom, roomType, bedCount, isActive)
VALUES (1, 'Disponible', 'Individual', 1, TRUE);

-- 2. resident
INSERT INTO resident (identification, name, age, healthStatus, numberRoom, photo, isActive)
VALUES ('12345678', 'Juan Pérez', 75, 'Estable', 1, 'juan.jpg', TRUE);

-- 3. schedule
INSERT INTO schedule (day, entryTime1, exitTime1, entryTime2, exitTime2, isActive)
VALUES ('Lunes', '08:00', '12:00', '14:00', '18:00', TRUE);

-- 4. person
INSERT INTO person (name, identification, salary, photoSchedule, email, password, scheduleID, isActive)
VALUES ('Laura Gómez', '87654321', 1500.00, 'laura.jpg', 'laura@example.com', 'password123', 1, TRUE);

-- 5. administrator
INSERT INTO administrator (id, officeContact)
VALUES (1, '555-0101');

-- 6. caregiver
INSERT INTO caregiver (id, shift, residentId)
VALUES (1, 'Mañana', 1);

-- 7. errand
INSERT INTO errand (caregiverId, description)
VALUES (1, 'Acompañar al residente al doctor');

-- 8. supplier
INSERT INTO supplier (name, phoneNumber, email, address, photo, isActive)
VALUES ('Proveedor Uno', '555-1234', 'proveedor@example.com', 'Calle Falsa 123', 'logo.png', TRUE);

-- 9. unit
INSERT INTO unit (unit_type, isActive)
VALUES ('Caja', TRUE);

-- 10. product
INSERT INTO product (name, price, category, expirationDate, photoURL, unitId, supplierId, isActive)
VALUES ('Leche', 2.50, 'Lácteos', CURDATE(), 'leche.jpg', 1, 1, TRUE);

-- 11. purchase
INSERT INTO purchase (id, date, idAdministrator, amount, isActive)
VALUES ('0001-' + DATE_FORMAT(CURDATE(), '%Y%m%d'), CURDATE(), 1, 25.00, 1);

-- 1. Insertar en purchase_product
INSERT INTO purchase_product (idPurchase, idProduct, quantity, expirationDate)
VALUES ('20250605', 1, 10, '2025-06-04');

-- 2. Insertar en inventory
INSERT INTO inventory (quantity, category, photo, productId, purchaseId, isActive)
VALUES (10, 'Alimentos', 'leche.jpg', 1, '20250605', 1);

-- 14. visit
INSERT INTO visit (name, visitDate, contact, relationship, photo, idresident, isActive)
VALUES ('Ana López', CURDATE(), '555-6789', 'Hija', 'ana.jpg', 1, 1);

-- 15. activity
INSERT INTO activity (name, description, type, date, startTime, endTime, location, status, responsibleId, isActive)
VALUES ('Yoga', 'Ejercicios suaves', 'Recreación', CURDATE(), '10:00', '11:00', 'Sala común', 'Programado', 1, 1);

-- 16. resident_activity
INSERT INTO resident_activity (resident_id, activity_id)
VALUES (1, 1);

-- 17. caregiver_resident
INSERT INTO caregiver_resident (caregiver_id, resident_id)
VALUES (1, 1);

SELECT * FROM PURCHASE
SELECT * FROM INVENTORY
SELECT * FROM purchase_product
SHOW COLUMNS FROM billing;


SELECT * FROM BILLING


