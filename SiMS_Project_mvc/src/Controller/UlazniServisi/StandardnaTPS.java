package Controller.UlazniServisi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controller.KomponentKontroler;
import Model.RowData;
import View.KomponentPogled;

public class StandardnaTPS {
    private KomponentPogled komponentPogled;
    private CitacTabelaPobudeIzlaza citac;
    private KomponentKontroler komponentKontroler;

    public StandardnaTPS(KomponentPogled komponentPogled, KomponentKontroler komponentKontroler) {
        this.komponentPogled = komponentPogled;
        this.komponentKontroler = komponentKontroler;
        String csvFilePath = "src/data/test.csv"; // Adjust the path to your CSV file
        this.citac = new CitacTabelaPobudeIzlaza(csvFilePath);
    }

    public void loadCsvDataIntoTableView1() {
        try {
            citac.ucitajPodatke();
            List<String[]> podaci = citac.getPodaciZaTabelu("K04"); // Default table name
            komponentKontroler.postaviKolonePodaci(podaci);
            List<RowData> rowDataList = new ArrayList<>();

            for (int i = 1; i < podaci.size(); i++) { // Pretpostavljamo da prva linija sadrži zaglavlja
                String[] red = podaci.get(i);
                String[] bitovi = new String[red.length - 1];
                System.arraycopy(red, 0, bitovi, 0, red.length - 1);
                String f = red[red.length - 1];
                rowDataList.add(new RowData(bitovi, f));
            }
            komponentPogled.getTableView1().getItems().setAll(rowDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAvailableTables() {
        try {
            citac.ucitajPodatke();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return citac.getNaziviTabela();
    }

    public void loadCsvDataIntoTableView1(String tableName) {
        try {
            citac.ucitajPodatke();
            List<String[]> podaci = citac.getPodaciZaTabelu(tableName);
            komponentKontroler.postaviKolonePodaci(podaci);
            List<RowData> rowDataList = new ArrayList<>();

            for (int i = 1; i < podaci.size(); i++) { // Pretpostavljamo da prva linija sadrži zaglavlja
                String[] red = podaci.get(i);
                String[] bitovi = new String[red.length - 1];
                System.arraycopy(red, 0, bitovi, 0, red.length - 1);
                String f = red[red.length - 1];
                rowDataList.add(new RowData(bitovi, f));
            }
            komponentPogled.getTableView1().getItems().setAll(rowDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
