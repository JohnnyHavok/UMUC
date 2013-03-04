-- Project 2 SQL Step 4
-- Justin Smith
-- CMIS 320


-- Example A
SELECT CustomerID, CustomerFName, CustomerLName, CustomerSAddress, CustomerZIP
FROM Customer_T
ORDER BY CustomerID;

-- Example B
SELECT Rentals_T.RentalDate, Inventory_T.InventoryID, Movies_T.Title, Inventory_T.Format, RentalLine_T.ReturnDate
FROM Rentals_T, Inventory_T, Movies_T, Inventory_T, RentalLine_T
WHERE RentalLine_T.RentalOrderID = Rentals_T.RentalOrderID
AND RentalLine_T.InventoryID = Inventory_T.InventoryID
AND Inventory_T.MovieID = Movies_T.MovieID
ORDER BY Rentals_T.RentalDate

-- Example C
SELECT *
FROM Distributors_T
ORDER BY DistributorName;

-- Example D
UPDATE Customer_T
SET CustomerLName = 'Heffner'
WHERE CustomerID = '1030';

-- Example E
DELETE FROM Customer_T
WHERE CustomerID = 1018;
