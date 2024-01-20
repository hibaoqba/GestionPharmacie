package application;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class logininterfaceController implements Initializable {
	    @FXML
	    private AnchorPane anchorform;

	    @FXML
	    private Button exit;
	    @FXML
	    private Button loginbtn;

	    @FXML
	    private Pane paneform;

	    @FXML
	    private PasswordField passwd;

	    @FXML
	    private TextField username;
	    @FXML
	    private ComboBox<String> selectRole;
public void exit()
{
	System.exit(0);
}



public  void login() throws ClassNotFoundException, SQLException
{


	String usname=username.getText();
	String password=passwd.getText();
	String role =selectRole.getValue();
	
boolean result=Utilisateur.login(usname, password,role);
	Alert alert;
	if(username.getText().isEmpty())
	{
		alert=new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		alert.setHeaderText(null);
		alert.setContentText("completez vos donnees");
		alert.showAndWait();		
	}
	else
	{
		if(result)
		{	
			

			loginbtn.getScene().getWindow().hide();
		try {	
			Parent root = null;
			if(role.equals("Admin"))

				root = FXMLLoader.load(getClass().getResource("uiUSER.fxml"));
			else if(role.equals("Caissier"))

				root = FXMLLoader.load(getClass().getResource("uiMedsCashier.fxml"));
			
			
			Stage stage=new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
		else
		{
			alert=new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("identifiant ,position ou mot de passe incorrecte");
			alert.showAndWait();		

		}
	}
	
} 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginbtn.setOnAction(event -> {
			try {
				login();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		selectRole.getItems().addAll("Admin", "Caissier");
		selectRole.setValue("Admin");
	}
	
}
