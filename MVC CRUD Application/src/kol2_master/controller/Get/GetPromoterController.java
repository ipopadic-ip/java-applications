package kol2_master.controller.Get;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import kol2_master.model.Promoter;

public class GetPromoterController {
	 public ArrayList<Promoter> loadPromoterData(String fileName, DefaultTableModel model) {
		 ArrayList<Promoter> promoteri = new ArrayList<>();
    	 try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
             String line;
             while ((line = reader.readLine()) != null) {
                 String[] data = line.split("\\|");
                 double visinaPlate = Double.parseDouble(data[3]);
                 Promoter promoter = new Promoter(data[0], data[1], data[2], visinaPlate, data[4], data[5]);
                 promoteri.add(promoter);
                 model.addRow(new Object[]{promoter.getSifra(), promoter.getIme(), promoter.getPrezime(),
                         promoter.getVisinaPlate(), promoter.getPrimedbe(), promoter.getNadredjeni()});
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
    	 return promoteri;
    }
	 
	 public double izracunajSuma(ArrayList<Promoter> promoteri) {
	        double suma = 0.0;
	        for (Promoter promoter : promoteri) {
	            suma += promoter.getVisinaPlate();
	        }
	        return suma;
	    }
	 
	 public double izracunajProsek(ArrayList<Promoter> promoteri) {
	        double sum = 0.0;
	        int count = 0;
	        
	        for (Promoter promoter : promoteri) {
	            sum += promoter.getVisinaPlate();
	            count++;
	        }

	        if (count > 0) {
	        	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	            return Double.parseDouble(decimalFormat.format(sum / count));
	        } else {
	            return 0.0;
	        }
	    }
	 
	 public double nadjiMinVrednost(ArrayList<Promoter> promoteri) {
		 	double min = Double.MAX_VALUE;

	        for (Promoter promoter : promoteri) {
	            double visinaPlate = promoter.getVisinaPlate();
	            min = Math.min(min, visinaPlate);
	        }

	        return min;
	    }	 
	 
	 public double nadjiMaxVrednost(ArrayList<Promoter> promoteri) {
		 double max = Double.MIN_VALUE;

	        for (Promoter promoter : promoteri) {
	            double visinaPlate = promoter.getVisinaPlate();
	            max = Math.max(max, visinaPlate);
	        }

	        return max;
	    }
	 
}
