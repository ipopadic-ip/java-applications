package kol2_master.model;

import java.util.Date;

public class KvarljiviProizvod extends Proizvod {
	private Date rokTrajanja;
    private double temperaturaSkladistenja;
    public KvarljiviProizvod() {}
	public KvarljiviProizvod(String naziv, double cena, String jedinicaMere, String kategorija, String proizvodjac,
			Date rokTrajanja, double temperaturaSkladistenja) {
		super(naziv, cena, jedinicaMere, kategorija, proizvodjac);
		this.rokTrajanja = rokTrajanja;
		this.temperaturaSkladistenja = temperaturaSkladistenja;
	}
	public Date getRokTrajanja() {
		return rokTrajanja;
	}
	public void setRokTrajanja(Date rokTrajanja) {
		this.rokTrajanja = rokTrajanja;
	}
	public double getTemperaturaSkladistenja() {
		return temperaturaSkladistenja;
	}
	public void setTemperaturaSkladistenja(double temperaturaSkladistenja) {
		this.temperaturaSkladistenja = temperaturaSkladistenja;
	}
    
}
