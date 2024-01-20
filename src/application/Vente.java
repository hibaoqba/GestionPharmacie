package application;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vente {
    private int numero;
    private String idProduit;
    private double total;
    private LocalDate date;
    private int quantite;
    private double prix;
private Connection connection;
    public Vente(int numero, String idProduit,int quantite,double prix, double total, LocalDate date) {
        this.numero = numero;
        this.idProduit = idProduit;
        this.total = total;
        this.date = date;
        this.quantite=quantite;
        this.prix=prix;
    }

    // Getters and setters

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getProduit() {
        return idProduit;
    }

    public void setProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    
    
    
    public String getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(String idProduit) {
		this.idProduit = idProduit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	
    
    
    
}
