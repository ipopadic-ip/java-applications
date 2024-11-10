package kol2_master.view.tables;

import java.util.ArrayList;

import kol2_master.model.products.ProizvodOgranicenogTrajanja;
import kol2_master.model.products.TehnickiProizvod;

public class TehnickiProizvodTableModel extends ProizvodTableModel{
	private static final long serialVersionUID = -2376795993263210254L;
	public ArrayList<TehnickiProizvod> tehnickiProizvodiList;
    private final String[] additionalColumnNames = {"Boja", "Nominalna Snaga", "Radni Napon"};

    public TehnickiProizvodTableModel() {
        super(new ArrayList<>());
        this.tehnickiProizvodiList = new ArrayList<>();
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
        if (columnIndex < super.getColumnCount()) {
            return super.getValueAt(rowIndex, columnIndex);
        } else {
            TehnickiProizvod tehnickiProizvod = tehnickiProizvodiList.get(rowIndex);

            switch (columnIndex - super.getColumnCount()) {
                case 0:
                    return tehnickiProizvod.getBoja();
                case 1:
                    return tehnickiProizvod.getNominalnaSnaga();
                case 2:
                    return tehnickiProizvod.getRadniNapon();
                default:
                    return null;
            }
        }
    }
    
    public boolean containsTehnickiProizvod(TehnickiProizvod proizvod) {
        for (TehnickiProizvod existingProizvod : tehnickiProizvodiList) {
            if (existingProizvod.equals(proizvod)) {
                return true;
            }
        }
        return false;
    }

    public int getIndexOfTehnickiProizvodByNaziv(String naziv) {
        for (int i = 0; i < tehnickiProizvodiList.size(); i++) {
            TehnickiProizvod tp = tehnickiProizvodiList.get(i);
            if (tp.getNaziv().equals(naziv)) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsNaziv(String naziv) {
        for (TehnickiProizvod tp : tehnickiProizvodiList) {
            if (tp.getNaziv().equals(naziv)) {
                return true;
            }
        }
        return false;
    }

    public void addTehnickiProizvod(TehnickiProizvod tehnickiProizvod) {
        tehnickiProizvodiList.add(tehnickiProizvod);
        super.addProizvod(tehnickiProizvod);
        fireTableDataChanged();
    }

    public void updateTehnickiProizvod(int rowIndex, TehnickiProizvod updatedTehnickiProizvod) {
        tehnickiProizvodiList.set(rowIndex, updatedTehnickiProizvod);
        super.updateProizvod(rowIndex, updatedTehnickiProizvod);
        fireTableDataChanged();
    }

    public void deleteTehnickiProizvod(int rowIndex) {
        tehnickiProizvodiList.remove(rowIndex);
        super.deleteProizvod(rowIndex);
        fireTableDataChanged();
    }

}
