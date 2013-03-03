-- Project 2 SQL Step 2
-- Justin Smith
-- CMIS 320

-- Fails Insert into Customer_T
INSERT INTO Customer_T
VALUES (SeqCustomerID.NEXTVAL,
		'Smith',
		'Justin',
		'3485 Promenade Place',
		'Waldorf',
		'Maryland', -- Fails here because the database only wants two letter states.
		'20603',
		'7573442000');


INSERT INTO Inventory_T (InventoryID, Format, MovieID, DistributorID, SerialNumber)
VALUES ('1298',
		'VHS',
		'2003-NEMO',
		'1245',
		'0000'); -- Fails FK constraint because the item serial number does not exist for vendor 1245
