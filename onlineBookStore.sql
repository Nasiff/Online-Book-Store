/* Book
* bid: unique identifier of Book (like ISBN)
* title: title of Book
* price: unit price WHEN ordered
* author: name of authors
* category: as specified
* review_score: aggregated score from all reviews of the book
* number_of_reviews: total number of reviews for the book
*/
DROP TABLE if exists Book;
CREATE TABLE Book (
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(60) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	author VARCHAR(40) NOT NULL,
	category ENUM('Science','Fiction','Engineering', 'Fantasy', 'Adventure', 'Romance', 'Contemporary', 'Mystery', 'Horror', 
		'Thriller', 'History', 'Cooking', 'Health', 'Travel', 'How To\'s', 'Ranking', 'Humor', 'Children\'s', 'Other') NOT NULL,
	review_score DECIMAL(2,1), 
	number_of_reviews INT NOT NULL,
	--image_url VARCHAR(60),
	PRIMARY KEY(bid)
);

--Adding data for table 'Book'
INSERT INTO Book (bid, title, price, author, category, review, number_of_reviews) VALUES ('b001', 'Little Prince', 19.99, 'Patrick Tan',  'Fiction', 3.6, 43);
INSERT INTO Book (bid, title, price, author, category, review, number_of_reviews) VALUES ('b002', 'Physics', 39.99, 'Nasif Haque', 'Science', 4.3, 65);
INSERT INTO Book (bid, title, price, author, category, review, number_of_reviews) VALUES ('b003', 'Mechanics', 54.49, 'Dennis Phetsomphou','Engineering', 4.0, 36);

/* Address
* id: address id
*
*/
DROP TABLE if exists Address;
CREATE TABLE Address (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	street VARCHAR(100) NOT NULL,
	province_state VARCHAR(20) NOT NULL,
	country VARCHAR(20) NOT NULL,
	zip VARCHAR(20) NOT NULL,
	phone VARCHAR(20),
	PRIMARY KEY(id)
);

--Inserting data for table 'address'
INSERT INTO Address (id, street, province_state, country, zip, phone) VALUES (1, '123 Yonge St', 'ON', 'Canada', 'K1E 6T5' ,'647-123-4567');
INSERT INTO Address (id, street, province_state, country, zip, phone) VALUES (2, '445 Avenue rd', 'ON', 'Canada', 'M1C 6K5' ,'416-123-8569');
INSERT INTO Address (id, street, province_state, country, zip, phone) VALUES (3, '789 Keele St.', 'ON', 'Canada', 'K3C 9T5' ,'416-123-9568');

/* Purchase Order
* lname: last name
* fname: first name
* id: purchase order id
* status: status of purchase
*/
DROP TABLE if exists PO;
CREATE TABLE PO (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	lname VARCHAR(20) NOT NULL,
	fname VARCHAR(20) NOT NULL,
	status ENUM('ORDERED','PROCESSED','DENIED') NOT NULL,
	address INT UNSIGNED NOT NULL,
	PRIMARY KEY(id),
	INDEX (address),
	FOREIGN KEY (address) REFERENCES Address (id) ON DELETE CASCADE
);

--Inserting data for table 'PO'
INSERT INTO PO (id, lname, fname, status, address) VALUES (1, 'John', 'White', 'PROCESSED', '1');
INSERT INTO PO (id, lname, fname, status, address) VALUES (2, 'Peter', 'Black', 'DENIED', '2');
INSERT INTO PO (id, lname, fname, status, address) VALUES (3, 'Andy', 'Green', 'ORDERED', '3');


/* Items on order
* id : purchase order id
* bid: unique identifier of Book
* price: unit price
*/
DROP TABLE if exists POItem;
CREATE TABLE POItem (
	id INT UNSIGNED NOT NULL,
	bid VARCHAR(20) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	PRIMARY KEY(id,bid),
	INDEX (id),
	FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
	INDEX (bid),
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);

--Inserting data for table 'POitem'
INSERT INTO POItem (id, bid, price) VALUES (1, 'b001', '20');
INSERT INTO POItem (id, bid, price) VALUES (2, 'b002', '201');
INSERT INTO POItem (id, bid, price) VALUES (3, 'b003', '100');


DROP TABLE if exists User;
CREATE TABLE User(
	uid INT UNSIGNED NOT NULL,
	lname VARCHAR(20) NOT NULL,
	fname VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL,
	password VARCHAR(20) NOT NULL,
	user_type ENUM('CUSTOMER','ADMIN','PARTNER') NOT NULL,
	PRIMARY KEY(uid)
);

DROP TABLE if exists Customer;
CREATE TABLE Customer(
	uid INT UNSIGNED NOT NULL,
	credit_card CHAR(16) NOT NULL,
	address_id INT UNSIGNED NOT NULL,
	PRIMARY KEY(uid),
	FOREIGN KEY(uid) REFERENCES User(uid) ON DELETE CASCADE,
	FOREIGN KEY(address_id) REFERENCES Address(id) ON DELETE CASCADE
);

/* visit to website
* day: date
* bid: unique identifier of Book
* eventtype: status of purchase
*/
DROP TABLE if exists VisitEvent;
CREATE TABLE VisitEvent (
	day varchar(8) NOT NULL,
	time char(8) NOT NULL,
	bid varchar(20) not null REFERENCES Book.bid,
	eventtype ENUM('VIEW','CART','PURCHASE') NOT NULL,
	FOREIGN KEY(bid) REFERENCES Book(bid)
);

--Dumping data for table 'VisitEvent'
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12202015', '11:23:45', 'b001', 'VIEW');
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12242015', '14:58:24', 'b001', 'CART');
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12252015', '17:32:17', 'b001', 'PURCHASE');
