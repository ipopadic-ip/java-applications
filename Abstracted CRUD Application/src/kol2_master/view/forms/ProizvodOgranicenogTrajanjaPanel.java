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
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import kol2_master.model.products.ProizvodOgranicenogTrajanja;
import kol2_master.model.users.Direktor;
import kol2_master.view.tables.ProizvodOgranicenogTrajanjaTableModel;

public class ProizvodOgranicenogTrajanjaPanel extends ProizvodPanel{
	/**
	 * 
	 */
		private static final long serialVersionUID = -8700985678998884053L;
		protected JSpinner rokTrajanjaSpinner, temperaturaSkladistenjaSpinner;
		protected JButton updateButton, deleteButton;
	    protected ProizvodOgranicenogTrajanjaTableModel tableModel;
	    protected JTable table;
	    private JButton saveToCSVButton;
	    
	    public void initializePanel() throws ParseException {
	        loadDataFromCSV();
	    }
	
	public ProizvodOgranicenogTrajanjaPanel(ProizvodOgranicenogTrajanjaTableModel tableModel) {
		this.tableModel = tableModel;
		SpinnerDateModel dateModel = new SpinnerDateModel();
	    rokTrajanjaSpinner = new JSpinner(dateModel);
	    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(rokTrajanjaSpinner, "dd.MM.yyyy");
	    rokTrajanjaSpinner.setEditor(dateEditor);
	    
	    setLayout(new GridLayout(0, 2, 10, 10));
	    add(new JLabel("Rok Trajanja:"));
        add(rokTrajanjaSpinner);
        add(new JLabel("Temperatura Skladistenja:"));
        add(temperaturaSkladistenjaSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999999999.9, 0.1)));
        
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
                	 ProizvodOgranicenogTrajanja proizvodOgranicenogTrajanja = getProizvodOgranicenogTrajanja();
                     tableModel.addProizvodOgranicenogTrajanja(proizvodOgranicenogTrajanja);
                 }
            }
        });

        updateButton = new JButton("Ažuriraj");
        add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	 String enteredNaziv = nazivProizvodaField.getText();
                 int indexOfProizvodOT = tableModel.getIndexOfProizvodOTByNaziv(enteredNaziv);

                 if (indexOfProizvodOT == -1) {
                     JOptionPane.showMessageDialog(null, "Proizvod ogranicenog trajanja sa tim nazivom ne postoji.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                 } else {
                	 ProizvodOgranicenogTrajanja updatedProizvodOgranicenogTrajanja = getProizvodOgranicenogTrajanja();
                     tableModel.updateProizvodOgranicenogTrajanja(indexOfProizvodOT, updatedProizvodOgranicenogTrajanja);
                 }
            	
            }
        });

        deleteButton = new JButton("Obriši");
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String enteredNaziv = nazivProizvodaField.getText();
                 int indexOfProizvodOT = tableModel.getIndexOfProizvodOTByNaziv(enteredNaziv);
                 if (indexOfProizvodOT == -1) {
                     JOptionPane.showMessageDialog(null, "Proizvod ogranicenog trajanja sa tim nazivom ne postoji.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                 } else {
                     tableModel.deleteProizvodOgranicenogTrajanja(indexOfProizvodOT);
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
		                if (line.trim().equals("$pot")) {
		                    deleteMode = true;
		                } else if (line.trim().equals("$tp")) {
		                    deleteMode = false;
		                    fileContent.append("$pot\n");
		    		        
		                    for (ProizvodOgranicenogTrajanja proizvodOgranicenogTrajanja : tableModel.proizvodiOgranicenogTrajanjaList) {
		                    	String dataLine = String.format("%s|%.2f|%s|%s|%s|%s|%.2f",
		    		            		proizvodOgranicenogTrajanja.getNaziv(), proizvodOgranicenogTrajanja.getCena(),
		    		            		proizvodOgranicenogTrajanja.getModel(), proizvodOgranicenogTrajanja.getJedinicaMere(),
		    		            		proizvodOgranicenogTrajanja.getDatumProizvodnje(), proizvodOgranicenogTrajanja.getRokTrajanja(),
		    		            		proizvodOgranicenogTrajanja.getTemperaturaSkladistenja());

		                        fileContent.append(dataLine).append("\n");
		                    }
		                    fileContent.append("$tp\n");
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
	                if (data.length == 7) {
	                	 String naziv = data[0];
	         	        double cena = Double.parseDouble(data[1]);
	         	        String model = data[2];
	         	        String jedinicaMere = data[3];
	         	        
//	         	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY"); 
	         	       SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
	                   	Date datumProizvodnje = dateFormat.parse(data[4]);
	                   	Date rokTrajanja = dateFormat.parse(data[5]);
	         	        double temperaturaSkladistenja = Double.parseDouble(data[6]);

	         	       ProizvodOgranicenogTrajanja proizvodOgranicenogTrajanja = new ProizvodOgranicenogTrajanja(naziv, cena, model, jedinicaMere, datumProizvodnje, rokTrajanja, temperaturaSkladistenja);
	                    
	         	      if (!tableModel.containsProizvodOgranicenogTrajanja(proizvodOgranicenogTrajanja)) {
	                      tableModel.addProizvodOgranicenogTrajanja(proizvodOgranicenogTrajanja);
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

	    public ProizvodOgranicenogTrajanja getProizvodOgranicenogTrajanja() {
	        String naziv = nazivProizvodaField.getText();
	        double cena = (double) cenaProizvodaSpinner.getValue();
	        String model = modelProizvodaField.getText();
	        String jedinicaMere = (String) jedinicaMereField.getText();
	        Date datumProizvodnje = (Date) datumProizvodnjeSpinner.getValue();
	        Date rokTrajanja = (Date) rokTrajanjaSpinner.getValue();
	        double temperaturaSkladistenja = (double) temperaturaSkladistenjaSpinner.getValue();

	        return new ProizvodOgranicenogTrajanja(naziv, cena, model, jedinicaMere, datumProizvodnje, rokTrajanja, temperaturaSkladistenja);
	    }	
}
