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

import kol2_master.model.ElektricniUredjaj;
import kol2_master.view.Post.FormaDodajElekricniUredjaj;

public class ElektricniUredjajController {
	public void sacuvajPodatke(FormaDodajElekricniUredjaj dodajElektricniUredjaj, ElektricniUredjaj elektricniUredjaj) {
        if (elektricniUredjaj.getNaziv().trim().isEmpty() || elektricniUredjaj.getJedinicaMere().trim().isEmpty() || elektricniUredjaj.getKategorija().trim().isEmpty() || elektricniUredjaj.getProizvodjac().trim().isEmpty() || elektricniUredjaj.getBoja().trim().isEmpty() || elektricniUredjaj.getDimenzije().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dodajElektricniUredjaj, "Polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String podaciZaCuvanje = formatirajPodatke(elektricniUredjaj);
        
        File file = new File("src/kol2_master/data/elektricniUredjaji.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split("\\|");
	                if (parts.length > 0 && parts[0].equals(elektricniUredjaj.getNaziv())) {
	                    JOptionPane.showMessageDialog(dodajElektricniUredjaj, "Elektricni Uredjaj sa nazivom " + elektricniUredjaj.getNaziv() + " već postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	            }
	        } 
	        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
	            writer.println(podaciZaCuvanje);
	            JOptionPane.showMessageDialog(dodajElektricniUredjaj, "Uspešno ste dodali Elektricni Uredjaj.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
	            writer.close();
	            dodajElektricniUredjaj.dispose();
	        } 
	        }catch (IOException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(dodajElektricniUredjaj, "Greška pri čuvanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
	        }
    	}
	
    public String formatirajPodatke(ElektricniUredjaj elektricniUredjaj) {
        return String.format("%s|%.2f|%s|%s|%s|%s|%d|%s",
        		elektricniUredjaj.getNaziv(),
        		elektricniUredjaj.getCena(),
                elektricniUredjaj.getJedinicaMere(),
                elektricniUredjaj.getKategorija(),
                elektricniUredjaj.getProizvodjac(),
                elektricniUredjaj.getBoja(),
                elektricniUredjaj.getNominalnaSnaga(),
                elektricniUredjaj.getDimenzije());
    }
}
