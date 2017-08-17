package controller;

import java.io.IOException;

import dbopt.DBService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static DBService db;
	@Override
	public void start(Stage primaryStage) {
		db=new DBService();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/MainLayout.fxml"));
		
		try {
			AnchorPane root=(AnchorPane)loader.load();
			
			Scene scene=new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
