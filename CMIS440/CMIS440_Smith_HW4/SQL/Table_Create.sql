-- Justin Smith
-- CMIS 440
-- Homework 2 SQL

-- Create Catalog Table
CREATE TABLE Catalog_T (
    ID                  NUMBER(*,0)     NOT NULL,
    Name                VARCHAR2(30)    NOT NULL,
    Description         VARCHAR2(2000)  NOT NULL,
    Price               NUMBER(10,2)    NOT NULL,
    QuantityInStock     NUMBER(5)       NOT NULL,

CONSTRAINT Catalog_PK PRIMARY KEY (ID)
);

-- Create some data items
INSERT ALL

    INTO Catalog_T
    VALUES ('12345', 'Widget Wings', 'Wings for your widgets', '25.00', '16')
    
    INTO Catalog_T
    VALUES ('468347', 'Sprocket Glue', 'Glue that sprocket down', '2.35', '20')

    INTO Catalog_T 
    VALUES ('99876', 'Rocket Fuel', 'Price per gallon', '1248.98', '200')

SELECT 1 FROM DUAL;