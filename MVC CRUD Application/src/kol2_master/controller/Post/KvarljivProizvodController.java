package kol2_master.controller.Post;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import kol2_master.model.KvarljiviProizvod;
import kol2_master.view.Post.FormaDodajKvarljivProizvod;

public class KvarljivProizvodController {
	public void sacuvajPodatke(FormaDodajKvarljivProizvod dodajKvarljivProizvod, KvarljiviProizvod kvarljiviProizvod) {
        if (kvarljiviProizvod.getNaziv().trim().isEmpty() || kvarljiviProizvod.getJedinicaMere().trim().isEmpty() || kvarljiviProizvod.getKategorija().trim().isEmpty() || kvarljiviProizvod.getProizvodjac().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dodajKvarljivProizvod, "Sva Polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String podaciZaCuvanje = formatirajPodatke(kvarljiviProizvod);
        
        File file = new File("src/kol2_master/data/kvarljiviProizvodi.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split("\\|");
	                if (parts.length > 0 && parts[0].equals(kvarljiviProizvod.getNaziv())) {
	                    JOptionPane.showMessageDialog(dodajKvarljivProizvod, "Kvarljivi Proizvod sa nazivom " + kvarljiviProizvod.getNaziv() + " već postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	            }
	        } 
	        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
	            writer.println(podaciZaCuvanje);
	            JOptionPane.showMessageDialog(dodajKvarljivProizvod, "Uspešno ste dodali Kvarljivi Proizvod.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
	            writer.close();
	            dodajKvarljivProizvod.dispose();
	        } 
	        }catch (IOException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(dodajKvarljivProizvod, "Greška pri čuvanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
	        }
    	}
	
    public String formatirajPodatke(KvarljiviProizvod kvarljiviProizvod) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(kvarljiviProizvod.getRokTrajanja());
        return String.format("%s|%.2f|%s|%s|%s|%s|%s",
        		kvarljiviProizvod.getNaziv(),
                kvarljiviProizvod.getCena(),
                kvarljiviProizvod.getJedinicaMere(),
                kvarljiviProizvod.getKategorija(),
                kvarljiviProizvod.getProizvodjac(),
                formattedDate,
                kvarljiviProizvod.getTemperaturaSkladistenja());
    }
}
