package Tema5.EX2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PerecheNumere {
    private int numar1;
    private int numar2;

    // Constructor fără parametri
    public PerecheNumere() {}

    // Constructor cu parametri
    public PerecheNumere(int numar1, int numar2) {
        this.numar1 = numar1;
        this.numar2 = numar2;
    }

    // Gettere și settere
    public int getNumar1() {
        return numar1;
    }

    public void setNumar1(int numar1) {
        this.numar1 = numar1;
    }

    public int getNumar2() {
        return numar2;
    }

    public void setNumar2(int numar2) {
        this.numar2 = numar2;
    }

    // Redefinirea metodei toString
    @Override
    public String toString() {
        return "(" + numar1 + ", " + numar2 + ")";
    }

    // Verifică dacă numerele sunt consecutive în șirul lui Fibonacci
    public boolean suntConsecutiveFibonacci() {
        return esteFibonacci(numar1) && esteFibonacci(numar2) && Math.abs(numar1 - numar2) == 1;
    }

    private boolean esteFibonacci(int n) {
        int x1 = 5 * n * n + 4;
        int x2 = 5 * n * n - 4;
        return estePatratPerfect(x1) || estePatratPerfect(x2);
    }

    private boolean estePatratPerfect(int numar) {
        int sqrt = (int) Math.sqrt(numar);
        return sqrt * sqrt == numar;
    }

    // Calculează cel mai mic multiplu comun
    public int celMaiMicMultipluComun() {
        return (numar1 * numar2) / celMaiMareDivizorComun(numar1, numar2);
    }

    private int celMaiMareDivizorComun(int a, int b) {
        if (b == 0) return a;
        return celMaiMareDivizorComun(b, a % b);
    }

    // Verifică dacă suma cifrelor celor două numere este egală
    public boolean auSumaCifrelorEgala() {
        return sumaCifrelor(numar1) == sumaCifrelor(numar2);
    }

    private int sumaCifrelor(int numar) {
        int suma = 0;
        while (numar > 0) {
            suma += numar % 10;
            numar /= 10;
        }
        return suma;
    }

    // Verifică dacă cele două numere au același număr de cifre pare
    public boolean auAcelasiNumarDeCifrePare() {
        return numarCifrePare(numar1) == numarCifrePare(numar2);
    }

    private int numarCifrePare(int numar) {
        int count = 0;
        while (numar > 0) {
            if ((numar % 10) % 2 == 0) count++;
            numar /= 10;
        }
        return count;
    }

    // Metodă statică pentru scrierea într-un fișier JSON
    public static void scriere(List<PerecheNumere> lista, String fisier) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fisier), lista);
    }

    // Metodă statică pentru citirea dintr-un fișier JSON
    public static List<PerecheNumere> citire(String fisier) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(fisier), new TypeReference<List<PerecheNumere>>() {});
    }
}