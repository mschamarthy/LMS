package com.mohanshashanka.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mohanshashanka.exceptions.UnAuthorizedException;
import com.mohanshashanka.models.Request;
import com.mohanshashanka.models.Role;
import com.mohanshashanka.models.User;
import com.mohanshashanka.sql.utils.ConnectionFactory;
import com.mohanshashanka.sql.utils.SqlUtils;

public class BorrowDetailsDAO {

	public boolean addBorrowDetail(Request request) throws UnAuthorizedException {
		request.hasAdminAccess(); // ONLY ADMIN
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			// Add borrow to clp_borrow table
			boolean newBorrowResult = SqlUtils.execute(conn, "create_new_borrow", params);
			
			// get the Last key
			params = new Object[] {};
			ResultSet rs = SqlUtils.executeQuery(conn, "read_last_borrow_id", params);
			int borrowId = -1;
			boolean newBorrowDetialResult = false; 
			if(rs.next()){
				borrowId = rs.getInt("newKey");
				
				// add the borrow id & date to details
				params = new Object[] {borrowId};
				newBorrowDetialResult = SqlUtils.execute(conn, "create_new_borrow_detail", params);
			}
			
			return newBorrowResult && newBorrowDetialResult;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static Request requestForNewBorrow(User user, String lendUserName, int bId) {
		Role accessCode = user.getRole();
		UserDAO userDao = new UserDAO();
		User lendUser = userDao.getUserByUserName(UserDAO.requestForUserByName(lendUserName));
		String status = "Borrowed";
		Object[] requestObjects = { status, lendUser.getuId(), bId };
		return new Request(accessCode, requestObjects);
	}

}
