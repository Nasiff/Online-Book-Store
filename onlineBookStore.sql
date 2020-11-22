CREATE TABLE Book (
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(60) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	author VARCHAR(40) NOT NULL,
	category VARCHAR(40) NOT NULL,
	review_score DECIMAL(2,1), 
	number_of_reviews INT NOT NULL,
	image_url VARCHAR(200),
	PRIMARY KEY(bid)
);

INSERT INTO Book (bid, title, price, author, category, review_score, number_of_reviews, image_url) 
VALUES 
('isbn-1786818248', 'The Dancing Girls', 13.99, 'M.M. Chouinard','Thriller', NULL, 0, 'some_url'),
('isbn-1989325149', 'Murder of Crows', 15.49, 'L.L. Abbott', 'Mystery', NULL, 0, 'some_url'),
('isbn-0133915425', 'Engineering Mechanics: Statics & Dynamics', 114.49, 'Russell Hibbeler','Science', NULL, 0, 'some_url'),
('isbn-0141199078', 'Pride and Prejudice', 19.99, 'Jane Austen','Romance', NULL, 0, 'some_url'),
('isbn-1784752637', 'To Kill A Mockingbird', 14.99, 'Harper Lee','Historical', NULL, 0, 'some_url'),
('isbn-0261103252', 'The Lord of the Rings', 24.99, 'J.R.R. Tolkien','Fantasy', NULL, 0, 'some_url'),
('isbn-0316485624', 'The Law of Innocence', 19.99, 'Michael Connelly','Thriller', NULL, 0, 'some_url'),
('isbn-0262033848', 'Introduction to Algorithms', 54.99, 'Thomas H. Cormen','Science', NULL, 0, 'some_url'),
('isbn-1526626586', 'Harry Potter and the Sorceror''s Stone', 19.99, 'J.K. Rowling','Fantasy', NULL, 0, 'some_url'),
('isbn-1408855666', 'Harry Potter and the Chamber of Secrets', 14.99, 'J.K. Rowling','Fantasy', NULL, 0, 'some_url'),
('isbn-1526622807', 'Harry Potter and the Prisoner of Azkaban', 27.99, 'J.K. Rowling','Fantasy', NULL, 0, 'some_url'),
('isbn-0920668372', 'Love You Forever', 7.99, 'Robert Munsch','Children''s', NULL, 0, 'some_url'),
('isbn-1443107859', 'The Very Cranky Bear', 8.49, 'Nick Bland','Children''s', NULL, 0, 'some_url');


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
('address-2', '445 Avenue Rd', 'ON', 'Canada', 'M1C 6K5' ,'416-123-8569'),
('address-3', '789 Keele St', 'ON', 'Canada', 'K3C 9T5' ,'416-123-9568');


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
('order-1', 'isbn-1786818248', 13.99, 3),
('order-1', 'isbn-1989325149', 15.49, 1),
('order-2', 'isbn-0133915425', 114.49, 1),
('order-2', 'isbn-0262033848', 54.99, 2),
('order-3', 'isbn-1526626586', 19.99, 3),
('order-3', 'isbn-1408855666', 14.99, 2),
('order-3', 'isbn-1526622807', 27.99, 1);


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
('review-1', 'isbn-1989325149', 43225, 'Enjoyable read for the most part, but I did not enjoy the ending.', 3),
('review-2', 'isbn-0262033848', 75465, 'Good textbook with helpful practice problems, but had some typos.', 4),
('review-3', 'isbn-0133915425', 75465, 'This textbook was extremely useful and helped me with the course material.', 5),
('review-4', 'isbn-1526626586', 61798, 'Great beginning for this book series, looking forward to the rest of the books.', 4);

