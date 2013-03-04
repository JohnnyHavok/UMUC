-- Project 2 SQL Step 3
-- Justin Smith
-- CMIS 320

-- Create some customers.
INSERT INTO Customer_T
VALUES ('1018', 'Hall', 'Justin', '13 Jackson Street', 'Waldorf', 'MD', '20603', '4579845600');

INSERT INTO Customer_T
VALUES ('1021', 'Parker', 'Peter', '1289 Main Street', 'Las Vegas', 'NV', '74588', '8044681278');

INSERT INTO Customer_T
VALUES ('1024', 'Jones', 'Tom', '15J 12th Ave', 'Boise', 'ID', '12458', '5204879865');

INSERT INTO Customer_T
VALUES ('1027', 'James', 'Rick', '1200 Parkplace St', 'Dallas', 'TX', '76849', '5204578898');

INSERT INTO Customer_T
VALUES ('1030', 'Smith', 'Sarah', '49 W Boardwalk', 'Fairfax', 'VA', '23587', '5204879865');


-- Create some distributors
INSERT INTO Distributors_T
VALUES ('0001', 'ABCRentalVideos', '12345', '35');

INSERT INTO Distributors_T
VALUES ('19784', 'ACME LTD', '9987462', '45.8');

INSERT INTO Distributors_T
VALUES ('1245', 'EZTV PLC', '8821', '50');

INSERT INTO Distributors_T
VALUES ('45796', 'RapidVideo Dist', '4578', '10');

INSERT INTO Distributors_T
VALUES ('1911', 'TheVideoDealer', '98', '0');

-- Create some Movies
INSERT INTO Movies_T
VALUES ('2012-TED', 'TED', 'Comedy', 'R', '2012');

INSERT INTO Movies_T
VALUES ('45892', 'Batman Begins', 'Action', 'PG-13', '2005');

INSERT INTO Movies_T
VALUES ('2003-NEMO', 'Finding Nemo', 'Childrens', 'G', '2003');

INSERT INTO Movies_T
VALUES ('1823', 'Terminator 2', 'Sci-Fi', 'R', '1991');

INSERT INTO Movies_T
VALUES ('PF', 'Pulp Fiction', 'Thriller', 'R', '1994');

-- Create some catalogs
INSERT INTO Catalogs_T
VALUES ('0001', '1', 'DVD', '25.00');

INSERT INTO Catalogs_T
VALUES ('0001', '2', 'DVD', '25.00');

INSERT INTO Catalogs_T
VALUES ('0001', '3', 'DVD', '25.00');

INSERT INTO Catalogs_T
VALUES ('1245', '210', 'VHS', '15.00');

INSERT INTO Catalogs_T
VALUES ('45796', '18796', 'DVD', '25.00');

-- Create some inventory items
INSERT INTO Inventory_T (InventoryID, Format, MovieID, DistributorID, SerialNumber)
VALUES ('1291', 'DVD', '2012-TED', '0001', '1');

INSERT INTO Inventory_T (InventoryID, Format, MovieID, DistributorID, SerialNumber)
VALUES ('1298', 'VHS', '2003-NEMO', '1245', '210');

INSERT INTO Inventory_T (InventoryID, Format, MovieID, DistributorID, SerialNumber)
VALUES ('2500', 'DVD', '45892', '45796', '18796');

INSERT INTO Inventory_T (InventoryID, Format, MovieID, DistributorID, SerialNumber)
VALUES ('2501', 'DVD', '45892', '45796', '18796');

INSERT INTO Inventory_T (InventoryID, Format, MovieID, DistributorID, SerialNumber)
VALUES ('2502', 'DVD', '45892', '45796', '18796');

INSERT INTO Inventory_T (InventoryID, Format, MovieID, DistributorID, SerialNumber)
VALUES ('3500', 'DVD', 'PF', '0001', '2');

INSERT INTO Inventory_T (InventoryID, Format, MovieID, DistributorID, SerialNumber)
VALUES ('3501', 'DVD', 'PF', '0001', '2');

