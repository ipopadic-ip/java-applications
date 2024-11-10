package kol2_master.model;

import java.util.ArrayList;


public class Rukovodilac extends Zaposleni {
	private ArrayList<Zaduzenje> zaduzenja;

	public Rukovodilac(String sifra, String ime, String prezime, double visinaPlate, ArrayList<Zaduzenje> zaduzenja) {
		super(sifra, ime, prezime, visinaPlate);
		this.zaduzenja = new ArrayList<>();
	}
	
	public Rukovodilac() {}

	public ArrayList<Zaduzenje> getZaduzenja() {
		return zaduzenja;
	}

	public void setZaduzenja(ArrayList<Zaduzenje> zaduzenja) {
		this.zaduzenja = zaduzenja;
	}	
}
