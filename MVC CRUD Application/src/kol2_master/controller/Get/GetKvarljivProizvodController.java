package kol2_master.controller.Get;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import kol2_master.model.KvarljiviProizvod;

public class GetKvarljivProizvodController {
	public ArrayList<KvarljiviProizvod> loadData(String fileName, DefaultTableModel model) throws ParseException {
		 ArrayList<KvarljiviProizvod> kvarljiviProizvodi = new ArrayList<>();
   	 try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                double cena = Double.parseDouble(data[1]);
                double tempSkladistenja = Double.parseDouble(data[6]);
                Date rokTrajanja = dateFormat.parse(data[5]);
                KvarljiviProizvod kvarljiviProizvod = new KvarljiviProizvod(data[0], cena, data[2], data[3], data[4], rokTrajanja, tempSkladistenja);
                kvarljiviProizvodi.add(kvarljiviProizvod);
                model.addRow(new Object[]{kvarljiviProizvod.getNaziv(), kvarljiviProizvod.getCena(), kvarljiviProizvod.getJedinicaMere(),
                		kvarljiviProizvod.getKategorija(), kvarljiviProizvod.getProizvodjac(), dateFormat.format(kvarljiviProizvod.getRokTrajanja()), kvarljiviProizvod.getTemperaturaSkladistenja()});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
   	 return kvarljiviProizvodi;
   }
	 
	 public double izracunajSuma(ArrayList<KvarljiviProizvod> kvarljiviProizvodi) {
	        double suma = 0.0;
	        for (KvarljiviProizvod kvarljivProizvod : kvarljiviProizvodi) {
	            suma += kvarljivProizvod.getCena();
	        }
	        return suma;
	    }
	 
	 public double izracunajProsek(ArrayList<KvarljiviProizvod> kvarljiviProizvodi) {
	        double sum = 0.0;
	        int count = 0;
	        
	        for (KvarljiviProizvod kvarljivProizvod : kvarljiviProizvodi) {
	            sum += kvarljivProizvod.getCena();
	            count++;
	        }

	        if (count > 0) {
	        	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	            return Double.parseDouble(decimalFormat.format(sum / count));
	        } else {
	            return 0.0;
	        }
	    }
	 
	 public double nadjiMinVrednost(ArrayList<KvarljiviProizvod> kvarljiviProizvodi) {
		 	double min = Double.MAX_VALUE;

	        for (KvarljiviProizvod kvarljivProizvod : kvarljiviProizvodi) {
	            double cena = kvarljivProizvod.getCena();
	            min = Math.min(min, cena);
	        }

	        return min;
	    }	 
	 
	 public double nadjiMaxVrednost(ArrayList<KvarljiviProizvod> kvarljiviProizvodi) {
		 double max = Double.MIN_VALUE;

	        for (KvarljiviProizvod kvarljivProizvod : kvarljiviProizvodi) {
	            double cena = kvarljivProizvod.getCena();
	            max = Math.max(max, cena);
	        }

	        return max;
	    }
}
