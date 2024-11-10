package kol2_master.view.Post;
import javax.swing.*;


import kol2_master.controller.Post.PromoterController;
import kol2_master.controller.Put.IzmeniPromoteraController;
import kol2_master.model.Promoter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DodajPromotera extends JFrame{
	 private JTextField sifraField, imeField, prezimeField, primedbeField, nadredjeniField;
	 private JSpinner visinaPlateSpinner;
	 private JButton sacuvajButton;
	 
	 public DodajPromotera(String akcija) {
		 
		 	setLayout(new GridLayout(7, 2, 10, 10));

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

	        add(new JLabel("Primedbe:"));
	        primedbeField = new JTextField();
	        add(primedbeField);

	        add(new JLabel("Nadređeni:"));
	        nadredjeniField = new JTextField();
	        add(nadredjeniField);

	        if (akcija.equals("Dodaj")) {
	            setTitle("Forma za Dodavanje Promotera");
	            sacuvajButton = new JButton("Dodaj");
	        } else if (akcija.equals("Izmeni")) {
	            setTitle("Forma za Izmenu Promotera");
	            sacuvajButton = new JButton("Izmeni");
	        }
	        sacuvajButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	PromoterController promoterController = new PromoterController();
	            	IzmeniPromoteraController izmeniPromoteraController = new IzmeniPromoteraController();
	                Promoter promoter = new Promoter();
	                promoter.setSifra(sifraField.getText());
	                promoter.setIme(imeField.getText());
	                promoter.setPrezime(prezimeField.getText());
	                promoter.setVisinaPlate((double) visinaPlateSpinner.getValue());
	                promoter.setPrimedbe(primedbeField.getText());
	                promoter.setNadredjeni(nadredjeniField.getText());
	                
	                if (akcija.equals("Dodaj")) {
	                	promoterController.sacuvajPodatke(DodajPromotera.this, promoter);
	                } else if (akcija.equals("Izmeni")) {
	                	izmeniPromoteraController.izmeniPodatke(DodajPromotera.this, promoter);
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
