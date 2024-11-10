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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import kol2_master.model.users.Direktor;
import kol2_master.view.tables.DirektorTableModel;

public class DirektorPanel extends ZaposleniPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8684325335627214564L;
	private JSpinner bonusSpinner;
	private JButton updateButton, deleteButton;
	private DirektorTableModel tableModel;
	private JTable table;
	private JButton saveToCSVButton;
	
	
	public void initializePanel() {
        loadDataFromCSV();
//        tableModel = new DirektorTableModel();
    }
	
	 public DirektorPanel(DirektorTableModel tableModel) {
	        this.tableModel = tableModel;
	        setLayout(new GridLayout(0, 2, 10, 10));
	        add(new JLabel("Bonus:"));
	        SpinnerNumberModel visinaPlateModel = new SpinnerNumberModel(0.0, 0.0, 99999999.9, 0.1);
	        bonusSpinner = new JSpinner(visinaPlateModel);
	        add(bonusSpinner);

	        addButton = new JButton("Dodaj");
	        add(addButton);
	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String enteredIme = imeField.getText();
	            	String enteredPrezime = prezimeField.getText();

	            	 if (enteredIme.isEmpty() || enteredPrezime.isEmpty()) {
	                     JOptionPane.showMessageDialog(null, "Ime i Prezime polja su obavezna.", "Empty Fields", JOptionPane.ERROR_MESSAGE);
	                     return;
	                 }
	            	 
	            	 if (tableModel.containsImeAndPrezime(enteredIme, enteredPrezime)) {
	                     JOptionPane.showMessageDialog(null, "Direktor sa zadatim Imenom i Prezimenom vec postoji.", "Duplicate Name", JOptionPane.ERROR_MESSAGE);
	                 } else {
	                     Direktor direktor = getDirektor();
	                     tableModel.addDirektor(direktor);
	                 }
	            }
	        });
	        
	        updateButton = new JButton("Ažuriraj");
	        add(updateButton);
	        updateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	 String enteredIme = imeField.getText();
	                 String enteredPrezime = prezimeField.getText();
	                 int indexOfDirektor = tableModel.getIndexOfDirektorByName(enteredIme, enteredPrezime);

	                 if (indexOfDirektor == -1) {
	                     JOptionPane.showMessageDialog(null, "Direktor sa tim imenom i prezimenom ne postoji.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
	                 } else {
	                     Direktor existingDirektor = tableModel.getDirektorAt(indexOfDirektor);

	                     existingDirektor.setIme(imeField.getText());
	                     existingDirektor.setPrezime(prezimeField.getText());
	                     existingDirektor.setVisinaPlate((double) visinaPlateSpinner.getValue());
	                     existingDirektor.setBonus((double) bonusSpinner.getValue());

	                     tableModel.updateDirektor(indexOfDirektor, existingDirektor);
	                 }
	            }
	        });

	        deleteButton = new JButton("Obriši");
	        add(deleteButton);
	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	 String enteredIme = imeField.getText();
	            	 String enteredPrezime = prezimeField.getText();
	                 int indexOfDirektor = tableModel.getIndexOfDirektorByName(enteredIme, enteredPrezime);

	                 if (indexOfDirektor == -1) {
	                     JOptionPane.showMessageDialog(null, "Direktor sa tim imenom i prezimenom ne postoji.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
	                 } else {
	                     tableModel.deleteDirektor(indexOfDirektor);
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
		                if (line.trim().equals("$d")) {
		                    deleteMode = true;
		                } else if (line.trim().equals("$p")) {
		                    deleteMode = false;
		                    fileContent.append("$d\n");
		                    for (Direktor direktor : tableModel.direktori) {
		                        String dataLine = String.format("%s|%s|%s|%.2f|%.2f",
		                                direktor.getSifra(), direktor.getIme(), direktor.getPrezime(),
		                                direktor.getVisinaPlate(), direktor.getBonus());

		                        fileContent.append(dataLine).append("\n");
		                    }
		                    fileContent.append("$p\n");
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
	 
	 private void loadDataFromCSV() {
	        try {
	            BufferedReader csvReader = new BufferedReader(new FileReader("src/kol2_master/data/data.csv"));
	            String row;
	            while ((row = csvReader.readLine()) != null) {
	                String[] data = row.split("\\|");
	                if (data.length == 5) {
	                    String sifra = data[0];
	                    String ime = data[1];
	                    String prezime = data[2];
	                    double visinaPlate = Double.parseDouble(data[3]);
	                    double bonus = Double.parseDouble(data[4]);

	                    Direktor direktor = new Direktor(sifra, ime, prezime, visinaPlate, bonus);
	                    
	                    if (!tableModel.containsDirektor(direktor)) {
	                        tableModel.addDirektor(direktor);
	                    }
	                }
	            }

	            csvReader.close();
	            refreshTable();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error loading data from CSV", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }


	    public void setTable(JTable table) {
	        this.table = table;
	    }
	    
	    public Direktor getDirektor() {
	         String ime = imeField.getText();
	         String prezime = prezimeField.getText();
	         double visinaPlate = (double) visinaPlateSpinner.getValue();
	         double bonus = (double) bonusSpinner.getValue();
	         return new Direktor(Direktor.generisiSifru(), ime, prezime, visinaPlate, bonus);
	    }
	    
	    private void refreshTable() {
	        if (table != null) {
	            tableModel.fireTableDataChanged();
	            table.repaint();
	        }
	    }
	    
}
