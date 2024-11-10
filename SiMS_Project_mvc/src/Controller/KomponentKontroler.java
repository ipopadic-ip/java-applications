//package Controller;
//
//import Controller.IzlazniServisi.PrikaziAlgebarski;
//import Controller.IzlazniServisi.PrikaziTabelarno;
//import Controller.UlazniServisi.StandardnaTPS;
//import Model.RowData;
//import View.KomponentPogled;
//import javafx.collections.ObservableList;
//
//import java.util.List;
//
//public class KomponentKontroler {
//    private KomponentPogled komponentPogled;
//    private StandardnaTPS standardnaTPS;
//    private PrikaziAlgebarski prikaziAlgebarski;
//    private PrikaziTabelarno prikaziTabelarno;
//
//    public KomponentKontroler(KomponentPogled komponentPogled) {
//        this.komponentPogled = komponentPogled;
//        this.standardnaTPS = new StandardnaTPS(komponentPogled);
//        this.prikaziAlgebarski = new PrikaziAlgebarski(komponentPogled.getTableView1().getItems(), komponentPogled.getAlgebraLabel());
//        this.prikaziTabelarno = new PrikaziTabelarno(komponentPogled.getTableView2());
//        komponentPogled.setKomponentKontroler(this);
//    }
//
//    public void handleProjektantskiButtonAction() {
//        standardnaTPS.loadCsvDataIntoTableView1();
//    }
//
//    public void handleTransformisiButtonAction() {
//        ObservableList<RowData> rowDataList = komponentPogled.getTableView1().getItems();
//        prikaziTabelarno.updateTableView2(rowDataList);
//        prikaziAlgebarski.generateBooleanExpression();
//    }
//
//    public List<String> getAvailableTables() {
//        return standardnaTPS.getAvailableTables();
//    }
//
//    public void handleTableSelection(String tableName) {
//        standardnaTPS.loadCsvDataIntoTableView1(tableName);
//    }
//}

package Controller;

