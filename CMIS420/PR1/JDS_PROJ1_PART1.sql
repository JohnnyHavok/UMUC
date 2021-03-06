/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  04/05/14
**  Project 1 - Part 1
*/

-- BEGIN Problem 1
PROMPT ==== Problem 1 ====
CREATE VIEW BUSY_STUDENT
AS SELECT (First_Name || ' ' || Last_Name) AS FULL_NAME, S.Student_ID, E.ENROLL_NUM 
  FROM STUDENT S JOIN 
  (
    SELECT Student_ID, COUNT(Student_ID) AS ENROLL_NUM
      FROM ENROLLMENT
      HAVING COUNT(Student_ID) > 2
      GROUP BY Student_ID
  ) E ON S.Student_ID = E.Student_ID;
-- END Problem 1


-- BEGIN Problem 2
-- Create Table with Constraints
PROMPT ==== Problem 2 ====
CREATE TABLE TEMP_STUDENT
( 
  STUD_ID NUMBER(8) NOT NULL,
  FIRST_NAME VARCHAR2(25),
  LAST_NAME VARCHAR2(25),
  ZIP VARCHAR2(5),
  REGISTRATION_DATE DATE NOT NULL,
  CONSTRAINT TEMP_STUDENT_PK PRIMARY KEY (STUD_ID),
  CONSTRAINT ZIP_ZIPCODE_FK FOREIGN KEY (ZIP) REFERENCES ZIPCODE(ZIP),
  CONSTRAINT REGISTRATION_DATE_CHECK CHECK
    (REGISTRATION_DATE > TO_DATE('26-AUG-2005','DD-MON-YYYY'))
);

-- Violates REGISTRATION_DATE NOT NULL CONSTRAINT
INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP)
VALUES (1234, 'Justin', 'Smith', '22485');

-- Violates REGISTRATION_DATE BEFORE 26-AUG-2005 CONSTRAINT
INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP, REGISTRATION_DATE)
VALUES (1234, 'Justin', 'Smith', '07021', TO_DATE('12-AUG-2005', 'DD-MON-YYYY'));

-- Violates ZIPCODE.ZIP FK Reference CONSTRAINT
INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP, REGISTRATION_DATE)
VALUES (1234, 'Justin', 'Smith', '22485', TO_DATE('27-AUG-2005', 'DD-MON-YYYY'));

-- Three TEMP_STUDENT records will be added sucessfully
INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP, REGISTRATION_DATE)
VALUES (1234, 'Justin', 'Smith', '02189', TO_DATE('27-AUG-2005', 'DD-MON-YYYY'));

INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP, REGISTRATION_DATE)
VALUES (1235, 'Sarah', 'Johnson', '07021', TO_DATE('28-AUG-2005', 'DD-MON-YYYY'));

INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP, REGISTRATION_DATE)
VALUES (1236, 'Steven', 'Edwards', '07021', TO_DATE('28-AUG-2005', 'DD-MON-YYYY'));
-- END Problem 2

-- BEGIN Problem 3
PROMPT ==== Problem 3 ====
SELECT Employer, COUNT(Employer) AS Num_Students_Employed
FROM STUDENT
HAVING COUNT(Employer) > 4
GROUP BY Employer
ORDER BY COUNT(Employer) DESC;
-- END Problem 3

-- BEGIN Problem 4
PROMPT ==== Problem 4 ====
SELECT Section_ID, MAX(Numeric_Grade)
FROM GRADE 
  WHERE Grade_Type_Code = 'MT'
GROUP BY Section_ID;
-- END Problem 4

-- BEGIN Problem 5
PROMPT ==== Problem 5 ====
SELECT SECTION_ID, TO_CHAR(START_DATE_TIME, 'HH24:MI') AS Start_Time
FROM SECTION
  WHERE TO_CHAR(START_DATE_TIME, 'HH24MI') = '1030'
ORDER BY SECTION_ID;
-- END Problem 5

-- BEGIN Problem 6
PROMPT ==== Problem 6 ====
SELECT TO_CHAR(Start_Date_Time, 'DY') AS DAY, Section_ID
FROM SECTION
  WHERE Section_ID IN (83, 86, 107)
ORDER BY DAY;
-- END Problem 6




/* RESULTS FROM RUNNING ABOVE SCRIPT

SQL> @jds_proj1_part1
==== Problem 1 ====

View created.

==== Problem 2 ====

Table created.

INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP)
*
ERROR at line 1:
ORA-01400: cannot insert NULL into
("JSMITH"."TEMP_STUDENT"."REGISTRATION_DATE")


INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP, REGISTRATION_DATE)
*
ERROR at line 1:
ORA-02290: check constraint (JSMITH.REGISTRATION_DATE_CHECK) violated


INSERT INTO TEMP_STUDENT (STUD_ID, FIRST_NAME, LAST_NAME, ZIP, REGISTRATION_DATE)
*
ERROR at line 1:
ORA-02291: integrity constraint (JSMITH.ZIP_ZIPCODE_FK) violated - parent key
not found



1 row created.


1 row created.


1 row created.

==== Problem 3 ====

EMPLOYER                                           NUM_STUDENTS_EMPLOYED
-------------------------------------------------- ---------------------
Electronic Engineers                                                  15
Amer.Legal Systems                                                    10
New York Pop                                                           8
Crane Co.                                                              6

==== Problem 4 ====

SECTION_ID MAX(NUMERIC_GRADE)
---------- ------------------
        80                 76
        81                 88
        83                 99
        84                 88
        85                 99
        86                 92
        87                 92
        89                 99
        90                 92
        91                 85
        92                 92

SECTION_ID MAX(NUMERIC_GRADE)
---------- ------------------
        94                 99
        95                 99
        96                 88
        99                 99
       100                 99
       101                 99
       102                 77
       103                 99
       104                 99
       105                 92
       106                 88

SECTION_ID MAX(NUMERIC_GRADE)
---------- ------------------
       107                 92
       108                 88
       109                 88
       111                 90
       112                 99
       113                 91
       116                 99
       117                 99
       119                 99
       120                 92
       123                 85

SECTION_ID MAX(NUMERIC_GRADE)
---------- ------------------
       125                 88
       126                 99
       127                 83
       128                 92
       130                 85
       131                 91
       132                 91
       135                 92
       138                 92
       140                 99
       141                 92

SECTION_ID MAX(NUMERIC_GRADE)
---------- ------------------
       142                 91
       143                 88
       145                 91
       146                 99
       147                 99
       148                 99
       150                 88
       151                 92
       152                 92
       153                 92
       154                 92

SECTION_ID MAX(NUMERIC_GRADE)
---------- ------------------
       156                 99

56 rows selected.

==== Problem 5 ====

SECTION_ID START
---------- -----
        85 10:30
        95 10:30
       104 10:30
       109 10:30
       116 10:30
       122 10:30

6 rows selected.

==== Problem 6 ====

DAY SECTION_ID
--- ----------
MON        107
TUE         86
WED         83

SQL>

*/