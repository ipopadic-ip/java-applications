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

import kol2_master.controller.Post.KvarljivProizvodController;
import kol2_master.model.KvarljiviProizvod;
import kol2_master.view.Post.FormaDodajKvarljivProizvod;

public class IzmeniKvarljivProizvodController {
	public void izmeniPodatke(FormaDodajKvarljivProizvod forma, KvarljiviProizvod kvarljiviProizvod) {
		if (kvarljiviProizvod.getNaziv().trim().isEmpty() || kvarljiviProizvod.getJedinicaMere().trim().isEmpty() || kvarljiviProizvod.getKategorija().trim().isEmpty() || kvarljiviProizvod.getProizvodjac().trim().isEmpty()) {
            JOptionPane.showMessageDialog(forma, "Sva Polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!proveriZaposlenog(kvarljiviProizvod)) {
            JOptionPane.showMessageDialog(forma, "Ne postoji proizvod sa zadatom šifrom.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        KvarljivProizvodController kvarljivProizvodController = new KvarljivProizvodController();
        String podaciZaAzuriranje = kvarljivProizvodController.formatirajPodatke(kvarljiviProizvod);
        
        azurirajPodatke(kvarljiviProizvod, podaciZaAzuriranje);

        JOptionPane.showMessageDialog(forma, "Uspešno ste izmenili podatke o proizvodu.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        forma.dispose();
    }

    private boolean proveriZaposlenog(KvarljiviProizvod kvarljiviProizvod) {
        File rukovodiocFile = new File("src/kol2_master/data/kvarljiviProizvodi.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(rukovodiocFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7 && parts[0].equals(kvarljiviProizvod.getNaziv())) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Greška pri čitanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    private void azurirajPodatke(KvarljiviProizvod kvarljiviProizvod, String azuriraniPodaci) {
        azurirajPodatkeZaFile(kvarljiviProizvod, azuriraniPodaci, "src/kol2_master/data/kvarljiviProizvodi.txt");
    }

    private void azurirajPodatkeZaFile(KvarljiviProizvod kvarljiviProizvod, String azuriraniPodaci, String filePath) {
        File file = new File(filePath);

        try {
            ArrayList<String> updatedLines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 7 && parts[0].equals(kvarljiviProizvod.getNaziv())) {
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
