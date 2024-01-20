package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class uiPOSController implements Initializable {
	@FXML
	private Button Ajouter;
	@FXML
	private Button Retirer;
	@FXML 
	private TextField searchField;
	@FXML
	private Button exit;
	 @FXML
	    private TableColumn<Produit, String> nameColumn;
	    @FXML
	    private TableColumn<Produit, String> formColumn;
	    @FXML
	    private TableColumn<Produit, Double> prixColumn;

	    @FXML
	    private TableColumn<Produit, Integer> quantityColumn;

	    @FXML
	    private TableColumn<Produit, String> refColumn;
	    @FXML
	    private TableColumn<Produit, String> nameColumn1;
		
	    @FXML
	    private TableColumn<Produit, Double> prixColumn1;

	    @FXML
	    private TableColumn<Produit, Integer> quantityColumn1;

	    @FXML
	    private TableColumn<Produit, String> refColumn1;
	    @FXML
	    private TableColumn<Produit, String> supplierColumn;

	    @FXML
	    private TableView<Produit> productTable;
	    @FXML
	    private TableView<Produit> saleTable;
	
	@FXML
	private Button Total;
	@FXML
	private Hyperlink supplierLink;
	@FXML
	private Hyperlink productLink;
	@FXML
	private Hyperlink POSLink;
	@FXML
	private Hyperlink HistoryLink;
	@FXML
	private Button logout;
    @FXML
    private Label totalField;
    
    
    public void deleteSearch()
    {
    	searchField.clear();
    	loadProductData();
    }

	public void loadUiProduct(ActionEvent event)  {
	
		 
		        Parent root = null;
		        try {
		            root = FXMLLoader.load(getClass().getResource("uiMedsCashier.fxml"));
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		        if (root != null) {
		            Scene currentScene = productLink.getScene();
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
	
	
	private ObservableList<Produit> selectedProducts;
	
	
	@FXML
	public void retirer()
	{ ObservableList<Produit> selectedItems = saleTable.getSelectionModel().getSelectedItems();
	    selectedProducts=saleTable.getItems();
		 saleTable.getItems().removeAll(selectedItems);
		 saleTable.refresh();
		 double total = 0.0;
		    for (Produit produit : selectedProducts) {
		        total += (produit.getPrixUnitaire() * produit.getQuantite());
		    }
		 totalField.setText(""+total);
	}
	
	
	
	
	public void addToCart(ActionEvent event) throws ClassNotFoundException, SQLException {
	    ObservableList<Produit> selectedItems = productTable.getSelectionModel().getSelectedItems();

	    for (Produit produit : selectedItems) {
	        boolean exists = false;

	        for (Produit selectedProduct : selectedProducts) {
	            if (produit.getId().equals(selectedProduct.getId())) {
	                int quantite = selectedProduct.getQuantite();
	                selectedProduct.setQuantite(quantite + 1);
	                exists = true;
	                break;
	            }
	        }

	        if (!exists) {
	            produit.setQuantite(1);
	            selectedProducts.add(produit);
	        }
	    }

	    saleTable.refresh(); 
	  

	    double total = 0.0;
	    for (Produit produit : selectedProducts) {
	        total += (produit.getPrixUnitaire() * produit.getQuantite());
	    }
	    totalField.setText(Double.toString(total)+" DH");
	}
	
	private String getCurrentDate() {
	    LocalDate currentDate = LocalDate.now();
	    return currentDate.toString();
	}

	private double calculateTotal(List<Produit> produitsVendus) {
	    double total = 0.0;

	    for (Produit produit : produitsVendus) {
	        total += produit.getPrixUnitaire() * produit.getQuantite();
	    }

	    return total;
	}

	 private void printTicket(String ticketContent) {
	        PrinterJob printerJob = PrinterJob.getPrinterJob();
	        printerJob.setPrintable(new Printable() {
	            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
	                if (pageIndex > 0) {
	                    return NO_SUCH_PAGE;
	                }

	                Graphics2D graphics2D = (Graphics2D) graphics;
	                graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

	                Font font = new Font("Courier New", Font.PLAIN, 12);
	                graphics2D.setFont(font);

	                String[] lines = ticketContent.split("\n");
	                int y = 15;
	                for (String line : lines) {
	                    graphics2D.drawString(line, 10, y);
	                    y += 15;
	                }

	                return PAGE_EXISTS;
	            }
	        });

	        try {
	            printerJob.print();
	        } catch (PrinterException e) {
	            e.printStackTrace();
	        }
	    }
	
	 private String generateTicketContent(List<Produit> produitsVendus) {
		    StringBuilder content = new StringBuilder();
		    content.append("Ticket de vente:\n");
		    content.append("Date: ").append(getCurrentDate()).append("\n");
		    content.append("Produits vendus:\n");

		    for (Produit produit : produitsVendus) {
		        content.append("- ").append(produit.getLibelle()).append(" ").append(produit.getForme()).append(": ").append(produit.getPrixUnitaire()).append(" DH x ").append(produit.getQuantite()).append("\n");
		    }

		    double total = calculateTotal(produitsVendus);
		    content.append("Total: ").append(total).append(" DH\n");

		    return content.toString();
		}

	



	public void confirmerVente(ActionEvent event) throws ClassNotFoundException, SQLException {
	    ObservableList<Produit> selectedItems = saleTable.getItems();
	    List<Produit> produitsVendus = new ArrayList<>();
	    int test = 0;
	    Alert alert;

	    if (!selectedItems.isEmpty()) {
	        for (Produit produit : selectedItems) {
	            int result = produit.verifierQuantiteDisponible();
	            if (result == 0) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Erreur");
	                alert.setHeaderText(null);
	                alert.setContentText("Quantite de '" + produit.getLibelle() + "' insuffisante");
	                alert.showAndWait();
	                test = 1;
	                break;  
	            } else {
	                produitsVendus.add(produit);
	                
	            }
	        }

	        if (test == 0) {
	            for (Produit produit : produitsVendus) {
	                produit.vendreProduit(produit.getQuantite());
	                
	            }

	            saleTable.getItems().removeAll(produitsVendus);
	            saleTable.refresh();

	            alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Information");
	            alert.setHeaderText(null);
	            alert.setContentText("Vente effectuée avec succès!");
	            alert.showAndWait();

	            totalField.setText("0");
	            loadProductData();
	            String ticketContent = generateTicketContent(produitsVendus);
	            printTicket(ticketContent);
	        }
	    } else {
	        alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText(null);
	        alert.setContentText("Veuillez ajouter des produits");
	        alert.showAndWait();
	    }
	}

	
	
	
	
	public void decrement() {
	    ObservableList<Produit> selectedItems = saleTable.getSelectionModel().getSelectedItems();
	    ObservableList<Produit> items = saleTable.getItems();
	    Alert alert;
	    for (Produit produit : selectedItems) {
	        int newQuantity = produit.getQuantite() - 1;
	        if (newQuantity >0) {
	            produit.setQuantite(newQuantity);
	        }
	        else {
	        	 alert = new Alert(Alert.AlertType.ERROR);
		            alert.setTitle("Erreur");
		            alert.setHeaderText(null);
		            alert.setContentText("Quantite minimale est 1!");
		            alert.showAndWait();
	        }
	    }
	    
	    saleTable.refresh();
	    
	    double total = 0.0;
	    for (Produit produit : items) {
	        total += (produit.getPrixUnitaire() * produit.getQuantite());
	    }
	    totalField.setText(Double.toString(total));
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
		    totalField.setText(0+"");
	        refColumn.setCellValueFactory(new PropertyValueFactory<Produit,String>("id"));
	        nameColumn.setCellValueFactory(new PropertyValueFactory<Produit,String>("libelle"));
	        quantityColumn.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("quantite"));
	        prixColumn.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prixUnitaire"));
	        supplierColumn.setCellValueFactory(new PropertyValueFactory<Produit,String>("idFournisseur"));
	        formColumn.setCellValueFactory(new PropertyValueFactory<Produit,String>("forme"));
	        refColumn1.setCellValueFactory(new PropertyValueFactory<Produit,String>("id"));
	        nameColumn1.setCellValueFactory(new PropertyValueFactory<Produit,String>("libelle"));
	        quantityColumn1.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("quantite"));
	        prixColumn1.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prixUnitaire"));
	        loadProductData();
	        
	    

	    productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    saleTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    selectedProducts = FXCollections.observableArrayList();
	    saleTable.setItems(selectedProducts);
	}

	}

