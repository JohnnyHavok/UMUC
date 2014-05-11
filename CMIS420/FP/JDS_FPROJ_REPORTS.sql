/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  05/10/14
**  Final Project
*/

-- BEGIN REPORT 1
SELECT PY.Type, T.ZipCode AS Territory, TO_CHAR(TO_DATE(PD.Month, 'MM'), 'Month') AS Month, SUM (S.FaceValue) AS TotalSales
FROM Sales S, Policy PY, Territory T, Period PD
  WHERE S.PolicyCode = PY.PolicyCode
    AND S.TerritoryCode = T.TerritoryCode
    AND S.PeriodCode = PD.PeriodCode
GROUP BY PY.Type, T.ZipCode, PD.Month
ORDER BY PY.Type DESC, PD.Month;
-- END REPORT 1

-- BEGIN REPORT 2
SELECT PY.Type, T.ZipCode AS Territory, PD.Quarter, SUM(S.FaceValue)
  OVER (PARTITION BY PY.PolicyCode ORDER BY PD.Quarter, S.TerritoryCode) AS TotalSales
FROM Sales S, Policy PY, Territory T, Period PD
  WHERE S.PolicyCode = PY.PolicyCode 
    AND S.TerritoryCode = T.TerritoryCode
    AND S.PeriodCode = PD.PeriodCode
    AND PD.Year = 2013 -- For previous year's quarters or else they might collide in reporting
ORDER BY S.PolicyCode, PD.Quarter;
-- END REPORT 2