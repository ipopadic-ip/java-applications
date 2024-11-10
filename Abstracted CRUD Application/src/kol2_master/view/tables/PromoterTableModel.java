package kol2_master.view.tables;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import kol2_master.model.users.Promoter;

public class PromoterTableModel extends ZaposleniTableModel{
	private static final long serialVersionUID = -7226604836069896095L;
	public ArrayList<Promoter> promoteri;
    private final String[] additionalColumnNames = {"Radno Mesto", "Odmori"};

    public PromoterTableModel() {
        super(new ArrayList<>());
        this.promoteri = new ArrayList<>();
    }
    
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
        Promoter promoter = promoteri.get(rowIndex);

        if (columnIndex < super.getColumnCount()) {
            return super.getValueAt(rowIndex, columnIndex);
        } else {
            switch (columnIndex - super.getColumnCount()) {
                case 0:
                    return promoter.getRadnoMesto();
                case 1:
                    if (promoter.getOdmoriList().isEmpty()) {
                        return new ArrayList<ArrayList<LocalDate>>(); // Vracamo praznu array listu ako korisnik nema odmore
                    } else {
                        return formatOdmor(promoter.getOdmoriList());
                    }
                default:
                    return null;
            }
        }
    }
    
    public ArrayList<ArrayList<LocalDate>> getOdmoriListForPromoter(Promoter promoter) {
        return promoter.getOdmoriList();
    }
    
    public void addOdmorForPromoter(int rowIndex, ArrayList<LocalDate> odmor) {
        Promoter promoter = getPromoterAt(rowIndex);
        if (promoter != null) {
            promoter.addOdmor(odmor);
            fireTableDataChanged();
        }
    }
    
    private String formatOdmor(ArrayList<ArrayList<LocalDate>> odmoriList) {
        StringBuilder formattedOdmor = new StringBuilder();
        for (ArrayList<LocalDate> odmori : odmoriList) {
            formattedOdmor.append(formatOdmorDates(odmori)).append(", ");
        }
        return formattedOdmor.toString().replaceAll(", $", "");
    }

    private String formatOdmorDates(ArrayList<LocalDate> odmori) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (!odmori.isEmpty()) {
            return odmori.get(0).format(formatter) + " - " + odmori.get(1).format(formatter);
        } else {
            return "";
        }
    }
    
    public Promoter getPromoterAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < promoteri.size()) {
            return promoteri.get(rowIndex);
        }
        return null;
    }
    
    public int getIndexOfPromoterByName(String name, String prezime) {
        for (int i = 0; i < promoteri.size(); i++) {
            Promoter promoter = promoteri.get(i);
            if (promoter.getIme().equals(name) && promoter.getPrezime().equals(prezime)) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsImeAndPrezime(String ime, String prezime) {
        for (Promoter promoter : promoteri) {
            if (promoter.getIme().equals(ime) && promoter.getPrezime().equals(prezime)) {
                return true;
            }
        }
        return false;
    }

    public void addPromoter(Promoter promoter) {
        promoteri.add(promoter);
        super.addZaposleni(promoter);
        fireTableDataChanged();
    }

    public void updatePromoter(int rowIndex, Promoter updatedPromoter) {
        promoteri.set(rowIndex, updatedPromoter);
        super.updateZaposleni(rowIndex, updatedPromoter);
        fireTableDataChanged();
    }

    public void deletePromoter(int rowIndex) {
        promoteri.remove(rowIndex);
        super.deleteZaposleni(rowIndex);
        fireTableDataChanged();
    }
	
}
