/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  05/10/14
**  Final Project
*/


-- DROP EVERYTHING BEFORE CREATING
DROP TABLE SALES;
DROP TABLE AGENT;
DROP TABLE PERIOD;
DROP TABLE TERRITORY;
DROP TABLE POLICY;


-- Create Tables
CREATE TABLE POLICY 
(
  POLICYCODE NUMBER NOT NULL 
, PRODUCTID NUMBER NOT NULL 
, TYPE VARCHAR2(20) NOT NULL 
, CONSTRAINT POLICY_PK PRIMARY KEY 
  (
    POLICYCODE 
  )
  ENABLE 
);

CREATE TABLE AGENT 
(
  AGENTCODE NUMBER NOT NULL 
, AGENTID NUMBER NOT NULL 
, AGENTNAME VARCHAR2(20) NOT NULL 
, CONSTRAINT AGENT_PK PRIMARY KEY 
  (
    AGENTCODE 
  )
  ENABLE 
);

CREATE TABLE PERIOD 
(
  PERIODCODE NUMBER(3) NOT NULL 
, YEAR NUMBER(4) NOT NULL 
, QUARTER NUMBER(1) NOT NULL 
, MONTH NUMBER(2) NOT NULL 
, CONSTRAINT PERIOD_PK PRIMARY KEY 
  (
    PERIODCODE 
  )
  ENABLE 
);

CREATE TABLE TERRITORY 
(
  TERRITORYCODE NUMBER NOT NULL 
, ZIPCODE VARCHAR2(5) NOT NULL 
, CONSTRAINT TERRITORY_PK PRIMARY KEY 
  (
    TERRITORYCODE 
  )
  ENABLE 
);

CREATE TABLE SALES 
(
  POLICYCODE NUMBER NOT NULL 
, AGENTCODE NUMBER NOT NULL 
, TERRITORYCODE NUMBER NOT NULL 
, PERIODCODE NUMBER NOT NULL 
, FACEVALUE NUMBER NOT NULL 
, EFFECTIVEDATE DATE NOT NULL 
, INFORCE CHAR NOT NULL 
, COMMISSION NUMBER NOT NULL 
, CONSTRAINT SALES_PK PRIMARY KEY 
  (
    POLICYCODE 
  , AGENTCODE 
  , TERRITORYCODE 
  , PERIODCODE 
  )
  ENABLE 
);

ALTER TABLE SALES
ADD CONSTRAINT SALES_AGENT_FK FOREIGN KEY
(
  AGENTCODE 
)
REFERENCES AGENT
(
  AGENTCODE 
)
ENABLE;

ALTER TABLE SALES
ADD CONSTRAINT SALES_PERIOD_FK FOREIGN KEY
(
  PERIODCODE 
)
REFERENCES PERIOD
(
  PERIODCODE 
)
ENABLE;

ALTER TABLE SALES
ADD CONSTRAINT SALES_POLICY_FK FOREIGN KEY
(
  POLICYCODE 
)
REFERENCES POLICY
(
  POLICYCODE 
)
ENABLE;

ALTER TABLE SALES
ADD CONSTRAINT SALES_TERRITORY_FK FOREIGN KEY
(
  TERRITORYCODE 
)
REFERENCES TERRITORY
(
  TERRITORYCODE 
)
ENABLE;



-- Crate Test Data

INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('1', '100', 'Smith', '22485')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('2', '101', 'Harper', '22640')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('3', '102', 'Johnson', '22640')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('4', '103', 'Sierra', '20601')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('5', '104', 'Green', '20606')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('6', '105', 'Hoffer', '22785')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('7', '106', 'Dewer', '23489')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('8', '107', 'Tyson', '20601')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('9', '108', 'Jordan', '20606')
INSERT INTO AGENT (AGENTCODE, AGENTID, AGENTNAME, ASSIGNEDTERRITORY) VALUES ('10', '109', 'Williams', '23489')


INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('1', '2013', '1', '1')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('2', '2013', '1', '2')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('3', '2013', '1', '3')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('4', '2013', '2', '4')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('5', '2013', '2', '5')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('6', '2013', '2', '6')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('7', '2013', '3', '7')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('8', '2013', '3', '8')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('9', '2013', '3', '9')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('10', '2013', '4', '10')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('11', '2013', '4', '11')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('12', '2013', '4', '12')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('13', '2014', '1', '1')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('14', '2014', '1', '2')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('15', '2014', '1', '3')
INSERT INTO PERIOD (PERIODCODE, YEAR, QUARTER, MONTH) VALUES ('16', '2014', '2', '4')


INSERT INTO TERRITORY (TERRITORYCODE, ZIPCODE) VALUES ('1', '22485')
INSERT INTO TERRITORY (TERRITORYCODE, ZIPCODE) VALUES ('2', '22640')
INSERT INTO TERRITORY (TERRITORYCODE, ZIPCODE) VALUES ('3', '20601')
INSERT INTO TERRITORY (TERRITORYCODE, ZIPCODE) VALUES ('4', '20606')
INSERT INTO TERRITORY (TERRITORYCODE, ZIPCODE) VALUES ('5', '22785')
INSERT INTO TERRITORY (TERRITORYCODE, ZIPCODE) VALUES ('6', '23489')


