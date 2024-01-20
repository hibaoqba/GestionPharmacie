package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Fournisseur {

	    private String id;
	    private String name;
	    private String telephone;
	    static Connection connection; 
	    
	    public Fournisseur(String id, String name, String telephone) {
	        this.id = id;
	        this.name =name;
	        this.telephone = telephone;
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }


	    public String getTelephone() {
	        return telephone;
	    }

	    public void setTelephone(String telephone) {
	        this.telephone = telephone;
	        }

		public static Connection getConnection() {
			return connection;
		}


		
	    
		
		
		
		public int ajouterfour() throws ClassNotFoundException, SQLException {
		    String query = "SELECT COUNT(*) FROM supplier WHERE id = ?";
		    connection = DBase.connection();
		    PreparedStatement checksupplier = connection.prepareStatement(query);
		    checksupplier.setString(1, id);
		    ResultSet resultSet = checksupplier.executeQuery();
		    resultSet.next();
		    int count = resultSet.getInt(1);

		    if (count > 0) {
		       
		        return 0;
		    } else {
		        
		        query = "INSERT INTO supplier VALUES (?,?,?)";
		        PreparedStatement prepare = connection.prepareStatement(query);
		        prepare.setString(1, id);
		        prepare.setString(2, name);
		        prepare.setString(3, telephone);
		        int result = prepare.executeUpdate();
		        return result;
		    }
		}

	    
		public int modifierFourn() throws ClassNotFoundException, SQLException {
		    String query = "UPDATE supplier SET name=?, contact_info=? WHERE id=?";
		    Connection connection = null;
		    PreparedStatement prepare = null;
		    int result = 0;

		    try {
		        connection = DBase.connection(); 
		        prepare = connection.prepareStatement(query);
		        prepare.setString(3, id);
		        prepare.setString(2, telephone);
		        prepare.setString(1, name);

		        result = prepare.executeUpdate();
		    } finally {
		        
		        try {
		            if (prepare != null) {
		                prepare.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        try {
		            if (connection != null) {
		                connection.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return result;
		}


	    
	    public int supprimerfourni() throws ClassNotFoundException, SQLException {
	        String query = "DELETE from supplier WHERE name=?";
	        Connection connection = null;
	        PreparedStatement prepare = null;
	        int result = 0;

	        try {
	            connection = DBase.connection();
	            prepare = connection.prepareStatement(query);
	            prepare.setString(1, name);
	           
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
