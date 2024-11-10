package Controller.UlazniServisi;

import java.util.Optional;

import Controller.KomponentKontroler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

public class RucnoPopunjavanjeTPS {
	 private KomponentKontroler komponentKontroler;

	    public RucnoPopunjavanjeTPS(KomponentKontroler komponentKontroler) {
	        this.komponentKontroler = komponentKontroler;
	    }

	    public void showBitAndRowSelectionDialog() {
	        TextInputDialog rowDialog = new TextInputDialog();
	        rowDialog.setTitle("Redosled redova");
	        rowDialog.setHeaderText("Izaberite broj redova");
	        rowDialog.setContentText("Unesite broj redova:");

	        Optional<String> rowResult = rowDialog.showAndWait();
	        rowResult.ifPresent(rows -> {
	            try {
	                int numRows = Integer.parseInt(rows);
	                if (numRows < 2 || numRows > 9) {
	                    throw new NumberFormatException("Broj redova mora biti između 2 i 9");
	                }

	                TextInputDialog bitDialog = new TextInputDialog();
	                bitDialog.setTitle("Broj Bitova");
	                bitDialog.setHeaderText("Izaberite broj bitova");
	                bitDialog.setContentText("Unesite broj bitova:");

	                Optional<String> bitResult = bitDialog.showAndWait();
	                bitResult.ifPresent(bits -> {
	                    try {
	                        int numBits = Integer.parseInt(bits);
	                        if (numBits < 2 || numBits > 9) {
	                            throw new NumberFormatException("Broj bitova mora biti između 2 i 9");
	                        }
	                        komponentKontroler.handleEdukativniButtonAction(numRows, numBits);
	                    } catch (NumberFormatException e) {
	                        showErrorAlert("Molimo unesite validan broj bitova između 2 i 9.");
	                    }
	                });

	            } catch (NumberFormatException e) {
	                showErrorAlert("Molimo unesite validan broj redova između 2 i 9.");
	            }
	        });
	    }

	    private void showErrorAlert(String message) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Greška");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
}
