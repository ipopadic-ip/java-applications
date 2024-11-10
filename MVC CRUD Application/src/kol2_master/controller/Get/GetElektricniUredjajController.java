package kol2_master.controller.Get;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import kol2_master.model.ElektricniUredjaj;

public class GetElektricniUredjajController {
	 public ArrayList<ElektricniUredjaj> loadData(String fileName, DefaultTableModel model) {
		 ArrayList<ElektricniUredjaj> elektricniUredjaji = new ArrayList<>();
    	 try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
             String line;
             while ((line = reader.readLine()) != null) {
                 String[] data = line.split("\\|");
                 double cena = Double.parseDouble(data[1]);
                 int nominalnaSnaga = Integer.parseInt(data[6]);
                 ElektricniUredjaj elektricniUredjaj = new ElektricniUredjaj(data[0], cena, data[2], data[3], data[4], data[5], nominalnaSnaga, data[7]);
                 elektricniUredjaji.add(elektricniUredjaj);
                 model.addRow(new Object[]{elektricniUredjaj.getNaziv(), elektricniUredjaj.getCena(), elektricniUredjaj.getJedinicaMere(),
                		 elektricniUredjaj.getKategorija(), elektricniUredjaj.getProizvodjac(), elektricniUredjaj.getBoja(), elektricniUredjaj.getNominalnaSnaga(), elektricniUredjaj.getDimenzije()});
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
    	 return elektricniUredjaji;
    }
	 
	 public double izracunajSuma(ArrayList<ElektricniUredjaj> elektricniUredjaji) {
	        double suma = 0.0;
	        for (ElektricniUredjaj elektricniUredjaj : elektricniUredjaji) {
	            suma += elektricniUredjaj.getCena();
	        }
	        return suma;
	    }
	 
	 public double izracunajProsek(ArrayList<ElektricniUredjaj> elektricniUredjaji) {
	        double sum = 0.0;
	        int count = 0;
	        
	        for (ElektricniUredjaj elektricniUredjaj : elektricniUredjaji) {
	            sum += elektricniUredjaj.getCena();
	            count++;
	        }

	        if (count > 0) {
	        	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	            return Double.parseDouble(decimalFormat.format(sum / count));
	        } else {
	            return 0.0;
	        }
	    }
	 
	 public double nadjiMinVrednost(ArrayList<ElektricniUredjaj> elektricniUredjaji) {
		 	double min = Double.MAX_VALUE;

	        for (ElektricniUredjaj elektricniUredjaj : elektricniUredjaji) {
	            double cena = elektricniUredjaj.getCena();
	            min = Math.min(min, cena);
	        }

	        return min;
	    }	 
	 
	 public double nadjiMaxVrednost(ArrayList<ElektricniUredjaj> elektricniUredjaji) {
		 double max = Double.MIN_VALUE;

	        for (ElektricniUredjaj elektricniUredjaj : elektricniUredjaji) {
	            double cena = elektricniUredjaj.getCena();
	            max = Math.max(max, cena);
	        }

	        return max;
	    }
}
