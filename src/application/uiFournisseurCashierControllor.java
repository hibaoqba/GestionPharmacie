

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class uiFournisseurCashierControllor  implements Initializable  {

	
	@FXML
    private Hyperlink POSLink;
	
	@FXML
    private Hyperlink historyLink;
	
	@FXML
    private Button ajouter;
	@FXML
    private Button logoutid;
    @FXML
    private Button exit;

    @FXML
    private TableColumn<Fournisseur, String>fullnameColumn;

    @FXML
    private TextField identifiant;

    @FXML
    private TableColumn<Fournisseur, String>identifiantcolmm;

    @FXML
    private Button modifier;

    @FXML
    private TextField nomfournisseur;

    @FXML
    private TextField phone;

    @FXML
    private Hyperlink product;

    @FXML
    private Button supprimer;

    @FXML
    private TableColumn<Fournisseur, String> telColumn;

    @FXML
    private TableView<Fournisseur> fourTable;
    private int lastClickedRowIndex = -1;
    private Fournisseur selectedSupplier;
    private int res;
    @FXML
    public void add(ActionEvent event) {
    	    try {
    	    	
    	    	String id=identifiant.getText();
    	    	String nom=nomfournisseur.getText();
    	    	String tel=phone.getText();
    	    
    	    Fournisseur four =new Fournisseur(id,nom,tel);
    	    	
    	    	
    	    	Alert alert;
    	    	if(nomfournisseur.getText().isEmpty() || phone.getText().isEmpty())
    	    	{
    	    		alert=new Alert(AlertType.ERROR);
    	    		alert.setTitle("Erreur");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText("Veuillez remplir tous les champs");
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
    	    		 res= four.ajouterfour();
    	    		
    	    		if(res>0)
    	    		{
    	    			alert=new Alert(AlertType.INFORMATION);
    	    			alert.setTitle("info message");
    	    			alert.setHeaderText(null);
    	    			alert.setContentText("Ajout réussi !");
    	    			alert.showAndWait();		
    	    			identifiant.clear();
    	            	nomfournisseur.clear();
    	    	        phone.clear();
    	    	        identifiant.setDisable(false);
    	    	        loadSupplierData();
    	    		}
    	    		else if(res==0)
    	    		{
    	    			alert=new Alert(AlertType.ERROR);
    	    			alert.setTitle("Erreur");
    	    			alert.setHeaderText(null);
    	    			alert.setContentText("cet identifiant existe déjà");
    	    			alert.showAndWait();
    	    		}
    	    	}
    	    	
    	    } catch (Exception e) {
    	    	
    	    	e.printStackTrace();
    	    }  
    }

    @FXML
   public void delete(ActionEvent event) {

        try {
          	
	    	String id=identifiant.getText();
	    	String nom=nomfournisseur.getText();
	    	String tel=phone.getText();
	       
	        Fournisseur four =new Fournisseur(id,nom,tel);
        	
        	
        	Alert alert;
        	if (id.isEmpty()) {
	            alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Erreur");
	            alert.setHeaderText(null);
	            alert.setContentText("Veuillez entrer un identifiant");
	            alert.showAndWait();
	        }
        	else
        	{ alert = new Alert(AlertType.CONFIRMATION);
             alert.setTitle("Confirmation");
             alert.setHeaderText(null);
        	 alert.setContentText("Êtes-vous sûr de vouloir supprimer ce Fournisseur ?");

            Optional<ButtonType> res = alert.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.OK) {
            	int result = four.supprimerfourni();
                   if (result>0) {
        			alert=new Alert(AlertType.INFORMATION);
        			alert.setTitle("info message");
        			alert.setHeaderText(null);
        			alert.setContentText("Suppression reussie");
        			alert.showAndWait();		
        			identifiant.clear();
        	        nomfournisseur.clear();
        	        phone.clear();
        	        identifiant.setDisable(false);
        	        loadSupplierData();
        	        }
        	    	
        		}		
        	}
        	
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }    
    }
  
    @FXML
   public void edit(ActionEvent event) {

    	try {
        	
        	String id=identifiant.getText();
        	String nom=nomfournisseur.getText();
        	String tel=phone.getText();
        
            Fournisseur four =new Fournisseur(id,nom,tel);
	    	
        	
        	Alert alert;
        	if(tel.isEmpty()|| nom.isEmpty())
        	{
        		alert=new Alert(AlertType.ERROR);
        		alert.setTitle("Erreur");
        		alert.setHeaderText(null);
        		alert.setContentText("Complétez tous les champs");
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
        		 res = four.modifierFourn();
        		if(res>0)
        		{
        			alert=new Alert(AlertType.INFORMATION);
        			alert.setTitle("info message");
        			alert.setHeaderText(null);
        			alert.setContentText("Modification réussie");
        			alert.showAndWait();		
        			identifiant.clear();
        	        nomfournisseur.clear();
        	        phone.clear();
        	        identifiant.setDisable(false);
        	        loadSupplierData();
        	        			      			
        		}    
        		
        	}        	
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }       
    }
    
 
    @FXML
     public void exit(ActionEvent event) {
    	System.exit(0);
    }
    @FXML
    public void loadUiHistorique()
	{
		 Parent root = null;
	        try {
	            root = FXMLLoader.load(getClass().getResource("UiHistorique.fxml"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        if (root != null) {
	            Scene currentScene = historyLink.getScene();
	            currentScene.setRoot(root);
	            Stage currentStage = (Stage) currentScene.getWindow();
	            currentStage.sizeToScene();	 
	        }
	}
    public void loadUiPOS(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("uiPOS.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            Scene currentScene = POSLink.getScene();
            currentScene.setRoot(root);
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.sizeToScene();
        }
    }
    @FXML
    public void loadUiLogin(ActionEvent event) {
        Parent root = null;
        
        try {
            root = FXMLLoader.load(getClass().getResource("logininterface.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            Scene currentScene = logoutid.getScene();
            currentScene.setRoot(root);
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.sizeToScene();
        
    }

    }
    private List<Fournisseur> getFourFromDatabase() throws ClassNotFoundException {
        List<Fournisseur> fours = new ArrayList<>();

        try (Connection connection= DBase.connection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM supplier");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nom = rs.getString("name");
             
                String telephone = rs.getString("contact_info");
                


                Fournisseur four = new Fournisseur(id,nom, telephone);
                fours.add(four);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fours;
    }
        
        
        public void loadUiMeds() {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("uiMedsCashier.fxml"));

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
        
        public void loadSupplierData() throws ClassNotFoundException
        {
        	List<Fournisseur> fourr = getFourFromDatabase();
            ObservableList<Fournisseur> fourList = FXCollections.observableArrayList(fourr);
            fourTable.setItems(fourList);
        }
        
        @FXML
    	public void displaySupplierInfo(javafx.scene.input.MouseEvent event) {
    	    selectedSupplier = fourTable.getSelectionModel().getSelectedItem();
    	    int clickedRowIndex = fourTable.getSelectionModel().getSelectedIndex();

    	    if (selectedSupplier != null) {
    	        if (clickedRowIndex == lastClickedRowIndex) {
    	            fourTable.getSelectionModel().clearSelection();
    	            lastClickedRowIndex = -1;
    	            identifiant.setDisable(false);
    	            identifiant.clear();
    	            phone.clear();
    	            nomfournisseur.clear();
    	          
    	        } else {
    	            lastClickedRowIndex = clickedRowIndex;
    	            identifiant.setDisable(true);
    	            identifiant.setText(selectedSupplier.getId());
    	            nomfournisseur.setText(selectedSupplier.getName());
    	            phone.setText(String.valueOf(selectedSupplier.getTelephone()));
    	            
    	        }
    	    }
    	}
   @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
     
        	identifiantcolmm.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("id"));
        	fullnameColumn.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("name"));
        	telColumn.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("telephone"));

        	try {
				loadSupplierData();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
        fourTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    	

    
   

}



