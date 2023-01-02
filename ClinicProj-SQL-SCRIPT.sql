##Creating table

CREATE TABLE employee(
employeeID	int			    NOT NULL,
SSN			    int			    NOT NULL UNIQUE, /*let the front end take care the rest*/
Fname		    varchar(20) NOT NULL,
Lname		    varchar(20)	NOT NULL,
birthDate	  Date		    NOT NULL,
sex			    char(1)		  NOT NULL,
phone		    bigint		  NOT NULL UNIQUE,
salary		  Float		    NOT NULL,
occupation	varchar(20)	NOT NULL,
PRIMARY KEY (employeeID)
)

CREATE TABLE patient(
patientNum			  int			  	NOT NULL,
birthDate			    date		  	NOT NULL,
insuranceProvider	varchar(20)	NOT NULL,
Fname				      varchar(20)	NOT NULL,
Lname				      varchar(20)	NOT NULL,
sex					      char(1)			NOT NULL,
Phone				      bigint			NOT NULL UNIQUE,
diagnosis			    varchar(100),
emergencyNO			  bigint			NOT NULL UNIQUE, /*use special value if no emergencyNO*/	
PRIMARY KEY (patientNum)
)

CREATE TABLE doctor(
docID				    int				  NOT NULL,
specialization	varchar(20)		NOT NULL,
PRIMARY KEY	(docID),
FOREIGN KEY (docID) REFERENCES employee(employeeID) ON DELETE CASCADE
)

