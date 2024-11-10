package Controller.IzlazniServisi;

import Model.RowData;
import javafx.collections.ObservableList;

public interface Prikazi {
	void prikazi();
	void prikaziTabelarno(ObservableList<RowData> rowDataList);
}
