package com.mohanshashanka;

import com.mohanshashanka.controller.helpers.Context;
import com.mohanshashanka.controller.helpers.StageHelper;

import javafx.application.Application;
import javafx.stage.Stage;

public class LMSApp extends Application {

	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

        initRootLayout();
	}
	
	private void initRootLayout() {
		Context initialContext = new Context(primaryStage, null);
		StageHelper.setScene(initialContext, "LoginView");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
