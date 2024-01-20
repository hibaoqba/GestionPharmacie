package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBase {
	
public static Connection connection() throws ClassNotFoundException, SQLException

{
	
		Class.forName("com.mysql.cj.jdbc.Driver");
	
	Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/pharmacie","root","");	
	
	return connection;

}


}
