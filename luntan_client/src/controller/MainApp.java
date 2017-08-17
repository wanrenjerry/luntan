package controller;

import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	public static Stage primaryStage;
	public static FXMLLoader loader;
	public static Socket ss;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		loader=new FXMLLoader();
		setConnect();	
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void setConnect(){
		try {
			loader.setLocation(MainApp.class.getResource("/view/Connect.fxml"));
			AnchorPane myroot=(AnchorPane)loader.load();
			Scene scene=new Scene(myroot);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
