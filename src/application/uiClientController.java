package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.Hyperlink;

import javafx.scene.control.TableColumn;

public class uiClientController {
	@FXML
	private TextField identifiant;
	@FXML
	private TableView fourTable;
	@FXML
	private TableColumn identifiantcolmm;
	@FXML
	private TableColumn fullnameColumn;
	@FXML
	private TableColumn telColumn;
	@FXML
	private TextField phone;
	@FXML
	private TextField nomfournisseur;
	@FXML
	private Button ajouter;
	@FXML
	private Button modifier;
	@FXML
	private Button supprimer;
	@FXML
	private Button exit;
	@FXML
	private Hyperlink product;
	@FXML
	private Hyperlink supplierLink;
	@FXML
	private Hyperlink POSLink;
	@FXML
	private Hyperlink historyLink;
	@FXML
	private Button logoutid;

	
}
