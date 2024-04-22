module com.projet.azizprojet {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
    requires org.controlsfx.controls;

    opens com.projet.azizprojet.Controllers to javafx.fxml;

	opens com.projet.azizprojet to javafx.fxml;
	exports com.projet.azizprojet;
	exports com.projet.azizprojet.Controllers;
}
