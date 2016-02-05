package com.mohanshashanka.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mohanshashanka.exceptions.UnAuthorizedException;
import com.mohanshashanka.models.Book;
import com.mohanshashanka.models.Request;
import com.mohanshashanka.models.Role;
import com.mohanshashanka.models.User;
import com.mohanshashanka.sql.utils.ConnectionFactory;
import com.mohanshashanka.sql.utils.SqlUtils;

public class BookDAO {

	// READ BOOK OPERATIONS

	public ArrayList<Book> getBooksByAuthor(Request request) {
		String queryFile = "read_book_by_author";
		return getBooksByCategory(request, queryFile);
	}

	public ArrayList<Book> getBooksByName(Request request) {
		String queryFile = "read_book_by_name";
		return getBooksByCategory(request, queryFile);
	}

	public ArrayList<Book> getBooksByCategory(Request request) {
		String queryFile = "read_book_by_category";
		return getBooksByCategory(request, queryFile);
	}

	public ArrayList<Book> getBooksById(Request request) {
		String queryFile = "read_book_by_id";
		return getBooksByCategory(request, queryFile);
	}

	private ArrayList<Book> getBooksByCategory(Request request, String queryFile) {
		ArrayList<Book> books = new ArrayList<Book>();
		Book book = null;
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			ResultSet result = SqlUtils.executeQuery(conn, queryFile, params);
			while (result.next()) {
				book = buildBookFromResult(result);
				books.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	// CREATE
	public boolean createBook(Request request) throws UnAuthorizedException {
		request.hasAdminAccess(); // ONLY ADMIN
		String queryFile = "insert_book";
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			return SqlUtils.execute(conn, queryFile, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// UPDATE
	public boolean updateBook(Request request) throws UnAuthorizedException {
		request.hasAdminAccess(); // ONLY ADMIN
		String queryFile = "update_book";
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			return SqlUtils.execute(conn, queryFile, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// UPDATE
	public boolean deleteBook(Request request) throws UnAuthorizedException {
		request.hasAdminAccess(); // ONLY ADMIN
		String queryFile = "delete_book";
		try {
			Object[] params = request.getRequestObjects();
			Connection conn = ConnectionFactory.getConnection();
			return SqlUtils.execute(conn, queryFile, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private Book buildBookFromResult(ResultSet result) {
		Book book = null;
		try {
			book = new Book();
			book.setBId(result.getInt("bid"));
			book.setAuthor(result.getString("author"));
			book.setName(result.getString("name"));
			book.setCost(result.getDouble("cost"));
			book.setQuantity(result.getInt("quantity"));
			book.setCategory(result.getString("category"));
			book.setRentalCost(result.getDouble("rentalCost"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
	}

	public static Request requestForBookByCategory(User user, String searchString) {
		Object[] requestObjects = { searchString };
		Role accessCode = user.getRole();
		return new Request(accessCode, requestObjects);
	}

	public static Request requestForBookById(User user, int parseInt) {
		Object[] requestObjects = { parseInt };
		Role accessCode = user.getRole();
		return new Request(accessCode, requestObjects);
	}

	public static Request requestForNewBook(User user, String name, String author, String category, double cost,
			double rentalCost, int quantity) {
		Object[] requestObjects = { name, author, category, cost, rentalCost, quantity };
		Role accessCode = user.getRole();
		return new Request(accessCode, requestObjects);
	}

	public static Request requestForUpdateBook(User user, int bId, String name, String author, String category,
			Double cost, Double rentalCost, int quantity) {
		Object[] requestObjects = { name, author, category, cost, rentalCost, quantity, bId };
		Role accessCode = user.getRole();
		return new Request(accessCode, requestObjects);
	}

}
