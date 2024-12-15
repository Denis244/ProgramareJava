package Tema5.EX3;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

 class Mobilier {
    private String nume;
    private List<Placa> placi;

    public Mobilier(String nume, List<Placa> placi) {
        this.nume = nume;
        this.placi = placi;
    }

    public int getArieTotala() {
        return placi.stream().mapToInt(Placa::getArie).sum();
    }

    @Override
    public String toString() {
        return "Mobilier: " + nume + ", Plăci: " + placi;
    }
}

 class Placa {
    public enum Orientare { LUNGIME, LATIME, ORICARE }

    private String descriere;
    private int lungime;
    private int latime;
    private Orientare orientare;
    private boolean[] canturi = new boolean[4];
    private int nrBucati;

    // Constructor, gettere și settere
    public Placa(String descriere, int lungime, int latime, Orientare orientare, boolean[] canturi, int nrBucati) {
        this.descriere = descriere;
        this.lungime = lungime;
        this.latime = latime;
        this.orientare = orientare;
        this.canturi = canturi;
        this.nrBucati = nrBucati;
    }

    public int getArie() {
        return lungime * latime * nrBucati;
    }

    @Override
    public String toString() {
        return "Placa: " + descriere + ", Dimensiuni: " + lungime + "x" + latime + ", Nr bucăți: " + nrBucati;
    }
}

public class MobilierMain {
    public static void main(String[] args) throws IOException {
        // Citirea datelor din mobilier.json
        ObjectMapper mapper = new ObjectMapper();
        List<Mobilier> mobilier = mapper.readValue(new File("mobilier.json"), mapper.getTypeFactory().constructCollectionType(List.class, Mobilier.class));

        // Afișarea tuturor pieselor de mobilier
        for (Mobilier m : mobilier) {
            System.out.println(m);
        }

        // Afișarea estimativă a colilor de pal necesare pentru o piesă de mobilier
        Mobilier birou = mobilier.get(0); // Exemplu: prima piesă
        int arieColiPal = 2800 * 2070;
        System.out.println("Coli de pal necesare pentru " + birou + ": " + Math.ceil((double) birou.getArieTotala() / arieColiPal));
    }
}