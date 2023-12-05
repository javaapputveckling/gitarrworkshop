USE
    ProjectDB;
-- Insert data into CLIENT table
INSERT INTO CLIENT (CLIENT_ID, CLIENT_NAME, CLIENT_PHONE, CLIENT_EMAIL, CLIENT_DATE)
VALUES (1, 'John Doe', '123-456-7890', 'john.doe@example.com', '2023-01-01'),
       (2, 'Jane Smith', '987-654-3210', 'jane.smith@example.com', '2023-02-15');

-- Insert data into PRODUCT table

INSERT INTO PRODUCT (PRODUCT_PRICE, PRODUCT_HISTORY_DESC, PRODUCT_MAIN_DESC, PRODUCT_YEAR, PRODUCT_NAME)
VALUES (49.99, 'Product A history', 'Product A main description', 2022, 'Product A'),
       (79.99, 'Product B history', 'Product B main description', 2021, 'Product B'),
       (99.99, 'Product C history', 'Product C main description', 2020, 'Product C'),
       (149.99, 'Product D history', 'Product D main description', 2019, 'Product D'),
       (199.99, 'Product E history', 'Product E main description', 2018, 'Product E'),
       (249.99, 'Product F history', 'Product F main description', 2017, 'Product F'),
       (299.99, 'Product G history', 'Product G main description', 2016, 'Product G');


-- Insert data into CASES table with NULL PRODUCT_ID
INSERT INTO CASES (CASE_ID, MEMBER_ID, PRODUCT_ID, STATUS, CASE_DATE_START, CASE_DATE_END, CASE_PROFIT, CASE_DESC,
                   CASE_HOURS, CASE_TYPE)
VALUES (1, 1, 1, 'Open', '2023-01-10', '2023-01-20', 500.00, 'Project A description', 30, 'Rented'),
       (2, 2, NULL, 'Closed', '2023-02-01', '2023-02-28', 800.00, 'Project B description', 40, 'Repairment');


-- Insert data into CASE_JOURNAL table
INSERT INTO CASE_JOURNAL (JOURNAL_ID, CASE_ID, JOURNAL_DESC)
VALUES (1, 1, 'Journal entry for Project A'),
       (2, 2, 'Journal entry for Project B');

-- Insert data into PROD_IMG table
INSERT INTO PROD_IMG (PRODUCT_ID, IMG_PATH_STRING)
VALUES (1, 'BobMarley.JPG'),
       (1, 'IMG_1186.JPG'),
       (1, 'IMG_1187.JPG'),
       (1, 'IMG_1188.JPG'),
       (2, 'IMG_1189.JPG'),
       (2, 'BobMarley.JPG'),
       (3, 'IMG_1186.JPG'),
       (3, 'IMG_1187.JPG'),
       (4, 'IMG_1188.JPG'),
       (4, 'IMG_1189.JPG'),
       (5, 'BobMarley.JPG'),
       (6, 'IMG_1186.JPG'),
       (7, 'IMG_1187.JPG');


-- Insert data into JOURNAL_IMG table
INSERT INTO JOURNAL_IMG (IMG_ID, JOURNAL_ID, IMG_PATH_STRING)
VALUES (1, 1, '../src/main/webapp/assets/journal_entry_a.jpg'),
       (2, 2, '../src/main/webapp/assets/journal_entry_b.jpg');

-- Insert data into APPOINTMENT table
INSERT INTO APPOINTMENT (APPOINTMENT_TYPE, APPOINTMENT_DATE, APPOINTMENT_TIME)
VALUES ('Reservation', '2024-01-01', '10:00:00');

INSERT INTO APPOINTMENT (APPOINTMENT_TYPE, APPOINTMENT_DATE, APPOINTMENT_TIME)
VALUES ('Reparation', '2024-01-01', '14:30:00');

INSERT INTO APPOINTMENT (APPOINTMENT_TYPE, APPOINTMENT_DATE, APPOINTMENT_TIME)
VALUES ('Reparation', '2024-01-01', '16:45:00');