-- Create some rentals
INSERT INTO Rentals_T (RentalOrderID, CustomerID, Taxes)
VALUES ('10001', '1018', '1.20');

INSERT INTO Rentals_T (RentalOrderID, CustomerID, Taxes)
VALUES ('10002', '1021', '1.25');

INSERT INTO Rentals_T (RentalOrderID, CustomerID, Taxes)
VALUES ('10003', '1024', '3.15');

INSERT INTO Rentals_T (RentalOrderID, CustomerID, Taxes)
VALUES ('10004', '1027', '3.15');

INSERT INTO Rentals_T (RentalOrderID, CustomerID, Taxes)
VALUES ('10005', '1030', '3.15');

-- Create some RentalLines
INSERT INTO RentalLine_T (RentalOrderID, InventoryID, ReturnDate)
VALUES ('10001', '3501', SYSDATE + 5);

INSERT INTO RentalLine_T (RentalOrderID, InventoryID, ReturnDate)
VALUES ('10001', '2502', SYSDATE + 5);

INSERT INTO RentalLine_T (RentalOrderID, InventoryID, ReturnDate)
VALUES ('10002', '1291', SYSDATE + 3);

INSERT INTO RentalLine_T (RentalOrderID, InventoryID, ReturnDate)
VALUES ('10003', '1298', SYSDATE + 10);

INSERT INTO RentalLine_T (RentalOrderID, InventoryID, ReturnDate)
VALUES ('10003', '2500', SYSDATE + 3);

INSERT INTO RentalLine_T (RentalOrderID, InventoryID, ReturnDate)
VALUES ('10004', '2501', SYSDATE + 5);

INSERT INTO RentalLine_T (RentalOrderID, InventoryID, ReturnDate)
VALUES ('10005', '3500', SYSDATE + 5);

-- Create some awards
INSERT INTO Awards_T
VALUES ('Oscars');

-- Create some Actors
INSERT ALL
	INTO Actors_T VALUES ('Arnold Schwarzenegger')
	INTO Actors_T VALUES ('Bruce Willis')
	INTO Actors_T VALUES ('Samual Jackson')
	INTO Actors_T VALUES ('Christian Bale')
	INTO Actors_T VALUES ('Mark Wahlberg')
SELECT 1 FROM DUAL;

-- Create some directors
INSERT ALL
	INTO Directors_T VALUES ('Christopher Nolan')
	INTO Directors_T VALUES ('Quentin Tarantino')
	INTO Directors_T VALUES ('Andrew Stanton')
	INTO Directors_T VALUES ('Lee Unkrich')
	INTO Directors_T VALUES ('Seth MacFarlane')
SELECT 1 FROM DUAL;

-- Some awards given
INSERT ALL
	INTO AwardsGiven_T VALUES ('PF', 'Oscars')
	INTO AwardsGiven_T VALUES ('45892', 'Oscars')
SELECT 1 FROM DUAL;

-- Assign some actors
INSERT ALL
  INTO ActorsFeatured_T VALUES ('1823', 'Arnold Schwarzenegger')
  INTO ActorsFeatured_T VALUES ('PF', 'Bruce Willis')
  INTO ActorsFeatured_T VALUES ('PF', 'Samual Jackson')
  INTO ActorsFeatured_T VALUES ('2012-TED', 'Mark Wahlberg')
  INTO ActorsFeatured_T VALUES ('45892', 'Christian Bale')
SELECT 1 FROM DUAL;

-- Assign some directors
INSERT ALL
  INTO DirectedBy_T VALUES ('2003-NEMO', 'Lee Unkrich')
  INTO DirectedBy_T VALUES ('PF', 'Quentin Tarantino')
  INTO DirectedBy_T VALUES ('2003-NEMO', 'Andrew Stanton')
  INTO DirectedBy_T VALUES ('2012-TED', 'Seth MacFarlane')
  INTO DirectedBy_T VALUES ('45892', 'Christopher Nolan')
SELECT 1 FROM DUAL;






