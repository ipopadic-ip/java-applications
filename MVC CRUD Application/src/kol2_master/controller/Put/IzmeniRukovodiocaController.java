package kol2_master.controller.Put;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import kol2_master.model.Rukovodilac;
import kol2_master.model.Zaduzenje;
import kol2_master.view.Post.FormaDodajRukovodilac;

public class IzmeniRukovodiocaController {

	public void izmeniPodatke(FormaDodajRukovodilac forma, Rukovodilac rukovodilac) {
		if (rukovodilac.getSifra().trim().isEmpty() || rukovodilac.getIme().trim().isEmpty() || rukovodilac.getPrezime().trim().isEmpty()) {
            JOptionPane.showMessageDialog(forma, "Polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!proveriZaposlenog(rukovodilac)) {
            JOptionPane.showMessageDialog(forma, "Ne postoji zaposleni sa zadatom šifrom.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String podaciZaAzuriranje = formatirajPodatke(rukovodilac);
        
        azurirajPodatke(rukovodilac, podaciZaAzuriranje);

        JOptionPane.showMessageDialog(forma, "Uspešno ste izmenili podatke o zaposlenom.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        forma.dispose();
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

    private boolean proveriZaposlenog(Rukovodilac rukovodilac) {
        File rukovodiocFile = new File("src/kol2_master/data/rukovodioci.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(rukovodiocFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 && parts[0].equals(rukovodilac.getSifra())) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Greška pri čitanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    private void azurirajPodatke(Rukovodilac rukovodilac, String azuriraniPodaci) {
        azurirajPodatkeZaFile(rukovodilac, azuriraniPodaci, "src/kol2_master/data/rukovodioci.txt");
    }

    private void azurirajPodatkeZaFile(Rukovodilac rukovodilac, String azuriraniPodaci, String filePath) {
        File file = new File(filePath);

        try {
            ArrayList<String> updatedLines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 4 && parts[0].equals(rukovodilac.getSifra())) {
                        updatedLines.add(azuriraniPodaci);
                    } else {
                        updatedLines.add(line);
                    }
                }
            }
            
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
                for (String updatedLine : updatedLines) {
                    writer.println(updatedLine);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Greška pri azuriranju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
	
}
