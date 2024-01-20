package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Produit {
	private String id;
private String libelle;
private int quantite;
private double prixUnitaire;
private String idFournisseur;
private String forme;
private Connection connection;
public Produit(String id,String libelle,int quantite,double prixUnitaire,String idFournisseur,String forme)
{
	this.id=id;
	this.libelle=libelle;
	this.quantite=quantite;
	this.prixUnitaire=prixUnitaire;
	this.idFournisseur=idFournisseur;
	this.forme=forme;
	
}

public String getForme() {
	return forme;
}

public void setForme(String forme) {
	this.forme = forme;
}

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getLibelle() {
	return libelle;
}
public void setLibelle(String libelle) {
	this.libelle = libelle;
}
public int getQuantite() {
	return quantite;
}
public void setQuantite(int quantite) {
	this.quantite = quantite;
}
public double getPrixUnitaire() {
	return prixUnitaire;
}
public void setPrixUnitaire(double prixUnitaire) {
	this.prixUnitaire = prixUnitaire;
}
public String getIdFournisseur() {
	return idFournisseur;
}
public void setIdFournisseur(String idFournisseur) {
	this.idFournisseur = idFournisseur;
}
public int ajouterProduit() throws ClassNotFoundException, SQLException {
    String supplierQuery = "SELECT COUNT(*) FROM supplier WHERE id = ?";
    String productQuery = "SELECT COUNT(*) FROM product WHERE id = ?";
    connection = DBase.connection();
    PreparedStatement checkSupplier = connection.prepareStatement(supplierQuery);
    checkSupplier.setString(1, idFournisseur);
    ResultSet supplierResultSet = checkSupplier.executeQuery();
    supplierResultSet.next();
    int supplierCount = supplierResultSet.getInt(1);

    if (supplierCount == 0) {
        
        return -1;
    } else {
        
        PreparedStatement checkProduct = connection.prepareStatement(productQuery);
        checkProduct.setString(1, id);
        ResultSet productResultSet = checkProduct.executeQuery();
        productResultSet.next();
        int productCount = productResultSet.getInt(1);

        if (productCount > 0) {
            
            return 0;
        } else {
         
            String insertQuery = "INSERT INTO product VALUES (?,?,?,?,?,?)";
            PreparedStatement prepare = connection.prepareStatement(insertQuery);
            prepare.setString(1, id);
            prepare.setString(2, libelle);
            prepare.setInt(3, quantite);
            prepare.setDouble(4, prixUnitaire);
            prepare.setString(5, idFournisseur);
            prepare.setString(6, forme);
            int result = prepare.executeUpdate();
            return result;
        }
    }
}

public int modifierProduit() throws ClassNotFoundException, SQLException {
    String supplierQuery = "SELECT COUNT(*) FROM supplier WHERE id = ?";
    String updateQuery = "UPDATE product SET name=?, quantity=?, price=?, supplier_id=?, forme=? WHERE id=?";
    Connection connection = null;
    PreparedStatement checkSupplier = null;
    PreparedStatement prepare = null;
    int result = 0;

    try {
        connection = DBase.connection();
        
        checkSupplier = connection.prepareStatement(supplierQuery);
        checkSupplier.setString(1, idFournisseur);
        ResultSet supplierResultSet = checkSupplier.executeQuery();
        supplierResultSet.next();
        int supplierCount = supplierResultSet.getInt(1);

        if (supplierCount == 0) {
            
        	 return -1;
        }
        prepare = connection.prepareStatement(updateQuery);
        prepare.setString(1, libelle);
        prepare.setInt(2, quantite);
        prepare.setDouble(3, prixUnitaire);
        prepare.setString(4, idFournisseur);
        prepare.setString(5, forme);
        prepare.setString(6, id);
        result = prepare.executeUpdate();
    } finally {
        if (checkSupplier != null) {
            checkSupplier.close();
        }
        if (prepare != null) {
            prepare.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
    return result;
}

public int supprimerProduit() throws ClassNotFoundException, SQLException {
    String query = "DELETE from product WHERE id=?";
    Connection connection = null;
    PreparedStatement prepare = null;
    int result = 0;

    try {
        connection = DBase.connection();
        prepare = connection.prepareStatement(query);
      
        prepare.setString(1, id);
        result = prepare.executeUpdate();
    } finally {
        if (prepare != null) {
            prepare.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    return result;
}



public void vendreProduit(int qtt) throws ClassNotFoundException, SQLException {
    int currentQuantity = 0; 
    String selectQuery = "SELECT quantity FROM product WHERE id = ?";
    String updateQuery = "UPDATE product SET quantity = ? WHERE id = ?";
    String insertQuery = "INSERT INTO sales (product_id, quantity, price, total, sale_date) VALUES (?, ?, ?, ?,?)";
    Connection connection = null;
    PreparedStatement selectPrepare = null;
    PreparedStatement updatePrepare = null;
    PreparedStatement insertPrepare = null;
    
    LocalDate date = LocalDate.now();
    Date sqlDate = Date.valueOf(date);
    try {
        connection = DBase.connection();


        selectPrepare = connection.prepareStatement(selectQuery);
        selectPrepare.setString(1, this.id);
        ResultSet resultSet = selectPrepare.executeQuery();
        if (resultSet.next()) {
            currentQuantity = resultSet.getInt("quantity");
        }

        int newQuantity = currentQuantity - qtt;
        if (newQuantity >= 0) {
        
            updatePrepare = connection.prepareStatement(updateQuery);
            updatePrepare.setInt(1, newQuantity);
            updatePrepare.setString(2, this.id);
            updatePrepare.executeUpdate();

            double price = this.prixUnitaire; 
            double total = price * qtt;
            insertPrepare = connection.prepareStatement(insertQuery);
            insertPrepare.setString(1, this.id);
            insertPrepare.setInt(2, qtt);
            insertPrepare.setDouble(3, price);
            insertPrepare.setDouble(4, total);
            insertPrepare.setDate(5,sqlDate);
            insertPrepare.executeUpdate();
        }
    } finally {
        if (selectPrepare != null) {
            selectPrepare.close();
        }
        if (updatePrepare != null) {
            updatePrepare.close();
        }
        if (insertPrepare != null) {
            insertPrepare.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

   
}
public int verifierQuantiteDisponible() throws ClassNotFoundException, SQLException {
    int currentQuantity = 0;
    String selectQuery = "SELECT quantity FROM product WHERE id = ?";
    Connection connection = null;
    PreparedStatement selectPrepare = null;
    int result = 0;

    try {
        connection = DBase.connection();
        selectPrepare = connection.prepareStatement(selectQuery);
        selectPrepare.setString(1, this.id);
        ResultSet resultSet = selectPrepare.executeQuery();

        if (resultSet.next()) {
            currentQuantity = resultSet.getInt("quantity");
        }

        if (currentQuantity >= this.quantite) {
            result = 1; 
        }
    } finally {
        if (selectPrepare != null) {
            selectPrepare.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    return result;
}


}

