package kol2_master.model;

public abstract class Proizvod {
	private String naziv;
    private double cena;
    private String jedinicaMere;
    private String kategorija;
    private String proizvodjac;
    public Proizvod() {}
	public Proizvod(String naziv, double cena, String jedinicaMere, String kategorija, String proizvodjac) {
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.jedinicaMere = jedinicaMere;
		this.kategorija = kategorija;
		this.proizvodjac = proizvodjac;
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
	public String getJedinicaMere() {
		return jedinicaMere;
	}
	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}
	public String getKategorija() {
		return kategorija;
	}
	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}
	public String getProizvodjac() {
		return proizvodjac;
	}
	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}
}
