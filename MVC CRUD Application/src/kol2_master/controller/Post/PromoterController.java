package kol2_master.controller.Post;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import kol2_master.model.Promoter;
import kol2_master.view.Post.DodajPromotera;

public class PromoterController {
	public void sacuvajPodatke(DodajPromotera dodajPromotera, Promoter promoter) {
        if (promoter.getSifra().trim().isEmpty() || promoter.getIme().trim().isEmpty() || promoter.getPrezime().trim().isEmpty() || promoter.getPrimedbe().trim().isEmpty() || promoter.getNadredjeni().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dodajPromotera, "Polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String podaciZaCuvanje = String.format("%s|%s|%s|%.2f|%s|%s",
                promoter.getSifra(), promoter.getIme(), promoter.getPrezime(), promoter.getVisinaPlate(),
                promoter.getPrimedbe(), promoter.getNadredjeni());

        File file = new File("src/kol2_master/data/promoteri.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 0 && parts[0].equals(promoter.getSifra())) {
                    JOptionPane.showMessageDialog(dodajPromotera, "Promoter sa šifrom " + promoter.getSifra() + " već postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dodajPromotera, "Greška pri čitanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {

            if (!file.exists()) {
                file.createNewFile();
            }
            writer.println(podaciZaCuvanje);
            JOptionPane.showMessageDialog(dodajPromotera, "Uspešno ste dodali promotera.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
            writer.close();
            dodajPromotera.dispose();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dodajPromotera, "Greška pri čuvanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
