package Application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.KomponentKontroler;
import Model.KomponentModel;
import View.KomponentPogled;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
    	KomponentModel komponentModel = new KomponentModel();
    	
        KomponentPogled komponentPogled = new KomponentPogled();
        
        KomponentKontroler komponentKontroler = new KomponentKontroler(komponentPogled);
        
        Scene scene = new Scene(komponentPogled.getMainPane(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QMC Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
