CREATE DATABASE IF NOT EXISTS ProjectDB;
USE ProjectDB;
-- Create CLIENT table
CREATE TABLE IF NOT EXISTS CLIENT
(
    CLIENT_ID    INT AUTO_INCREMENT PRIMARY KEY,
    CLIENT_NAME  VARCHAR(255),
    CLIENT_PHONE VARCHAR(20),
    CLIENT_EMAIL VARCHAR(255),
    PASSWORD VARCHAR(200),
    CLIENT_DATE  DATE
);

-- Create PRODUCT table
CREATE TABLE IF NOT EXISTS PRODUCT
(
    PRODUCT_ID           INT AUTO_INCREMENT PRIMARY KEY,
    PRODUCT_PRICE        DECIMAL(10, 2),
    PRODUCT_HISTORY_DESC TEXT,
    PRODUCT_MAIN_DESC    TEXT,
    PRODUCT_YEAR         INT,
    PRODUCT_NAME         VARCHAR(255)
);

-- Create CASES table
CREATE TABLE IF NOT EXISTS CASES
(
    CASE_ID         INT AUTO_INCREMENT PRIMARY KEY,
    MEMBER_ID       INT,
    PRODUCT_ID      INT,
    STATUS          VARCHAR(50),
    CASE_DATE_START DATE,
    CASE_DATE_END   DATE,
    CASE_PROFIT     DECIMAL(10, 2),
    CASE_DESC       TEXT,
    CASE_HOURS      INT,
    CASE_TYPE       VARCHAR(50),
    FOREIGN KEY (MEMBER_ID) REFERENCES CLIENT (CLIENT_ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID)
);


-- Create CASE_JOURNAL table
CREATE TABLE IF NOT EXISTS CASE_JOURNAL
(
    JOURNAL_ID   INT AUTO_INCREMENT PRIMARY KEY,
    CASE_ID      INT,
    JOURNAL_DESC TEXT,
    FOREIGN KEY (CASE_ID) REFERENCES CASES (CASE_ID)
);

-- Create PROD_IMG table
CREATE TABLE IF NOT EXISTS PROD_IMG
(
    PROD_IMG_ID     INT AUTO_INCREMENT PRIMARY KEY,
    PRODUCT_ID      INT,
    IMG_PATH_STRING VARCHAR(255),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID)
);

-- Create JOURNAL_IMG table
CREATE TABLE IF NOT EXISTS JOURNAL_IMG
(
    IMG_ID          INT AUTO_INCREMENT PRIMARY KEY,
    JOURNAL_ID      INT,
    IMG_PATH_STRING VARCHAR(255),
    FOREIGN KEY (JOURNAL_ID) REFERENCES CASE_JOURNAL (JOURNAL_ID)
);

-- Create APPOINTMENT table
CREATE TABLE IF NOT EXISTS APPOINTMENT
(
    APPOINTMENT_ID    VARCHAR(36) PRIMARY KEY,
    CLIENT_ID         INT,
    PRODUCT_ID        INT,
    APPOINTMENT_TYPE  VARCHAR(50),
    APPOINTMENT_COLOR VARCHAR(50),
    APPOINTMENT_FROM  TIMESTAMP,
    APPOINTMENT_TO    TIMESTAMP,
    APPOINTMENT_DESC  TEXT,
    FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT (CLIENT_ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID)
);