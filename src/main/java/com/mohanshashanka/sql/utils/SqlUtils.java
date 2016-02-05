package com.mohanshashanka.sql.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class SqlUtils {

	public static String getSqlFileAsString(String fileName) {
		FileSystem fs = FileSystems.getDefault();
		String separator = fs.getSeparator();

		String filename = "sql" + separator + fileName + ".sql";
		InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(filename);
		if (input == null) {
			System.out.println("Sorry, unable to find " + filename);
			return null;
		}
		StringBuilder sqlStatement = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			String s;
			sqlStatement = new StringBuilder();
			while ((s = br.readLine()) != null) {
				sqlStatement = sqlStatement.append(s).append("\n");
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sqlStatement.toString();
	}

	public static boolean execute(Connection conn, String fileName) throws SQLException {
		String query = getSqlFileAsString(fileName);
		PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		return statement.execute();
	}
	
	public static boolean execute(Connection conn, String fileName, Object[] params) throws SQLException {
		String query = getSqlFileAsString(fileName);
		PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int count = params.length;
		for (int i = 1; i <= count; i++) {
			if (params[i - 1].getClass().equals("".getClass())) {
				statement.setString(i, (String) params[i - 1]);
			} else if (params[i - 1].getClass().equals(Integer.class)) {
				statement.setInt(i, (Integer) params[i - 1]);
			} else if (params[i - 1].getClass().equals(Double.class)) {
				statement.setDouble(i, (Double) params[i - 1]);
			}
		}
		return statement.execute();
	}

	public static ResultSet executeQuery(Connection conn, String fileName, Object[] params) throws SQLException {
		String query = getSqlFileAsString(fileName);
		PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int count = params.length;
		for (int i = 1; i <= count; i++) {
			if (params[i - 1].getClass().equals("".getClass())) {
				statement.setString(i, (String) params[i - 1]);
			} else if (params[i - 1].getClass().equals(Integer.class)) {
				statement.setInt(i, (Integer) params[i - 1]);
			} else if (params[i - 1].getClass().equals(Double.class)) {
				statement.setDouble(i, (Double) params[i - 1]);
			}
		}
//		System.out.println(statement);
		ResultSet result = statement.executeQuery();
		return result;
	}

}
