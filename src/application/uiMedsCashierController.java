package application;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class uiMedsCashierController implements Initializable{
	
	    @FXML
	    private Button exit;
	    @FXML 
	    private TextField searchField;
	    @FXML
        private ComboBox <String> formeSelect;
	    @FXML
	    private TableColumn<Produit, String> formColumn;
	    @FXML
	    private Button logout;
	    @FXML
	    private Button addProduct;
	    @FXML
	    private TableColumn<Produit, String> nameColumn;
	
	    @FXML
	    private TableColumn<Produit, Double> prixColumn;

	    @FXML
	    private TableColumn<Produit, Integer> quantityColumn;

	    @FXML
	    private TableColumn<Produit, String> refColumn;

	    @FXML
	    private TableColumn<Produit, String> supplierColumn;

	    @FXML
	    private TableView<Produit> productTable;
	

	    @FXML
	    private TextField libelle;

	    

	    @FXML
	    private TextField prix;


	    @FXML
	    private TextField quantite;
	    @FXML
		private Hyperlink HistoryLink;
	  
	    @FXML
	    private TextField reference;

	    @FXML
	    private TextField supplier;

	    
	    @FXML
	    private Hyperlink supplierLink;
	    
	    @FXML
	    private Hyperlink POSLink;

	    private Produit selectedProduct;

	    
	    
	    public void deleteSearch()
	    {
	    	searchField.clear();
	    	loadProductData();
	    }
	public void exit()
    {
    	System.exit(0);
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
	private List<Produit> searchProductsFromDatabase(String searchQuery) throws ClassNotFoundException {
	    List<Produit> products = new ArrayList<>();

	    try (Connection connection = DBase.connection();
		         PreparedStatement stmt = connection.prepareStatement("SELECT * FROM product WHERE name LIKE ? OR id LIKE ?");
		    ) {
		        stmt.setString(1, "%" + searchQuery + "%");
		        stmt.setString(2, "%" + searchQuery + "%");
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String reference = rs.getString("id");
	            String name = rs.getString("name");
	            int quantite = rs.getInt("quantity");
	            double prix = rs.getDouble("price");
	            String idFournisseur = rs.getString("supplier_id");
	            String forme = rs.getString("forme");

	            Produit produit = new Produit(reference, name, quantite, prix, idFournisseur, forme);
	            products.add(produit);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return products;
	}

	@FXML
	public void searchProduct(ActionEvent event) {
	    String searchQuery = searchField.getText().trim();
	    if (!searchQuery.isEmpty()) {
	        try {
	            List<Produit> products = searchProductsFromDatabase(searchQuery);
	            ObservableList<Produit> productList = FXCollections.observableArrayList(products);
	            productTable.setItems(productList);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    } else {
	       
	        loadProductData();
	    }
	}
	public void loadUiHistorique()
	{
		 Parent root = null;
	        try {
	            root = FXMLLoader.load(getClass().getResource("UiHistorique.fxml"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        if (root != null) {
	            Scene currentScene = HistoryLink.getScene();
	            currentScene.setRoot(root);
	            Stage currentStage = (Stage) currentScene.getWindow();
	            currentStage.sizeToScene();	 
	        }
	}
	private List<Produit> getProductsFromDatabase() throws ClassNotFoundException {
        List<Produit> prod = new ArrayList<>();

        try (Connection connection= DBase.connection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM product");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String reference = rs.getString("id");
                String name = rs.getString("name");
                int quantite = rs.getInt("quantity");
               double prix=rs.getDouble("price");
                String idFournisseur = rs.getString("supplier_id");
                String forme=rs.getString("forme");


                Produit produit = new Produit(reference,name ,quantite,prix, idFournisseur,forme);
                prod.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prod;
    }
	private int lastClickedRowIndex = -1;
	@FXML

	public void displayProductInfo(javafx.scene.input.MouseEvent event) {
	    selectedProduct = productTable.getSelectionModel().getSelectedItem();
	    int clickedRowIndex = productTable.getSelectionModel().getSelectedIndex();

	    if (selectedProduct != null) {
	        if (clickedRowIndex == lastClickedRowIndex) {
	            productTable.getSelectionModel().clearSelection();
	            lastClickedRowIndex = -1;
	            reference.setDisable(false);
	            reference.clear();
	            libelle.clear();
	            quantite.clear();
	            prix.clear();
	            supplier.clear();
	            formeSelect.getSelectionModel().clearSelection();
	        } else {
	            lastClickedRowIndex = clickedRowIndex;
	            reference.setDisable(true);
	            reference.setText(selectedProduct.getId());
	            libelle.setText(selectedProduct.getLibelle());
	            quantite.setText(String.valueOf(selectedProduct.getQuantite()));
	            prix.setText(String.valueOf(selectedProduct.getPrixUnitaire()));
	            supplier.setText(selectedProduct.getIdFournisseur());
	            formeSelect.setValue(selectedProduct.getForme());
	        }
	    }
	}

	
	public void add() {
	    try {
	        String reference = this.reference.getText();
	        String libelle = this.libelle.getText();
	        int quantite = Integer.parseInt(this.quantite.getText());
	        double prixUnitaire = Double.parseDouble(this.prix.getText());
	        String idFournisseur = this.supplier.getText();
	        String forme=formeSelect.getValue();
	        Produit produit = new Produit(reference, libelle, quantite, prixUnitaire, idFournisseur,forme);

	        Alert alert;
	        if (reference.isEmpty() || libelle.isEmpty() || forme==null || this.prix.getText().isEmpty() || idFournisseur.isEmpty() ) {
	            alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Erreur");
	            alert.setHeaderText(null);
	            alert.setContentText("Veuillez remplir tous les champs");
	            alert.showAndWait();
	        } else {
	           
	            int result = produit.ajouterProduit();

	            if (result > 0) {
	                alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Message d'information");
	                alert.setHeaderText(null);
	                alert.setContentText("Ajout réussi");
	                alert.showAndWait();

	                
	                this.reference.clear();
	                this.libelle.clear();
	                this.quantite.clear();
	                this.prix.clear();
	                this.supplier.clear();
	                this.searchField.clear();
	                this.reference.setDisable(false);
	                formeSelect.getSelectionModel().clearSelection();
	                loadProductData();

	                
	                
	            } 
	            else if (result == 0) {
	            	alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Message d'erreur");
	                alert.setHeaderText(null);
	                alert.setContentText("Cette référence existe déjà");
	                alert.showAndWait();
	            }
	            else {
	            	alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Message d'erreur");
	                alert.setHeaderText(null);
	                alert.setContentText("id de fournisseur non valide");
	                alert.showAndWait();
	            }
	       
	        }
	    } catch (NumberFormatException e) {

	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText(null);
	        alert.setContentText("Veuillez entrer des valeurs numériques valides pour la quantité et le prix");
	        alert.showAndWait();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void edit() {
	    try {
	        String ref = this.reference.getText();
	        String lib = this.libelle.getText();
	        int quant = Integer.parseInt(this.quantite.getText());
	        double prixU = Double.parseDouble(this.prix.getText());
	        String idFourn = this.supplier.getText();
	        String forme=formeSelect.getValue();
	        Produit produit = new Produit(ref, lib, quant, prixU, idFourn,forme);

	        Alert alert;
	        if (ref.isEmpty() || lib.isEmpty() || forme==null || this.prix.getText().isEmpty() || idFourn.isEmpty()) {
	            alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Erreur");
	            alert.setHeaderText(null);
	            alert.setContentText("Veuillez remplir tous les champs");
	            alert.showAndWait();
	        } else {
	            int result = produit.modifierProduit();
	            if (result > 0) {
	                alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Message d'information");
	                alert.setHeaderText(null);
	                alert.setContentText("Modification réussie");
	                alert.showAndWait();
	                reference.clear();
        	        libelle.clear();
        	        quantite.clear();
        	        prix.clear();
        	        supplier.clear();    	
        	        this.searchField.clear();
	                this.reference.setDisable(false);
	                formeSelect.getSelectionModel().clearSelection();
	                loadProductData();

	            }
	            else if(result==-1){
	            	alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Message d'erreur");
	                alert.setHeaderText(null);
	                alert.setContentText("id de fournisseur non valide");
	                alert.showAndWait();
	            }
	        }
	    } catch (NumberFormatException e) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText(null);
	        alert.setContentText("Veuillez entrer des valeurs numériques valides pour la quantité et le prix");
	        alert.showAndWait();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void delete() {
	    try {
	        String ref = this.reference.getText();
	        Produit produit = new Produit(ref, null, 0, 0, null,null);

	        Alert alert;
	        if (ref.isEmpty()) {
	            alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Erreur");
	            alert.setHeaderText(null);
	            alert.setContentText("Veuillez entrer une référence");
	            alert.showAndWait();
	        } else {
	            alert = new Alert(AlertType.CONFIRMATION);
	            alert.setTitle("Confirmation");
	            alert.setHeaderText(null);
	            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ?");

	            Optional<ButtonType> result = alert.showAndWait();
	            if (result.isPresent() && result.get() == ButtonType.OK) {
	                int deleteResult = produit.supprimerProduit();
	                if (deleteResult > 0) {
	                    alert = new Alert(AlertType.INFORMATION);
	                    alert.setTitle("Message d'information");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Suppression réussie");
	                    alert.showAndWait();
	                    reference.clear();
	                    libelle.clear();
	                    quantite.clear();
	                    prix.clear();
	                    supplier.clear();
	                    this.searchField.clear();
		                this.reference.setDisable(false);
		                formeSelect.getSelectionModel().clearSelection();
		                loadProductData();

	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
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
	public void loadUIfournisseur()
	{
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("uiFournisseurCashier.fxml"));
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


	private void loadProductData() {
        try {
            List<Produit> users = getProductsFromDatabase();
            ObservableList<Produit> userList = FXCollections.observableArrayList(users);
            productTable.setItems(userList);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		formeSelect.getItems().addAll("Injectable", "Solution buvable","poudre","Gélule","Comprimé","Pommade","Autre");
    	refColumn.setCellValueFactory(new PropertyValueFactory<Produit,String>("id"));
	    nameColumn.setCellValueFactory(new PropertyValueFactory<Produit,String>("libelle"));
	    quantityColumn.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("quantite"));
	    prixColumn.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prixUnitaire"));
	    supplierColumn.setCellValueFactory(new PropertyValueFactory<Produit,String>("idFournisseur"));
	    formColumn.setCellValueFactory(new PropertyValueFactory<Produit,String>("forme"));
	    
	    loadProductData();

    productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	}
		
	}

