/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  04/05/14
**  Project 1 - Part 2
*/

-- Begin Problem 1
PROMPT ==== Problem 1 ====

SELECT CCUNITNAME, NURSEID
FROM CCASSIGNMENT_T;

-- End Problem 1

-- Begin Problem 2
PROMPT ==== Problem 2 ====

SELECT D.DiagnosisName, C.NUM_DIAGNOSIS
FROM DIAGNOSIS_T D JOIN
(
  -- Need Additional nested SELECT statements to make ROWNUM work correctly.
  SELECT DiagnosisCode, NUM_DIAGNOSIS
  FROM ( SELECT DiagnosisCode, COUNT(DiagnosisCode) AS NUM_DIAGNOSIS
         FROM PHYSICIANDX_T
         GROUP BY DiagnosisCode
         ORDER BY COUNT(DiagnosisCode) DESC )
  WHERE ROWNUM <= 5
) C ON D.DiagnosisCode = C.DiagnosisCode;
-- End Problem 2

-- Begin Problem 3
PROMPT ==== Problem 3 ====

SELECT P.PersonName, IC.ItemNo, IT.ItemDesc
FROM PERSON_T P, ITEMCONSUMPTION_T IC, ITEM_T IT
WHERE IC.PATIENTID = P.PERSONID
  AND IC.ITEMNO = IT.ITEMNO
ORDER BY P.PersonName;
-- End Problem 3
