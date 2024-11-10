package kol2_master.model.products;

import java.util.Date;

public abstract class Proizvod {
	private String naziv;
    private double cena;
    private String model;
    private String jedinicaMere;
    private Date datumProizvodnje;
    
    public Proizvod() {}
    
	public Proizvod(String naziv, double cena, String model, String jedinicaMere, Date datumProizvodnje) {
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.model = model;
		this.jedinicaMere = jedinicaMere;
		this.datumProizvodnje = datumProizvodnje;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getJedinicaMere() {
		return jedinicaMere;
	}
	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}
	public Date getDatumProizvodnje() {
		return datumProizvodnje;
	}
	public void setDatumProizvodnje(Date datumProizvodnje) {
		this.datumProizvodnje = datumProizvodnje;
	}
}
