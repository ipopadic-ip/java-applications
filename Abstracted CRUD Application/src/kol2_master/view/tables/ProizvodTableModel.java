package kol2_master.view.tables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import kol2_master.model.products.Proizvod;

public class ProizvodTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 7300825905730698737L;
	private ArrayList<Proizvod> proizvodiList;
    private final String[] columnNames = {"Naziv", "Cena", "Model", "Jedinica Mere", "Datum Proizvodnje"};
    public ProizvodTableModel(ArrayList<Proizvod> proizvodiList) {
        this.proizvodiList = new ArrayList<>(proizvodiList);
    }

    @Override
    public int getRowCount() {
        return proizvodiList.size();
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
        Proizvod proizvod = proizvodiList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return proizvod.getNaziv();
            case 1:
                return proizvod.getCena();
            case 2:
                return proizvod.getModel();
            case 3:
                return proizvod.getJedinicaMere();
            case 4:
                return formatDate(proizvod.getDatumProizvodnje());
            default:
                return null;
        }
    }
    
    public static String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            return sdf.format(date);
        } else {
            return null;
        }
    }
    
    public void addProizvod(Proizvod proizvod) {
        proizvodiList.add(proizvod);
        fireTableDataChanged();
    }

    public void updateProizvod(int rowIndex, Proizvod updatedProizvod) {
        proizvodiList.set(rowIndex, updatedProizvod);
        fireTableDataChanged();
    }

    public void deleteProizvod(int rowIndex) {
        proizvodiList.remove(rowIndex);
        fireTableDataChanged();
    }
}
