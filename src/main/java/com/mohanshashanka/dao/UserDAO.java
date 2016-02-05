package com.mohanshashanka.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mohanshashanka.exceptions.DuplicateUserException;
import com.mohanshashanka.exceptions.InvalidUserException;
import com.mohanshashanka.exceptions.UnAuthorizedException;
import com.mohanshashanka.models.Request;
import com.mohanshashanka.models.Role;
import com.mohanshashanka.models.User;
import com.mohanshashanka.sql.utils.ConnectionFactory;
import com.mohanshashanka.sql.utils.SqlUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class UserDAO {

	// READ USER OPERATIONS

	public User getUserByUserName(Request request) {
		User user = null;
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			ResultSet result = SqlUtils.executeQuery(conn, "read_user_by_username", params);
			if (result.next()) {
				user = buildUserFromResult(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * Used to validate the user credentials
	 * 
	 * @param userName
	 * @param password
	 * @return if the credentials are valid
	 * @throws InvalidUserException
	 */
	public boolean isValidUser(Request request) throws InvalidUserException {
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			ResultSet result = SqlUtils.executeQuery(conn, "login_query", params);
			if (result.next()) {
				if (Integer.parseInt(result.getString("count")) != 0) {
					return true;
				} else {
					throw new InvalidUserException("User Login Failed");
				}
			} else {
				throw new InvalidUserException("User Login Failed");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InvalidUserException exception = new InvalidUserException("Used Login failed due to SQLException");
			exception.printStackTrace();
			throw exception;
		}
	}

	public User getUserIfValid(Request request) throws InvalidUserException {
		if (isValidUser(request)) {
			return getUserByUserName(requestForUserByName((String) (request.getRequestObjects())[0]));
		}
		return null;
	}

	// DELETE USER
	public boolean deleteUserByUserName(Request request) throws UnAuthorizedException {
		request.hasAdminAccess(); // ONLY ADMIN
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			return SqlUtils.execute(conn, "delete_user_by_username", params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	// CREATE USER
	public boolean createUser(Request request) throws UnAuthorizedException, DuplicateUserException {
		request.hasAdminAccess(); // ONLY ADMIN
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			return SqlUtils.execute(conn, "insert_user", params);
		} catch (MySQLIntegrityConstraintViolationException sqlE) {
			throw new DuplicateUserException("User with same username already Exists");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	// UPDATE USER

	// Request Structure: < newPassword, userName , oldPassword >
	public boolean updatePassword(Request request) throws UnAuthorizedException {
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			return SqlUtils.execute(conn, "update_user_password", params);
		} catch (MySQLIntegrityConstraintViolationException sqlE) {
			throw new UnAuthorizedException("User old password does not match records");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	// Request Structure: < newPassword, userName , oldPassword >
		public boolean updateUser(Request request) throws UnAuthorizedException {
			try {
				Object[] params = request.getRequestObjects();
				Connection conn = ConnectionFactory.getConnection();
				return SqlUtils.execute(conn, "update_user", params);
			} catch (MySQLIntegrityConstraintViolationException sqlE) {
				throw new UnAuthorizedException("User old password does not match records");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

	// PRIVATE METHODS

	private User buildUserFromResult(ResultSet rs) {
		User user = null;
		try {
			user = new User();
			user.setuId(rs.getInt("uid"));
			user.setName(rs.getString("name"));
			user.setUserName(rs.getString("userName"));
			user.setDepartment(rs.getString("department"));
			user.setRole(rs.getString("role"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	// Static Request Methods
	public static Request requestForLoginValidation(String userName, String password) {
		Object[] requestObjects = { userName, password };
		Role accessCode = null;
		return new Request(accessCode, requestObjects);
	}

	public static Request requestForUserByName(String userName) {
		Object[] requestObjects = { userName };
		Role accessCode = null;
		return new Request(accessCode, requestObjects);
	}

	public static Request requestForUserByName(User user, String userName) {
		Object[] requestObjects = { userName };
		Role accessCode = user.getRole();
		return new Request(accessCode, requestObjects);
	}

	public static Request requestForNewUser(User user, String name, String department, String role, String userName,
			String password) {
		Object[] requestObjects = { name, department, role, userName, password };
		Role accessCode = user.getRole();
		return new Request(accessCode, requestObjects);
	}

	public static Request requestForUpdateUser(User user, int userId, String name, String department, String role, String userName) {
		Object[] requestObjects = { name, department, role, userName, userId };
		Role accessCode = user.getRole();
		return new Request(accessCode, requestObjects);
	}

	
	public static Request requestForNewPassword(User user, String oldPassword, String newPassword) {
		Object[] requestObjects = { newPassword, user.getUserName(), oldPassword };
		Role accessCode = user.getRole();
		return new Request(accessCode, requestObjects);
	}

	// TESTING
	public static void main(String args[]) throws UnAuthorizedException, DuplicateUserException, InvalidUserException {
		UserDAO u = new UserDAO();
		User user = u.getUserIfValid(requestForLoginValidation("mohan", "mohan"));
		// user = u.getUserIfValid(User.requestForLoginValidation("cham",
		// "cham"));
		// u.deleteUserByUserName(User.requestForUserByName(user, "mohan"));
		u.createUser(requestForNewUser(user, "Sunny", "User", "User", "sunny", "sunny"));
	}
}
