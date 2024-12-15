package Tema3;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class Produs {
    private String denumire;
    private double pret;
    private int cantitate;
    private LocalDate dataExpirare;
    private static double incasari = 0.0;

    public Produs(String denumire, double pret, int cantitate, LocalDate dataExpirare) {
        this.denumire = denumire;
        this.pret = pret;
        this.cantitate = cantitate;
        this.dataExpirare = dataExpirare;
    }

    public String getDenumire() {
        return denumire;
    }

    public double getPret() {
        return pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public LocalDate getDataExpirare() {
        return dataExpirare;
    }

    public static double getIncasari() {
        return incasari;
    }

    public static void adaugaIncasari(double suma) {
        incasari += suma;
    }

    @Override
    public String toString() {
        return String.format("Produs: %s, Pret: %.2f, Cantitate: %d, Data expirare: %s",
                denumire, pret, cantitate, dataExpirare);
    }
}

public class Main2 {
    public static void main(String[] args) throws IOException {
        List<Produs> produse = new ArrayList<>();

        // Citirea produselor din fisier
        List<String> lines = Files.readAllLines(Paths.get("produse.csv"));
        for (String line : lines) {
            String[] parts = line.split(",");
            String denumire = parts[0].trim();
            double pret = Double.parseDouble(parts[1].trim());
            int cantitate = Integer.parseInt(parts[2].trim());
            LocalDate dataExpirare = LocalDate.parse(parts[3].trim());

            produse.add(new Produs(denumire, pret, cantitate, dataExpirare));
        }

        Scanner scanner = new Scanner(System.in);
        int optiune;

        do {
            System.out.println("\nMeniu:");
            System.out.println("1. Afisare produse");
            System.out.println("2. Afisare produse expirate");
            System.out.println("3. Vanzare produs");
            System.out.println("4. Afisare produse cu pret minim");
            System.out.println("5. Salvare produse cu cantitate mica");
            System.out.println("6. Afisare incasari totale");
            System.out.println("0. Iesire");
            System.out.print("Alege o optiune: ");
            optiune = scanner.nextInt();
            scanner.nextLine(); // Consumare newline

            switch (optiune) {
                case 1:
                    // a) Afisare produse
                    produse.forEach(System.out::println);
                    break;
                case 2:
                    // b) Afisare produse expirate
                    LocalDate today = LocalDate.now();
                    produse.stream()
                            .filter(p -> p.getDataExpirare().isBefore(today))
                            .forEach(System.out::println);
                    break;
                case 3:
                    // c) Vanzare produs
                    System.out.print("Introduceti denumirea produsului: ");
                    String denumire = scanner.nextLine();
                    System.out.print("Introduceti cantitatea dorita: ");
                    int cantitateVanduta = scanner.nextInt();

                    Optional<Produs> produsOptional = produse.stream()
                            .filter(p -> p.getDenumire().equalsIgnoreCase(denumire))
                            .findFirst();

                    if (produsOptional.isPresent()) {
                        Produs produs = produsOptional.get();
                        if (produs.getCantitate() >= cantitateVanduta) {
                            produs.setCantitate(produs.getCantitate() - cantitateVanduta);
                            Produs.adaugaIncasari(produs.getPret() * cantitateVanduta);
                            System.out.println("Produs vandut cu succes.");
                            if (produs.getCantitate() == 0) {
                                produse.remove(produs);
                                System.out.println("Produsul a fost eliminat din lista deoarece cantitatea a ajuns la 0.");
                            }
                        } else {
                            System.out.println("Cantitate insuficienta pe stoc.");
                        }
                    } else {
                        System.out.println("Produsul nu exista.");
                    }
                    break;
                case 4:
                    // d) Afisare produse cu pret minim
                    double pretMinim = produse.stream()
                            .mapToDouble(Produs::getPret)
                            .min()
                            .orElse(Double.MAX_VALUE);

                    produse.stream()
                            .filter(p -> p.getPret() == pretMinim)
                            .forEach(System.out::println);
                    break;
                case 5:
                    // e) Salvare produse cu cantitate mica
                    System.out.print("Introduceti valoarea cantitatii: ");
                    int pragCantitate = scanner.nextInt();

                    List<Produs> produseCuCantitateMica = produse.stream()
                            .filter(p -> p.getCantitate() < pragCantitate)
                            .collect(Collectors.toList());

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("produse_cantitate_mica.txt"))) {
                        for (Produs produs : produseCuCantitateMica) {
                            writer.write(produs.toString());
                            writer.newLine();
                        }
                    }

                    System.out.println("Produsele au fost salvate in fisierul produse_cantitate_mica.txt.");
                    break;
                case 6:
                    // Afisare incasari totale
                    System.out.printf("Incasari totale: %.2f%n", Produs.getIncasari());
                    break;
                case 0:
                    System.out.println("Iesire din program.");
                    break;
                default:
                    System.out.println("Optiune invalida. Reincercati.");
            }
        } while (optiune != 0);

        scanner.close();
    }
}
