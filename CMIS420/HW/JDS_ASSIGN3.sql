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
