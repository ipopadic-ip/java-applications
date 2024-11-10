package kol2_master.model;

public class Promoter extends Zaposleni {
	private String primedbe;
    private String nadredjeni;
    public Promoter() {}
	public Promoter(String sifra, String ime, String prezime, double visinaPlate, String primedbe, String nadredjeni) {
		super(sifra, ime, prezime, visinaPlate);
		this.primedbe = primedbe;
        this.nadredjeni = nadredjeni;
	}
	public String getPrimedbe() {
		return primedbe;
	}
	public void setPrimedbe(String primedbe) {
		this.primedbe = primedbe;
	}
	public String getNadredjeni() {
		return nadredjeni;
	}
	public void setNadredjeni(String nadredjeni) {
		this.nadredjeni = nadredjeni;
	}
	 public String formatForFile() {
	        return getSifra() + "|" + getIme() + "|" + getPrezime() + "|" + getVisinaPlate() + "|" + primedbe + "|" + nadredjeni;
	    }

}
