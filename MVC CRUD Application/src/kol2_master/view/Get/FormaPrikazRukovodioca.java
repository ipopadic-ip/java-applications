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
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import kol2_master.controller.Get.GetRukovodiocController;
import kol2_master.model.Rukovodilac;


public class FormaPrikazRukovodioca extends JFrame{
	 private JTable table;
	 private DefaultTableModel model;
	 private Color chosenColor = Color.YELLOW;
	 
	 public FormaPrikazRukovodioca() throws ParseException {
	        model = new DefaultTableModel();
	        model.addColumn("Šifra");
	        model.addColumn("Ime");
	        model.addColumn("Prezime");
	        model.addColumn("Visina plate");
	        model.addColumn("Naziv Zaduženja");
	        model.addColumn("Datum Zaduženja");

	        GetRukovodiocController getRukovodiocController = new GetRukovodiocController();
	        ArrayList<Rukovodilac> rukovodioci = getRukovodiocController.loadData("src/kol2_master/data/rukovodioci.txt", model);
	        
	        double suma = getRukovodiocController.izracunajSuma(rukovodioci);
	        double prosek = getRukovodiocController.izracunajProsek(rukovodioci);
	        double minVrednost = getRukovodiocController.nadjiMinVrednost(rukovodioci);
	        double maxVrednost = getRukovodiocController.nadjiMaxVrednost(rukovodioci);
	        model.addRow(new Object[]{"Suma", "Prosek", "Minimalna Vrednost", "Maksimalna Vrednost"});
	        model.addRow(new Object[]{suma, prosek, minVrednost, maxVrednost});
	        
	        table = new JTable(model);

	        JScrollPane scrollPane = new JScrollPane(table);
	        add(scrollPane, BorderLayout.CENTER);
	        
	        JPanel buttonPanel = new JPanel();
	        JButton colorButton = new JButton("Izaberite Boju");
	        colorButton.addActionListener(e -> chooseColor());
	        
	        JButton highlightButton = new JButton("Istaknite Redove");
	        highlightButton.addActionListener(e -> highlightRows(rukovodioci));
	        buttonPanel.add(highlightButton);
	        
	        buttonPanel.add(colorButton);
	        add(buttonPanel, BorderLayout.SOUTH);
	      
	        setTitle("Rukovodioci Tabela");
	        setSize(800, 400);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setVisible(true);
	    }
	 
	 	private void chooseColor() {
	        chosenColor = JColorChooser.showDialog(this, "Izaberite Boju", chosenColor);
	    }

	    private void highlightRows(ArrayList<Rukovodilac> rukovodioci) {
	    	 GetRukovodiocController getRukovodiocController = new GetRukovodiocController();

	    	    double prosek = getRukovodiocController.izracunajProsek(rukovodioci);
	    	    
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
