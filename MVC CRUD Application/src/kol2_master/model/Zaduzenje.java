package kol2_master.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Zaduzenje {
	
	private String naziv;
    private Date datumIzvrsenja;
    public Zaduzenje() {}
	public Zaduzenje(String naziv, Date datumIzvrsenja) {
		super();
		this.naziv = naziv;
		this.datumIzvrsenja = datumIzvrsenja;
	}
	
	 public Zaduzenje(String naziv, String datumIzvrsenja) {
	        this.naziv = naziv;

	        if ("Nije realizovano".equals(datumIzvrsenja)) {
	            this.datumIzvrsenja = null;
	        } else {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	            try {
	                this.datumIzvrsenja = dateFormat.parse(datumIzvrsenja);
	            } catch (ParseException e) {
	                e.printStackTrace();
	                this.datumIzvrsenja = null;
	            }
	        }
	    }
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Date getDatumIzvrsenja() {
		return datumIzvrsenja;
	}
	public void setDatumIzvrsenja(Date datumIzvrsenja) {
		this.datumIzvrsenja = datumIzvrsenja;
	}
}
