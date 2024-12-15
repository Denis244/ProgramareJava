package Tema8;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/lab8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("\nMeniu:");
                System.out.println("1. Adăugarea unei persoane");
                System.out.println("2. Adăugarea unei excursii");
                System.out.println("3. Afișarea tuturor persoanelor și excursiilor");
                System.out.println("4. Afișarea excursiilor unei persoane");
                System.out.println("5. Afișarea persoanelor care au vizitat o anumită destinație");
                System.out.println("6. Afișarea persoanelor care au făcut excursii într-un an");
                System.out.println("7. Ștergerea unei excursii");
                System.out.println("8. Ștergerea unei persoane și excursiilor sale");
                System.out.println("9. Ieșire");
                System.out.print("Alegeți o opțiune: ");
                int optiune = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (optiune) {
                    case 1:
                        adaugaPersoana(conn, scanner);
                        break;
                    case 2:
                        adaugaExcursie(conn, scanner);
                        break;
                    case 3:
                        afiseazaPersoaneSiExcursii(conn);
                        break;
                    case 4:
                        afiseazaExcursiiPersoana(conn, scanner);
                        break;
                    case 5:
                        afiseazaPersoaneDestinatie(conn, scanner);
                        break;


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 1. Adăugarea unei persoane
    private static void adaugaPersoana(Connection conn, Scanner scanner) {
        try {
            System.out.print("Introduceti numele persoanei: ");
            String nume = scanner.nextLine();
            System.out.print("Introduceti varsta persoanei: ");
            int varsta = citesteVarsta(scanner);

            String sql = "INSERT INTO persoane (nume, varsta) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nume);
                stmt.setInt(2, varsta);
                stmt.executeUpdate();
                System.out.println("Persoana a fost adăugată cu succes.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Adăugarea unei excursii
    private static void adaugaExcursie(Connection conn, Scanner scanner) {
        try {
            System.out.print("Introduceti ID-ul persoanei: ");
            int idPersoana = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (!persoanaExista(conn, idPersoana)) {
                System.out.println("Persoana nu exista in baza de date.");
                return;
            }

            System.out.print("Introduceti destinatia excursiei: ");
            String destinatia = scanner.nextLine();
            System.out.print("Introduceti anul excursiei: ");
            int anul = citesteAnExcursie(scanner);

            String sql = "INSERT INTO excursii (id_persoana, destinatia, anul) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idPersoana);
                stmt.setString(2, destinatia);
                stmt.setInt(3, anul);
                stmt.executeUpdate();
                System.out.println("Excursia a fost adăugată cu succes.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Afișarea tuturor persoanelor și excursiilor
    private static void afiseazaPersoaneSiExcursii(Connection conn) {
        String sql = "SELECT p.id, p.nume, p.varsta, e.destinatia, e.anul " +
                "FROM persoane p LEFT JOIN excursii e ON p.id = e.id_persoana";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Persoana: " + rs.getString("nume") + ", Vârstă: " + rs.getInt("varsta") +
                        ", Destinație: " + rs.getString("destinatia") + ", Anul: " + rs.getInt("anul"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Afișarea excursiilor unei persoane
    private static void afiseazaExcursiiPersoana(Connection conn, Scanner scanner) {
        System.out.print("Introduceti numele persoanei: ");
        String nume = scanner.nextLine();

        String sql = "SELECT e.destinatia, e.anul FROM excursii e " +
                "JOIN persoane p ON e.id_persoana = p.id WHERE p.nume = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nume);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Destinație: " + rs.getString("destinatia") + ", Anul: " + rs.getInt("anul"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Afișarea persoanelor care au vizitat o anumită destinație
    private static void afiseazaPersoaneDestinatie(Connection conn, Scanner scanner) {
        System.out.print("Introduceti destinatia: ");
        String destinatia = scanner.nextLine();

        String sql = "SELECT p.nume FROM persoane p " +
                "JOIN excursii e ON p.id = e.id_persoana WHERE e.destinatia = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, destinatia);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Persoana: " + rs.getString("nume"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
