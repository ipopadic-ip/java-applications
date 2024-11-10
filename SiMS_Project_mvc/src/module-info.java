module bgn {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	
	opens Application to javafx.graphics, javafx.fxml;
}