import Controller.IzlazniServisi.Prikazi;
import Controller.IzlazniServisi.PrikaziAlgebarski;
import Controller.IzlazniServisi.PrikaziTabelarno;
import Controller.UlazniServisi.RucnoPopunjavanjeTPS;
import Controller.UlazniServisi.StandardnaTPS;
import Controller.UnutrasnjeFunkcije.KvajnMekklaskiData;
import Model.KomponentModel;
import Model.RowData;
import View.KomponentPogled;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class KomponentKontroler {
    private KomponentPogled komponentPogled;
    private StandardnaTPS standardnaTPS;
    private Prikazi prikaziAlgebarski;
    private Prikazi prikaziTabelarno;
    private RucnoPopunjavanjeTPS rucnoPopunjavanjeTPS;

    public KomponentKontroler(KomponentPogled komponentPogled) {
        this.komponentPogled = komponentPogled;
        this.rucnoPopunjavanjeTPS=new RucnoPopunjavanjeTPS(this);
        this.standardnaTPS = new StandardnaTPS(komponentPogled, this);
        this.prikaziAlgebarski = new PrikaziAlgebarski(komponentPogled.getTableView1().getItems(), komponentPogled.getAlgebraLabel());
        this.prikaziTabelarno = new PrikaziTabelarno(komponentPogled.getTableView2());
        komponentPogled.setKomponentKontroler(this);
    }

    public void handleProjektantskiButtonAction() {
        standardnaTPS.loadCsvDataIntoTableView1();
    }

    public void handleTransformisiButtonAction() {
    	 System.out.println("Content of TableView1 before transformation:");
         for (RowData row : komponentPogled.getTableView1().getItems()) {
             System.out.println(row);
         }
    	prikaziAlgebarski.prikazi();
        ObservableList<RowData> rowDataList = komponentPogled.getTableView1().getItems();
        prikaziTabelarno.prikaziTabelarno(rowDataList);        
    }

    public void handleEdukativniButtonAction(int numRows, int numBits) {
        updateTableView1(numRows, numBits);
        prikaziAlgebarski.prikazi();
    }

    public List<String> getAvailableTables() {
        return standardnaTPS.getAvailableTables();
    }

    public void handleTableSelection(String tableName) {
        standardnaTPS.loadCsvDataIntoTableView1(tableName);
    }
    
    public void handleSaveButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cuvanje tabele");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        Stage stage = (Stage) komponentPogled.getMainPane().getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveTableToCSV(file);
        }
    }

    private void saveTableToCSV(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            for (TableColumn<KomponentModel, ?> column : komponentPogled.getTableView2().getColumns()) {
                writer.write(column.getText() + ",");
            }
            writer.write("\n");

            for (KomponentModel data : komponentPogled.getTableView2().getItems()) {
                writer.write(String.join(",", data.getBitovi()) + ",");
                writer.write(data.getGrupa() + ",");
                writer.write(data.getImplikanta() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void postaviKolonePodaci(List<String[]> podaci) {
        komponentPogled.getTableView1().getColumns().clear();

        if (podaci.isEmpty()) return;

        String[] prviRed = podaci.get(0);
        int brojKolona = prviRed.length;

        for (int i = 0; i < brojKolona - 1; i++) {
            TableColumn<RowData, String> column = new TableColumn<>("x" + (i + 1));
            final int colIndex = i;
            column.setCellValueFactory(cellData -> cellData.getValue().bitProperty(colIndex));
            komponentPogled.getTableView1().getColumns().add(column);
        }

        TableColumn<RowData, String> columnF = new TableColumn<>("f");
        columnF.setCellValueFactory(cellData -> cellData.getValue().fProperty());
        komponentPogled.getTableView1().getColumns().add(columnF);
    }

    public void postaviKolone(int brojBitova) {
        komponentPogled.getTableView1().getColumns().clear();

        for (int i = 0; i < brojBitova; i++) {
            TableColumn<RowData, String> column = new TableColumn<>("x" + (i + 1));
            final int colIndex = i;
            column.setCellValueFactory(cellData -> cellData.getValue().bitProperty(colIndex));
            komponentPogled.getTableView1().getColumns().add(column);
        }

        TableColumn<RowData, String> columnF = new TableColumn<>("f");
        columnF.setCellValueFactory(cellData -> cellData.getValue().fProperty());
        komponentPogled.getTableView1().getColumns().add(columnF);
    }

    public void showTableSelectionDialog() {
        List<String> choices = getAvailableTables();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Izbor Tabele");
        dialog.setHeaderText("Izaberite tabelu koju želite da učitate");
        dialog.setContentText("Tabele:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::handleTableSelection);
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    public void updateTableView1(int numRows, int numBits) {
        ObservableList<RowData> rowDataList = FXCollections.observableArrayList();

        for (int i = 0; i < numRows; i++) {
            String[] bitovi = new String[numBits];
            for (int j = 0; j < numBits; j++) {
                bitovi[j] = "0";
            }
            rowDataList.add(new RowData(bitovi, "0"));
        }
        postaviKolone(numBits);
        komponentPogled.getTableView1().setItems(rowDataList);
        enableEditing();
    }

    @SuppressWarnings("unchecked")
    public void enableEditing() {
        komponentPogled.getTableView1().setEditable(true);
        for (TableColumn<RowData, String> column : komponentPogled.getTableView1().getColumns().stream()
                .map(c -> (TableColumn<RowData, String>) c)
                .toArray(TableColumn[]::new)) {
            column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            column.setOnEditCommit(event -> {
                String newValue = event.getNewValue();
                if (!newValue.equals("0") && !newValue.equals("1")) {
                    showErrorAlert("Unesite samo 0 ili 1.");
                    komponentPogled.getTableView1().refresh();
                } else {
                    int row = event.getTablePosition().getRow();
                    RowData rowData = event.getTableView().getItems().get(row);
                    if (column.getText().startsWith("x")) {
                        rowData.setBit(Integer.parseInt(column.getText().substring(1)) - 1, newValue);
                    } else if (column.getText().equals("f")) {
                        rowData.setF(newValue);
                    }
                    System.out.println("Updated rowData: " + rowData);
                }
            });
        }
    }
    
    public void rucnoPopunjavanje() {
    	rucnoPopunjavanjeTPS.showBitAndRowSelectionDialog();
    }
    
}


