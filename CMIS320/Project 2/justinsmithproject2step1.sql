-- Project 2 SQL Step 1
-- Justin Smith
-- CMIS 320

-- Create CustomerID Sequence
-- To use SeqCustomerID.nextval
CREATE SEQUENCE SeqCustomerID START WITH 1000 INCREMENT BY 3;

-- Create RentalOrderID Sequence
-- To use SeqRentalOrderID.nextval
CREATE SEQUENCE SeqRentalOrderID START WITH 10000 INCREMENT BY 7;

-- Create tables with no foreign keys first
-- Begin Customer_T
CREATE TABLE Customer_T (
    CustomerID         NUMBER(*,0)     NOT NULL,
    CustomerLName      VARCHAR2(20)    NOT NULL,
    CustomerFName      VARCHAR2(20)    NOT NULL,
    CustomerSAddress   VARCHAR2(30)    NOT NULL,
    CustomerCity       VARCHAR2(20)    NOT NULL,
    CustomerState      CHAR(2)         NOT NULL,
    CustomerZIP        VARCHAR2(5)     NOT NULL,
    CustomerPhone      VARCHAR(10)     NOT NULL,

CONSTRAINT Customer_PK PRIMARY KEY (CustomerID) );
-- End Customer_T

-- Begin Movies_T
CREATE TABLE Movies_T (
    MovieID            VARCHAR2(10)    NOT NULL,
    Title              VARCHAR2(20)    NOT NULL,
    Genre              VARCHAR2(10)    NOT NULL,
    Rating             VARCHAR2(6),
    YearRelease        DATE            NOT NULL,

CONSTRAINT Movies_PK PRIMARY KEY (MovieID) );
-- End Movies_T

-- Begin Distributors_T
CREATE TABLE Distributors_T (
    DistributorID   VARCHAR2(10)    NOT NULL,
    DistributorName VARCHAR2(15)    NOT NULL,
    AccountNumber   NUMBER(10, 0)   NOT NULL,
    DiscountRate    NUMBER(3,2)     NOT NULL,

CONSTRAINT Distributors_PK PRIMARY KEY (DistributorID) );
-- End Distributors_T

-- Begin Catalogs_T
CREATE TABLE Catalogs_T (
    DistributorID   VARCHAR2(10)    NOT NULL,
    SerialNumber    VARCHAR2(10)    NOT NULL,
    Format          CHAR(3)         NOT NULL,
    WholesaleCost   NUMBER(10,2)    NOT NULL,

CONSTRAINT Catalogs_PK PRIMARY KEY (DistributorID, SerialNumber),
CONSTRAINT Catalogs_FK FOREIGN KEY (DistributorID) REFERENCES Distributors_T(DistributorID) );
-- End Catalogs_T

-- Begin Inventory_T
CREATE TABLE Inventory_T (
    InventoryID     NUMBER(*,0)     NOT NULL,
    Format          CHAR(3)         NOT NULL,
    DateAdded       DATE DEFAULT (SYSDATE) NOT NULL,
    MovieID         VARCHAR2(10)    NOT NULL,
    DistributorID   VARCHAR2(10)    NOT NULL,
    SerialNumber    VARCHAR2(10)    NOT NULL,

CONSTRAINT Inventory_PK PRIMARY KEY (InventoryID),
CONSTRAINT Inventory_FK1 FOREIGN KEY (MovieID) REFERENCES Movies_T(MovieID),
CONSTRAINT Inventory_FK2 FOREIGN KEY (DistributorID) REFERENCES Distributors_T(DistributorID),
CONSTRAINT Inventory_FK3 FOREIGN KEY (SerialNumber) REFERENCES Catalogs_T(SerialNumber) );
-- End Inventory_T

-- Begin Discounts_T
CREATE TABLE Discounts_T (
    InventoryID     NUMBER(*,0)     NOT NULL,
    DiscountRate    NUMBER(10,2)    NOT NULL,
    DisccountExpiry DATE DEFAULT (SYSDATE) NOT NULL,

CONSTRAINT Discounts_PK PRIMARY KEY (InventoryID),
CONSTRAINT Discounts_FK FOREIGN KEY (InventoryID) REFERENCES Inventory_T(InventoryID) );
-- End Discounts_T

-- Begin Rentals_T
CREATE TABLE Rentals_T (
    RentalOrderID      NUMBER(*,0)              NOT NULL,
    CustomerID         NUMBER(*,0)              NOT NULL,
    RentalDate         DATE DEFAULT (SYSDATE)   NOT NULL,
    Taxes              NUMBER(*,2)              NOT NULL,

CONSTRAINT Rentals_PK PRIMARY KEY (RentalOrderID),
CONSTRAINT Rentals_FK FOREIGN KEY (CustomerID) REFERENCES Customer_T(CustomerID) );
-- End Rentals_T

-- Begin RentalLine_T
CREATE TABLE RentalLine_T (
    RentalOrderID      NUMBER(*,0)              NOT NULL,
    InventoryID        NUMBER(*,0)              NOT NULL,
    ReturnDate         DATE DEFAULT (SYSDATE)   NOT NULL,
    DateReturned       DATE DEFAULT (SYSDATE),

CONSTRAINT RentalLine_PK PRIMARY KEY (RentalOrderID, InventoryID),
CONSTRAINT OrderLine_FK1 FOREIGN KEY (RentalOrderID) REFERENCES Rentals_T(RentalOrderID),
CONSTRAINT OrderLine_FK2 FOREIGN KEY (InventoryID) REFERENCES Inventory_T(InventoryID) );
-- End RentalLine_T

-- Begin Fees_T
CREATE TABLE Fees_T (
    CustomerID      NUMBER(*,0)     NOT NULL,
    RentalOrderID   NUMBER(*,0)     NOT NULL,
    InventoryID     NUMBER(*,0)     NOT NULL,
    LateFee         NUMBER(10,2),
    DamageFee       NUMBER(10,2),
    RewindFee       NUMBER(10,2),
    ReplacementFee  NUMBER(10,2),

CONSTRAINT Fees_PK  PRIMARY KEY (CustomerID, RentalOrderID, InventoryID),
CONSTRAINT Fees_FK1 FOREIGN KEY (CustomerID) REFERENCES Customer_T(CustomerID),
CONSTRAINT Fees_FK2 FOREIGN KEY (RentalOrderID) REFERENCES Rentals_T(RentalOrderID),
CONSTRAINT Fees_FK3 FOREIGN KEY (InventoryID) REFERENCES Inventory_T(InventoryID) );
-- End Fees_T





