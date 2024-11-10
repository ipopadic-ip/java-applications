package kol2_master.view.forms;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ProizvodPanel extends JPanel{
	private static final long serialVersionUID = -7367021704414475227L;
		protected JTextField nazivProizvodaField;
	    protected JTextField cenaProizvodaField;
	    protected JTextField modelProizvodaField;
	    protected JTextField jedinicaMereField;
	    protected JSpinner datumProizvodnjeSpinner, cenaProizvodaSpinner;
	    protected JButton addButton;
	    
	    public ProizvodPanel() {
	        init();
	    }

	    protected void init() {
	        setLayout(new GridLayout(0, 2));

	        nazivProizvodaField = new JTextField(10);
	        cenaProizvodaField = new JTextField(10);
	        modelProizvodaField = new JTextField(10);
	        jedinicaMereField = new JTextField(10);
	        
	        SpinnerDateModel dateModel = new SpinnerDateModel();
	        datumProizvodnjeSpinner = new JSpinner(dateModel);
	        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(datumProizvodnjeSpinner, "dd.MM.yyyy");
	        datumProizvodnjeSpinner.setEditor(dateEditor);
	        
	        SpinnerNumberModel cenaPlateModel = new SpinnerNumberModel(0.0, 0.0, 99999999.9, 0.1);
	        cenaProizvodaSpinner = new JSpinner(cenaPlateModel);

	        add(new JLabel("Naziv proizvoda:"));
	        add(nazivProizvodaField);
	        add(new JLabel("Cena proizvoda:"));
	        add(cenaProizvodaSpinner);
	        add(new JLabel("Model proizvoda:"));
	        add(modelProizvodaField);
	        add(new JLabel("Jedinica mere:"));
	        add(jedinicaMereField);
	        add(new JLabel("Datum proizvodnje:"));
	        add(datumProizvodnjeSpinner);
	    }
	    
}
