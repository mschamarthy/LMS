-- create_table_myUser.sql
CREATE TABLE IF NOT EXISTS cms_MyUser ( UID int not null auto_increment, 
										Name varchar(20),
										Department varchar(20),
										Role varchar(10),
										UserName varchar(20) UNIQUE,
										Password varchar(20) BINARY, 
										Primary Key(UID) );