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

-- Begin Problem 4
PROMPT ==== Problem 4 ====

SELECT V.VendorName, I.Total_Supplied
FROM VENDOR_T V JOIN 
(
  SELECT VendorID, COUNT(VendorID) AS Total_Supplied
  FROM INVENTORY_T
  GROUP BY VendorID
) I ON V.VendorID = I.VendorID;

-- End Problem 4

-- Begin Problem 5
PROMPT ==== Problem 5 ====

SELECT P.PersonName, A.Total_Admissions
FROM PERSON_T P JOIN 
(
  SELECT AdmitPhys, COUNT(AdmitPhys) AS Total_Admissions
  FROM PATIENT_T
  GROUP BY AdmitPhys
) A ON A.AdmitPhys = P.PersonID;

-- End Problem 5

/**  Results of running above SQL

SQL> @jds_proj1_part2
==== Problem 1 ====

CCUNITNAME           NURSE
-------------------- -----
ER                   101
ICU                  102
Surgery              103

==== Problem 2 ====

DIAGNOSISNAME                                                NUM_DIAGNOSIS
------------------------------------------------------------ -------------
Back Pain                                                                4
Cardiogenic Pulmonary Edema                                              3
Bacterial Arhritris                                                      3
Labor and Delivery                                                       3
Lactose Intolerance                                                      2

==== Problem 3 ====

PERSONNAME                          ITEMN
----------------------------------- -----
ITEMDESC
----------------------------------------
Drew                                5
150cc Injector

Drew                                1
18ga Needle

Drew                                7
10 PK EKG Pads


PERSONNAME                          ITEMN
----------------------------------- -----
ITEMDESC
----------------------------------------
Jeff                                2
Bed Pan

Jeff                                10
Aloe Cream

Stacy                               3
1lb Cast Plaster


PERSONNAME                          ITEMN
----------------------------------- -----
ITEMDESC
----------------------------------------
Stacy                               10
Aloe Cream

Stacy                               9
ACE Bandage

Stacy                               8
Splint


PERSONNAME                          ITEMN
----------------------------------- -----
ITEMDESC
----------------------------------------
Steve                               6
5FT IV Tube

Steve                               1
18ga Needle

Steve                               4
1L Saline Solution


PERSONNAME                          ITEMN
----------------------------------- -----
ITEMDESC
----------------------------------------
Steve                               5
150cc Injector


13 rows selected.

==== Problem 4 ====

VENDORNAME                               TOTAL_SUPPLIED
---------------------------------------- --------------
Bobs Medical Supply                                   4
MegaCorp Supply                                       3
Action Tech Medical                                   3

==== Problem 5 ====

PERSONNAME                          TOTAL_ADMISSIONS
----------------------------------- ----------------
Greg                                               3
John                                               3
Mark                                               3

SQL>

**/
