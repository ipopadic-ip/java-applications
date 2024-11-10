package kol2_master.controller.Del;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kol2_master.model.Promoter;
import kol2_master.view.Delete.FormaIzbrisi;

public class DeletePromoterController {
	
	public ArrayList<Promoter> loadPromoterData(String fileName) {
	ArrayList<Promoter> promoterList = new ArrayList<>();
   	 try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                double visinaPlate = Double.parseDouble(data[3]);
                Promoter promoter = new Promoter(data[0], data[1], data[2], visinaPlate, data[4], data[5]);
                promoterList.add(promoter);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
   	 return promoterList;
   }
	 public void izbrisiPodatke(FormaIzbrisi forma, ArrayList<Promoter> promoterList, Promoter promoter) {
		 if (promoter != null) {
	            if (promoter.getSifra().trim().isEmpty()) {
	                JOptionPane.showMessageDialog(forma, "Polje Sifra mora biti popunjeno.", "Greška",
	                        JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	        }

	        boolean found = false;
	        for (int i = 0; i < promoterList.size(); i++) {
	            if (promoterList.get(i).getSifra().equals(promoter.getSifra())) {
	                promoterList.remove(i);
	                found = true;
	                break;
	            }
	        }

	        if (found) {
	            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/kol2_master/data/promoteri.txt"))) {
	                for (Promoter p : promoterList) {
	                    bw.write(p.formatForFile());
	                    bw.newLine();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(forma, "Greška prilikom pisanja u fajl.", "Greška",
	                        JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            JOptionPane.showMessageDialog(forma, "Uspešno ste obrisali podatke o zaposlenom.", "Informacija",
	                    JOptionPane.INFORMATION_MESSAGE);
	            forma.dispose();
	        } else {
	            JOptionPane.showMessageDialog(forma, "Ne postoji zaposleni sa zadatom šifrom.", "Greška",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
}
