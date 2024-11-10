package kol2_master.model.products;

import java.util.Date;

public class ProizvodOgranicenogTrajanja extends Proizvod {
	
	private Date rokTrajanja;
    private double temperaturaSkladistenja;

	public ProizvodOgranicenogTrajanja() {
		super();
	}

	public ProizvodOgranicenogTrajanja(String naziv, double cena, String model, String jedinicaMere,
			Date datumProizvodnje, Date rokTrajanja, double temperaturaSkladistenja) {
		super(naziv, cena, model, jedinicaMere, datumProizvodnje);
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
