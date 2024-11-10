package kol2_master.view.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import kol2_master.model.users.Zaposleni;

public class ZaposleniTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -6686161929626805527L;

	private ArrayList<Zaposleni> zaposleniList;

    private final String[] columnNames = {"Sifra Zaposlenog", "Ime", "Prezime", "Visina Plate"};

    public ZaposleniTableModel(ArrayList<Zaposleni> zaposleniList) {
        this.zaposleniList = new ArrayList<>(zaposleniList);
    }

    @Override
    public int getRowCount() {
        return zaposleniList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Zaposleni zaposleni = zaposleniList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return zaposleni.getSifra();
            case 1:
                return zaposleni.getIme();
            case 2:
                return zaposleni.getPrezime();
            case 3:
                return zaposleni.getVisinaPlate();
            default:
                return null;
        }
    }
    
    public void addZaposleni(Zaposleni zaposleni) {
        zaposleniList.add(zaposleni);
        fireTableDataChanged();
    }
    
    public void updateZaposleni(int rowIndex, Zaposleni updatedZaposleni) {
        zaposleniList.set(rowIndex, updatedZaposleni);
        fireTableDataChanged();
    }
    
    public void deleteZaposleni(int rowIndex) {
        zaposleniList.remove(rowIndex);
        fireTableDataChanged();
    }
    
}
