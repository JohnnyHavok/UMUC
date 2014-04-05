/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  04/05/14
**  Project 1 - Part 1
*/


-- Problem 1
CREATE VIEW BUSY_STUDENT
AS SELECT (First_Name || ' ' || Last_Name) AS FULL_NAME, S.Student_ID, E.ENROLL_NUM 
  FROM STUDENT S JOIN 
  (
    SELECT Student_ID, COUNT(Student_ID) AS ENROLL_NUM
      FROM ENROLLMENT
      HAVING COUNT(Student_ID) > 2
      GROUP BY Student_ID
  ) E ON S.Student_ID = E.Student_ID;
  