/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  05/10/14
**  Final Project
*/

-- BEGIN REPORT 1
SELECT TO_CHAR(TO_DATE(PD.Month, 'MM'), 'Month') AS Month, T.ZipCode AS Territory, PY.Type, SUM (S.FaceValue) AS TotalSales
FROM Sales S, Policy PY, Territory T, Period PD
  WHERE S.PolicyCode = PY.PolicyCode
    AND S.TerritoryCode = T.TerritoryCode
    AND S.PeriodCode = PD.PeriodCode
GROUP BY PY.Type, T.ZipCode, PD.Month
ORDER BY PD.Month;
-- END REPORT 1

-- BEGIN REPORT 2
SELECT PD.Quarter, T.ZipCode AS Territory, PY.Type, SUM(S.FaceValue) AS TotalSales
FROM Sales S, Policy PY, Territory T, Period PD
  WHERE S.PolicyCode = PY.PolicyCode 
    AND S.TerritoryCode = T.TerritoryCode
    AND S.PeriodCode = PD.PeriodCode
    AND PD.Year = 2013 -- For previous year's quarters or else they might collide in reporting
GROUP BY PY.Type, T.ZipCode, PD.Quarter
ORDER BY PD.Quarter;
-- END REPORT 2

-- BEGIN REPORT 3
SELECT TO_CHAR(TO_DATE(PD.Month, 'MM'), 'Month') AS Month, A.AgentName, PY.Type, SUM (S.FaceValue) AS TotalSales
FROM Sales S, Policy PY, Agent A, Period PD
  WHERE S.PolicyCode = PY.PolicyCode
    AND S.AgentCode = A.AgentCode
    AND S.PeriodCode = PD.PeriodCode
GROUP BY PY.Type, A.AgentName, PD.Month
ORDER BY PD.Month;
-- END REPORT 3

-- BEGIN REPORT 4
SELECT AgentName, EffectiveMonth, SUM (TotalSales) AS TotalSales
FROM (
  SELECT TO_CHAR(S.EffectiveDate, 'Month') AS EffectiveMonth, A.AgentName, SUM (S.FaceValue) AS TotalSales
    FROM Sales S, Agent A
  WHERE S.AgentCode = A.AgentCode
  GROUP BY S.EffectiveDate, A.AgentName )
GROUP BY EffectiveMonth, AgentName
ORDER BY AgentName, TO_DATE(EffectiveMonth, 'Month');
-- END REPORT 4

-- BEGIN REPORT 5
SELECT A.AgentName, COUNT (S.InForce) AS NumPoliciesInForce
FROM AGENT A, 
  ( SELECT AgentCode, InForce FROM SALES
    WHERE InForce = 'Y') S
WHERE S.AgentCode = A.AgentCode
GROUP BY A.AgentName;
-- END REPORT 5