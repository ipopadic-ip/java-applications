package kol2_master.controller.Get;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import kol2_master.model.Promoter;
import kol2_master.model.Rukovodilac;
import kol2_master.model.Zaduzenje;

public class GetRukovodiocController {
	 public ArrayList<Rukovodilac> loadData(String fileName, DefaultTableModel model) throws ParseException {
		 ArrayList<Rukovodilac> rukovodioci = new ArrayList<>();
		    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
		        String line;
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		        while ((line = reader.readLine()) != null) {
		            String[] data = line.split("\\|");
		            double visinaPlate = Double.parseDouble(data[3]);
		            
		            Zaduzenje zaduzenje;
		            if ("Nije realizovano".equals(data[5])) {
		                zaduzenje = new Zaduzenje(data[4], "Nije realizovano");
		            } else {
		                try {
		                    Date zaduzenjeDate = dateFormat.parse(data[5]);
		                    zaduzenje = new Zaduzenje(data[4], zaduzenjeDate);
		                } catch (ParseException e) {
		                    e.printStackTrace();
		                    zaduzenje = new Zaduzenje(data[4], "Invalid Date");
		                }
		            }
		            
		            ArrayList<Zaduzenje> zaduzenjaList = new ArrayList<>();
		            zaduzenjaList.add(zaduzenje);
		            
		            Rukovodilac rukovodilac = new Rukovodilac();
		            rukovodilac.setSifra(data[0]);
		            rukovodilac.setIme(data[1]);
		            rukovodilac.setPrezime(data[2]);
		            rukovodilac.setVisinaPlate(visinaPlate);
		            rukovodilac.setZaduzenja(zaduzenjaList);
		            rukovodioci.add(rukovodilac);
		            
		            Object[] rowData = new Object[]{
		                    rukovodilac.getSifra(),
		                    rukovodilac.getIme(),
		                    rukovodilac.getPrezime(),
		                    rukovodilac.getVisinaPlate()
		            };
		            
		            for (Zaduzenje currentZaduzenje : rukovodilac.getZaduzenja()) {
		                rowData = Arrays.copyOf(rowData, rowData.length + 2);
		                rowData[rowData.length - 2] = currentZaduzenje.getNaziv();
		                rowData[rowData.length - 1] = currentZaduzenje.getDatumIzvrsenja() != null ?
		                        dateFormat.format(currentZaduzenje.getDatumIzvrsenja()) : "Nije realizovano";
		            }

		            model.addRow(rowData);
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return rukovodioci;
    }
	 
	 public double izracunajSuma(ArrayList<Rukovodilac> rukovodioci) {
	        double suma = 0.0;
	        for (Rukovodilac rukovodilac : rukovodioci) {
	            suma += rukovodilac.getVisinaPlate();
	        }
	        return suma;
	    }
	 
	 public double izracunajProsek(ArrayList<Rukovodilac> rukovodioci) {
	        double sum = 0.0;
	        int count = 0;
	        
	        for (Rukovodilac rukovodilac : rukovodioci) {
	            sum += rukovodilac.getVisinaPlate();
	            count++;
	        }

	        if (count > 0) {
	        	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	            return Double.parseDouble(decimalFormat.format(sum / count));
	        } else {
	            return 0.0;
	        }
	    }
	 
	 public double nadjiMinVrednost(ArrayList<Rukovodilac> rukovodioci) {
		 	double min = Double.MAX_VALUE;

	        for (Rukovodilac rukovodilac : rukovodioci) {
	            double visinaPlate = rukovodilac.getVisinaPlate();
	            min = Math.min(min, visinaPlate);
	        }

	        return min;
	    }	 
	 
	 public double nadjiMaxVrednost(ArrayList<Rukovodilac> rukovodioci) {
		 double max = Double.MIN_VALUE;

	        for (Rukovodilac rukovodilac : rukovodioci) {
	            double visinaPlate = rukovodilac.getVisinaPlate();
	            max = Math.max(max, visinaPlate);
	        }

	        return max;
	    }
}
