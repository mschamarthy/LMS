package com.mohanshashanka.controller;

import com.mohanshashanka.controller.helpers.StageHelper;
import com.mohanshashanka.dao.UserDAO;
import com.mohanshashanka.exceptions.InvalidUserException;
import com.mohanshashanka.exceptions.UnAuthorizedException;
import com.mohanshashanka.models.User;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

public class ChangePasswordController extends BaseController {

	@FXML
	private PasswordField oldPass;
	@FXML
	private PasswordField newPass;
	@FXML
	private PasswordField confirmPass;
	@FXML
	private Text errorMessage;

	public ChangePasswordController() {
	}

	@Override
	public void initController() {
		setTitle("Change Password for " + context.getUser().getName());
	}

	public void changePassword() {
		UserDAO userDao = new UserDAO();
		User user = this.context.getUser();
		String oldPassword = oldPass.getText();
		String newPassword = newPass.getText();
		String confirmPassword = confirmPass.getText();
		// If new password & confirm password does not match
		if (!newPassword.equals(confirmPassword)) {
			errorMessage.setText("New password & Confirm password does not match");
			newPass.clear();
			confirmPass.clear();
		} else {
			try {
				if (userDao.isValidUser(UserDAO.requestForLoginValidation(user.getUserName(), oldPassword))) {
					userDao.updatePassword(UserDAO.requestForNewPassword(user, oldPassword, newPassword));
					back();
				}
			} catch (InvalidUserException | UnAuthorizedException e) {
				errorMessage.setText("Old password does not match records");
			}
		}

	}

	public void back() {
		StageHelper.setScene(context, "UserHomeView");
	}

}
