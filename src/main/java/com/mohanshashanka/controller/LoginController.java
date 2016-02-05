package com.mohanshashanka.controller;

import com.mohanshashanka.controller.helpers.StageHelper;
import com.mohanshashanka.dao.UserDAO;
import com.mohanshashanka.exceptions.InvalidUserException;
import com.mohanshashanka.models.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController{

	@FXML
	private TextField userName;
	@FXML
	private PasswordField password;
	@FXML
	private Label errorLabel;

	public LoginController() {
	}

	@Override
	public void initController() {
		setTitle("Login - Library Management System");
	}
	
	// Caller for login button
	public boolean validateAndLogin() {
		// Get the parameters from UI
		String userName = this.userName.getText();
		String password = this.password.getText();

		// Validate with DB
		UserDAO userDao = new UserDAO();
		User user;
		try {
			user = userDao.getUserIfValid(UserDAO.requestForLoginValidation(userName, password));
			if (user != null) {
				// Change the stage based on if User is admin/user
				changeStageToUserHome(user);
			} else {
				
			}
		} catch (InvalidUserException e) {
			errorLabel.setText("User Name / Password does not match.");
		}
		return true;
	}

	
	// Caller for close Button
	public void close(){
		context.getStage().close();
		System.exit(0);
	}
	
	// PRIVATE
	
	private void changeStageToUserHome(User user) {
		// Add the present user to context
		context.setUser(user);
		StageHelper.setScene(context, "UserHomeView");
	}

}
