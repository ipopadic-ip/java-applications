package Controller.IzlazniServisi;

import Model.RowData;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class PrikaziAlgebarski implements Prikazi {
    private static final String ALGEABRSKI = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ObservableList<RowData> tableView1;
    private Label algebraLabel;

    public PrikaziAlgebarski(ObservableList<RowData> tableView1, Label algebraLabel) {
        this.tableView1 = tableView1;
        this.algebraLabel = algebraLabel;
    }

    public void generateBooleanExpression() {
        List<String> minterms = tableView1.stream()
            .filter(row -> "1".equals(row.getF()))
            .map(this::createMinterm)
            .collect(Collectors.toList());

        String expression = String.join(" + ", minterms);
        algebraLabel.setText("Algebarski Prikaz: " + expression);
    }

    private String createMinterm(RowData row) {
        StringBuilder minterm = new StringBuilder();
        String[] bitovi = row.getBitovi();

        for (int i = 0; i < bitovi.length; i++) {
            char bit = bitovi[i].charAt(0);
            if (bit != '-') {
                minterm.append(ALGEABRSKI.charAt(i));
                if (bit == '0') {
                    minterm.append("'");
                }
            }
        }

        return minterm.toString();
    }
    @Override
    public void prikazi() {
        generateBooleanExpression();
    }

	@Override
	public void prikaziTabelarno(ObservableList<RowData> rowDataList) {
		// TODO Auto-generated method stub
		
	}
}

