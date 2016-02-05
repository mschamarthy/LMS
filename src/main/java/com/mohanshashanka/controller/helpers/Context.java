package com.mohanshashanka.controller.helpers;

import com.mohanshashanka.models.User;

import javafx.stage.Stage;

public class Context {

	private User user;
	private Stage stage;
	
	public Context() {
		// TODO Auto-generated constructor stub
	}

	public Context(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
