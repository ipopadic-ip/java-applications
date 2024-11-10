package kol2_master.model.users;

import java.time.LocalDate;
import java.util.ArrayList;

public class Promoter extends Zaposleni{
	
	private ArrayList<LocalDate>odmori;
	private ArrayList<ArrayList<LocalDate>> odmoriList;
	private String radnoMesto;

	public Promoter() {
		super();
	}
	
	public Promoter(String sifra, String ime, String prezime, double visinaPlate, ArrayList<ArrayList<LocalDate>> odmoriList, String radnoMesto) {
        super(sifra, ime, prezime, visinaPlate);
        this.odmoriList = odmoriList;
        this.radnoMesto = radnoMesto;
    }

	public ArrayList<LocalDate> getOdmori() {
		return odmori;
	}

	public void setOdmori(ArrayList<LocalDate> odmori) {
		this.odmori = odmori;
	}
	
	public ArrayList<ArrayList<LocalDate>> getOdmoriList() {
        return odmoriList;
    }

    public void setOdmoriList(ArrayList<ArrayList<LocalDate>> odmoriList) {
        this.odmoriList = odmoriList;
    }

	public String getRadnoMesto() {
		return radnoMesto;
	}

	public void setRadnoMesto(String radnoMesto) {
		this.radnoMesto = radnoMesto;
	}
	
	@Override
	public String toString() {
		return "Prodavac [odmori=" + odmori + ", radnoMesto=" + radnoMesto + "]" +super.toString();
	}
	
	public void addOdmor(ArrayList<LocalDate> odmor) {
	    this.odmoriList.add(odmor);
	}

	
	

}
