/* 
**  Author: Justin Smith
**  Course: CMIS 420.7980
**  Date:  03/19/14
**  Assignment 1
*/

/* BEGIN Problem 35 */
SELECT SalesTerritoryID, COUNT (SalesTerritoryID) AS NumSalesPersons
  FROM SalesPerson_T
    GROUP BY SalesTerritoryID
    HAVING COUNT (SalesTerritoryID) > 1;
/* END Problem 35 */

/* BEGIN Problem 36 */
SELECT SalesPersonID, COUNT (SalesPersonID) 
  FROM Order_T
    WHERE SalesPersonID NOT IN (3, 5, 9) 
    GROUP BY SalesPersonID;
/* END Problem 36 */

/* BEGIN Problem 37 */
SELECT SalesPersonID, TO_CHAR(OrderDate, 'MON') AS MONTH, COUNT (OrderDate)
  FROM Order_T
    WHERE OrderDate >= '01-JAN-2010' AND SalesPersonID IS NOT NULL
    GROUP BY TO_CHAR(OrderDate, 'MON'), SalesPersonID
    ORDER BY SalesPersonID;
/* END Problem 37 */

/* BEGIN Problem 38 */
SELECT MaterialName, Material, Width
  FROM RawMaterial_T
    WHERE Material NOT IN ('Cherry', 'Oak') AND Width > 10;
/* END Problem 38 */

/* BEGIN Problem 39 */
SELECT ProductID, ProductDescription, ProductFinish, ProductStandardPrice
  FROM Product_T
    WHERE (ProductFinish = 'Oak' AND ProductStandardPrice > 400)
       OR (ProductFinish = 'Cherry' AND ProductStandardPrice < 300);
/* END Problem 39 */

/* BEGIN Problem 40 */
SELECT OrderID, CustomerID, OrderDate, MostRecent
  FROM Order_T, ( SELECT MAX(OrderDate) AS MostRecent FROM Order_T );
  /* Create Derived Table for Most Recent order date in Order_T */  
/* END Problem 40 */
