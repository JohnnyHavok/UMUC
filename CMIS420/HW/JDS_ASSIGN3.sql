/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  04/12/14
**  Assignment 3
*/

set serveroutput on

-- BEGIN PROBLEM 1 --
-- CREATE NAMED FUNCTION -- 
CREATE OR REPLACE FUNCTION ZIP_EXIST(p_zip_tocheck IN ZIPCODE.ZIP%TYPE)
RETURN boolean IS

  v_zipcode       ZIPCODE.ZIP%TYPE;

  CURSOR get_zipcode (p_zipcode ZIPCODE.ZIP%TYPE) IS
    SELECT ZIP FROM ZIPCODE WHERE ZIP = p_zipcode;

BEGIN
  OPEN get_zipcode(p_zip_tocheck);
  FETCH get_zipcode INTO v_zipcode;

  IF get_zipcode%ROWCOUNT = 1 THEN
    RETURN true;
  ELSE 
    RETURN false;
  END IF;

  CLOSE get_zipcode;
END;
/
-- END NAMED FUNCTION --

-- BEGIN TEST BLOCK --
DECLARE
  v_result        boolean;

BEGIN
  DBMS_OUTPUT.PUT_LINE('Testing Problem 1');
  
  DBMS_OUTPUT.PUT_LINE('Searching for ZIP code 10014');
  v_result := zip_exist('10014');
  IF v_result = TRUE THEN
    DBMS_OUTPUT.PUT_LINE('Zipcode Passed Exist');
  ELSE
    DBMS_OUTPUT.PUT_LINE('Zipcode Passed Does Not Exist');
  END IF;

  DBMS_OUTPUT.PUT_LINE('Searching for ZIP code 22485');
  v_result := zip_exist('22485');
  IF v_result = TRUE THEN
    DBMS_OUTPUT.PUT_LINE('Zipcode Passed Exist');
  ELSE
    DBMS_OUTPUT.PUT_LINE('Zipcode Passed Does Not Exist');
  END IF;
END;
/
-- END TEST BLOCK --
-- END PROBLEM 1 --

-- BEGIN PROBLEM 2 --
-- CREATE NAMED FUNCTION --
CREATE OR REPLACE FUNCTION SECTIONS_COUNT(p_instructorID IN INSTRUCTOR.INSTRUCTOR_ID%TYPE)
RETURN VARCHAR2 IS

  v_count       NUMBER;

  CURSOR get_section_count (p_ID SECTION.INSTRUCTOR_ID%TYPE) IS
    SELECT COUNT(INSTRUCTOR_ID) AS NUM_CLASSES
    FROM SECTION
    WHERE INSTRUCTOR_ID = p_ID;

BEGIN
  OPEN get_section_count(p_instructorID);
  FETCH get_section_count INTO v_count;

  IF v_count >= 3 THEN
    RETURN 'Instructor Needs a Vacation';
  ELSE 
    RETURN 'The Instructor is Teaching '|| v_count ||' Sections.';
  END IF;

  CLOSE get_section_count;

END;
/
-- END NAMED FUNCTION --

-- BEGIN TEST BLOCK --
BEGIN
  DBMS_OUTPUT.PUT_LINE('Testing Problem 2');
  DBMS_OUTPUT.PUT_LINE('Looking up Instructor: 101');
  DBMS_OUTPUT.PUT_LINE(SECTIONS_COUNT(101));

  -- Instructor 110 does not exist.  Is teaching 0 courses. --
  -- In test data, all instructors had more than 3 courses. --
  DBMS_OUTPUT.PUT_LINE('Looking up Instructor: 110');
  DBMS_OUTPUT.PUT_LINE(SECTIONS_COUNT(110));
END;
/
-- END TEST BLOCK --
-- END PROBLEM 2 --

-- BEGIN PROBLEM 3 --
-- CREATE STORED PROCEDURE --
CREATE OR REPLACE PROCEDURE UPDATE_STUDENT(p_studentID IN STUDENT.STUDENT_ID%TYPE,
                                           p_studentPhone IN STUDENT.PHONE%TYPE,
                                           p_employer IN STUDENT.EMPLOYER%TYPE)
