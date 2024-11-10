package kol2_master.controller.Put;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import kol2_master.model.Promoter;
import kol2_master.view.Post.DodajPromotera;

public class IzmeniPromoteraController {
	public void izmeniPodatke(DodajPromotera forma, Promoter promoter) {
		if (promoter.getSifra().trim().isEmpty() || promoter.getIme().trim().isEmpty() || promoter.getPrezime().trim().isEmpty() || promoter.getPrimedbe().trim().isEmpty() || promoter.getNadredjeni().trim().isEmpty()) {
            JOptionPane.showMessageDialog(forma, "Polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!proveriZaposlenog(promoter)) {
            JOptionPane.showMessageDialog(forma, "Ne postoji zaposleni sa zadatom šifrom.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String podaciZaAzuriranje = formatirajPodatke(promoter);
        
        azurirajPodatke(promoter, podaciZaAzuriranje);

        JOptionPane.showMessageDialog(forma, "Uspešno ste izmenili podatke o zaposlenom.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        forma.dispose();
    }
	
	private String formatirajPodatke(Promoter promoter) {
        
        StringBuilder formattedData = new StringBuilder();
        
            formattedData.append(String.format("%s|%s|%s|%.2f|%s|%s",
            		promoter.getSifra(),
            		promoter.getIme(),
            		promoter.getPrezime(),
            		promoter.getVisinaPlate(),
            		promoter.getPrimedbe(),
            		promoter.getNadredjeni()
            ));
        return formattedData.toString();
	}

    public boolean proveriZaposlenog(Promoter promoter) {
        File rukovodiocFile = new File("src/kol2_master/data/promoteri.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(rukovodiocFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 && parts[0].equals(promoter.getSifra())) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Greška pri čitanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    private void azurirajPodatke(Promoter promoter, String azuriraniPodaci) {
        azurirajPodatkeZaFile(promoter, azuriraniPodaci, "src/kol2_master/data/promoteri.txt");
    }

    private void azurirajPodatkeZaFile(Promoter promoter, String azuriraniPodaci, String filePath) {
        File file = new File(filePath);

        try {
            ArrayList<String> updatedLines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 4 && parts[0].equals(promoter.getSifra())) {
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
