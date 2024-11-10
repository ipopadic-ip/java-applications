package Model;

import java.util.Arrays;

public class KomponentModel {
	 	private final String[] bitovi;
	    private final String f;
	    private final int implikanta;
	    private final int grupa;

	    public KomponentModel(String[] bitovi, String f, int implikanta, int grupa) {
	        this.bitovi = bitovi;
	        this.f = f;
	        this.implikanta = implikanta;
	        this.grupa = grupa;
	    }
	    

	    public KomponentModel() {
			this.bitovi = null;
			this.f = "";
			this.implikanta = 0;
			this.grupa = 0;
		}


		public String[] getBitovi() { return bitovi; }
	    public String getF() { return f; }
	    public int getImplikanta() { return implikanta; }
	    public int getGrupa() { return grupa; }

	    @Override
	    public String toString() {
	        return "KvajnMekklaskiDataModal{bitovi=" + Arrays.toString(bitovi) + ", f=" + f + ", implikanta=" + implikanta + ", grupa=" + grupa + "}";
	    }
}
