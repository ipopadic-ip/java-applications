package kol2_master.model.products;

import java.util.Date;

public class TehnickiProizvod extends Proizvod {
	private String boja;
    private double nominalnaSnaga;
    private double radniNapon;

    public TehnickiProizvod() {
        // Default constructor
    }

    public TehnickiProizvod(String naziv, double cena, String model, String jedinicaMere, Date datumProizvodnje,
                            String boja, double nominalnaSnaga, double radniNapon) {
        super(naziv, cena, model, jedinicaMere, datumProizvodnje);
        this.boja = boja;
        this.nominalnaSnaga = nominalnaSnaga;
        this.radniNapon = radniNapon;
    }

	public String getBoja() {
		return boja;
	}

	public void setBoja(String boja) {
		this.boja = boja;
	}

	public double getNominalnaSnaga() {
		return nominalnaSnaga;
	}

	public void setNominalnaSnaga(double nominalnaSnaga) {
		this.nominalnaSnaga = nominalnaSnaga;
	}

	public double getRadniNapon() {
		return radniNapon;
	}

	public void setRadniNapon(double radniNapon) {
		this.radniNapon = radniNapon;
	}
    
}
