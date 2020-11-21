CREATE TABLE Book (
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(60) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	author VARCHAR(40) NOT NULL,
	category VARCHAR(40) NOT NULL,
	review_score DECIMAL(2,1), 
	number_of_reviews INT NOT NULL,
	image_url VARCHAR(90),
	PRIMARY KEY(bid)
);

INSERT INTO Book (bid, title, price, author, category, review_score, number_of_reviews) VALUES ('b001', 'Little Prince', 19.99, 'Patrick Tan',  'Fiction', 3.6, 43);
INSERT INTO Book (bid, title, price, author, category, review_score, number_of_reviews) VALUES ('b002', 'Physics', 39.99, 'Nasif Haque', 'Science', 4.3, 65);
INSERT INTO Book (bid, title, price, author, category, review_score, number_of_reviews) VALUES ('b003', 'Mechanics', 54.49, 'Dennis Phetsomphou','Engineering', 4.0, 36);


CREATE TABLE Address (
	id INT NOT NULL,
	street VARCHAR(100) NOT NULL,
	province_state VARCHAR(20) NOT NULL,
	country VARCHAR(20) NOT NULL,
	zip VARCHAR(20) NOT NULL,
	phone VARCHAR(20),
	PRIMARY KEY(id)
);
INSERT INTO Address (id, street, province_state, country, zip, phone) VALUES (1, '123 Yonge St', 'ON', 'Canada', 'K1E 6T5' ,'647-123-4567');
INSERT INTO Address (id, street, province_state, country, zip, phone) VALUES (2, '445 Avenue rd', 'ON', 'Canada', 'M1C 6K5' ,'416-123-8569');
INSERT INTO Address (id, street, province_state, country, zip, phone) VALUES (3, '789 Keele St.', 'ON', 'Canada', 'K3C 9T5' ,'416-123-9568');


CREATE TABLE PO (
	id INT NOT NULL,
	lname VARCHAR(20) NOT NULL,
	fname VARCHAR(20) NOT NULL,
	status VARCHAR(10) NOT NULL,
	address INT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (address) REFERENCES Address (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX address on Address (id);

INSERT INTO PO (id, lname, fname, status, address) VALUES (1, 'White', 'John', 'PROCESSED', 1);
INSERT INTO PO (id, lname, fname, status, address) VALUES (2, 'Black', 'Peter', 'DENIED', 2);
INSERT INTO PO (id, lname, fname, status, address) VALUES (3, 'Green', 'Andy', 'ORDERED', 3);


CREATE TABLE POItem (
	id INT NOT NULL,
	bid VARCHAR(20) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	PRIMARY KEY(id,bid),
	FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);
CREATE UNIQUE INDEX id on POItem (id);
CREATE UNIQUE INDEX bid on POItem (bid);

INSERT INTO POItem (id, bid, price) VALUES (1, 'b001', 19.99);
INSERT INTO POItem (id, bid, price) VALUES (2, 'b002', 39.99);
INSERT INTO POItem (id, bid, price) VALUES (3, 'b003', 54.49);


CREATE TABLE UserAccount (
	uid INT NOT NULL,
	lname VARCHAR(20) NOT NULL,
	fname VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL,
	password VARCHAR(20) NOT NULL,
	user_type VARCHAR(10) NOT NULL,
	PRIMARY KEY(uid)
);

INSERT INTO UserAccount (uid, lname, fname, email, password, user_type) 
VALUES 
(1, 'White', 'John', 'john.white@gmail.com', 'j0hnIsAm@zing' ,'CUSTOMER'),
(2, 'Smith', 'Bob', 'bob.smith@gmail.com', 'fdsuo32842DFSa' ,'ADMIN'),
(3, 'Black', 'Peter', 'peter.black@yahoo.com', 'superst@rOf2001' ,'CUSTOMER'),
(4, 'Newton', 'Andrew', 'andrew.newton@gmail.com', 'biadvsd543iu43y29' ,'PARTNER'),
(5, 'Green', 'Andy', 'andy.green@hotmail.com', '43fsdf23r2DSdf' ,'CUSTOMER');

CREATE TABLE CustomerAccount (
	uid INT NOT NULL,
	credit_card CHAR(16) NOT NULL,
	address_id INT NOT NULL,
	PRIMARY KEY(uid),
	FOREIGN KEY(uid) REFERENCES UserAccount(uid) ON DELETE CASCADE,
	FOREIGN KEY(address_id) REFERENCES Address(id) ON DELETE CASCADE
);

INSERT INTO CustomerAccount (uid, credit_card, address_id) 
VALUES 
(1, '3187241291421453', 1),
(3, '5253245436361453', 2),
(5, '5769823758923324', 3);

