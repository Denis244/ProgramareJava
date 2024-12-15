package Tema2;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    // 1. Sortarea județelor și căutare binară
    public static void exercise1() throws IOException {
        // Citirea județelor din fișier
        List<String> counties = Files.readAllLines(Paths.get("judete_in.txt"));
        String[] countiesArray = counties.toArray(new String[0]);

        // Sortarea alfabetică
        Arrays.sort(countiesArray);

        // Introducerea unui județ de la tastatură
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceți județul căutat: ");
        String county = scanner.nextLine();

        // Căutare binară
        int position = Arrays.binarySearch(countiesArray, county);
        if (position >= 0) {
            System.out.println("Județul " + county + " se află pe poziția " + position + " în vectorul ordonat.");
        } else {
            System.out.println("Județul nu a fost găsit.");
        }
    }

    // 2. Prelucrarea unui fișier cu versuri
    public static void exercise2() throws IOException {
        class Vers {
            private String text;

            public Vers(String text) {
                this.text = text;
            }

            public int getWordCount() {
                return text.split("\\s+").length;
            }

            public int getVowelCount() {
                return (int) text.toLowerCase().chars().filter(c -> "aeiou".indexOf(c) != -1).count();
            }

            public boolean endsWith(String suffix) {
                return text.endsWith(suffix);
            }

            public String getText() {
                return text;
            }

            public void toUpperCase() {
                this.text = text.toUpperCase();
            }
        }

        // Citirea versurilor din fișier
        List<String> lines = Files.readAllLines(Paths.get("cantec_in.txt"));
        List<Vers> verses = new ArrayList<>();
        for (String line : lines) {
            verses.add(new Vers(line));
        }

        // Scrierea în fișierul de ieșire
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cantec_out.txt"))) {
            Random random = new Random();
            for (Vers vers : verses) {
                StringBuilder output = new StringBuilder(vers.getText());
                output.append(" | Cuvinte: ").append(vers.getWordCount());
                output.append(" | Vocale: ").append(vers.getVowelCount());

                if (vers.endsWith("aleasă")) {
                    output.append(" *");
                }

                if (random.nextDouble() < 0.1) {
                    vers.toUpperCase();
                }

                writer.write(output.toString());
                writer.newLine();
            }
        }
    }

    // 3. Inserare și ștergere într-un șir de caractere
    public static void exercise3() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți șirul principal: ");
        StringBuilder mainString = new StringBuilder(scanner.nextLine());

        System.out.print("Introduceți șirul de inserat: ");
        String insertString = scanner.nextLine();

        System.out.print("Introduceți poziția de inserare: ");
        int insertPosition = scanner.nextInt();

        mainString.insert(insertPosition, insertString);
        System.out.println("Rezultatul după inserare: " + mainString);

        System.out.print("Introduceți poziția de ștergere: ");
        int deletePosition = scanner.nextInt();

        System.out.print("Introduceți numărul de caractere de șters: ");
        int deleteLength = scanner.nextInt();

        mainString.delete(deletePosition, deletePosition + deleteLength);
        System.out.println("Rezultatul după ștergere: " + mainString);
    }

    // 4. CNP și vârsta persoanelor
    public static void exercise4() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți numărul de persoane: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consumă linia

        String[] names = new String[n];
        String[] cnps = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Introduceți numele persoanei " + (i + 1) + ": ");
            names[i] = scanner.nextLine();

            while (true) {
                System.out.print("Introduceți CNP-ul persoanei " + (i + 1) + ": ");
                String cnp = scanner.nextLine();
                if (isValidCNP(cnp)) {
                    cnps[i] = cnp;
                    break;
                } else {
                    System.out.println("CNP invalid. Reîncercați.");
                }
            }
        }

        System.out.println("\nInformațiile introduse:");
        for (int i = 0; i < n; i++) {
            System.out.println("Nume: " + names[i] + ", CNP: " + cnps[i] + ", Vârsta: " + calculateAge(cnps[i]));
        }
    }

    private static boolean isValidCNP(String cnp) {
        if (cnp.length() != 13 || !cnp.matches("\\d{13}")) {
            return false;
        }

        char firstDigit = cnp.charAt(0);
        if (firstDigit != '1' && firstDigit != '2' && firstDigit != '5' && firstDigit != '6') {
            return false;
        }

        int[] controlWeights = {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9};
        int controlSum = 0;
        for (int i = 0; i < 12; i++) {
            controlSum += (cnp.charAt(i) - '0') * controlWeights[i];
        }

        int controlDigit = controlSum % 11;
        if (controlDigit == 10) {
            controlDigit = 1;
        }

        return controlDigit == (cnp.charAt(12) - '0');
    }

    private static int calculateAge(String cnp) {
        int yearPrefix = (cnp.charAt(0) == '1' || cnp.charAt(0) == '2') ? 1900 : 2000;
        int birthYear = yearPrefix + Integer.parseInt(cnp.substring(1, 3));
        int birthMonth = Integer.parseInt(cnp.substring(3, 5));
        int birthDay = Integer.parseInt(cnp.substring(5, 7));

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - birthYear;

        if (today.get(Calendar.MONTH) + 1 < birthMonth ||
                (today.get(Calendar.MONTH) + 1 == birthMonth && today.get(Calendar.DAY_OF_MONTH) < birthDay)) {
            age--;
        }

        return age;
    }

    public static void main(String[] args) throws IOException {
        // Alegeți exercițiul dorit
        System.out.println("Selectați exercițiul (1-4):");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                exercise1();
                break;
            case 2:
                exercise2();
                break;
            case 3:
                exercise3();
                break;
            case 4:
                exercise4();
                break;
            default:
                System.out.println("Opțiune invalidă.");
        }
    }
}
