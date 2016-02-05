package com.mohanshashanka.controller.helpers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Optional;

import com.mohanshashanka.LMSApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StageHelper {

	public StageHelper() {
	}

	public static <T> T setScene(Context context, String sceneViewName) {
		try {
			String viewPath = getViewPath(sceneViewName);
			FXMLLoader loader = new FXMLLoader(StageHelper.class.getResource(viewPath));
			AnchorPane layout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(layout);
			scene.getStylesheets().add(LMSApp.class.getResource(getCSSPath()).toExternalForm());
			Stage stage = context.getStage();
			stage.setScene(scene);
			T controller = loader.getController();
			Class<?> clazz = controller.getClass();
			Method contextPasserMethod = clazz.getMethod("setContext", Context.class);
			contextPasserMethod.invoke(controller, context);
			return controller;
		} catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			System.out.println("exception occured while changing scene");
			e.printStackTrace();
			return null;
		}
	}
	
	public static String inputDialog(String title, String headerText, String contentText){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    return result.get();
		}
		return null;
	}

	// TO MAKE PATH OS INDEPENDENT
	private static String getViewPath(String sceneViewName) {
		FileSystem fs = FileSystems.getDefault();
		String seperator = fs.getSeparator();
		return "../../view" + seperator + sceneViewName + ".fxml";
	}

	private static String getCSSPath() {
		FileSystem fs = FileSystems.getDefault();
		String seperator = fs.getSeparator();
		return "view" + seperator + "css" + seperator + "application.css";
	}

}
