package kol2_master.view;
import javax.swing.*;

import kol2_master.view.Delete.FormaIzbrisi;
import kol2_master.view.Post.DodajPromotera;
import kol2_master.view.Post.FormaDodajElekricniUredjaj;
import kol2_master.view.Post.FormaDodajKvarljivProizvod;
import kol2_master.view.Post.FormaDodajRukovodilac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormaPanel extends JPanel{
	private JComboBox<String> entitetComboBox;
    private JButton dodajButton, izmeniButton, obrisiButton;

    public FormaPanel() {
        entitetComboBox = new JComboBox<>(new String[]{null, "Rukovodilac", "Promoter", "KvarljiviProizvod", "ElektricniUredjaji"});
        entitetComboBox.setSelectedItem(null);

        dodajButton = new JButton("Dodaj");
        izmeniButton = new JButton("Izmeni");
        obrisiButton = new JButton("Izbriši");

        dodajButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                String selectedEntitet = (String) entitetComboBox.getSelectedItem();
                if (selectedEntitet != null && !selectedEntitet.isEmpty()) {
                	 prikaziFormu(selectedEntitet, "Dodaj");
                } else {
                    JOptionPane.showMessageDialog(FormaPanel.this, "Izaberite entitet pre dodavanja.");
                }
            }
        });

        izmeniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String selectedEntitet = (String) entitetComboBox.getSelectedItem();
                if (selectedEntitet != null && !selectedEntitet.isEmpty()) {
                	 prikaziFormu(selectedEntitet, "Izmeni");
                } else {
                    JOptionPane.showMessageDialog(FormaPanel.this, "Izaberite entitet pre dodavanja.");
                }
            }
        });

        obrisiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String selectedEntitet = (String) entitetComboBox.getSelectedItem();
                if (selectedEntitet != null && !selectedEntitet.isEmpty()) {
                	prikaziFormuBrisanja(selectedEntitet);
                } else {
                    JOptionPane.showMessageDialog(FormaPanel.this, "Izaberite entitet pre dodavanja.");
                }
            }
        });

        add(new JLabel("Izaberite entitet:"));
        add(entitetComboBox);
        add(dodajButton);
        add(izmeniButton);
        add(obrisiButton);
    }
    private void prikaziFormuBrisanja(String entitet) {
        switch (entitet) {
            case "Promoter":
                new FormaIzbrisi(entitet);
                break;
            case "Rukovodilac":
                new FormaIzbrisi(entitet);
                break;
            case "KvarljiviProizvod":
                new FormaIzbrisi(entitet);
                break;
            case "ElektricniUredjaji":
                new FormaIzbrisi(entitet);
                break;
            default:
                break;
        }
    }
    private void prikaziFormu(String entitet, String akcija) {
        switch (entitet) {
            case "Promoter":
                new DodajPromotera(akcija);
                break;
            case "Rukovodilac":
                new FormaDodajRukovodilac(akcija);
                break;
            case "KvarljiviProizvod":
                new FormaDodajKvarljivProizvod(akcija);
                break;
            case "ElektricniUredjaji":
                new FormaDodajElekricniUredjaj(akcija);
                break;
            default:
                break;
        }
    }
}
