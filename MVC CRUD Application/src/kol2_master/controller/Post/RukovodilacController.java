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

import kol2_master.model.Rukovodilac;
import kol2_master.model.Zaduzenje;
import kol2_master.view.Post.FormaDodajRukovodilac;

public class RukovodilacController {
	public void sacuvajPodatke(FormaDodajRukovodilac dodajRukovodilca, Rukovodilac rukovodilac) {
		if (rukovodilac.getSifra().trim().isEmpty() || rukovodilac.getIme().trim().isEmpty() || rukovodilac.getPrezime().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dodajRukovodilca, "Polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String podaciZaCuvanje = formatirajPodatke(rukovodilac);
        
        File file = new File("src/kol2_master/data/rukovodioci.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split("\\|");
	                if (parts.length > 0 && parts[0].equals(rukovodilac.getSifra())) {
	                    JOptionPane.showMessageDialog(dodajRukovodilca, "Rukovodilac sa šifrom " + rukovodilac.getSifra() + " već postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	            }
	        } 
	        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
	            writer.println(podaciZaCuvanje);
	            JOptionPane.showMessageDialog(dodajRukovodilca, "Uspešno ste dodali rukovodioca.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
	            writer.close();
	            dodajRukovodilca.dispose();
	        } 
	        }catch (IOException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(dodajRukovodilca, "Greška pri čuvanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
	        }
    	}
    
    private String formatirajPodatke(Rukovodilac rukovodilac) {
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        
        StringBuilder formattedData = new StringBuilder();

        for (Zaduzenje zaduzenje : rukovodilac.getZaduzenja()) {
            String formattedDate = (zaduzenje.getDatumIzvrsenja() != null) ? dateFormat.format(zaduzenje.getDatumIzvrsenja()) : "Nije realizovano";
            
            formattedData.append(String.format("%s|%s|%s|%.2f|%s|%s",
                    rukovodilac.getSifra(),
                    rukovodilac.getIme(),
                    rukovodilac.getPrezime(),
                    rukovodilac.getVisinaPlate(),
                    zaduzenje.getNaziv(),
                    formattedDate
            ));
        }
        return formattedData.toString();
    }
    
}
