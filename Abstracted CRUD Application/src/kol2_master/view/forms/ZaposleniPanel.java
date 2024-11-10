package kol2_master.view.forms;

import javax.swing.*;
import java.awt.*;

public class ZaposleniPanel extends JPanel{
	private static final long serialVersionUID = -2109840679633490976L;
	
	protected JTextField sifraField;
    protected JTextField imeField;
    protected JTextField prezimeField;
    protected JSpinner visinaPlateSpinner;
    protected JButton addButton;

    public ZaposleniPanel() {
        init();
    }

    protected void init() {
        setLayout(new GridLayout(0, 1));

        sifraField = new JTextField(10);
        imeField = new JTextField(10);
        prezimeField = new JTextField(10);
        
        add(new JLabel("Ime:"));
        add(imeField);
        add(new JLabel("Prezime:"));
        add(prezimeField);
        add(new JLabel("Visina plate:"));
        SpinnerNumberModel visinaPlateModel = new SpinnerNumberModel(0.0, 0.0, 99999999.9, 0.1);
        visinaPlateSpinner = new JSpinner(visinaPlateModel);
        add(visinaPlateSpinner);
    }
}
