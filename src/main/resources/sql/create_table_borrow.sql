-- create_table_borrow.sql
CREATE TABLE IF NOT EXISTS cms_Borrow (	BorrowID int not null auto_increment,
										Status varchar(20),
										UID int,
										BID int,
										Primary Key (BorrowID),
										FOREIGN KEY(UID) REFERENCES cms_MyUser(UID),
										FOREIGN KEY(BID) REFERENCES cms_Book(BID) );
