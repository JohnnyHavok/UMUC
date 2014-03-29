/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  03/28/14
**  Assignment 2
*/

/* BEGIN Problem 17 */
SELECT E.EmployeeName, E.EmployeeBirthDate, 
	M.EmployeeName AS Manager, M.EmployeeBirthDate AS ManagerBirth
  FROM Employee_T E, Employee_T M
  WHERE E.EmployeeSupervisor = M.EmployeeID
    AND E.EmployeeBirthDate < M.EmployeeBirthDate;
/* END Problem 17 */

/* BEGIN Problem 21 */
SELECT P.ProductID, P.ProductDescription, P.ProductStandardPrice,
 SUM(LineItem) AS TotCost
FROM Product_T P JOIN 
  ( SELECT DISTINCT U.ProductID, 
     (U.QuantityRequired * R.MaterialStandardPrice) AS LineItem
    FROM Uses_T U, RawMaterial_T R
    WHERE U.MaterialID = R.MaterialID ) TBL 
  ON P.ProductID = TBL.ProductID
GROUP BY P.ProductID, P.ProductDescription, P.ProductStandardPrice
ORDER BY P.ProductID;
/* END Problem 21 */

/* BEGIN Problem 22 */
SELECT P.OrderID, (SUM(LineItem)) AS TotalDue, P.PaymentAmount 
FROM Payment_T P JOIN 
  ( SELECT DISTINCT OL.OrderID, 
     (OL.OrderedQuantity * PR.ProductStandardPrice) AS LineItem
    FROM OrderLine_T OL, Product_T PR
    WHERE OL.ProductID = PR.ProductID ) ORD 
  ON P.OrderID = ORD.OrderID
GROUP BY P.OrderID, P.PaymentAmount
ORDER BY (TotalDue - P.PaymentAmount) DESC;
/* END Problem 22 */

/* BEGIN Problem 24 */
SELECT DISTINCT M.EmployeeName AS Manager
  FROM Employee_T E, Employee_T M 
  WHERE E.EmployeeSupervisor = M.EmployeeID
   AND E.EmployeeID IN 
   (SELECT EmployeeID from EmployeeSkills_T where SkillID = 'BS12');
/* END Problem 24 */

/* BEGIN Problem 26 */
SELECT W.WorkCenterID, COUNT(P.WorkCenterID) AS TotalProducts
FROM WorkCenter_T W LEFT OUTER JOIN ProducedIn_T P
ON W.WorkCenterID = P.WorkCenterID
GROUP BY W.WorkCenterID;
/* END Problem 26 */

/* BEGIN Problem 27 */
-- This method provides 0's for customers without Vendors in their
-- state.
SELECT C.CustomerName, COUNT(VendorState) AS NumVendors
FROM Customer_T C LEFT OUTER JOIN 
  (SELECT VendorState FROM Vendor_T) VS
  ON C.CustomerState = VS.VendorState
GROUP BY C.CustomerName;
/* END Problem 27 */

/* BEGIN Problem 39 */
SELECT OL.orderid, OL.ProductID
  FROM OrderLine_T OL
  WHERE OL.OrderedQuantity > ALL 
    ( SELECT  AVG(OE.OrderedQuantity) FROM OrderLine_T OE
      WHERE OE.ProductID = OL.productid );
/* END Problem 39 */

/* BEGIN Problem 41 */

/* END Problem 41 */
