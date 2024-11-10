package kol2_master.model;

public class ElektricniUredjaj extends Proizvod{
	private String boja;
    private int nominalnaSnaga;
    private String dimenzije;
	public ElektricniUredjaj(String naziv, double cena, String jedinicaMere, String kategorija, String proizvodjac,
			String boja, int nominalnaSnaga, String dimenzije) {
		super(naziv, cena, jedinicaMere, kategorija, proizvodjac);
		this.boja = boja;
		this.nominalnaSnaga = nominalnaSnaga;
		this.dimenzije = dimenzije;
	}
	public ElektricniUredjaj() {}
	public String getBoja() {
		return boja;
	}
	public void setBoja(String boja) {
		this.boja = boja;
	}
	public int getNominalnaSnaga() {
		return nominalnaSnaga;
	}
	public void setNominalnaSnaga(int nominalnaSnaga) {
		this.nominalnaSnaga = nominalnaSnaga;
	}
	public String getDimenzije() {
		return dimenzije;
	}
	public void setDimenzije(String dimenzije) {
		this.dimenzije = dimenzije;
	}
}
