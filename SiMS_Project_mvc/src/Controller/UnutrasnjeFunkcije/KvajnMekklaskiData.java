package Controller.UnutrasnjeFunkcije;

import java.util.Arrays;

import Model.KomponentModel;

public class KvajnMekklaskiData {
	
	
	 public KomponentModel createKvajnMekklaskiData(String[] bitovi, String f) {
	        int implikanta = createImplikanta(bitovi);
	        int grupa = createGrupa(bitovi);
	        return new KomponentModel(bitovi, f, implikanta, grupa);
	    }

	    private int createImplikanta(String[] bitovi) {
	        int implikanta = 0;
	        for (int i = 0; i < bitovi.length; i++) {
	            if (bitovi[i].equals("1")) {
	                implikanta += Math.pow(2, (bitovi.length - 1 - i));
	            }
	        }
	        return implikanta;
	    }

	    private int createGrupa(String[] bitovi) {
	        int grupa = 0;
	        for (String bit : bitovi) {
	            if (bit.equals("1")) {
	                grupa++;
	            }
	        }
	        return grupa;
	    }
}
