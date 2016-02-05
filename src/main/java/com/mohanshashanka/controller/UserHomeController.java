package com.mohanshashanka.controller;

import java.util.ArrayList;

import com.mohanshashanka.controller.helpers.StageHelper;
import com.mohanshashanka.dao.BookDAO;
import com.mohanshashanka.dao.BorrowDetailsDAO;
import com.mohanshashanka.exceptions.UnAuthorizedException;
import com.mohanshashanka.models.Book;
import com.mohanshashanka.models.Request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class UserHomeController extends BaseController {

	@FXML
	private Text welcomeMessage;
	@FXML
	private ChoiceBox<String> searchCriteria;
	@FXML
	private TextField searchString;
	@FXML
	private TableView<Book> searchResultTable;
	@FXML
	private Button lendButton;
	@FXML
	private Button adminOps;
	@FXML
	private Button historyBtn;

	private boolean isTableInitialized = false;

	private ObservableList<Book> bookList = FXCollections.observableArrayList();

	public UserHomeController() {

	}

	@Override
	public void initController() {
		setTitle("Welcome " + context.getUser().getName());
		this.welcomeMessage.setText("Welcome " + context.getUser().getName());
		searchResultTable.setVisible(false);
		setCriterias();
		modifyAdminPrivilagedItems();
	}

	private void modifyAdminPrivilagedItems() {
		// HISTORY NOT COMPLETED so MADE IT FALSE;
		historyBtn.setVisible(false);
		
		if (context.getUser().isAdmin()) {
			// LEND NOT COMPLETED so MADE IT FALSE;
			lendButton.setVisible(true);
			adminOps.setVisible(true);
		} else {
			lendButton.setVisible(false);
			adminOps.setVisible(false);
		}
	}

	private void setCriterias() {
		searchCriteria.setItems(FXCollections.observableArrayList("Author", "Name", "Category", "Id"));
		if(context.getUser().isAdmin()){
			searchCriteria.setValue("Id");
		} else {
			searchCriteria.setValue("Author");
		}
		
	}

	public void searchByCriteria() {
		String category = searchCriteria.getSelectionModel().getSelectedItem();
		category = category == null ? "" : category;

		String searchString = this.searchString.getText();

		BookDAO bookDao = new BookDAO();
		Request request = BookDAO.requestForBookByCategory(context.getUser(), searchString);
		ArrayList<Book> books = new ArrayList<Book>();
		switch (category.trim().toLowerCase()) {
		case "author":
			books = bookDao.getBooksByAuthor(request);
			break;
		case "name":
			books = bookDao.getBooksByName(request);
			break;
		case "category":
			books = bookDao.getBooksByCategory(request);
			break;
		case "id":
			request = BookDAO.requestForBookById(context.getUser(), Integer.parseInt(searchString));
			books = bookDao.getBooksById(request);
			break;
		default:
			books = bookDao.getBooksByAuthor(request);
			break;
		}
		showBookListTable();
		bookList.clear();
		bookList.addAll(books);
		searchResultTable.setItems(bookList);
		searchResultTable.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private void showBookListTable() {
		if (!isTableInitialized) {
			searchResultTable.setEditable(false);
			searchResultTable.getColumns().clear();

			TableColumn<Book, Integer> bId = new TableColumn<Book, Integer>("BID");
			bId.setMinWidth(50);
			bId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bId"));

			TableColumn<Book, String> name = new TableColumn<Book, String>("Name");
			name.setMinWidth(50);
			name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));

			TableColumn<Book, String> author = new TableColumn<Book, String>("Author");
			author.setMinWidth(50);
			author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));

			TableColumn<Book, String> category = new TableColumn<Book, String>("Category");
			category.setMinWidth(50);
			category.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));

			TableColumn<Book, Double> rentalCost = new TableColumn<Book, Double>("Rental Cost");
			rentalCost.setMinWidth(20);
			rentalCost.setCellValueFactory(new PropertyValueFactory<Book, Double>("rentalCost"));
			
			TableColumn<Book, Integer> quantity = new TableColumn<Book, Integer>("Quantity");
			quantity.setMinWidth(20);
			quantity.setCellValueFactory(new PropertyValueFactory<Book, Integer>("quantity"));

			searchResultTable.getColumns().addAll(bId, name, author, category, rentalCost, quantity);
			isTableInitialized = true;
		}
	}

	public void changePassword() {
		StageHelper.setScene(context, "ChangePasswordView");
	}

	public void showHistory() {
		// ***************
		System.out.println("Yet To implement History *****");
	}

	public void lendBook() {
		try {
			Book selectedBook = searchResultTable.getSelectionModel().getSelectedItem();
			String lendUserName = StageHelper.inputDialog("Enter User Name", "Enter Lend User Name", "User Name :");
			BorrowDetailsDAO borrowDAO = new BorrowDetailsDAO();
			borrowDAO.addBorrowDetail(BorrowDetailsDAO.requestForNewBorrow(context.getUser(), lendUserName, selectedBook.getBId()));
		} catch (UnAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openAdminOperations() {
		if (context.getUser().isAdmin()) {
			StageHelper.setScene(context, "AdminHomeView");
		}
	}
}
