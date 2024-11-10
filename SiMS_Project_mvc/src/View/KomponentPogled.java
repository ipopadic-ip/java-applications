
package View;

import java.util.List;
import java.util.Optional;

import Controller.KomponentKontroler;
import Controller.UlazniServisi.RucnoPopunjavanjeTPS;
import Controller.UnutrasnjeFunkcije.KvajnMekklaskiData;
import Model.KomponentModel;
import Model.RowData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;



public class KomponentPogled {
    private AnchorPane mainPane;
    private Button projektantskiButton;
    private Button edukativniButton;
    private Button saveButton; // Dodato dugme
    private TableView<RowData> tableView1;
    private TableView<KomponentModel> tableView2;
    private Button transformButton;
    private Label algebraLabel;
    private KomponentKontroler komponentKontroler;

    public KomponentPogled() {
        projektantskiButton = new Button("Projektantski");
        edukativniButton = new Button("Edukativni");
        saveButton = new Button("Cuvanje"); // Inicijalizacija dugmeta

        tableView1 = new TableView<>();
        tableView2 = new TableView<>();

        transformButton = new Button("Transformacija");
        algebraLabel = new Label("Algebarski Prikaz:");

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("K04");
        tab1.setClosable(false);

        VBox tabContent = new VBox(tableView1, transformButton, tableView2, algebraLabel, saveButton); // Dodato dugme u layout
        tab1.setContent(tabContent);

        tabPane.getTabs().add(tab1);

        mainPane = new AnchorPane();
        mainPane.getChildren().addAll(projektantskiButton, edukativniButton, tabPane);

        AnchorPane.setTopAnchor(projektantskiButton, 10.0);
        AnchorPane.setLeftAnchor(projektantskiButton, 10.0);

        AnchorPane.setTopAnchor(edukativniButton, 50.0);
        AnchorPane.setLeftAnchor(edukativniButton, 10.0);

        AnchorPane.setTopAnchor(tabPane, 10.0);
        AnchorPane.setLeftAnchor(tabPane, 150.0);
        AnchorPane.setRightAnchor(tabPane, 10.0);
        AnchorPane.setBottomAnchor(tabPane, 10.0);

        projektantskiButton.setOnAction(event -> {
            if (komponentKontroler != null) {
            	komponentKontroler.showTableSelectionDialog();
            }
        });

        transformButton.setOnAction(event -> {
            if (komponentKontroler != null) {
                komponentKontroler.handleTransformisiButtonAction();
            }
        });
        
        edukativniButton.setOnAction(event -> {
        	 if (komponentKontroler != null) {
                 komponentKontroler.rucnoPopunjavanje();
             }
        });

        saveButton.setOnAction(event -> {
            if (komponentKontroler != null) {
                komponentKontroler.handleSaveButtonAction();
            }
        });
    }

    public void setKomponentKontroler(KomponentKontroler komponentKontroler) {
        this.komponentKontroler = komponentKontroler;
    }

    public AnchorPane getMainPane() {
        return mainPane;
    }

    public Button getProjektantskiButton() {
        return projektantskiButton;
    }

    public Button getEdukativniButton() {
        return edukativniButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public TableView<RowData> getTableView1() {
        return tableView1;
    }

    public TableView<KomponentModel> getTableView2() {
        return tableView2;
    }

    public Button getTransformButton() {
        return transformButton;
    }

    public Label getAlgebraLabel() {
        return algebraLabel;
    }
}