IS
  v_temp        STUDENT.STUDENT_ID%TYPE;

BEGIN
  -- Check for Student Exist -- 
  SELECT STUDENT_ID INTO v_temp 
  FROM STUDENT
  WHERE STUDENT_ID = p_studentID;
  -- Will throw NO_DATA_FOUND EXCEPTION --   

  -- If No Exception Thrown -- 
  UPDATE STUDENT
  SET PHONE = p_studentPhone,
      EMPLOYER = p_employer
  WHERE STUDENT_ID = p_studentID;

  -- If Student Doesn't Exist --
  -- Catch NO_DATA_FOUND 
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    INSERT INTO STUDENT
    (STUDENT_ID, LAST_NAME, ZIP, PHONE, EMPLOYER, REGISTRATION_DATE, 
      CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE)
    VALUES
    (p_studentID, 'Temp', '10014', p_studentPhone, p_employer, SYSDATE, 
      'UPDATE_STUDENT', SYSDATE, 'UPDATE_STUDENT', SYSDATE);
END; 
/
-- END STORED PROCEDURE --

-- BEGIN TEST BLOCK --
DECLARE 
  v_record        STUDENT%ROWTYPE;

BEGIN 
  DBMS_OUTPUT.PUT_LINE('Testing Problem 3');

  -- Test Case 1, Update Student 102 --
  DBMS_OUTPUT.PUT_LINE('Student Record for ID: 102');
  SELECT * INTO v_record FROM STUDENT WHERE STUDENT_ID = 102;
  DBMS_OUTPUT.PUT_LINE('Student ID: '|| v_record.STUDENT_ID 
                        ||' Student Phone: '|| v_record.PHONE 
                        ||' Student Employer: ' ||v_record.EMPLOYER);

  DBMS_OUTPUT.PUT_LINE('Updating Record for ID: 102');
  UPDATE_STUDENT(102, '301-744-1982', 'Gregs Electronics');

  DBMS_OUTPUT.PUT_LINE('Student Record for ID: 102');
  SELECT * INTO v_record FROM STUDENT WHERE STUDENT_ID = 102;
  DBMS_OUTPUT.PUT_LINE('Student ID: '|| v_record.STUDENT_ID 
                        ||' Student Phone: '|| v_record.PHONE 
                        ||' Student Employer: ' ||v_record.EMPLOYER);

  -- Test Case 2, Update Student 103 --
  DBMS_OUTPUT.PUT_LINE('Student Record for ID: 103');
  SELECT * INTO v_record FROM STUDENT WHERE STUDENT_ID = 103;
  DBMS_OUTPUT.PUT_LINE('Student ID: '|| v_record.STUDENT_ID 
                        ||' Student Phone: '|| v_record.PHONE 
                        ||' Student Employer: ' ||v_record.EMPLOYER);

  DBMS_OUTPUT.PUT_LINE('Updating Record for ID: 103');
  UPDATE_STUDENT(103, '757-694-7846', 'McDonalds');

  DBMS_OUTPUT.PUT_LINE('Student Record for ID: 103');
  SELECT * INTO v_record FROM STUDENT WHERE STUDENT_ID = 103;
  DBMS_OUTPUT.PUT_LINE('Student ID: '|| v_record.STUDENT_ID 
                        ||' Student Phone: '|| v_record.PHONE 
                        ||' Student Employer: ' ||v_record.EMPLOYER);

  -- Test Case 3, Update Student 42, does not exist in my data Set --
  DBMS_OUTPUT.PUT_LINE('Updating Record for ID: 42');
  UPDATE_STUDENT(42, '804-412-6958', 'WalMart');

  DBMS_OUTPUT.PUT_LINE('Student Record for ID: 42');
  SELECT * INTO v_record FROM STUDENT WHERE STUDENT_ID = 42;
  DBMS_OUTPUT.PUT_LINE('Student ID: '|| v_record.STUDENT_ID 
                        ||' Student Phone: '|| v_record.PHONE 
                        ||' Student Employer: ' ||v_record.EMPLOYER);

END;
/
-- END TEST BLOCK --
-- END PROBLEM 3 --
