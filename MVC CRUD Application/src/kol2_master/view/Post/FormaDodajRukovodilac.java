package kol2_master.view.Post;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import kol2_master.controller.Post.RukovodilacController;
import kol2_master.controller.Put.IzmeniRukovodiocaController;
import kol2_master.model.Rukovodilac;
import kol2_master.model.Zaduzenje;

public class FormaDodajRukovodilac extends JFrame {
	 private JTextField sifraField, imeField, prezimeField, nazivZaduzenjaField;
	 private JSpinner visinaPlateSpinner, datumIzvrsenjaSpinner;
	 private JButton sacuvajButton;

    public FormaDodajRukovodilac(String akcija) {
    	setLayout(new GridLayout(7, 2, 10, 10));

        SpinnerDateModel dateModel = new SpinnerDateModel();
        datumIzvrsenjaSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(datumIzvrsenjaSpinner, "dd.MM.yyyy");
        datumIzvrsenjaSpinner.setEditor(dateEditor);
        
        add(new JLabel("Šifra:"));
        sifraField = new JTextField();
        add(sifraField);

        add(new JLabel("Ime:"));
        imeField = new JTextField();
        add(imeField);

        add(new JLabel("Prezime:"));
        prezimeField = new JTextField();
        add(prezimeField);

        add(new JLabel("Visina plate:"));
        SpinnerNumberModel visinaPlateModel = new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1);
        visinaPlateSpinner = new JSpinner(visinaPlateModel);
        add(visinaPlateSpinner);
        
        add(new JLabel("Naziv zaduženja:"));
        nazivZaduzenjaField = new JTextField();
        add(nazivZaduzenjaField);
        
        add(new JLabel("Datum izvršenja zaduženja:"));
        add(datumIzvrsenjaSpinner);
        JCheckBox nullDateCheckBox = new JCheckBox("Zaduženje Nije realizovano");
        add(nullDateCheckBox);

        if (akcija.equals("Dodaj")) {
            setTitle("Forma za Dodavanje Rukovodioca");
            sacuvajButton = new JButton("Dodaj");
        } else if (akcija.equals("Izmeni")) {
            setTitle("Forma za Izmenu Rukovodioca");
            sacuvajButton = new JButton("Izmeni");
        }
        sacuvajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	RukovodilacController rukovodilacController = new RukovodilacController();
            	IzmeniRukovodiocaController izmeniRukovodiocaController = new IzmeniRukovodiocaController();
            	Zaduzenje zaduzenje = new Zaduzenje();
                Rukovodilac rukovodilac = new Rukovodilac();
                rukovodilac.setSifra(sifraField.getText());
                rukovodilac.setIme(imeField.getText());
                rukovodilac.setPrezime(prezimeField.getText());
                rukovodilac.setVisinaPlate((double) visinaPlateSpinner.getValue());
                if (rukovodilac.getZaduzenja() == null) {
                    rukovodilac.setZaduzenja(new ArrayList<>());
                }
                zaduzenje.setNaziv(nazivZaduzenjaField.getText());
                if (nullDateCheckBox.isSelected()) {
                    zaduzenje.setDatumIzvrsenja(null);
                } else {
                    Date datumIzvrsenja = (Date) datumIzvrsenjaSpinner.getValue();
                    zaduzenje.setDatumIzvrsenja(datumIzvrsenja);
                }
                
                rukovodilac.getZaduzenja().add(zaduzenje);
                if (akcija.equals("Dodaj")) {
                	rukovodilacController.sacuvajPodatke(FormaDodajRukovodilac.this, rukovodilac);
                } else if (akcija.equals("Izmeni")) {
                    izmeniRukovodiocaController.izmeniPodatke(FormaDodajRukovodilac.this, rukovodilac);
                }
            }
        });
        add(sacuvajButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setVisible(true);
    }
}
