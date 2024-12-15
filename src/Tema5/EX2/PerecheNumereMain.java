package Tema5.EX2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PerecheNumereMain {
    public static void main(String[] args) throws IOException {
        // Crearea listei de perechi de numere
        List<PerecheNumere> lista = new ArrayList<>();
        lista.add(new PerecheNumere(8, 13));
        lista.add(new PerecheNumere(10, 15));
        lista.add(new PerecheNumere(5, 8));

        // Scrierea listei în fișier JSON
        PerecheNumere.scriere(lista, "perechi.json");

        // Citirea listei din fișier JSON
        List<PerecheNumere> perechiDinFisier = PerecheNumere.citire("perechi.json");

        // Afișarea perechilor citite
        for (PerecheNumere pereche : perechiDinFisier) {
            System.out.println(pereche);
            System.out.println("Consecutive Fibonacci: " + pereche.suntConsecutiveFibonacci());
            System.out.println("CMC: " + pereche.celMaiMicMultipluComun());
            System.out.println("Suma cifrelor egală: " + pereche.auSumaCifrelorEgala());
            System.out.println("Număr de cifre pare egal: " + pereche.auAcelasiNumarDeCifrePare());
        }
    }
}{
}