CREATE TABLE emgContact(
emergencyNO		bigint				  NOT NULL,
ECname			  varchar(20)			NOT NULL,
relation		  varchar(20)			NOT NULL,
PRIMARY KEY	(emergencyNO),
FOREIGN KEY (emergencyNO) REFERENCES patient(emergencyNO) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE treat(
patientNum		int		  	NOT NULL,
docID			    int		  	NOT NULL,
timeslot		  DateTime	NOT NULL
PRIMARY KEY (patientNum, docID, timeslot),
FOREIGN KEY (patientNum) REFERENCES patient(patientNum) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (docID) REFERENCES doctor(docID) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE Dschedule(
docID		  	int			  NOT NULL,
schedule		DateTime	NOT NULL,
PRIMARY KEY (docID, schedule),
FOREIGN KEY (docID) REFERENCES doctor(docID) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE drug(
productNUM	      int					NOT NULL,
Dname		          varchar(20)	NOT NULL UNIQUE,
price		          Float				NOT NULL,
reserve		         int,
needPrescription	varchar(1)	NOT NULL,
PRIMARY KEY (productNUM)
)

CREATE TABLE receiveDrug(
patientNum		int		NOT NULL,
drugNUM			  int		NOT NULL,
quantity		  int		NOT NULL,
FOREIGN KEY (patientNum) REFERENCES patient(patientNum) ON UPDATE CASCADE,             
FOREIGN KEY (drugNUM) REFERENCES drug(productNUM) ON UPDATE CASCADE
)
CREATE TABLE trans(
billID		  int			NOT NULL,
patientNum	int			NOT NULL,
employeeID	int			NOT NULL DEFAULT 0,
dateIssued	Date		NOT NULL,
cost		    Float		NOT NULL,
PRIMARY KEY(billID),
FOREIGN KEY (patientNum) REFERENCES patient(patientNum) ON UPDATE CASCADE,
FOREIGN KEY (employeeID) REFERENCES employee(employeeID) ON DELETE SET DEFAULT 
)
CREATE TABLE supplier(
Sname		    varchar(20)		NOT NULL,
phone		    bigint			  NOT NULL UNIQUE,
contact		  varchar(20)		NOT NULL,
PRIMARY KEY (Sname)
)
CREATE TABLE supplies(
orderID			  int			    NOT NULL,
orderDate		  Date		    NOT NULL,
supplierName	varchar(20)	NOT NULL,
employeeID		int			    NOT NULL DEFAULT 0,
prodNum			  int			    NOT NULL,
quantity		  int			    NOT NULL,
price			    float		    NOT NULL,
PRIMARY KEY(orderID),
FOREIGN KEY (supplierName) REFERENCES supplier(Sname) ON UPDATE CASCADE,	
FOREIGN KEY (employeeID) REFERENCES employee(employeeID) ON DELETE SET DEFAULT
)

/*INSERTING DATE */ 
## This is the initial data
## We had alter and inserted more data since then.
INSERT INTO employee VALUES 
(001, 123456789, 'Brandon', 'Fendy', '1996-05-06','M', 1023456789, 90000, 'pharmacist'),
(002, 555000222, 'Diana', 'Hearth', '1997-03-12','F', 1203456789, 90000, 'pharmacist'),
(003, 333456987, 'Isabel', 'Callista', '2000-07-30','F', 1230456789, 70000, 'cashier'),
(004, 694201234, 'Jason', 'Lim', '1990-11-23', 'M', 9114056789, 150000, 'doctor'),
(005, 420691234, 'Ryan', 'Allen', '1990-04-15', 'M', 9114506789, 1500000, 'doctor'),
(006, 123469420, 'Nate', 'Ingram', '1990-03-21', 'M', 9114560789, 1500000, 'doctor'),
(007, 987654321, 'Russel', 'Nixon', '2000-01-01', 'M', 1234567089, 70000, 'cashier'),
(008, 678253611, 'Jalal', 'Omer', '1980-12-03', 'M', 1234567809, 170000, 'head manager'),
(009, 678253612, 'Mary', 'Theresa', '1970-08-26', 'F',1234567890, 95000, 'nurse'),
(010, 678253613, 'Dwayne', 'Johnson', '1980-12-03', 'M', 9090123321,95000,'nurse');

INSERT INTO patient VALUES 
(0001, '1958-08-29', 'NONE', 'Michael', 'Jackson', 'M', 9876543210, 'chest pain and irregular heartbeat', 9998887776),
(0002, '1946-06-14', 'Obama Care', 'Donald', 'Trump', 'M', 9876543201, 'skin itchy and has rash', 9998887666),
(0003, '1986-04-22', 'NONE', 'Amber', 'Heard', 'F', 9879879870, 'abdomidal pain with diarrhea', 5125125122),
(0004, '1958-08-29', 'medicaid', 'Jane', 'Doe', 'F', 4443332221, 'stomache feel like being prick with needles', 4443332221),
(0005, '1954-04-07', 'CCP Insurance', 'Jackie', 'Chan', 'M', 9879878987, 'skin rash', 9879878987);

INSERT INTO emgContact VALUES
(9998887776, 'Elvis', 'friend'),
(9998887666, 'Millenia', 'spouse'), 
(5125125122, 'Camille', 'friend');

INSERT INTO doctor VALUES
(004, 'Gastroenterologists'),
(005, 'Dermatologist'),
(006, 'Cardiologists');

INSERT INTO treat VALUES
(0001, 006, '2022-07-21 14:20:00'),
(0002, 005,  '2022-07-21 15:20:00'),
(0003, 004, '2022-07-21 14:00:00'),
(0004, 004,  '2022-07-22 15:00:00'),
(0005, 005, '2022-07-22 15:20:00');

INSERT INTO Dschedule VALUES
(004,'2022-07-21 14:00:00'),
(004,  '2022-07-21 14:20:00'),
(004,  '2022-07-22 15:00:00'),
(004,  '2022-07-22 15:20:00'),
(005, '2022-07-21 15:00:00'),
(005,  '2022-07-21 15:20:00'),
(005,  '2022-07-22 15:00:00'),
(005, '2022-07-22 15:20:00'),
(006,  '2022-07-21 14:20:00'),
(006, '2022-07-21 14:40:00'),
(006,  '2022-07-22 14:20:00'),
(006,  '2022-07-22 14:40:00');

INSERT INTO trans VALUES
(1001, 0001, 003, '2022-07-21', 700),
(1002, 0002, 003, '2022-07-21', 256),
(1003, 0003, 007, '2022-07-21', 400),
(1004, 0004, 007, '2022-07-22', 1500),
(1005, 0005, 007, '2022-07-22', 420.69);

INSERT INTO supplier VALUES
('UTD Drug Co.', 3331112225, 'Richard Benson'),
('Generic Co.', 1234123412, 'John Doe');

INSERT INTO drug VALUES
(1, 'Aspirin', 10, 1000, 'N'),
(2, 'Cough drop', 5, 100000, 'N'),
(3, 'Dronabinol', 100, 500000, 'Y');

INSERT INTO supplies VALUES
(1, '2022-07-21', 'UTD Drug Co.', 001, 3, 30000, 100000000),
(2, '2022-07-20', 'Generic Co.', 002, 1, 1, 1);

INSERT INTO receiveDrug VALUES
(0001, 3, 3),
(0002, 2, 5),
(0003, 1, 2);

## Script use in report
SELECT AVG(salary), occupation
FROM employee
GROUP BY occupation;

SELECT DISTINCT Fname, LName, schedule, docID 
FROM Dschedule, employee
WHERE docID = 6 AND employeeID = 6 AND schedule NOT IN 
(SELECT timeslot FROM treat);

SELECT Fname, Lname, docID, specialization 
FROM employee, doctor 
WHERE doctor.docID=employeeID order by specialization
