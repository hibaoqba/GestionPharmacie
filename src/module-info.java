module GestionPharmacie1 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires java.desktop;
	
	     

	opens application to javafx.graphics, javafx.base,
	 javafx.fxml;
}
