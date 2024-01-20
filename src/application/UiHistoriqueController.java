package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.sql.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.TableColumn;

public class UiHistoriqueController implements Initializable {
	@FXML
	private Button exit;
	@FXML
	private TableView <Vente> historyTable;
	
	@FXML
	private TableColumn<Vente,LocalDate> dateColumn;
	@FXML
	private TableColumn <Vente,String>referenceColumn;
	@FXML
	private TableColumn<Vente,Double> priceColumn;
	@FXML
	private TableColumn<Vente,Integer> quantityColumn;
	@FXML
	private TableColumn<Vente,Double> totalColumn;
	@FXML
	private Hyperlink supplierLink;
	@FXML
	private Hyperlink productLink;
	@FXML
	private Hyperlink POSLink;
	@FXML
	private Button logout;


@FXML

	public void exit()
    {
    	System.exit(0);
    }

	
	@FXML
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
	public List<Vente> getSalesFromDatabase() throws ClassNotFoundException, SQLException {
	    List<Vente> sales = new ArrayList<>();
	    Connection connection = null;

	    try {
	        connection = DBase.connection();
	        String query = "SELECT * FROM sales";
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        while (resultSet.next()) {
	            int id = resultSet.getInt("sale_id");
	            String productId = resultSet.getString("product_id");
	            int quantity = resultSet.getInt("quantity");
	            double productPrice = resultSet.getDouble("price");
	            double total = resultSet.getDouble("total");
	            LocalDate newDate = resultSet.getObject("sale_date", LocalDate.class);
	            Vente vente = new Vente(id, productId, quantity, productPrice, total, newDate);
	            sales.add(vente);
	        }
	    } finally {
	        if (connection != null) {
	            connection.close();
	        }
	    }

	    return sales;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {	
        referenceColumn.setCellValueFactory(new PropertyValueFactory<>("idProduit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		List<Vente> sales;
		
			sales = getSalesFromDatabase();
		
		 ObservableList<Vente> userList = FXCollections.observableArrayList(sales);
		historyTable.setItems(userList);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
