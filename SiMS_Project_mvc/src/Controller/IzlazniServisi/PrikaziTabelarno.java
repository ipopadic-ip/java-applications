package Controller.IzlazniServisi;

import Controller.UnutrasnjeFunkcije.KvajnMekklaskiData;
import Model.KomponentModel;
import Model.RowData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;

import java.util.Comparator;

public class PrikaziTabelarno implements Prikazi {
    private TableView<KomponentModel> tableView2;
    private static final String ALGEABRSKI = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public PrikaziTabelarno(TableView<KomponentModel> tableView2) {
        this.tableView2 = tableView2;
    }

    public void postaviKolone(int brojBitova) {
        tableView2.getColumns().clear();

        for (int i = 0; i < brojBitova; i++) {
            TableColumn<KomponentModel, String> column = new TableColumn<>(String.valueOf(ALGEABRSKI.charAt(i)));
            final int colIndex = i;
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBitovi()[colIndex]));
            tableView2.getColumns().add(column);
        }

        TableColumn<KomponentModel, String> groupColumn = new TableColumn<>("Grupa");
        groupColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getGrupa())));
        tableView2.getColumns().add(groupColumn);

        TableColumn<KomponentModel, String> implikantaColumn = new TableColumn<>("Implikanta");
        implikantaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getImplikanta())));
        tableView2.getColumns().add(implikantaColumn);
    }

    public void updateTableView2(ObservableList<RowData> rowDataList) {
        if (!rowDataList.isEmpty()) {
            ObservableList<KomponentModel> kvajnMekklaskiDataList = FXCollections.observableArrayList();

            int brojBitova = rowDataList.get(0).getBitovi().length;
            postaviKolone(brojBitova);

            KvajnMekklaskiData kvajnMekklaskiData = new KvajnMekklaskiData();
            for (RowData rowData : rowDataList) {
                if ("1".equals(rowData.getF()) && isNumeric(rowData.getF())) {
                    KomponentModel data = kvajnMekklaskiData.createKvajnMekklaskiData(rowData.getBitovi(), rowData.getF());
                    kvajnMekklaskiDataList.add(data);
                }
            }

            // Sortiranje po grupama (od najmanje ka najvećoj)
            FXCollections.sort(kvajnMekklaskiDataList, Comparator.comparingInt(KomponentModel::getGrupa));

            tableView2.setItems(kvajnMekklaskiDataList);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	@Override
	public void prikazi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prikaziTabelarno(ObservableList<RowData> rowDataList) {
		// TODO Auto-generated method stub
		updateTableView2(rowDataList);
	}
}
