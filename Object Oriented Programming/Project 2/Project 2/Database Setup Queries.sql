SHOW databases;
use closecontact;

CREATE TABLE Person(
firstName VARCHAR(20),
middleName VARCHAR(20),
lastName VARCHAR(20),
phone VARCHAR(10),
email VARCHAR(30),
personId INT UNIQUE PRIMARY KEY);

INSERT INTO Person VALUES
('John', 'Michael', 'Davies', '0836665432', 'johnMD76@gmail.com', 00),
('Sara', 'Rey', 'Young', '0838750923', 'sarareyy2@gmail.com', 01),
('Tanner', 'Xavier', 'Welch', '0837632912', 'tannerXwelch@hotmail.com', 02);

SELECT * FROM Person;

CREATE TABLE Record(
personId1 int,
personId2 int,
contactDate DATE);

INSERT INTO Record VALUES
(01, 02, '2021-04-07'),
(00, 01, '2021-05-02');

SELECT * FROM Record;

#Error 1290
#SELECT personId1, personId2, contactDate FROM Record
#INTO OUTFILE 'D:\CIT\Year 2\Semester 2\Object Oriented Programming\Project 2\RecordData.txt';

#DROP TABLE Person;
#DROP TABLE Record;