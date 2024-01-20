package application;
import javafx.fxml.FXML;

import java.sql.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("logininterface.fxml"));
			
			        Scene scene = new Scene(root);

			        primaryStage.setScene(scene);
			        
			        
			        primaryStage.initStyle(StageStyle.UNDECORATED);
			        
			        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
			
	}
	
	}



