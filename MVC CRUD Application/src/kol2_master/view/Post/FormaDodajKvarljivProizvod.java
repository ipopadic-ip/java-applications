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

import kol2_master.controller.Post.KvarljivProizvodController;
import kol2_master.controller.Put.IzmeniKvarljivProizvodController;
import kol2_master.model.KvarljiviProizvod;

public class FormaDodajKvarljivProizvod extends JFrame{
	 	private JTextField nazivField, kategorijaField, proizvodjacField;
	    private JSpinner cenaSpinner, rokTrajanjaSpinner, temperaturaSkladistenjaSpinner;
	    private JComboBox<String> jedinicaMereComboBox;
	    private JButton sacuvajButton;

	    public FormaDodajKvarljivProizvod(String akcija) {
	        setLayout(new BorderLayout());

	        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
	        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        SpinnerDateModel dateModel = new SpinnerDateModel();
	        rokTrajanjaSpinner = new JSpinner(dateModel);
	        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(rokTrajanjaSpinner, "dd.MM.yyyy");
	        rokTrajanjaSpinner.setEditor(dateEditor);

	        dodajLabeluITextField(panel, "Naziv:", nazivField = new JTextField());
	        dodajLabeluISpinner(panel, "Cena:", cenaSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1)));
	        dodajLabeluIComboBox(panel, "Jedinica Mere:", jedinicaMereComboBox = new JComboBox<>(new String[]{"kg","L"}));
	        dodajLabeluITextField(panel, "Kategorija:", kategorijaField = new JTextField());
	        dodajLabeluITextField(panel, "Proizvođač:", proizvodjacField = new JTextField());
	        dodajLabeluISpinner(panel, "Rok Trajanja:", rokTrajanjaSpinner);
	        dodajLabeluISpinner(panel, "Temperatura Skladištenja:", temperaturaSkladistenjaSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1)));

	        JPanel buttonPanel = new JPanel();
	        if (akcija.equals("Dodaj")) {
	            setTitle("Forma za Dodavanje Kvarljivog Proizvoda");
	            buttonPanel.add(sacuvajButton = new JButton("Sačuvaj"));
	        } else if (akcija.equals("Izmeni")) {
	            setTitle("Forma za Izmenu Kvarljivog Proizvoda");
	            buttonPanel.add(sacuvajButton = new JButton("Izmeni"));
	        }

	        add(panel, BorderLayout.CENTER);
	        add(buttonPanel, BorderLayout.SOUTH);

	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setSize(600, 300);
	        setLocationRelativeTo(null);
	        setResizable(false);
	        setVisible(true);

	        sacuvajButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                KvarljivProizvodController kvarljivProizvodController = new KvarljivProizvodController();
	                IzmeniKvarljivProizvodController izmeniKvarljivProizvodController = new IzmeniKvarljivProizvodController();
	                KvarljiviProizvod kvarljivProizvod = new KvarljiviProizvod();
	                kvarljivProizvod.setNaziv(nazivField.getText());
	                kvarljivProizvod.setCena((double) cenaSpinner.getValue());
	                kvarljivProizvod.setJedinicaMere(jedinicaMereComboBox.getSelectedItem().toString());
	                kvarljivProizvod.setKategorija(kategorijaField.getText());
	                kvarljivProizvod.setProizvodjac(proizvodjacField.getText());
	                Date rokTrajanja = (Date) rokTrajanjaSpinner.getValue();
	                kvarljivProizvod.setRokTrajanja(rokTrajanja);
	                kvarljivProizvod.setTemperaturaSkladistenja((double) temperaturaSkladistenjaSpinner.getValue());
	                if (akcija.equals("Dodaj")) {
	                	kvarljivProizvodController.sacuvajPodatke(FormaDodajKvarljivProizvod.this, kvarljivProizvod);
	                } else if (akcija.equals("Izmeni")) {
	                	izmeniKvarljivProizvodController.izmeniPodatke(FormaDodajKvarljivProizvod.this, kvarljivProizvod);
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
