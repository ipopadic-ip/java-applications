package Controller.UlazniServisi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CitacTabelaPobudeIzlaza {
    private String filePath;
    private Map<String, List<String[]>> tabele;

    public CitacTabelaPobudeIzlaza(String filePath) {
        this.filePath = filePath;
        this.tabele = new HashMap<>();
    }

    public Map<String, List<String[]>> ucitajPodatke() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String trenutnaTabela = null;
            List<String[]> podaci = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                if (!line.contains(",")) {
                    // Ovo je naziv nove tabele
                    if (trenutnaTabela != null && !podaci.isEmpty()) {
                        tabele.put(trenutnaTabela, new ArrayList<>(podaci));
                        podaci.clear();
                    }
                    trenutnaTabela = line.trim();
                } else {
                    podaci.add(line.split(","));
                }
            }

            if (trenutnaTabela != null && !podaci.isEmpty()) {
                tabele.put(trenutnaTabela, podaci);
            }
        }
        return tabele;
    }

    public List<String> getNaziviTabela() {
        return new ArrayList<>(tabele.keySet());
    }

    public List<String[]> getPodaciZaTabelu(String nazivTabele) {
        return tabele.getOrDefault(nazivTabele, new ArrayList<>());
    }
}

