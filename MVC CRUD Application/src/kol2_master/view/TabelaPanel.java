package kol2_master.view;
import javax.swing.*;

import kol2_master.view.Get.FormaPrikazElektricniUredjaj;
import kol2_master.view.Get.FormaPrikazKvarljivProizvod;
import kol2_master.view.Get.FormaPrikazPromoter;
import kol2_master.view.Get.FormaPrikazRukovodioca;
import kol2_master.view.Post.DodajPromotera;
import kol2_master.view.Post.FormaDodajElekricniUredjaj;
import kol2_master.view.Post.FormaDodajKvarljivProizvod;
import kol2_master.view.Post.FormaDodajRukovodilac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class TabelaPanel extends JPanel{
	private JComboBox<String> tabelaComboBox;

    public TabelaPanel() {
        tabelaComboBox = new JComboBox<>(new String[]{"Rukovodilac", "Promoter", "KvarljiviProizvod", "ElektricniUredjaji"});
        tabelaComboBox.setSelectedItem(null);

        JButton prikaziButton = new JButton("Prikaži Tabelu");
        prikaziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String selectedEntitet = (String) tabelaComboBox.getSelectedItem();
                if (selectedEntitet != null && !selectedEntitet.isEmpty()) {
                	 try {
						prikaziTabelu(selectedEntitet);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
                } else {
                    JOptionPane.showMessageDialog(TabelaPanel.this, "Izaberite entitet pre dodavanja.");
                }
            }
        });

        add(new JLabel("Izaberite tabelu:"));
        add(tabelaComboBox);
        add(prikaziButton);
    }
    
    private void prikaziTabelu(String entitet) throws ParseException {
        switch (entitet) {
            case "Promoter":
                new FormaPrikazPromoter();
                break;
            case "Rukovodilac":
                new FormaPrikazRukovodioca();
                break;
            case "KvarljiviProizvod":
                new FormaPrikazKvarljivProizvod();
                break;
            case "ElektricniUredjaji":
            	new FormaPrikazElektricniUredjaj();
                break;
            default:
                break;
        }
    }
}
