package kol2_master.view.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import kol2_master.model.users.Direktor;

public class DirektorTableModel extends ZaposleniTableModel{
		private static final long serialVersionUID = 1546991686790555285L;
		public ArrayList<Direktor> direktori;
	 	private final String[] additionalColumnNames = {"Bonus"};

	    public DirektorTableModel() {
	    	super(new ArrayList<>());
	        this.direktori = new ArrayList<>();
	    }

		@Override
	    public int getColumnCount() {
	        return super.getColumnCount() + additionalColumnNames.length;
	    }

	    @Override
	    public String getColumnName(int columnIndex) {
	        if (columnIndex < super.getColumnCount()) {
	            return super.getColumnName(columnIndex);
	        } else {
	            return additionalColumnNames[columnIndex - super.getColumnCount()];
	        }
	    }

	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        Direktor direktor = (Direktor)direktori.get(rowIndex);

	        if (columnIndex < super.getColumnCount()) {
	            return super.getValueAt(rowIndex, columnIndex);
	        } else {
	            switch (columnIndex - super.getColumnCount()) {
	                case 0:
	                    return direktor.getBonus();
	                default:
	                    return null;
	            }
	        }
	    }
    
    public int getIndexOfDirektorByName(String name, String prezime) {
        for (int i = 0; i < direktori.size(); i++) {
            Direktor direktor = direktori.get(i);
            if (direktor.getIme().equals(name) && direktor.getPrezime().equals(prezime)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean containsImeAndPrezime(String ime, String prezime) {
        for (Direktor direktor : direktori) {
            if (direktor.getIme().equals(ime) && direktor.getPrezime().equals(prezime)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDirektor(Direktor direktor) {
        for (Direktor existingDirektor : direktori) {
            if (existingDirektor.equals(direktor)) {
                return true;
            }
        }
        return false;
    }
    
    public Direktor getDirektorAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < direktori.size()) {
            return direktori.get(rowIndex);
        }
        return null;
    }
    
    public void addDirektor(Direktor direktor) {
        direktori.add(direktor);
        super.addZaposleni(direktor);
        fireTableDataChanged();
    }

    public void updateDirektor(int rowIndex, Direktor updatedDirektor) {
        direktori.set(rowIndex, updatedDirektor);
        super.updateZaposleni(rowIndex, updatedDirektor);
        fireTableDataChanged();
    }

    public void deleteDirektor(int rowIndex) {
        direktori.remove(rowIndex);
        super.deleteZaposleni(rowIndex);
        fireTableDataChanged();
    }
}
