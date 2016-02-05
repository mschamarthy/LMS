-- create_table_book.sql
CREATE TABLE IF NOT EXISTS cms_Book (	BID int not null auto_increment,
		     							Name varchar(30),
	      	 							Author varchar(30),
		     							Category varchar(20),
		   								Cost double,
		     							RentalCost double,
		     							Quantity int,
		     							Primary Key (BID) );