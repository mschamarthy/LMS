package com.mohanshashanka.controller;

import java.util.ArrayList;

import com.mohanshashanka.controller.helpers.StageHelper;
import com.mohanshashanka.dao.BookDAO;
import com.mohanshashanka.dao.UserDAO;
import com.mohanshashanka.exceptions.DuplicateUserException;
import com.mohanshashanka.exceptions.UnAuthorizedException;
import com.mohanshashanka.models.Book;
import com.mohanshashanka.models.User;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AdminHomeController extends BaseController {

	@FXML
	RadioButton userRadio;
	@FXML
	RadioButton bookRadio;
	@FXML
	RadioButton cRadio;
	@FXML
	RadioButton uRadio;
	@FXML
	RadioButton dRadio;
	@FXML
	Button findButton;
	@FXML
	Button executeButton;
	@FXML
	GridPane grid;
	@FXML
	ChoiceBox<String> opSelect;
	@FXML
	TextField operandIdent;
	@FXML
	Label identLabel;
	@FXML
	Text errorMessage;

	@FXML
	Text f1;
	@FXML
	Text f2;
	@FXML
	Text f3;
	@FXML
	Text f4;
	@FXML
	Text f5;
	@FXML
	Text f6;
	@FXML
	Text f7;

	@FXML
	TextField t1;
	@FXML
	TextField t2;
	@FXML
	TextField t3;
	@FXML
	TextField t4;
	@FXML
	TextField t5;
	@FXML
	TextField t6;
	@FXML
	TextField t7;

	int entitySelected = -1; // 0 for user; 1 for book
	int operationSelected = -1; // 0 for create, 1 for update, 2 for delete

	ToggleGroup entityRadioGroup = new ToggleGroup();
	ToggleGroup crudRadioGroup = new ToggleGroup();

	public AdminHomeController() {
	}

	@Override
	public void initController() {
		setTitle("Welcome " + context.getUser().getName());
		userRadio.setToggleGroup(entityRadioGroup);
		bookRadio.setToggleGroup(entityRadioGroup);
		cRadio.setToggleGroup(crudRadioGroup);
		uRadio.setToggleGroup(crudRadioGroup);
		dRadio.setToggleGroup(crudRadioGroup);
		grid.setVisible(false);
		entitySelected = -1;
		operationSelected = -1;
		setErrorMessage("");
	}

	private void setErrorMessage(String message) {
		errorMessage.setText(message);
	}

	public void back() {
		StageHelper.setScene(context, "UserHomeView");
	}

	public void userSelect() {
		clearOperandIdent();
		clearCRUDSelection();
		grid.setVisible(true);
		entitySelected = 0;
		operationSelected = -1;
		disableUserFields();
		opSelect.setItems(FXCollections.observableArrayList("UserName"));
		opSelect.setValue("UserName");
		identLabel.setText("Enter UserName :");
		f1.setText("Uid");
		t1.setDisable(true);
		f2.setText("userName");
		f3.setText("Name");
		f4.setText("Department");
		f5.setText("Role");
		f6.setVisible(false);
		t6.setVisible(false);
		f7.setVisible(false);
		t7.setVisible(false);
	}

	public void bookSelect() {
		clearOperandIdent();
		clearCRUDSelection();
		grid.setVisible(true);
		entitySelected = 1;
		operationSelected = -1;
		disableBookFields();
		opSelect.setItems(FXCollections.observableArrayList("Id"));
		opSelect.setValue("Id");
		identLabel.setText("Enter Book Id :");
		f1.setText("BId");
		t1.setDisable(true);
		f2.setText("Name");
		f3.setText("Author");
		f4.setText("Category");
		f5.setText("Cost");
		f6.setText("Rental Cost");
		f7.setText("Quantity");
		f6.setVisible(true);
		t6.setVisible(true);
		f7.setVisible(true);
		t7.setVisible(true);
	}

	private void clearCRUDSelection() {
		crudRadioGroup.selectToggle(null);
	}

	private void disableControls() {
		opSelect.setDisable(true);
		operandIdent.setDisable(true);
		findButton.setDisable(true);
	}

	private void enableControls() {
		opSelect.setDisable(false);
		operandIdent.setDisable(false);
		findButton.setDisable(false);
	}

	private void enableUserFields() {
		f1.setDisable(false);
		f2.setDisable(false);
		f3.setDisable(false);
		f4.setDisable(false);
		f5.setDisable(false);
		t1.setDisable(true);
		t2.setDisable(false);
		t3.setDisable(false);
		t4.setDisable(false);
		t5.setDisable(false);
	}

	private void disableUserFields() {
		clearDisplayFields();
		f1.setDisable(true);
		f2.setDisable(true);
		f3.setDisable(true);
		f4.setDisable(true);
		f5.setDisable(true);
		t1.setDisable(true);
		t2.setDisable(true);
		t3.setDisable(true);
		t4.setDisable(true);
		t5.setDisable(true);
	}

	private void enableBookFields() {
		f1.setDisable(false);
		f2.setDisable(false);
		f3.setDisable(false);
		f4.setDisable(false);
		f5.setDisable(false);
		f6.setDisable(false);
		f7.setDisable(false);
		t1.setDisable(true);
		t2.setDisable(false);
		t3.setDisable(false);
		t4.setDisable(false);
		t5.setDisable(false);
		t6.setDisable(false);
		t7.setDisable(false);
	}

	private void disableBookFields() {
		clearDisplayFields();
		f1.setDisable(true);
		f2.setDisable(true);
		f3.setDisable(true);
		f4.setDisable(true);
		f5.setDisable(true);
		f6.setDisable(true);
		f7.setDisable(true);
		t1.setDisable(true);
		t2.setDisable(true);
		t3.setDisable(true);
		t4.setDisable(true);
		t5.setDisable(true);
		t6.setDisable(true);
		t7.setDisable(true);
	}

	private void clearDisplayFields() {
		t7.clear();
		t6.clear();
		t5.clear();
		t4.clear();
		t3.clear();
		t2.clear();
		t1.clear();
	}

	private void clearOperandIdent() {
		operandIdent.clear();
	}

	private void enableFields() {
		if (entitySelected == 0) {
			enableUserFields();
		} else {
			enableBookFields();
		}
	}

	private void disableFields() {
		if (entitySelected == 0) {
			disableUserFields();
		} else {
			disableBookFields();
		}
	}

	public void cSelect() {
		operationSelected = 0;
		clearOperandIdent();
		executeButton.setText("Create");
		enableFields();
		disableControls();
	}

	// public void rSelect() {
	// clearOperandIdent();
	// executeButton.setText("Read");
	// disableFields();
	// enableControls();
	// }

	public void uSelect() {
		operationSelected = 1;
		clearOperandIdent();
		executeButton.setText("Update");
		disableFields();
		enableControls();
	}

	public void dSelect() {
		operationSelected = 2;
		clearOperandIdent();
		executeButton.setText("Delete");
		disableFields();
		enableControls();
	}

	public void opSelected() {
		System.out.println("opSelected");
	}

	public void find() {
		clearDisplayFields();
		setErrorMessage("");
		String searchString = operandIdent.getText();
		try {
			if (entitySelected == 0) { // USER entity selected
				User user = executeUserSearch(searchString);
				displayUser(user);
			} else { // BOOK Entity selected
				Book book = executeBookSearch(searchString);
				displayBook(book);
			}
		} catch (Exception e) {
			setErrorMessage("Could not find Entity in the records");
		}
		enableFields();
	}

	private Book executeBookSearch(String bidString) {
		int bid = Integer.parseInt(bidString);
		BookDAO bookDao = new BookDAO();
		ArrayList<Book> books = bookDao.getBooksById(BookDAO.requestForBookById(context.getUser(), bid));

		return books.get(0);
	}

	private User executeUserSearch(String userName) {
		UserDAO userDao = new UserDAO();
		User user = userDao.getUserByUserName(UserDAO.requestForUserByName(userName));
		return user;
	}

	public void execute() {
		if (entitySelected == 0) { // USER entity selected
			User user = getUser();
			executeUserQueries(user);
		} else { // BOOK Entity selected
			Book book = getBook();
			executeBookQueries(book);
		}
		clearDisplayFields();
		clearCRUDSelection();
		clearDisplayFields();
		System.out.println("Execute");
	}

	// ***************
	private void executeUserQueries(User user) {
		setErrorMessage("");
		UserDAO userDao = new UserDAO();
		if (operationSelected == 0) { // CREATE
			try {
				userDao.createUser(UserDAO.requestForNewUser(context.getUser(), user.getName(), user.getDepartment(),
						user.getRoleAsString(), user.getUserName(), user.getUserName().trim().toLowerCase()));
			} catch (UnAuthorizedException | DuplicateUserException e) {
				setErrorMessage("Duplicate User - User with same userName already exist");
			}
		} else if (operationSelected == 1) { // UPDATE
			try {
				userDao.updateUser(UserDAO.requestForUpdateUser(context.getUser(), user.getuId(), user.getName(),
						user.getDepartment(), user.getRoleAsString(), user.getUserName()));
			} catch (UnAuthorizedException e) {
				setErrorMessage("Unauthorized to make the update");
			}
		} else if (operationSelected == 2) { // DELETE
			try {
				userDao.deleteUserByUserName(UserDAO.requestForUserByName(context.getUser(), user.getUserName()));
			} catch (UnAuthorizedException e) {
				setErrorMessage("Unauthorized to Delete");
			}
		}
	}

	private void executeBookQueries(Book book) {
		setErrorMessage("");
		BookDAO bookDao = new BookDAO();
		if (operationSelected == 0) { // CREATE
			try {
				bookDao.createBook(BookDAO.requestForNewBook(context.getUser(), book.getName(), book.getAuthor(),
						book.getCategory(), book.getCost(), book.getRentalCost(), book.getQuantity()));
			} catch (UnAuthorizedException e) {
				setErrorMessage("Duplicate User - User with same userName already exist");
			}
		} else if (operationSelected == 1) { // UPDATE
			try {
				bookDao.updateBook(BookDAO.requestForUpdateBook(context.getUser(), book.getBId(), book.getName(), book.getAuthor(),
						book.getCategory(), book.getCost(), book.getRentalCost(), book.getQuantity()));
			} catch (UnAuthorizedException e) {
				setErrorMessage("Unauthorized to make the update");
			}
		} else if (operationSelected == 2) { // DELETE
			try {
				bookDao.deleteBook(BookDAO.requestForBookById(context.getUser(), book.getBId()));
			} catch (UnAuthorizedException e) {
				setErrorMessage("Unauthorized to Delete");
			}
		}
	}

	private void displayUser(User user) {
		Integer uid = new Integer(user.getuId());
		t1.setText(uid.toString());
		t2.setText(user.getUserName());
		t3.setText(user.getName());
		t4.setText(user.getDepartment());
		t5.setText(user.getRole().toString());
	}

	private void displayBook(Book book) {
		Integer bid = new Integer(book.getBId());
		t1.setText(bid.toString());
		t2.setText(book.getName());
		t3.setText(book.getAuthor());
		t4.setText(book.getCategory());
		t5.setText(new Double(book.getCost()).toString());
		t6.setText(new Double(book.getRentalCost()).toString());
		t7.setText(new Integer(book.getQuantity()).toString());
	}

	private User getUser() {
		User user = new User();
		Integer uid = (t1.getText() == null || t1.getText().equals("")) ? -1 : Integer.parseInt(t1.getText());
		user.setuId(uid);
		user.setName(t3.getText());
		user.setUserName(t2.getText());
		user.setDepartment(t4.getText());
		user.setRole(t5.getText());
		return user;
	}

	private Book getBook() {
		Book book = new Book();
		Integer bid = (t1.getText() == null || t1.getText().equals("")) ? -1 : Integer.parseInt(t1.getText());
		Double cost = (t5.getText() == null || t5.getText().equals("")) ? 0.0 : Double.parseDouble(t5.getText());
		Double rentalCost = (t6.getText() == null || t6.getText().equals("")) ? 0.0 : Double.parseDouble(t6.getText());
		Integer quantity = (t7.getText() == null || t7.getText().equals("")) ? 1 : Integer.parseInt(t7.getText());
		book.setBId(bid);
		book.setName(t2.getText());
		book.setAuthor(t3.getText());
		book.setCategory(t4.getText());
		book.setCost(cost);
		book.setRentalCost(rentalCost);
		book.setQuantity(quantity);
		return book;
	}

}
