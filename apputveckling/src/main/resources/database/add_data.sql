USE
    ProjectDB;
-- Insert data into CLIENT table
INSERT INTO CLIENT (CLIENT_ID, CLIENT_NAME, CLIENT_PHONE, CLIENT_EMAIL, CLIENT_DATE)
VALUES (1, 'Isaac Kanda', '123-456-1111', 'Isaac@example.com', '2016-11-01'),
       (2, 'Johannes Joujo', '987-654-2222', 'Johannes@example.com', '2017-02-15'),
       (3, 'Nasir Alizade', '123-456-3333', 'Nasir@example.com', '2018-01-08'),
       (4, 'Bashar Levin Goat', '123-456-4444', 'Bashar@example.com', '2019-05-01'),
       (5, 'Amarildo Rajta', '123-456-5555', 'Amarildo@example.com', '2020-07-01'),
       (6, 'Taha Khudher', '123-456-6666', 'Taha@example.com', '2021-01-01'),
       (7, 'Serhad Yildirim', '123-456-7777', 'Serhad@example.com', '2021-12-01'),
       (8, 'Emanuel Strid', '123-456-8888', 'Emanuel@example.com', '2023-09-01');

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
       (2, 2, NULL, 'Closed', '2023-02-01', '2023-02-28', 800.00, 'Project B description', 40, 'Repairment'),
       (3, 1, 1, 'Closed', '2023-01-10', '2023-03-21', 500.00, 'Project A description', 30, 'Rented'),
       (4, 2, 1, 'Open', '2023-01-10', '2023-05-12', 500.00, 'Project A description', 30, 'Rented'),
       (5, 3, 1, 'Open', '2023-01-10', '2023-06-07', 500.00, 'Project A description', 30, 'Rented'),
       (6, 3, 1, 'Closed', '2023-01-10', '2023-11-17', 500.00, 'Project A description', 30, 'Rented'),
       (7, 5, 1, 'Open', '2023-01-10', '2023-12-24', 500.00, 'Project A description', 30, 'Rented');


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
INSERT INTO ProjectDB.Events (title, start_date, end_date, all_day)
VALUES ('Reservation', '2024-01-01 10:00:00', '2024-01-01 11:00:00', 0);

INSERT INTO ProjectDB.Events (title, start_date, end_date, all_day)
VALUES ('Reparation', '2024-01-01 14:30:00', '2024-01-01 15:30:00', 0);

INSERT INTO ProjectDB.Events (title, start_date, end_date, all_day)
VALUES ('Reparation', '2024-01-03 16:45:00', '2024-01-03 17:00:00', 0);
