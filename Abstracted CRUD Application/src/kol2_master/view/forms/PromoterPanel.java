package kol2_master.view.forms;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kol2_master.model.products.TehnickiProizvod;
import kol2_master.model.users.Promoter;
import kol2_master.view.tables.PromoterTableModel;

public class PromoterPanel extends ZaposleniPanel{
	 	/**
	 * 
	 */
	private static final long serialVersionUID = -2443926518393998367L;
		private JTextField radnoMestoField;
	 	private PromoterTableModel tableModel;
	 	private JButton updateButton, deleteButton, saveToCSVButton;
	    private JTable table;
	    private ArrayList<ArrayList<LocalDate>> odmoriList;
	    private ArrayList<LocalDate> odmori = new ArrayList<>();
	    private JButton obrisiOdmorButton;
	    private Promoter promoter;
	    private JButton showOdmoriButton, dodajOdmor; 
	    
	    public void initializePanel() throws ParseException {
	        loadPromoterDataFromCSV();
	    }

	    public PromoterPanel(PromoterTableModel tableModel) {
	    	this.tableModel = tableModel;
	    	radnoMestoField = new JTextField(10);
	        setLayout(new GridLayout(0, 2, 10, 10));
	        
	        add(new JLabel("Radno Mesto:"));
	        add(radnoMestoField);	
	        
	        addButton = new JButton("Dodaj");
	        add(addButton);
	        
	        odmoriList = new ArrayList<>();
	        	        	        
	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String enteredIme = imeField.getText();
	                String enteredPrezime = prezimeField.getText();

	                if (enteredIme.isEmpty() || enteredPrezime.isEmpty()) {
	                    JOptionPane.showMessageDialog(null, "Ime i Prezime polja su obavezna.", "Empty Fields",
	                            JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                if (tableModel.containsImeAndPrezime(enteredIme, enteredPrezime)) {
	                    JOptionPane.showMessageDialog(null,
	                            "Promoter sa zadatim Imenom i Prezimenom već postoji.", "Duplicate Name",
	                            JOptionPane.ERROR_MESSAGE);
	                } else {
	                	
	                	odmoriList.clear();
	                	 
	                    Promoter promoter = getPromoter();
	                    tableModel.addPromoter(promoter);
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
	                int indexOfPromoter = tableModel.getIndexOfPromoterByName(enteredIme, enteredPrezime);

	                if (indexOfPromoter == -1) {
	                    JOptionPane.showMessageDialog(null, "Promoter sa tim imenom i prezimenom ne postoji.",
	                            "Invalid Name", JOptionPane.ERROR_MESSAGE);
	                } else {
	                	Promoter existingPromoter = tableModel.getPromoterAt(indexOfPromoter);

	                	existingPromoter.setIme(imeField.getText());
	                	existingPromoter.setPrezime(prezimeField.getText());
	                	existingPromoter.setVisinaPlate((double) visinaPlateSpinner.getValue());
	                	existingPromoter.setRadnoMesto(radnoMestoField.getText());
	                	
	                	tableModel.updatePromoter(indexOfPromoter, existingPromoter);
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
	                int indexOfPromoter = tableModel.getIndexOfPromoterByName(enteredIme, enteredPrezime);

	                if (indexOfPromoter == -1) {
	                    JOptionPane.showMessageDialog(null, "Promoter sa tim imenom i prezimenom ne postoji.",
	                            "Invalid Name", JOptionPane.ERROR_MESSAGE);
	                } else {
	                    tableModel.deletePromoter(indexOfPromoter);
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
            
            dodajOdmor = new JButton("Dodaj Odmore");
	        add(dodajOdmor);
	        
	        dodajOdmor.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String input1 = JOptionPane.showInputDialog("Unesite ime promotera: ");
                    String input2 = JOptionPane.showInputDialog("Unesite prezime promotera:");
                    int index = tableModel.getIndexOfPromoterByName(input1, input2);

                    if (index != -1) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                            Date startDate = dateFormat.parse(JOptionPane.showInputDialog("Unesite datum pocetka odmora (dd.MM.yyyy): "));
                            Date endDate = dateFormat.parse(JOptionPane.showInputDialog("Unesite datum zavrsetka odmora (dd.MM.yyyy): "));

                            ArrayList<LocalDate> odmor = new ArrayList<>();
                            odmor.add(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            odmor.add(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                            tableModel.addOdmorForPromoter(index, odmor);

                            JOptionPane.showMessageDialog(null, "Odmor uspešno dodat.");
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(null, "Neispravan format datuma. Unesite validne datume.");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Greska prilikom dodavanja odmora.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Promoter nije pronađen.");
                    }
	            }
	        });
            	        
	        showOdmoriButton = new JButton("Prikaži Odmore");
	        add(showOdmoriButton);

	        showOdmoriButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String input1 = JOptionPane.showInputDialog("Unesite ime promotera: ");
                    String input2 = JOptionPane.showInputDialog("Unesite prezime promotera:");
                    int index = tableModel.getIndexOfPromoterByName(input1, input2);

                    if (index != -1) {
                        Object odmoriPromotera = tableModel.getValueAt(index, 5);
                        JOptionPane.showMessageDialog(null, "Promoter sa imenom i prezimenom: " + input1 + " " + input2 + " ima odmore: " + odmoriPromotera);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Promoter nije pronađen.");
                    }
	            }
	        });

	        obrisiOdmorButton = new JButton("Obrisi Odmor");
	        add(obrisiOdmorButton);
	        obrisiOdmorButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String input1 = JOptionPane.showInputDialog("Unesite ime promotera: ");
	                String input2 = JOptionPane.showInputDialog("Unesite prezime promotera:");
	                
	                int columnIndex = 5; 

	                int rowIndex = tableModel.getIndexOfPromoterByName(input1, input2);

	                if (rowIndex != -1) {
	                    ArrayList<ArrayList<LocalDate>> odmoriList = tableModel.getOdmoriListForPromoter(tableModel.getPromoterAt(rowIndex));

	                    if (odmoriList.isEmpty()) {
	                        JOptionPane.showMessageDialog(null, "Nema odmora za unesenog promotera.");
	                    } else {
	                        StringBuilder message = new StringBuilder("Odmori za promoter " + input1 + " " + input2 + ":\n");
	                        for (int i = 0; i < odmoriList.size(); i++) {
	                            message.append(i + 1).append(": ").append(odmoriList.get(i)).append("\n");
	                        }
	                        
	                        String deleteIndexInput = JOptionPane.showInputDialog(null, message.toString() + "\nUnesite redni broj odmora za brisanje:");

	                        if (deleteIndexInput != null && !deleteIndexInput.isEmpty()) {
	                            try {
	                                int deleteIndex = Integer.parseInt(deleteIndexInput);
	                                if (deleteIndex >= 1 && deleteIndex <= odmoriList.size()) {
	                                    odmoriList.remove(deleteIndex - 1);
	                                    JOptionPane.showMessageDialog(null, "Odmor uspešno obrisan.");
	                                } else {
	                                    JOptionPane.showMessageDialog(null, "Neispravan redni broj odmora za brisanje.");
	                                }
	                            } catch (NumberFormatException ex) {
	                                JOptionPane.showMessageDialog(null, "Neispravan unos za redni broj odmora za brisanje.");
	                            }
	                        }
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Promoter nije pronađen.");
	                }
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
		                if (line.trim().equals("$p")) {
		                    deleteMode = true;
		                } else if (line.trim().equals("$pot")) {
		                    deleteMode = false;
		                    fileContent.append("$p\n");
		                    
		                    for (Promoter promoter : tableModel.promoteri) {
		                    	String dataLine = String.format("%s|%s|%s|%.2f|%s|%s",
					            		promoter.getSifra(), promoter.getIme(), promoter.getPrezime(),
					            		promoter.getVisinaPlate(), promoter.getRadnoMesto(), promoter.getOdmoriList());

		                        fileContent.append(dataLine).append("\n");
		                    }
		                    fileContent.append("$pot\n");
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
	    
	    public void loadPromoterDataFromCSV() throws ParseException {
	        try {
	            BufferedReader csvReader = new BufferedReader(new FileReader("src/kol2_master/data/data.csv"));

	            String row;
	            while ((row = csvReader.readLine()) != null) {
	                String[] data = row.split("\\|");
	                if (data.length == 6) {
	                	String sifra = data[0];
	                    String ime = data[1];
	                    String prezime = data[2];
	                    double visinaPlate = Double.parseDouble(data[3]);
	                    String radnoMesto = data[4];
	                    
	                    String[] odmoriArray = data[5].split("\\]\\s*,\\s*\\["); // Split po '],['
	                    ArrayList<ArrayList<LocalDate>> odmoriList = new ArrayList<>();

	                    for (String odmoriStr : odmoriArray) {
	                        // Sklanjamo [] i spiltujemo po ,
	                        String[] dateStrings = odmoriStr.replaceAll("[\\[\\]]", "").split("\\s*,\\s*");

	                        ArrayList<LocalDate> odmoriDates = new ArrayList<>();
	                        for (String dateString : dateStrings) {
	                        	 if (!dateString.isEmpty()) {
	                                 LocalDate date = LocalDate.parse(dateString.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	                                 odmoriDates.add(date);
	                             }
	                        }

	                        odmoriList.add(odmoriDates);
	                    }
	                    Promoter promoter = new Promoter(sifra, ime, prezime, visinaPlate, odmoriList, radnoMesto);
	                    tableModel.addPromoter(promoter);
	                }
	            }
	            csvReader.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error loading data from CSV", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    
	    public Promoter getPromoter() {
	        String ime = imeField.getText();
	        String prezime = prezimeField.getText();
	        double visinaPlate = (double) visinaPlateSpinner.getValue();
	        String radnoMesto = radnoMestoField.getText();
	        
	        Promoter promoter = new Promoter(Promoter.generisiSifru(), ime, prezime, visinaPlate, new ArrayList<>(odmoriList), radnoMesto);
	        promoter.setOdmoriList(new ArrayList<>(odmoriList));	        
	        return promoter;
	    }

	    public void setTable(JTable table) {
	        this.table = table;
	    }
	    
}
