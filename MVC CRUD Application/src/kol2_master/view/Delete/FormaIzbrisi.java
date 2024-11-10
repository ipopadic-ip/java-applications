package kol2_master.view.Delete;

import javax.swing.*;

import kol2_master.controller.Del.DeleteController;
import kol2_master.controller.Del.DeleteControllerProizvod;
import kol2_master.controller.Del.DeletePromoterController;
import kol2_master.controller.Post.ElektricniUredjajController;
import kol2_master.controller.Put.IzmeniElektricniUredjajController;
import kol2_master.model.ElektricniUredjaj;
import kol2_master.model.KvarljiviProizvod;
import kol2_master.model.Promoter;
import kol2_master.model.Rukovodilac;
import kol2_master.view.Post.DodajPromotera;
import kol2_master.view.Post.FormaDodajElekricniUredjaj;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormaIzbrisi extends JFrame{
	private JButton button;
	private JLabel sifraLabel;
	public FormaIzbrisi(String entitet) {
        
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        
        
        
        JPanel buttonPanel = new JPanel();
        if (entitet.equals("Promoter")) {
            setTitle("Forma za Brisanje Promotera");
            centerPanel.add(sifraLabel = new JLabel("Unesite sifru Promotera:"));
        } else if (entitet.equals("Rukovodilac")) {
            setTitle("Forma za Brisanje Rukovodioca");
            centerPanel.add(sifraLabel = new JLabel("Unesite sifru Rukovodioca:"));
        } else if (entitet.equals("KvarljiviProizvod")) {
            setTitle("Forma za Brisanje Kvarljivog Proizvoda");
            centerPanel.add(sifraLabel = new JLabel("Unesite naziv Kvarljivog Proizvoda:"));
        }else if (entitet.equals("ElektricniUredjaji")) {
            setTitle("Forma za Brisanje Elektricnog Uredjaja");
            centerPanel.add(sifraLabel = new JLabel("Unesite naziv Elektricnog Uredjaja:"));
        }
        
        JTextField textField = new JTextField(25);
        centerPanel.add(textField);
        buttonPanel.add(button = new JButton("Obriši"));
        panel.add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	DeleteController deleteController = new DeleteController();
            	DeleteControllerProizvod deleteControllerProizvod = new DeleteControllerProizvod();
            	switch (entitet) {
                case "Promoter":
                	DeletePromoterController deletePromoterController = new DeletePromoterController();
                    Promoter promoter = new Promoter();
                    promoter.setSifra(textField.getText());
                    ArrayList<Promoter> promoteriLista = new ArrayList<Promoter>();
                    promoteriLista = deletePromoterController.loadPromoterData("src/kol2_master/data/promoteri.txt");
                    deletePromoterController.izbrisiPodatke(FormaIzbrisi.this, promoteriLista, promoter);
                    break;
                case "Rukovodilac":
                    Rukovodilac rukovodilac = new Rukovodilac();
                    rukovodilac.setSifra(textField.getText());
                    deleteController.izbrisiPodatke(FormaIzbrisi.this, "src/kol2_master/data/rukovodioci.txt", rukovodilac);
                    break;
                case "KvarljiviProizvod":
                    KvarljiviProizvod kvarljiviProizvod = new KvarljiviProizvod();
                    kvarljiviProizvod.setNaziv(textField.getText());
                    deleteControllerProizvod.izbrisiPodatke(FormaIzbrisi.this, "src/kol2_master/data/kvarljiviProizvodi.txt", kvarljiviProizvod, null);
                    break;
                case "ElektricniUredjaji":
                    ElektricniUredjaj elektricniUredjaj = new ElektricniUredjaj();
                    elektricniUredjaj.setNaziv(textField.getText());
                    deleteControllerProizvod.izbrisiPodatke(FormaIzbrisi.this, "src/kol2_master/data/elektricniUredjaji.txt", null, elektricniUredjaj);
                    break;
                default:
                    break;
            }
            }
        });
	}
	
}
