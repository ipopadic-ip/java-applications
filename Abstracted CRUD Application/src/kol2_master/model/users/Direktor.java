package kol2_master.model.users;

import java.util.Random;

public class Direktor extends Zaposleni {
	private double bonus;

	public Direktor() {
		super();
	}

	public Direktor(String sifra, String ime, String prezime, double visinaPlate, double bonus) {
		super(sifra, ime, prezime, visinaPlate);
		this.bonus = bonus;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	@Override
	public double getVisinaPlate() {
	        // Plata sa % bonusa
	        return super.getVisinaPlate() * (1 + bonus / 100);
	}
	
	public static String generisiSifru() {
        StringBuilder sifra = new StringBuilder();
        Random randomBroj = new Random();
        for (int i = 0; i < 6; i++) {
            int randomInt = randomBroj.nextInt(10);
            sifra.append(randomInt);
        }
        return sifra.toString();
    }
	 
}
