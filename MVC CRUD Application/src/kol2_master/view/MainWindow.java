package kol2_master.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
	
	private JTabbedPane tabbedPane;
	private TabelaPanel tabelePanel;
    private FormaPanel formePanel;
    
    public MainWindow() {
    	
        setLayout(new BorderLayout());

        tabelePanel = new TabelaPanel();
        formePanel = new FormaPanel();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tabele", tabelePanel);
        tabbedPane.addTab("Forme", formePanel);
        add(tabbedPane, BorderLayout.CENTER);

        setTitle("Aplikacija za Upravljanje Podacima");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);    	
    }
}
