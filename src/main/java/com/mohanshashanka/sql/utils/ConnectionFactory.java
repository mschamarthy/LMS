package com.mohanshashanka.sql.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private String dbDriver;
	private String host;
	private String dbName;
	private String user;
	private String password;

	private ConnectionFactory() {
	}

//	enum EnvType {
//		LOCAL("LOCAL_"), PROD("PROD_");
//
//		String prefix;
//
//		EnvType(String prefix) {
//			this.prefix = prefix;
//		}
//
//		@Override
//		public String toString() {
//			return this.prefix;
//		}
//		
//		public static EnvType envTypeFor(String anotherString){
//			if(EnvType.LOCAL.name().equalsIgnoreCase(anotherString)){
//				return EnvType.LOCAL;
//			} else if (EnvType.PROD.name().equalsIgnoreCase(anotherString)){
//				return EnvType.PROD;
//			} else {
//				return EnvType.LOCAL;
//			}
//		}
//	}

//	public static Connection getLocalConnection() throws SQLException {
//		return getConnection(EnvType.LOCAL);
//	}
//	
//	public static Connection getProdConnection() throws SQLException {
//		return getConnection(EnvType.PROD);
//	}
//	
//	public static Connection getConnection() throws SQLException {
//		return getConnection(null);
//	}

//	public static Connection getConnection(EnvType env) throws SQLException {
//		ConnectionFactory conf = ConnectionFactory.getConfiguration(env);
//		String dbPath = conf.dbPath();
//		Connection connection = null;
//		connection = DriverManager.getConnection(dbPath);
//		return connection;
//	}

//	private static ConnectionFactory getConfiguration(EnvType env) {
//		Properties prop = new Properties();
//		InputStream input = null;
//		ConnectionFactory configuration = new ConnectionFactory();
//		
//		try {
//			FileSystem fs = FileSystems.getDefault();
//			String separator = fs.getSeparator();
//
//			String filename = "config" + separator + "dbConfig.properties";
//			input = ConnectionFactory.class.getClassLoader().getResourceAsStream(filename);
//			if (input == null) {
//				System.out.println("Sorry, unable to find " + filename);
//				return null;
//			}
//
//			// load a properties file from class path, inside static method
//			prop.load(input);
//
//			if (env == null) {
//				env = EnvType.envTypeFor(prop.getProperty("env"));
//			}
//			String envPrefix = env.toString();
//			
//			// get the property value and print it out
//			configuration.setDbDriver(prop.getProperty(envPrefix + "dbDriver"));
//			configuration.setHost(prop.getProperty(envPrefix + "host"));
//			configuration.setDbName(prop.getProperty(envPrefix + "dbName"));
//			configuration.setUser(prop.getProperty(envPrefix + "user"));
//			configuration.setPassword(prop.getProperty(envPrefix + "password"));
//
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return configuration;
//	}
	
	public static Connection getConnection() throws SQLException {
		ConnectionFactory conf = ConnectionFactory.getConfiguration();
		String dbPath = conf.dbPath();
		Connection connection = null;
		connection = DriverManager.getConnection(dbPath);
		return connection;
	}
	
	private static ConnectionFactory getConfiguration() {
		Properties prop = new Properties();
		InputStream input = null;
		ConnectionFactory configuration = new ConnectionFactory();
		
		try {
			FileSystem fs = FileSystems.getDefault();
			String separator = fs.getSeparator();

			String filename = "config" + separator + "dbConfig.properties";
			input = ConnectionFactory.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return null;
			}

			// load a properties file from class path, inside static method
			prop.load(input);

			String envPrefix = prop.getProperty("env") + "_";
			
			// get the property value and print it out
			configuration.setDbDriver(prop.getProperty(envPrefix + "dbDriver"));
			configuration.setHost(prop.getProperty(envPrefix + "host"));
			configuration.setDbName(prop.getProperty(envPrefix + "dbName"));
			configuration.setUser(prop.getProperty(envPrefix + "user"));
			configuration.setPassword(prop.getProperty(envPrefix + "password"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return configuration;
	}

	private String dbPath() {
		String dbPath = this.dbDriver + "://" + this.host + "/" + this.dbName + "?user=" + this.user + "&password="
				+ this.password;
		return dbPath;
	}

	private void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	private void setHost(String host) {
		this.host = host;
	}

	private void setDbName(String dbName) {
		this.dbName = dbName;
	}

	private void setUser(String user) {
		this.user = user;
	}

	private void setPassword(String password) {
		this.password = password;
	}
}
