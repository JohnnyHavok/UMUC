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
-- END TEST BLOCK --
-- END PROBLEM 2 --

-- BEGIN PROBLEM 3 --
-- CREATE STORED PROCEDURE --

-- END STORED PROCEDURE --

-- BEGIN TEST BLOCK --

-- END TEST BLOCK --
-- END PROBLEM 3 --
