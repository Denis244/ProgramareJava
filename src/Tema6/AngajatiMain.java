package Tema6;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

class Angajat {
    private String nume;
    private String functie;
    private double salariu;
    private LocalDate dataAngajarii;

    // Constructor
    public Angajat(String nume, String functie, double salariu, LocalDate dataAngajarii) {
        this.nume = nume;
        this.functie = functie;
        this.salariu = salariu;
        this.dataAngajarii = dataAngajarii;
    }

    // Gettere
    public String getNume() {
        return nume;
    }

    public String getFunctie() {
        return functie;
    }

    public double getSalariu() {
        return salariu;
    }

    public LocalDate getDataAngajarii() {
        return dataAngajarii;
    }

    // toString
    @Override
    public String toString() {
        return "Angajat{" +
                "nume='" + nume + '\'' +
                ", functie='" + functie + '\'' +
                ", salariu=" + salariu +
                ", dataAngajarii=" + dataAngajarii +
                '}';
    }
}


public class AngajatiMain {
    public static void main(String[] args) {
        // Lista de angajați
        var angajati = List.of(
                new Angajat("Ion Popescu", "director executiv", 5000, LocalDate.of(2022, 4, 10)),
                new Angajat("Maria Ionescu", "șef departament", 4000, LocalDate.of(2021, 6, 15)),
                new Angajat("Andrei Vasile", "analist", 2500, LocalDate.of(2020, 8, 5)),
                new Angajat("Elena Georgescu", "contabil", 2800, LocalDate.of(2019, 4, 20)),
                new Angajat("Cristian Tudor", "programator", 3500, LocalDate.of(2021, 7, 30))
        );

        // 1. Afișarea listei de angajați folosind referințe la metode
        System.out.println("1. Lista de angajați:");
        angajati.forEach(System.out::println);

        // 2. Afișarea angajaților cu salariul peste 2500 RON
        System.out.println("\n2. Angajați cu salariul peste 2500 RON:");
        angajati.stream()
                .filter(angajat -> angajat.getSalariu() > 2500)
                .forEach(System.out::println);

        // 3. Lista angajaților din aprilie anul trecut, cu funcție de conducere
        int anulCurent = Year.now().getValue();
        System.out.println("\n3. Angajați din aprilie anul trecut, cu funcție de conducere:");
        var conducere = angajati.stream()
                .filter(angajat -> angajat.getDataAngajarii().getYear() == anulCurent - 1 &&
                        angajat.getDataAngajarii().getMonthValue() == 4 &&
                        (angajat.getFunctie().contains("șef") || angajat.getFunctie().contains("director")))
                .collect(Collectors.toList());
        conducere.forEach(System.out::println);

        // 4. Angajați fără funcție de conducere, ordonați descrescător după salariu
        System.out.println("\n4. Angajați fără funcție de conducere, ordonați descrescător după salariu:");
        angajati.stream()
                .filter(angajat -> !(angajat.getFunctie().contains("șef") || angajat.getFunctie().contains("director")))
                .sorted((a1, a2) -> Double.compare(a2.getSalariu(), a1.getSalariu()))
                .forEach(System.out::println);

        // 5. Lista de nume ale angajaților scrise cu majuscule
        System.out.println("\n5. Numele angajaților cu majuscule:");
        var numeAngajati = angajati.stream()
                .map(angajat -> angajat.getNume().toUpperCase())
                .collect(Collectors.toList());
        numeAngajati.forEach(System.out::println);

        // 6. Afișarea salariilor mai mici de 3000 RON
        System.out.println("\n6. Salarii mai mici de 3000 RON:");
        angajati.stream()
                .filter(angajat -> angajat.getSalariu() < 3000)
                .map(Angajat::getSalariu)
                .forEach(System.out::println);

        // 7. Afișarea datelor primului angajat al firmei
        System.out.println("\n7. Primul angajat al firmei:");
        angajati.stream()
                .min(Comparator.comparing(Angajat::getDataAngajarii))
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Nu există angajați.")
                );


    }
}


