/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  05/10/14
**  Final Project
*/

-- BEGIN REPORT 1
SELECT PY.Type, T.ZipCode AS Territory, TO_CHAR(TO_DATE(PD.Month, 'MM'), 'Month') AS Month, SUM(S.FaceValue)
  OVER (PARTITION BY S.PolicyCode ORDER BY S.TerritoryCode, S.PeriodCode) AS TotalSales
FROM Sales S, Policy PY, Territory T, Period PD
  WHERE S.PolicyCode = PY.PolicyCode 
    AND S.TerritoryCode = T.TerritoryCode
    AND S.PeriodCode = PD.PeriodCode
ORDER BY S.PolicyCode, PD.Month;
-- END REPORT 1

