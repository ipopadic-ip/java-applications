package kol2_master.controller.Del;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import kol2_master.model.Promoter;
import kol2_master.model.Rukovodilac;
import kol2_master.view.Delete.FormaIzbrisi;

public class DeleteController {
	public void izbrisiPodatke(FormaIzbrisi forma, String file, Rukovodilac rukovodilac) {
		
		if (rukovodilac.getSifra().trim().isEmpty()) {
	           JOptionPane.showMessageDialog(forma, "Polje Sifra mora biti popunjeno.", "Greška", JOptionPane.ERROR_MESSAGE);
	           return;
	    }
		
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(forma, "Greška prilikom čitanja fajla.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean found = false;
        	for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("\\|");
                if (parts.length > 0 && parts[0].equals(rukovodilac.getSifra())) {
                    lines.remove(i);
                    found = true;
                    break;
                }
            }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(forma, "Greška prilikom pisanja u fajl.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (found) {
            JOptionPane.showMessageDialog(forma, "Uspešno ste obrisali podatke o zaposlenom.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
            forma.dispose();
        } else {
            JOptionPane.showMessageDialog(forma, "Ne postoji zaposleni sa zadatom šifrom.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
	}
}
