-- Creating database:
CREATE DATABASE rtuFP22;

-- Selecting created "rtuFP22" database for further usage:
USE rtuFP22;	

-- Creating "clients" table in "rtuFP22" database:
CREATE TABLE clients(
cli_id INT AUTO_INCREMENT PRIMARY KEY,
full_name VARCHAR(20) NOT NULL,
phone_number INT NOT NULL UNIQUE );

-- Creating "requests" table in "rtuFP22" database:
	-- We will use "client_id" as a FOREIGN KEY 
	-- in order to relate two tables ("clients" and "requests")
CREATE TABLE requests(
req_id INT AUTO_INCREMENT PRIMARY KEY,
client_id INT,
visit_purpose VARCHAR(50) NOT NULL,
FOREIGN KEY (client_id)
	REFERENCES clients(cli_id)
		ON UPDATE CASCADE
		ON DELETE SET NULL
);

-- Initial filling in the "clients":
	-- It is desirable (but not obligatory) to do this 
    -- to facilitate further testing of the functionality 
	-- of the Java program of the Operator.java class.
INSERT INTO clients (full_name, phone_number)
VALUES 	('Oliver Brown', 21111111),
		('Bob Smith', 22222222),
		('Ava White', 23333333),
		('Oscar Jones', 24444444),
		('Lily Wang', 25555555),
        ('Bob Marley', 66666666),
        ('James Cook',67777777),
        ('Bob Smith', 68888888),
        ('Lily Wang', 69999999),
        ('Oliver Brown', 60000000);

-- Initial filling in the "requests":
	-- It is desirable (but not obligatory) to do this 
    -- to facilitate further testing of the functionality 
	-- of the Java program of the Operator.java class.
INSERT INTO requests(client_id, visit_purpose)
VALUES 	(1, 'Consultation'),
		(2, 'Obtaining/issuing certificates'),
        (3, 'Specialist visit by an appointment'),
        (4, 'Specialist visit without prior appointment'),
        (5, 'Other'),
        (6, 'Other'),
        (7, 'Specialist visit without prior appointment'),
        (8, 'Specialist visit by an appointment'),
        (9, 'Obtaining/issuing certificates'),
        (10, 'Consultation'),
		(1, 'Other'),
        (3, 'Consultation'),
        (5, 'Specialist visit without prior appointment'),
        (7, 'Obtaining/issuing certificates'),
        (9, 'Specialist visit by an appointment');

-- Checking the contents of the tables "clients" and "requests":
SELECT * FROM clients;
SELECT * FROM requests;

-- Combined data retrieving of two tables "clients" and "requests":
SELECT requests.req_id, clients.full_name, clients.phone_number, requests.visit_purpose
FROM requests
INNER JOIN clients ON requests.client_id=clients.cli_id
ORDER BY requests.req_id;

-- If you need to "reset" database tables content,
	-- to the view as they were filled previously 
	-- ("clients" table at line 30 and "requests" table at line 46)
	--  use the following commands:
DELETE FROM clients WHERE cli_id > 10;
ALTER TABLE clients AUTO_INCREMENT = 11;
DELETE FROM requests WHERE req_id > 15;
ALTER TABLE requests AUTO_INCREMENT = 16;