package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


import javafx.scene.control.ComboBox;
public class uiUSERController implements Initializable {
	@FXML
    private Button add;

    @FXML
    private TableColumn<Utilisateur,String> passColumn;

    @FXML
    private Button delete;
    @FXML
    private Button logout;
    @FXML
    private Button edit;

    @FXML
    private Button exit;

    @FXML
    private TextField fullname;
    @FXML
    private Hyperlink user;

    @FXML
    private Hyperlink historiqueLink;
    @FXML
    private TableColumn<Utilisateur,String> fullnameColumn;

    @FXML
    private TextField password;

    @FXML
    private TextField phone;

    @FXML
    private Hyperlink product;

    @FXML
    private ComboBox<String> role;

    @FXML
    private TableColumn<Utilisateur,String> roleColumn;
	@FXML
	private Hyperlink supplierLink;
   
    @FXML
    private TableColumn<Utilisateur,String> telColumn;

    @FXML
    private TableView<Utilisateur> userTable;

    @FXML
    private TextField username;

    @FXML
    private TableColumn<Utilisateur,String> usernameColumn;
	    private Utilisateur selectedUser;

    public void exit()
    {
    	System.exit(0);
    }
   
    private int result;
    
    
    public  void add()
    {
    	
    
    try {
    	
    	String uname=username.getText();
    	String passwd=password.getText();
    	String newRole=role.getValue();
    	String tel=phone.getText();
    	String nom=fullname.getText();
    	Utilisateur user=new Utilisateur(uname,nom,passwd,newRole,tel);
    	
    	
    	Alert alert;
    	if(username.getText().isEmpty() || password.getText().isEmpty()||newRole==null ||phone.getText().isEmpty()|| fullname.getText().isEmpty())
    	{
    		alert=new Alert(AlertType.ERROR);
    		alert.setTitle("Erreur");
    		alert.setHeaderText(null);
    		alert.setContentText("completez vos donnees");
    		alert.showAndWait();		
    	}
    	else if(phone.getText().length()!=10 || !tel.matches("\\d+"))
    	{
    		alert=new Alert(AlertType.ERROR);
    		alert.setTitle("Erreur");
    		alert.setHeaderText(null);
    		alert.setContentText("Le numéro de téléphone doit contenir 10 chiffres");
    		alert.showAndWait();
    	}
    	else
    	{
    		result=user.ajouterUtilisateur();
    		
    		if(result>0)
    		{
    			alert=new Alert(AlertType.INFORMATION);
    			alert.setTitle("info");
    			alert.setHeaderText(null);
    			alert.setContentText("Ajout reussi");
    			alert.showAndWait();		
    			username.clear();
    	        fullname.clear();
    	        role.getSelectionModel().clearSelection();
    	        phone.clear();
    	        password.clear();
    	        username.setDisable(false);
    	        loadUserData();
    	        
    			
    			
    			
    		}
    		else if(result==0)
    		{
    			alert=new Alert(AlertType.ERROR);
    			alert.setTitle("erreur");
    			alert.setHeaderText(null);
    			alert.setContentText("cet identifiant existe déjà");
    			alert.showAndWait();
    		}
    	
    		
    	}
    	
    } catch (Exception e) {
    	
    	e.printStackTrace();
    }
  

    
}
    public void edit()
    {
    	try {
        	
        	String id=username.getText();
        	String passwd=password.getText();
        	String poste=role.getValue();
        	String tel=phone.getText();
        	String nom=fullname.getText();
        	Utilisateur user=new Utilisateur(id,nom,passwd,poste,tel);
        	
        	
        	Alert alert;
        	if(passwd.isEmpty()|| tel.isEmpty()|| nom.isEmpty())
        	{
        		alert=new Alert(AlertType.ERROR);
        		alert.setTitle("Erreur");
        		alert.setHeaderText(null);
        		alert.setContentText("completez vos donnees");
        		alert.showAndWait();		
        	}
        	else if(phone.getText().length()!=10)
        	{
        		alert=new Alert(AlertType.ERROR);
        		alert.setTitle("Erreur");
        		alert.setHeaderText(null);
        		alert.setContentText("Le numero de telephone doit etre de 10 chiffres");
        		alert.showAndWait();
        	}
        	else
        	{
        		result=user.modifierUtilisateur();
        		if(result>0)
        		{
        			alert=new Alert(AlertType.INFORMATION);
        			alert.setTitle("info message");
        			alert.setHeaderText(null);
        			alert.setContentText("Modification reussie");
        			alert.showAndWait();		
        			username.clear();
        	        fullname.clear();
        	        role.getSelectionModel().clearSelection();
        	        phone.clear();
        	        password.clear();
        	        username.setDisable(false);
        	        loadUserData();
        		}    
        		
        	}        	
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }       
    }
    @FXML
	public void loadUIfournisseur()
	{
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("uiFournisseur.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            Scene currentScene = supplierLink.getScene();
            currentScene.setRoot(root);
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.sizeToScene();
        }
    }
    public void loadUiMeds() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("uiMeds.fxml"));

            if (root != null) {
                Scene currentScene = product.getScene();
                currentScene.setRoot(root);
                Stage currentStage = (Stage) currentScene.getWindow();
               
                currentStage.sizeToScene();
                currentStage.show();  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadUiHistorique() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("UiHistoriqueAdmin.fxml"));

            if (root != null) {
                Scene currentScene = historiqueLink.getScene();
                currentScene.setRoot(root);
                Stage currentStage = (Stage) currentScene.getWindow();
               
                currentStage.sizeToScene();
                currentStage.show();  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<Utilisateur> getUsersFromDatabase() throws ClassNotFoundException {
        List<Utilisateur> users = new ArrayList<>();

        try (Connection connection= DBase.connection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String fullName = rs.getString("fullname");
                String role = rs.getString("role");
               String password=rs.getString("password");
                String telephone = rs.getString("telephone");
                


                Utilisateur user = new Utilisateur(username, fullName, password,role, telephone);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
@FXML

    public void displayUserInfo(javafx.scene.input.MouseEvent event) {
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        int clickedRowIndex = userTable.getSelectionModel().getSelectedIndex();

        if (selectedUser != null) {
            if (clickedRowIndex == lastClickedRowIndex) {
                userTable.getSelectionModel().clearSelection();
                lastClickedRowIndex = -1;
                username.setDisable(false);
                username.clear();
                fullname.clear();
                role.getSelectionModel().clearSelection();
                phone.clear();
                password.clear();
            } else {
                lastClickedRowIndex = clickedRowIndex;
                username.setDisable(true);
                username.setText(selectedUser.getIdentifiant());
                fullname.setText(selectedUser.getNom());
                role.setValue(selectedUser.getRole());
                phone.setText(selectedUser.getTelephone());
                password.setText(selectedUser.getMotDePasse());
            }
        }
    }
public void loadUiLogin()
{ 
    Parent root = null;
    try {
        root = FXMLLoader.load(getClass().getResource("logininterface.fxml"));
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (root != null) {
        Scene currentScene = logout.getScene();
        currentScene.setRoot(root);
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.sizeToScene();
    
}

}
    public  void delete()
    {

    try {
    	
    	String uname=username.getText();
    	String passwd=password.getText();
    	String newRole=role.getValue();
    	String tel=phone.getText();
    	String nom=fullname.getText();
    	Utilisateur user=new Utilisateur(uname,nom,passwd,newRole,tel);
    	result=user.supprimerUtilisateur();
    	
    	Alert alert;
    	if(username.getText().isEmpty())
    	{
    		alert=new Alert(AlertType.ERROR);
    		alert.setTitle("Erreur");
    		alert.setHeaderText(null);
    		alert.setContentText("Veuillez entrer un nom d'utilisateur");
    		alert.showAndWait();		
    	}
    	else
    	{ alert = new Alert(AlertType.CONFIRMATION);
         alert.setTitle("Confirmation");
         alert.setHeaderText(null);
    	 alert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
    		
    			alert=new Alert(AlertType.INFORMATION);
    			alert.setTitle("info message");
    			alert.setHeaderText(null);
    			alert.setContentText("Suppression reussie");
    			alert.showAndWait();		
    			username.clear();
    	        fullname.clear();
    	        role.getSelectionModel().clearSelection();
    	        phone.clear();
    	        password.clear();
    	        username.setDisable(false);
    	        loadUserData();
    		}		
    	}
    	
    } catch (Exception e) {
    	
    	e.printStackTrace();
    }    
}
   
    private int lastClickedRowIndex = -1;


    private void loadUserData() {
        try {
            List<Utilisateur> users = getUsersFromDatabase();
            ObservableList<Utilisateur> userList = FXCollections.observableArrayList(users);
            userTable.setItems(userList);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void initialize(URL arg0, ResourceBundle arg1)
    {   role.getItems().addAll("Admin", "Caissier");
        
    	usernameColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>("identifiant"));
	    fullnameColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>("nom"));
	    roleColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>("role"));
	    passColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>("motDePasse"));
	    telColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>("telephone"));
 loadUserData();

    userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	}
    }
    
    
