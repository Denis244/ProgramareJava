package Tema3;

import java.io.*;
import java.util.*;

//problema1
class Parabola {
    private int a, b, c;

    // Constructor
    public Parabola(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // Metodă pentru calcularea vârfului parabolei
    public double[] getVertex() {
        double x = -b / (2.0 * a);
        double y = (-b * b + 4.0 * a * c) / (4.0 * a);
        return new double[]{x, y};
    }

    // Suprascrierea metodei toString()
    @Override
    public String toString() {
        return "f(x) = " + a + "x^2 + " + b + "x + " + c;
    }

    // Metodă pentru calcularea coordonatelor mijlocului dintre două vârfuri
    public double[] midpointWith(Parabola other) {
        double[] vertex1 = this.getVertex();
        double[] vertex2 = other.getVertex();
        double x = (vertex1[0] + vertex2[0]) / 2;
        double y = (vertex1[1] + vertex2[1]) / 2;
        return new double[]{x, y};
    }

    // Metodă statică pentru calcularea mijlocului segmentului dintre două parabole
    public static double[] midpointBetween(Parabola p1, Parabola p2) {
        double[] vertex1 = p1.getVertex();
        double[] vertex2 = p2.getVertex();
        double x = (vertex1[0] + vertex2[0]) / 2;
        double y = (vertex1[1] + vertex2[1]) / 2;
        return new double[]{x, y};
    }

    // Metodă pentru calcularea lungimii segmentului dintre două vârfuri
    public double distanceTo(Parabola other) {
        double[] vertex1 = this.getVertex();
        double[] vertex2 = other.getVertex();
        return Math.hypot(vertex2[0] - vertex1[0], vertex2[1] - vertex1[1]);
    }

    // Metodă statică pentru calcularea lungimii segmentului dintre două parabole
    public static double distanceBetween(Parabola p1, Parabola p2) {
        double[] vertex1 = p1.getVertex();
        double[] vertex2 = p2.getVertex();
        return Math.hypot(vertex2[0] - vertex1[0], vertex2[1] - vertex1[1]);
    }
}

public class Main {
    public static void main(String[] args) {
        List<Parabola> parabolas = new ArrayList<>();

        // Citirea coeficienților din fișier
        try (BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length == 3) {
                    int a = Integer.parseInt(parts[0]);
                    int b = Integer.parseInt(parts[1]);
                    int c = Integer.parseInt(parts[2]);
                    parabolas.add(new Parabola(a, b, c));
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }

        // Afișarea parabolas și vârfurilor
        for (Parabola parabola : parabolas) {
            System.out.println(parabola);
            double[] vertex = parabola.getVertex();
            System.out.printf("Vârful: (%.2f, %.2f)%n", vertex[0], vertex[1]);
        }

        // Calcularea mijlocului și lungimii segmentului pentru primele două parabole
        if (parabolas.size() >= 2) {
            Parabola p1 = parabolas.get(0);
            Parabola p2 = parabolas.get(1);

            // Mijlocul segmentului
            double[] midpoint = Parabola.midpointBetween(p1, p2);
            System.out.printf("Mijlocul segmentului dintre primele două parabole: (%.2f, %.2f)%n", midpoint[0], midpoint[1]);

            // Lungimea segmentului
            double distance = Parabola.distanceBetween(p1, p2);
            System.out.printf("Lungimea segmentului dintre primele două parabole: %.2f%n", distance);
        }
    }
}
