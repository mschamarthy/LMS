package com.mohanshashanka.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mohanshashanka.sql.utils.ConnectionFactory;
import com.mohanshashanka.sql.utils.SqlUtils;

public class DDL {
	public static boolean dbInit() {
		try {
			Connection conn = ConnectionFactory.getConnection();
			SqlUtils.execute(conn, "create_table_myUser");
			SqlUtils.execute(conn, "create_table_book");
			SqlUtils.execute(conn, "create_table_borrow");
			SqlUtils.execute(conn, "create_table_borrowDetails");
			SqlUtils.execute(conn, "adhoc");
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return true;
	}

	public static void main(String args[]) {
		DDL.dbInit();
	}
}
