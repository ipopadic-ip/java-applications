package kol2_master.view.Get;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kol2_master.controller.Get.GetElektricniUredjajController;
import kol2_master.controller.Get.GetKvarljivProizvodController;
import kol2_master.model.ElektricniUredjaj;
import kol2_master.model.KvarljiviProizvod;

public class FormaPrikazKvarljivProizvod extends JFrame {
	 private JTable table;
	 private DefaultTableModel model;
	 private Color chosenColor = Color.YELLOW;
	 
	 public FormaPrikazKvarljivProizvod() throws ParseException {
	        model = new DefaultTableModel();
	        model.addColumn("Naiv");
	        model.addColumn("Cena");
	        model.addColumn("Jedinica Mere");
	        model.addColumn("Kategorija");
	        model.addColumn("Proizvođač");
	        model.addColumn("Rok Trajanja");
	        model.addColumn("Temperatura Skladištenja");

	        GetKvarljivProizvodController getKvarljivProizvodController = new GetKvarljivProizvodController();
	        ArrayList<KvarljiviProizvod> kvarljiviProizvodi = getKvarljivProizvodController.loadData("src/kol2_master/data/kvarljiviProizvodi.txt", model);
	        
	        double suma = getKvarljivProizvodController.izracunajSuma(kvarljiviProizvodi);
	        double prosek = getKvarljivProizvodController.izracunajProsek(kvarljiviProizvodi);
	        double minVrednost = getKvarljivProizvodController.nadjiMinVrednost(kvarljiviProizvodi);
	        double maxVrednost = getKvarljivProizvodController.nadjiMaxVrednost(kvarljiviProizvodi);
	        model.addRow(new Object[]{"Suma", "Prosek", "Minimalna Vrednost", "Maksimalna Vrednost"});
	        model.addRow(new Object[]{suma, prosek, minVrednost, maxVrednost});
	        
	        table = new JTable(model);

	        JScrollPane scrollPane = new JScrollPane(table);
	        add(scrollPane, BorderLayout.CENTER);
	        
	        JPanel buttonPanel = new JPanel();
	        JButton colorButton = new JButton("Izaberite Boju");
	        colorButton.addActionListener(e -> chooseColor());
	        
	        JButton highlightButton = new JButton("Istaknite Redove");
	        highlightButton.addActionListener(e -> highlightRows(kvarljiviProizvodi));
	        buttonPanel.add(highlightButton);
	        
	        buttonPanel.add(colorButton);
	        add(buttonPanel, BorderLayout.SOUTH);
	      
	        setTitle("Kvarljivi Proizvod Tabela");
	        setSize(900, 400);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setVisible(true);
	    }
	 
	 	private void chooseColor() {
	        chosenColor = JColorChooser.showDialog(this, "Izaberite Boju", chosenColor);
	    }

	    private void highlightRows(ArrayList<KvarljiviProizvod> kvarljiviProizvodi) {
	        	GetKvarljivProizvodController getKvarljivProizvodController = new GetKvarljivProizvodController();

	    	    double prosek = getKvarljivProizvodController.izracunajProsek(kvarljiviProizvodi);
	    	    
	    	    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
	    	        @Override
	    	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	    	                                                       boolean hasFocus, int row, int column) {
	    	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	    	            if (value instanceof Double) {
	    	                double cena = (double) value;
	    	                if (cena < prosek && row < model.getRowCount() - 1 && column < model.getColumnCount() - 2) {
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
