package kol2_master.view.Post;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import kol2_master.controller.Post.ElektricniUredjajController;
import kol2_master.controller.Post.KvarljivProizvodController;
import kol2_master.controller.Put.IzmeniElektricniUredjajController;
import kol2_master.controller.Put.IzmeniElektricniUredjajController;
import kol2_master.model.ElektricniUredjaj;
import kol2_master.model.KvarljiviProizvod;

public class FormaDodajElekricniUredjaj extends JFrame {
	private JTextField nazivField, kategorijaField, proizvodjacField, bojaField, dimenzijeField;
    private JSpinner cenaSpinner, nominalnaSnagaSpinner;
    private JComboBox<String> jedinicaMereComboBox;
    private JButton sacuvajButton;

    public FormaDodajElekricniUredjaj(String akcija) {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dodajLabeluITextField(panel, "Naziv:", nazivField = new JTextField());
        dodajLabeluISpinner(panel, "Cena:", cenaSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1)));
        dodajLabeluIComboBox(panel, "Jedinica Mere:", jedinicaMereComboBox = new JComboBox<>(new String[]{"m", "m2", "kg", "J", "L"}));
        dodajLabeluITextField(panel, "Kategorija:", kategorijaField = new JTextField());
        dodajLabeluITextField(panel, "Proizvođač:", proizvodjacField = new JTextField());
        dodajLabeluITextField(panel, "Boja:", bojaField = new JTextField());
        dodajLabeluISpinner(panel, "Nominalna Snaga:", nominalnaSnagaSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1)));
        dodajLabeluITextField(panel, "Dimenzije:", dimenzijeField = new JTextField());


        JPanel buttonPanel = new JPanel();
        if (akcija.equals("Dodaj")) {
            setTitle("Forma za Dodavanje Elektricnog Uredjaja");
            buttonPanel.add(sacuvajButton = new JButton("Sačuvaj"));
        } else if (akcija.equals("Izmeni")) {
            setTitle("Forma za Izmenu Elektricnog Uredjaja");
            buttonPanel.add(sacuvajButton = new JButton("Izmeni"));
        }
        
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        sacuvajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ElektricniUredjajController elektricniUredjajController = new ElektricniUredjajController();
                ElektricniUredjaj elektricniUredjaj = new ElektricniUredjaj();
                IzmeniElektricniUredjajController izmeniElektricniUredjaj = new IzmeniElektricniUredjajController(); 
                elektricniUredjaj.setNaziv(nazivField.getText());
                elektricniUredjaj.setCena((double) cenaSpinner.getValue());
                elektricniUredjaj.setJedinicaMere(jedinicaMereComboBox.getSelectedItem().toString());
                elektricniUredjaj.setKategorija(kategorijaField.getText());
                elektricniUredjaj.setProizvodjac(proizvodjacField.getText());
                elektricniUredjaj.setNominalnaSnaga((int) nominalnaSnagaSpinner.getValue());
                elektricniUredjaj.setBoja(bojaField.getText());
                elektricniUredjaj.setDimenzije(dimenzijeField.getText());
                if (akcija.equals("Dodaj")) {
                	elektricniUredjajController.sacuvajPodatke(FormaDodajElekricniUredjaj.this, elektricniUredjaj);
                } else if (akcija.equals("Izmeni")) {
                	izmeniElektricniUredjaj.izmeniPodatke(FormaDodajElekricniUredjaj.this, elektricniUredjaj);
                }
            }
        });
    }
    private void dodajLabeluITextField(JPanel panel, String labela, JTextField textField) {
        panel.add(new JLabel(labela));
        panel.add(textField);
    }

    private void dodajLabeluISpinner(JPanel panel, String labela, JSpinner spinner) {
        panel.add(new JLabel(labela));
        panel.add(spinner);
    }

    private void dodajLabeluIComboBox(JPanel panel, String labela, JComboBox comboBox) {
    	panel.add(new JLabel(labela));
    	panel.add(comboBox);
    }
}
