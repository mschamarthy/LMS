-- create_table_borrowDetails.sql
CREATE TABLE IF NOT EXISTS cms_BorrowDetails (	BorrowID int,
												BorrowDate date,
												ReturnDate date,
												LateFee double,
												Primary Key (BorrowID),
												FOREIGN KEY(BorrowID) REFERENCES cms_Borrow(BorrowID) );