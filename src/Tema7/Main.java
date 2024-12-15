package Tema7;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

abstract class InstrumentMuzical {
    private String nume;
    private double pret;

    public InstrumentMuzical(String nume, double pret) {
        this.nume = nume;
        this.pret = pret;
    }

    public String getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }

    public abstract void afiseazaDetalii();
}

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
class Chitara extends InstrumentMuzical {
    private int numarCorzi;

    public Chitara(String nume, double pret, int numarCorzi) {
        super(nume, pret);
        this.numarCorzi = numarCorzi;
    }

    public int getNumarCorzi() {
        return numarCorzi;
    }

    @Override
    public void afiseazaDetalii() {
        System.out.println("Chitara: " + getNume() + ", Pret: " + getPret() + " RON, Numar Corzi: " + numarCorzi);
    }
}

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
class Toba extends InstrumentMuzical {
    private int numarTobe;

    public Toba(String nume, double pret, int numarTobe) {
        super(nume, pret);
        this.numarTobe = numarTobe;
    }

    public int getNumarTobe() {
        return numarTobe;
    }

    @Override
    public void afiseazaDetalii() {
        System.out.println("Toba: " + getNume() + ", Pret: " + getPret() + " RON, Numar Tobe: " + numarTobe);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Creează colecția de instrumente muzicale
        Set<InstrumentMuzical> instrumente = new HashSet<>();
        instrumente.add(new Chitara("Chitara1", 2500, 6));
        instrumente.add(new Chitara("Chitara2", 3200, 7));
        instrumente.add(new Chitara("Chitara3", 1500, 6));
        instrumente.add(new Toba("Toba1", 2000, 3));
        instrumente.add(new Toba("Toba2", 1500, 4));
        instrumente.add(new Toba("Toba3", 1800, 2));

        // 2. Salvează colecția în fișierul instrumente.json
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
        mapper.writeValue(new File("instrumente.json"), instrumente);

        // 3. Încarcă datele din fișierul instrumente.json
        Set<InstrumentMuzical> instrumenteDinFisier = mapper.readValue(new File("instrumente.json"),
                TypeFactory.defaultInstance().constructCollectionType(Set.class, InstrumentMuzical.class));

        // 4. Afișează implementarea utilizată de ObjectMapper pentru Set
        System.out.println("Implementarea Set utilizată de ObjectMapper: " + instrumenteDinFisier.getClass().getName());

        // 5. Verifică dacă colecția Set permite duplicate
        InstrumentMuzical instrumentNou = new Chitara("Chitara1", 2500, 6);
        boolean adaugat = instrumenteDinFisier.add(instrumentNou);
        if (!adaugat) {
            System.out.println("Instrumentul nu a fost adăugat (este duplicat).");
        }

        // 6. Șterge instrumentele cu preț mai mare de 3000 RON
        instrumenteDinFisier.removeIf(instrument -> instrument.getPret() > 3000);

        // 7. Afișează chitarele utilizând Stream API și instanceof
        System.out.println("\nChitare:");
        instrumenteDinFisier.stream()
                .filter(instrument -> instrument instanceof Chitara)
                .forEach(instrument -> instrument.afiseazaDetalii());

        // 8. Afișează tobele utilizând Stream API și getClass()
        System.out.println("\nTobe:");
        instrumenteDinFisier.stream()
                .filter(instrument -> instrument.getClass().equals(Toba.class))
                .forEach(instrument -> instrument.afiseazaDetalii());


    }
}
