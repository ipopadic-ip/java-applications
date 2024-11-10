 package kol2_master.view.forms;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import kol2_master.model.products.ProizvodOgranicenogTrajanja;
import kol2_master.model.products.TehnickiProizvod;
import kol2_master.view.tables.TehnickiProizvodTableModel;

public class TehnickiProizvodPanel extends ProizvodPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3100515750892692612L;
	private JTextField bojaField;
    private JSpinner nominalnaSnagaSpinner, radniNaponSpinner;
    protected JButton updateButton, deleteButton, saveToCSVButton;
    protected TehnickiProizvodTableModel tableModel;
    protected JTable table;
    
    public void initializePanel() throws ParseException {
        loadDataFromCSV();
    }
    
    public TehnickiProizvodPanel(TehnickiProizvodTableModel tableModel) {
    	 this.tableModel = tableModel;
    	 setLayout(new GridLayout(0, 2, 10, 10));
    	 
    	 bojaField = new JTextField(10);
    	 
    	 add(new JLabel("Boja Proizvoda:"));
	     add(bojaField);
    	 
         add(new JLabel("Nominalna Snaga:"));
         add(nominalnaSnagaSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999999999.9, 0.1)));
         
         add(new JLabel("Radni Napon:"));
         add(radniNaponSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999999999.9, 0.1)));
         
         addButton = new JButton("Dodaj");
         add(addButton);
         addButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String enteredNaziv = nazivProizvodaField.getText();
                 if (enteredNaziv.isEmpty()) {
                     JOptionPane.showMessageDialog(null, "Naziv polje je obavezna.", "Empty Fields", JOptionPane.ERROR_MESSAGE);
                     return;
                 }
                 if (tableModel.containsNaziv(enteredNaziv)) {
                     JOptionPane.showMessageDialog(null, "Proizvod sa zadatim nazivom vec postoji.", "Duplicate Name", JOptionPane.ERROR_MESSAGE);
                 } else {
                     TehnickiProizvod tehnickiProizvod = getTehnickiProizvod();
                     tableModel.addTehnickiProizvod(tehnickiProizvod);
                 }
             }
         });

         updateButton = new JButton("Ažuriraj");
         add(updateButton);
         updateButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String enteredNaziv = nazivProizvodaField.getText();
                 int indexOfTehnickiProizvod = tableModel.getIndexOfTehnickiProizvodByNaziv(enteredNaziv);

                 if (indexOfTehnickiProizvod == -1) {
                     JOptionPane.showMessageDialog(null, "Tehnicki proizvod sa tim nazivom ne postoji.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                 } else {
                     TehnickiProizvod updatedTehnickiProizvod = getTehnickiProizvod();
                     tableModel.updateTehnickiProizvod(indexOfTehnickiProizvod, updatedTehnickiProizvod);
                 }
             }
         });

         deleteButton = new JButton("Obriši");
         add(deleteButton);
         deleteButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String enteredNaziv = nazivProizvodaField.getText();
                 int indexOfTehnickiProizvod = tableModel.getIndexOfTehnickiProizvodByNaziv(enteredNaziv);
                 if (indexOfTehnickiProizvod == -1) {
                     JOptionPane.showMessageDialog(null, "Tehnicki proizvod sa tim nazivom ne postoji.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                 } else {
                     tableModel.deleteTehnickiProizvod(indexOfTehnickiProizvod);
                 }
             }
         });
         
         saveToCSVButton = new JButton("Save to CSV");
	        add(saveToCSVButton);
	        saveToCSVButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                saveDataToCSV();
	            }
	        });
    }
    
    public void saveDataToCSV() {
	    try {
	        File file = new File("src/kol2_master/data/data.csv");

	        StringBuilder fileContent = new StringBuilder();
	        boolean deleteMode = false;
	        
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.trim().equals("$tp")) {
	                    deleteMode = true;
	                } else if (line.trim().equals("$kraj")) {
	                    deleteMode = false;
	                    fileContent.append("$tp\n");
	    		        
	                    for (TehnickiProizvod tehnickiProizvod : tableModel.tehnickiProizvodiList) {
	                    	String dataLine = String.format("%s|%.2f|%s|%s|%s|%s|%.2f|%.2f",
				            		tehnickiProizvod.getNaziv(), tehnickiProizvod.getCena(), tehnickiProizvod.getModel(),
				            		tehnickiProizvod.getJedinicaMere(), tehnickiProizvod.getDatumProizvodnje(), tehnickiProizvod.getBoja(),
				            		tehnickiProizvod.getNominalnaSnaga(), tehnickiProizvod.getRadniNapon());

	                        fileContent.append(dataLine).append("\n");
	                    }
	                    fileContent.append("$kraj\n");
	                } else if (!deleteMode) {
	                    fileContent.append(line).append("\n");
	                }
	            }
	        }
	        
	        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
	            writer.write(fileContent.toString());
	        }

	        JOptionPane.showMessageDialog(null, "Podaci uspešno sačuvani u CSV fajlu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

	    } catch (IOException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Greška prilikom čuvanja podataka u CSV fajlu.", "Greška", JOptionPane.ERROR_MESSAGE);
	    }
	}
    
    private void loadDataFromCSV() throws ParseException {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("src/kol2_master/data/data.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split("\\|");
                if (data.length == 8) {
                	String naziv = data[0];
         	        double cena = Double.parseDouble(data[1]);
         	        String model = data[2];
         	        String jedinicaMere = data[3];
         	        
//         	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY"); 
         	        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                   	Date datumProizvodnje = dateFormat.parse(data[4]);
                   	String boja = data[5];
         	        double nominalnaSnaga = Double.parseDouble(data[6]);
         	        double radniNapon = Double.parseDouble(data[7]);

         	       TehnickiProizvod tehnickiProizvod = new TehnickiProizvod(naziv, cena, model, jedinicaMere, datumProizvodnje, boja, nominalnaSnaga, radniNapon);
                    
         	      if (!tableModel.containsTehnickiProizvod(tehnickiProizvod)) {
         	    	 tableModel.addTehnickiProizvod(tehnickiProizvod);
                  }
                }
            }

            csvReader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data from CSV", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setTable(JTable table) {
        this.table = table;
    }

    public TehnickiProizvod getTehnickiProizvod() {
        String naziv = nazivProizvodaField.getText();
        double cena = (double) cenaProizvodaSpinner.getValue();
        String model = modelProizvodaField.getText();
        String jedinicaMere = (String) jedinicaMereField.getText();
        Date datumProizvodnje = (Date) datumProizvodnjeSpinner.getValue();
        String boja = bojaField.getText();
        double nominalnaSnaga = (double) nominalnaSnagaSpinner.getValue();
        double radniNapon = (double) radniNaponSpinner.getValue();

        return new TehnickiProizvod(naziv, cena, model, jedinicaMere, datumProizvodnje, boja, nominalnaSnaga, radniNapon);
    }
    
}
