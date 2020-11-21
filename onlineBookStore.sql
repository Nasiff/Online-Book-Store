CREATE TABLE Book (
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(60) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	author VARCHAR(40) NOT NULL,
	category VARCHAR(40) NOT NULL,
	review_score DECIMAL(2,1), 
	number_of_reviews INT NOT NULL,
	image_url VARCHAR(80),
	PRIMARY KEY(bid)
);

INSERT INTO Book (bid, title, price, author, category, review_score, number_of_reviews, image_url) 
VALUES 
('b001', 'Little Prince', 19.99, 'Patrick Tan',  'Fiction', 3.6, 43, 'some_url'),
('b002', 'Physics', 39.99, 'Nasif Haque', 'Science', 4.3, 65, 'some_url'),
('b003', 'Mechanics', 54.49, 'Dennis Phetsomphou','Engineering', 4.0, 36, 'some_url');


CREATE TABLE Address (
	id VARCHAR(15) NOT NULL,
	street VARCHAR(100) NOT NULL,
	province_state VARCHAR(20) NOT NULL,
	country VARCHAR(20) NOT NULL,
	zip VARCHAR(20) NOT NULL,
	phone VARCHAR(20),
	PRIMARY KEY(id)
);

INSERT INTO Address (id, street, province_state, country, zip, phone) 
VALUES 
('address-1', '123 Yonge St', 'ON', 'Canada', 'K1E 6T5' ,'647-123-4567'),
('address-2', '445 Avenue rd', 'ON', 'Canada', 'M1C 6K5' ,'416-123-8569'),
('address-3', '789 Keele St.', 'ON', 'Canada', 'K3C 9T5' ,'416-123-9568');


CREATE TABLE PO (
	id VARCHAR(15) NOT NULL,
	lname VARCHAR(20) NOT NULL,
	fname VARCHAR(20) NOT NULL,
	status VARCHAR(10) NOT NULL,
	address_id VARCHAR(15) NOT NULL,
	po_date DATE NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (address_id) REFERENCES Address (id) ON DELETE CASCADE
);

INSERT INTO PO (id, lname, fname, status, address_id, po_date) 
VALUES 
('order-1', 'White', 'John', 'PROCESSED', 'address-1', '2020-11-20'),
('order-2', 'Black', 'Peter', 'DENIED', 'address-2', '2020-11-21'),
('order-3', 'Green', 'Andy', 'ORDERED', 'address-3', '2020-11-21');


CREATE TABLE POItem (
	id VARCHAR(15) NOT NULL,
	bid VARCHAR(20) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	quantity INT NOT NULL,
	PRIMARY KEY(id,bid),
	FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);

INSERT INTO POItem (id, bid, price, quantity) 
VALUES 
('order-1', 'b001', 19.99, 3),
('order-2', 'b002', 39.99, 1),
('order-2', 'b003', 54.49, 2),
('order-3', 'b003', 54.49, 1);


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
(43225, 'White', 'John', 'john.white@gmail.com', 'j0hnIsAm@zing' ,'CUSTOMER'),
(26547, 'Smith', 'Bob', 'bob.smith@gmail.com', 'fdsuo32842DFSa' ,'ADMIN'),
(75465, 'Black', 'Peter', 'peter.black@yahoo.com', 'superst@rOf2001' ,'CUSTOMER'),
(48032, 'Newton', 'Andrew', 'andrew.newton@gmail.com', 'biadvsd543iu43y29' ,'PARTNER'),
(61798, 'Green', 'Andy', 'andy.green@hotmail.com', '43fsdf23r2DSdf' ,'CUSTOMER');


CREATE TABLE CustomerAccount (
	uid INT NOT NULL,
	address_id  VARCHAR(15) NOT NULL,
	PRIMARY KEY(uid),
	FOREIGN KEY(uid) REFERENCES UserAccount(uid) ON DELETE CASCADE,
	FOREIGN KEY(address_id) REFERENCES Address(id) ON DELETE CASCADE
);

INSERT INTO CustomerAccount (uid, address_id) 
VALUES 
(43225, 'address-1'),
(75465, 'address-2'),
(61798, 'address-3');


CREATE TABLE BookReview (
	rid VARCHAR(15) NOT NULL,
	bid VARCHAR(20) NOT NULL,
	uid INT NOT NULL,
	review VARCHAR(200) NOT NULL,
	score INT NOT NULL,
	PRIMARY KEY (rid),
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE,
	FOREIGN KEY(uid) REFERENCES CustomerAccount(uid) ON DELETE CASCADE
);

INSERT INTO BookReview (rid, bid, uid, review, score) 
VALUES 
('review-1', 'b001', 43225, 'Enjoyable read, but a little short for the price.', 3),
('review-2', 'b002', 75465, 'Good textbook with helpful practice problems.', 4),
('review-3', 'b003', 75465, 'This textbook was extremely useful and helped me with the course material.', 5),
('review-4', 'b003', 61798, 'Pretty decent textbook with good examples, but a lot of typos although still understandable.', 4);

