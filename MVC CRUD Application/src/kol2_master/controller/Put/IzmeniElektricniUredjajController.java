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

import kol2_master.controller.Post.ElektricniUredjajController;
import kol2_master.model.ElektricniUredjaj;
import kol2_master.view.Post.FormaDodajElekricniUredjaj;

public class IzmeniElektricniUredjajController {
	public void izmeniPodatke(FormaDodajElekricniUredjaj forma, ElektricniUredjaj elektricniUredjaj) {
		if (elektricniUredjaj.getNaziv().trim().isEmpty() || elektricniUredjaj.getJedinicaMere().trim().isEmpty() || elektricniUredjaj.getKategorija().trim().isEmpty() || elektricniUredjaj.getProizvodjac().trim().isEmpty() || elektricniUredjaj.getBoja().trim().isEmpty() || elektricniUredjaj.getDimenzije().trim().isEmpty()) {
            JOptionPane.showMessageDialog(forma, "Polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!proveriZaposlenog(elektricniUredjaj)) {
            JOptionPane.showMessageDialog(forma, "Ne postoji proizvod sa zadatom šifrom.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ElektricniUredjajController elektricniUredjajController = new ElektricniUredjajController();
        String podaciZaAzuriranje = elektricniUredjajController.formatirajPodatke(elektricniUredjaj);
        
        azurirajPodatke(elektricniUredjaj, podaciZaAzuriranje);

        JOptionPane.showMessageDialog(forma, "Uspešno ste izmenili podatke o proizvodu.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        forma.dispose();
    }

    private boolean proveriZaposlenog(ElektricniUredjaj elektricniUredjaj) {
        File rukovodiocFile = new File("src/kol2_master/data/elektricniUredjaji.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(rukovodiocFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7 && parts[0].equals(elektricniUredjaj.getNaziv())) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Greška pri čitanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    private void azurirajPodatke(ElektricniUredjaj elektricniUredjaj, String azuriraniPodaci) {
        azurirajPodatkeZaFile(elektricniUredjaj, azuriraniPodaci, "src/kol2_master/data/elektricniUredjaji.txt");
    }

    private void azurirajPodatkeZaFile(ElektricniUredjaj elektricniUredjaj, String azuriraniPodaci, String filePath) {
        File file = new File(filePath);

        try {
            ArrayList<String> updatedLines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 7 && parts[0].equals(elektricniUredjaj.getNaziv())) {
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
