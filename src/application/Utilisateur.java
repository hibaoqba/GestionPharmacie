package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilisateur {
	
	    private String identifiant;
	    private String nom;
	    private String telephone;
	    private String motDePasse;
	    private String role;
	    static Connection connection;
	    
	    public Utilisateur(String id,String nom, String mdp, String role,String telephone)throws ClassNotFoundException, SQLException
	    
	    { 
	    this.identifiant = id;
	        this.nom=nom;
	        this.telephone=telephone;
	        this.motDePasse = mdp;
	        this.role = role;
	    }


	    
	    public String getIdentifiant() {
			return identifiant;
		}



		public void setIdentifiant(String identifiant) {
			this.identifiant = identifiant;
		}



		public String getNom() {
			return nom;
		}



		public void setNom(String nom) {
			this.nom = nom;
		}



		public String getTelephone() {
			return telephone;
		}



		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}



		public String getMotDePasse() {
			return motDePasse;
		}



		public void setMotDePasse(String motDePasse) {
			this.motDePasse = motDePasse;
		}



		public String getRole() {
			return role;
		}



		public void setRole(String role) {
			this.role = role;
		}



		public static Connection getConnection() {
			return connection;
		}



		


		public static boolean login(String username, String password,String role) throws SQLException {
	    	String query = "SELECT * FROM user WHERE username=? AND password=? AND role=?";
	    	try {
				connection= DBase.connection();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
	
				e.printStackTrace();
			}
	        try (PreparedStatement prepare = connection.prepareStatement(query)) {
	            prepare.setString(1, username);
	            prepare.setString(2, password);
	            prepare.setString(3, role);
	            try (ResultSet result = prepare.executeQuery()) {
	                return result.next();
	    }
	        }
	    }
	    
		
		
		
		public int ajouterUtilisateur() throws ClassNotFoundException, SQLException {
		    String query = "SELECT COUNT(*) FROM user WHERE username = ?";
		    connection = DBase.connection();
		    PreparedStatement checkUsername = connection.prepareStatement(query);
		    checkUsername.setString(1, identifiant);
		    ResultSet resultSet = checkUsername.executeQuery();
		    resultSet.next();
		    int count = resultSet.getInt(1);

		    if (count > 0) {
		       
		        return 0;
		    } else {
		        
		        query = "INSERT INTO user VALUES (null,?,?,?,?,?)";
		        PreparedStatement prepare = connection.prepareStatement(query);
		        prepare.setString(1, identifiant);
		        prepare.setString(2, motDePasse);
		        prepare.setString(3, role);
		        prepare.setString(4, telephone);
		        prepare.setString(5, nom);
		        int result = prepare.executeUpdate();
		        return result;
		    }
		}

	    
	    
	    
	    
	    public int modifierUtilisateur() throws ClassNotFoundException, SQLException {
	        String query = "UPDATE user SET  password=?, role=?, telephone=?, fullname=? WHERE username=?";
	        Connection connection = null;
	        PreparedStatement prepare = null;
	        int result = 0;

	        try {
	            connection = DBase.connection();
	            prepare = connection.prepareStatement(query);
	           
	            prepare.setString(1, motDePasse);
	            prepare.setString(2, role);
	            prepare.setString(3, telephone);
	            prepare.setString(4, nom);
	            prepare.setString(5, identifiant);
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
	    
	    
	    
	    
	    public int supprimerUtilisateur() throws ClassNotFoundException, SQLException {
	        String query = "DELETE from user WHERE username=?";
	        Connection connection = null;
	        PreparedStatement prepare = null;
	        int result = 0;

	        try {
	            connection = DBase.connection();
	            prepare = connection.prepareStatement(query);
	          
	            prepare.setString(1, identifiant);
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

}
	
	 


