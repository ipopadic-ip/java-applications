package kol2_master.view;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import kol2_master.view.forms.DirektorPanel;
import kol2_master.view.forms.ProizvodOgranicenogTrajanjaPanel;
import kol2_master.view.forms.PromoterPanel;
import kol2_master.view.forms.TehnickiProizvodPanel;
import kol2_master.view.tables.DirektorTableModel;
import kol2_master.view.tables.ProizvodOgranicenogTrajanjaTableModel;
import kol2_master.view.tables.PromoterTableModel;
import kol2_master.view.tables.TehnickiProizvodTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class MainWindow {
	private JFrame frame;
	public JSpinner minValueSpinner, maxValueSpinner;
	private ProizvodOgranicenogTrajanjaTableModel proizvodOgranicenogTrajanjaTableModel;
	private DirektorTableModel direktorTableModel;
	private TehnickiProizvodTableModel tehnickiProizvodTableModel;
	private PromoterTableModel promoterTableModel;

    public MainWindow() {
        init();
    }

    private void init() {
        frame = new JFrame("Aplikacija za upravljanje prodavnicama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Direktor", createTabPanel("Direktor"));
        tabbedPane.addTab("Promoter", createTabPanel("Promoter"));
        tabbedPane.addTab("Tehnicki Proizvod", createTabPanel("Tehnicki Proizvod"));
        tabbedPane.addTab("Proizvod Ogranicenog Trajanja", createTabPanel("Proizvod Ogranicenog Trajanja"));

        frame.getContentPane().add(tabbedPane);
        frame.setLocationRelativeTo(null);
    }

    private JPanel createTabPanel(String tabName) {
    	
        JPanel tabPanel = new JPanel(new BorderLayout());

        // Levi Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.7), 0));

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150, 30));
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        leftPanel.add(searchPanel, BorderLayout.NORTH);
                
        JButton searchByValueRangeButton = new JButton("Pretraga po opsegu vrednosti");
        searchPanel.add(searchByValueRangeButton);
        
        tabPanel.add(leftPanel, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        direktorTableModel = new DirektorTableModel();
        DirektorPanel direktorPanel = new DirektorPanel(direktorTableModel);
        direktorPanel.initializePanel();
        JTable table1 = new JTable(direktorTableModel);
        direktorPanel.setTable(table1);
        JScrollPane tableScrollPane = new JScrollPane(table1);      
                
        proizvodOgranicenogTrajanjaTableModel = new ProizvodOgranicenogTrajanjaTableModel();
        ProizvodOgranicenogTrajanjaPanel proizvodOgranicenogTrajanjaPanel = new ProizvodOgranicenogTrajanjaPanel(proizvodOgranicenogTrajanjaTableModel);
        try {
			proizvodOgranicenogTrajanjaPanel.initializePanel();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        JTable tabelaOgranicenogTrajanja = new JTable(proizvodOgranicenogTrajanjaTableModel);
        proizvodOgranicenogTrajanjaPanel.setTable(tabelaOgranicenogTrajanja);
        JScrollPane tableScrollPane1 = new JScrollPane(tabelaOgranicenogTrajanja);
        
        tehnickiProizvodTableModel = new TehnickiProizvodTableModel();
        TehnickiProizvodPanel tehnickiProizvodPanel = new TehnickiProizvodPanel(tehnickiProizvodTableModel);
        try {
        	tehnickiProizvodPanel.initializePanel();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        JTable tabelaTehnickihProizvoda = new JTable(tehnickiProizvodTableModel);
        tehnickiProizvodPanel.setTable(tabelaTehnickihProizvoda);
        JScrollPane tableScrollPane2 = new JScrollPane(tabelaTehnickihProizvoda);
        
        promoterTableModel = new PromoterTableModel();
        PromoterPanel promoterPanel = new PromoterPanel(promoterTableModel);
        try {
            promoterPanel.loadPromoterDataFromCSV();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        JTable tabelaPromotera = new JTable(promoterTableModel);
        promoterPanel.setTable(tabelaPromotera);
        JScrollPane tableScrollPanePromoter = new JScrollPane(tabelaPromotera);

        JPanel specificInputsPanel = new JPanel();
        if ("Direktor".equals(tabName)) {
            specificInputsPanel.add(new DirektorPanel(direktorTableModel));
            leftPanel.add(tableScrollPane, BorderLayout.CENTER);
        } else if ("Promoter".equals(tabName)) {
            specificInputsPanel.add(new PromoterPanel(promoterTableModel));
            leftPanel.add(tableScrollPanePromoter, BorderLayout.CENTER);
        } else if ("Tehnicki Proizvod".equals(tabName)) {
            specificInputsPanel.add(new TehnickiProizvodPanel(tehnickiProizvodTableModel));
            leftPanel.add(tableScrollPane2, BorderLayout.CENTER);
        } else if ("Proizvod Ogranicenog Trajanja".equals(tabName)) {
            specificInputsPanel.add(new ProizvodOgranicenogTrajanjaPanel(proizvodOgranicenogTrajanjaTableModel));
            leftPanel.add(tableScrollPane1, BorderLayout.CENTER);
        }
        
        rightPanel.add(specificInputsPanel, BorderLayout.CENTER);
        
        tabPanel.add(rightPanel, BorderLayout.CENTER);
                
        searchByValueRangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	
                	String input1 = JOptionPane.showInputDialog("Unesite prvu (minimalnu) vrednost: ");
                    String input2 = JOptionPane.showInputDialog("Unesite drugu (maksimalnu) vrednost:");

                    double minValue, maxValue;

                    try {
                    	minValue = Double.parseDouble(input1);
                        maxValue = Double.parseDouble(input2);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Molimo Vas unesite broj.");
                        return;
                    }
                    
                    if ("Direktor".equals(tabName)) {
                    	applyPriceRangeFilter(table1, minValue, maxValue);
                    } else if ("Promoter".equals(tabName)) {
                    	applyPriceRangeFilter(tabelaPromotera, minValue, maxValue);
                    } else if ("Tehnicki Proizvod".equals(tabName)) {
                    	applyPriceRangeFilterProizvodi(tabelaTehnickihProizvoda, minValue, maxValue);
                    } else if ("Proizvod Ogranicenog Trajanja".equals(tabName)) {
                        applyPriceRangeFilterProizvodi(tabelaOgranicenogTrajanja, minValue, maxValue);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Molimo Vas, unesite brojeve.");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String searchText = searchField.getText().toLowerCase();
                        
                        if ("Direktor".equals(tabName)) {
                            applySorting(table1, searchText);
                            ((AbstractTableModel) table1.getModel()).fireTableDataChanged();
                        } else if ("Promoter".equals(tabName)) {
                            applySorting(tabelaPromotera, searchText);
                        } else if ("Tehnicki Proizvod".equals(tabName)) {
                            applySorting(tabelaTehnickihProizvoda, searchText);
                        } else if ("Proizvod Ogranicenog Trajanja".equals(tabName)) {
                            applySorting(tabelaOgranicenogTrajanja, searchText);
                        }
                    }
                });
            }
        });
        return tabPanel;
    }    
      
    private void applyPriceRangeFilter(JTable table, double minValue, double maxValue) {
    	RowFilter<TableModel, Object> rowFilter = new RowFilter<TableModel, Object>() {
    	    @Override
    	    public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
    	        try {
    	            double numericValue = ((Number) entry.getValue(3)).doubleValue();
    	            return numericValue >= minValue && numericValue <= maxValue;
    	        } catch (Exception e) {
    	            return false;
    	        }
    	    }
    	};
    	TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
    	sorter.setRowFilter(rowFilter);
    	table.setRowSorter(sorter);

    }
    
    private void applyPriceRangeFilterProizvodi(JTable table, double minValue, double maxValue) {
    	RowFilter<TableModel, Object> rowFilter = new RowFilter<TableModel, Object>() {
    	    @Override
    	    public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
    	        try {
    	            double numericValue = ((Number) entry.getValue(1)).doubleValue();
    	            return numericValue >= minValue && numericValue <= maxValue;
    	        } catch (Exception e) {
    	            return false;
    	        }
    	    }
    	};

    	// Kreiramo TableRowSorter za model tabele
    	TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
    	sorter.setRowFilter(rowFilter);

    	// Postavljamo TableRowSorter
    	table.setRowSorter(sorter);

    }


 // Metod za primenu filtriranja redova u tabeli na osnovu unetog teksta za pretragu
    private void applySorting(JTable table, String searchText) {
    	
    	  RowFilter<TableModel, Object> rowFilter = new RowFilter<TableModel, Object>() {
              @Override
              public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
                  // Iteracija kroz vrednosti ćelija u trenutnom redu
                  for (int i = 0; i < entry.getValueCount(); i++) {
                      if (entry.getStringValue(i).toLowerCase().contains(searchText)) {
                          return true;
                      }
                  }
                  return false;
              }
          };

          // Kreiranje TableRowSorter objekta koji će omogućiti sortiranje i filtriranje redova u tabeli
          TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
          sorter.setRowFilter(rowFilter); // Postavljanje definisanog RowFiltera na sorter

          // Postavljanje TableRowSorter objekta na JTable
          table.setRowSorter(sorter);
    }

    public void show() {
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
