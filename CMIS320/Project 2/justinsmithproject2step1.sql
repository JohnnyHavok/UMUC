-- Project 2 SQL Step 1
-- Justin Smith
-- CMIS 320

-- Create CustomerID Sequence
-- To use SeqCustomerID.nextval
CREATE SEQUENCE SeqCustomerID START WITH 1000 INCREMENT BY 1;

-- Create all tables

/*
 * Customer Table:
 * Customer_T
 * Primary Key: CustomerID, use Sequence SeqCustomerID
 */

CREATE TABLE Customer_T
    (CustomerID         NUMBER(*,0)     NOT NULL,
     CustomerLName      VARCHAR2(20)    NOT NULL,
     CustomerFName      VARCHAR2(20)    NOT NULL,
     CustomerSAddress   VARCHAR2(30)    NOT NULL,
     CustomerCity       VARCHAR2(20)    NOT NULL,
     CustomerState      CHAR(2)         NOT NULL,
     CustomerZIP        VARCHAR2(5)     NOT NULL,
     CustomerPhone      VARCHAR(10)     NOT NULL,

CONSTRAINT CustomerID_PK PRIMARY KEY (CustomerID));
