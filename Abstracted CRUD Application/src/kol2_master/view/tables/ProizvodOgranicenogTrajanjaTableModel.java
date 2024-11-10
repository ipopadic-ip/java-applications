package kol2_master.view.tables;

import java.util.ArrayList;
import java.util.Date;

import kol2_master.model.products.ProizvodOgranicenogTrajanja;
import kol2_master.model.users.Direktor;

//import kol2_master.view.forms.SimpleDateFormat;
import java.text.SimpleDateFormat;

public class ProizvodOgranicenogTrajanjaTableModel extends ProizvodTableModel{
		private static final long serialVersionUID = 1765004195563686597L;
	
		public ArrayList<ProizvodOgranicenogTrajanja> proizvodiOgranicenogTrajanjaList;
	    private final String[] additionalColumnNames = {"Rok Trajanja", "Temperatura Skladištenja"};

	    public ProizvodOgranicenogTrajanjaTableModel() {
	        super(new ArrayList<>());
	        this.proizvodiOgranicenogTrajanjaList = new ArrayList<>();
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
	            ProizvodOgranicenogTrajanja proizvodOgranicenogTrajanja = proizvodiOgranicenogTrajanjaList.get(rowIndex);

	            switch (columnIndex - super.getColumnCount()) {
	                case 0:
	                    return formatDate(proizvodOgranicenogTrajanja.getRokTrajanja());
	                case 1:
	                    return proizvodOgranicenogTrajanja.getTemperaturaSkladistenja();
	                default:
	                    return null;
	            }
	        }
	    }
	    
	    public boolean containsProizvodOgranicenogTrajanja(ProizvodOgranicenogTrajanja proizvod) {
	        for (ProizvodOgranicenogTrajanja existingProizvod : proizvodiOgranicenogTrajanjaList) {
	            if (existingProizvod.equals(proizvod)) {
	                return true;
	            }
	        }
	        return false;
	    }

	    
	    public int getIndexOfProizvodOTByNaziv(String naziv) {
	        for (int i = 0; i < proizvodiOgranicenogTrajanjaList.size(); i++) {
	            ProizvodOgranicenogTrajanja pog = proizvodiOgranicenogTrajanjaList.get(i);
	            if (pog.getNaziv().equals(naziv)) {
	                return i;
	            }
	        }
	        return -1;
	    }
	    
	    public boolean containsNaziv(String naziv) {
	        for (ProizvodOgranicenogTrajanja pog : proizvodiOgranicenogTrajanjaList) {
	            if (pog.getNaziv().equals(naziv)) {
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    public void addProizvodOgranicenogTrajanja(ProizvodOgranicenogTrajanja proizvodOgranicenogTrajanja) {
	        proizvodiOgranicenogTrajanjaList.add(proizvodOgranicenogTrajanja);
	        super.addProizvod(proizvodOgranicenogTrajanja);
	        fireTableDataChanged();
	    }

	    public void updateProizvodOgranicenogTrajanja(int rowIndex, ProizvodOgranicenogTrajanja updatedProizvodOgranicenogTrajanja) {
	        proizvodiOgranicenogTrajanjaList.set(rowIndex, updatedProizvodOgranicenogTrajanja);
	        super.updateProizvod(rowIndex, updatedProizvodOgranicenogTrajanja);
	        fireTableDataChanged();
	    }

	    public void deleteProizvodOgranicenogTrajanja(int rowIndex) {
	        proizvodiOgranicenogTrajanjaList.remove(rowIndex);
	        super.deleteProizvod(rowIndex);
	        fireTableDataChanged();
	    }

}
