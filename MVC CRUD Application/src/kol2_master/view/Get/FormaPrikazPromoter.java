package kol2_master.view.Get;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kol2_master.controller.Get.GetPromoterController;
import kol2_master.model.Promoter;

public class FormaPrikazPromoter extends JFrame {
	 private JTable table;
	 private DefaultTableModel model;
	 private Color chosenColor = Color.YELLOW;
	 
	 public FormaPrikazPromoter() {
	        model = new DefaultTableModel();
	        model.addColumn("Šifra");
	        model.addColumn("Ime");
	        model.addColumn("Prezime");
	        model.addColumn("Visina plate");
	        model.addColumn("Primedbe");
	        model.addColumn("Nadređeni");

	        GetPromoterController getPromoterController = new GetPromoterController();
	        ArrayList<Promoter> promoteri = getPromoterController.loadPromoterData("src/kol2_master/data/promoteri.txt", model);
	        
	        double suma = getPromoterController.izracunajSuma(promoteri);
	        double prosek = getPromoterController.izracunajProsek(promoteri);
	        double minVrednost = getPromoterController.nadjiMinVrednost(promoteri);
	        double maxVrednost = getPromoterController.nadjiMaxVrednost(promoteri);
	        model.addRow(new Object[]{"Suma", "Prosek", "Minimalna Vrednost", "Maksimalna Vrednost"});
	        model.addRow(new Object[]{suma, prosek, minVrednost, maxVrednost});
	        
	        table = new JTable(model);

	        JScrollPane scrollPane = new JScrollPane(table);
	        add(scrollPane, BorderLayout.CENTER);
	        
	        JPanel buttonPanel = new JPanel();
	        JButton colorButton = new JButton("Izaberite Boju");
	        colorButton.addActionListener(e -> chooseColor());
	        
	        JButton highlightButton = new JButton("Istaknite Redove");
	        highlightButton.addActionListener(e -> highlightRows(promoteri));
	        buttonPanel.add(highlightButton);
	        
	        buttonPanel.add(colorButton);
	        add(buttonPanel, BorderLayout.SOUTH);
	      
	        setTitle("Promoter Tabela");
	        setSize(800, 400);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setVisible(true);
	    }
	 
	 	private void chooseColor() {
	        chosenColor = JColorChooser.showDialog(this, "Izaberite Boju", chosenColor);
	    }

	    private void highlightRows(ArrayList<Promoter> promoteri) {
	    	 GetPromoterController getPromoterController = new GetPromoterController();

	    	    double prosek = getPromoterController.izracunajProsek(promoteri);
	    	    
	    	    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
	    	        @Override
	    	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	    	                                                       boolean hasFocus, int row, int column) {
	    	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	    	            if (value instanceof Double) {
	    	                double visinaPlate = (double) value;
	    	                if (visinaPlate < prosek && row < model.getRowCount() - 1) {
	    	                    c.setBackground(chosenColor);
	    	                } else {
	    	                    c.setBackground(table.getBackground());
	    	                }
	    	            } else {
	    	                c.setBackground(table.getBackground());
	    	            }

	    	            c.setForeground(table.getForeground());

	    	            return c;
	    	        }
	    	    };

	    	    for (int col = 0; col < model.getColumnCount(); col++) {
	    	        table.getColumnModel().getColumn(col).setCellRenderer(cellRenderer);
	    	    }
	    	    table.repaint();
	    }
}
