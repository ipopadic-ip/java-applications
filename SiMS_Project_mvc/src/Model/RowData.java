//package Model;
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
//public class RowData {
//    private final StringProperty[] bitovi;
//    private final StringProperty f;
//
//    public RowData(String[] bitovi, String f) {
//        this.bitovi = new StringProperty[bitovi.length];
//        for (int i = 0; i < bitovi.length; i++) {
//            this.bitovi[i] = new SimpleStringProperty(bitovi[i]);
//        }
//        this.f = new SimpleStringProperty(f);
//    }
//
//    public String[] getBitovi() {
//        String[] bitoviArray = new String[bitovi.length];
//        for (int i = 0; i < bitovi.length; i++) {
//            bitoviArray[i] = bitovi[i].get();
//        }
//        return bitoviArray;
//    }
//
//    public StringProperty bitProperty(int index) {
//        return bitovi[index];
//    }
//
//    public String getF() {
//        return f.get();
//    }
//
//    public StringProperty fProperty() {
//        return f;
//    }
//
//    public void setF(String f) {
//        this.f.set(f);
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder("RowData{bitovi=");
//        for (StringProperty bit : bitovi) {
//            sb.append(bit.get()).append(",");
//        }
//        sb.append("f=").append(f.get()).append("}");
//        return sb.toString();
//    }
//}
package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RowData {
    private final StringProperty[] bitovi;
    private final StringProperty f;

    public RowData(String[] bitovi, String f) {
        this.bitovi = new StringProperty[bitovi.length];
        for (int i = 0; i < bitovi.length; i++) {
            this.bitovi[i] = new SimpleStringProperty(bitovi[i]);
        }
        this.f = new SimpleStringProperty(f);
    }

    public String[] getBitovi() {
        String[] bitoviArray = new String[bitovi.length];
        for (int i = 0; i < bitovi.length; i++) {
            bitoviArray[i] = bitovi[i].get();
        }
        return bitoviArray;
    }

    public StringProperty bitProperty(int index) {
        return bitovi[index];
    }

    public void setBit(int index, String value) {
        this.bitovi[index].set(value);
    }

    public String getF() {
        return f.get();
    }

    public StringProperty fProperty() {
        return f;
    }

    public void setF(String f) {
        this.f.set(f);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RowData{bitovi=");
        for (StringProperty bit : bitovi) {
            sb.append(bit.get()).append(",");
        }
        sb.append("f=").append(f.get()).append("}");
        return sb.toString();
    }
}

