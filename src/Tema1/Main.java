package Tema1;

import javax.print.DocFlavor;
import java.util.*;
import java.io.*;
import java.math.*;

public class Main {
    public static void main(String[] args) {
        //ex1
        Scanner sc = new Scanner(System.in);

        System.out.printf("Lungime dreptunghi L=");
        int L=sc.nextInt();

        System.out.printf("latime dreptunghi l=");
        int l=sc.nextInt();

        System.out.println("Perimetrul dereptunghiului este "+(l+L)+"\nIar aria este "+(l*L));

        //ex2
        int suma=0,min=1000,max=0,n=0;
        float media=0;
        try {

            BufferedWriter writer =new BufferedWriter(new FileWriter("C:\\Users\\CockRoach\\IdeaProjects\\tema1\\src\\Tema1\\out.txt"));

            File fisier = new File("C:\\Users\\CockRoach\\IdeaProjects\\tema1\\src\\Tema1\\in.txt");
            Scanner myReader = new Scanner(fisier);
            while (myReader.hasNextLine()) {
                int data = myReader.nextInt();
                suma=suma+data;
                n++;
                if (data >max)max=data;
                if (data <min)min=data;
            }
            media=(float)suma/n;
            writer.write("\nSuma: "+suma);
            writer.write("\nMaxima: "+max);
            writer.write("\nMinima: "+min);
            writer.write("\nMedia: "+media);
            myReader.close();
            writer.close();
        }catch(FileNotFoundException e){
            System.out.println("Error");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //ex3
        System.out.println("Introduceti un numar:");
        n=sc.nextInt();
        int k=0;
        System.out.println("1");
        for(int i=2;i<n/2;i++){
            if( n%i == 0){
                k=1;
                System.out.println(i);
            }
        }
        System.out.println(n);
        if( k == 0){System.out.println(" Numarul ales este prim");}

        //ex4
        Random rand  = new Random();
        int a = rand.nextInt(31);
        int b = rand.nextInt(31);
        System.out.println("Cele 2 numere generate aleator sunt"+a+" "+b);
        int aux;
        int cmmdc=1;
        if(a>b){
            aux=a;
            a=b;
            b=aux;
        }
        for(int i=2;i<=(a/2);i++)
        {
            if(a%i==0)
                if(b%i==0)cmmdc=i;
        }

        System.out.println("Cel mai mare divizor comul a celor doua numere este"+cmmdc);

        //ex5
        a=2;
        b=1;

        Random random = new Random();
        int randomNumber = random.nextInt(21);
        int ab=randomNumber;

        if(ab==a || ab==b){
            System.out.println("Numarul ales face parte din Sirul lui Fibonaci");
        }
        else {
            while (a <= ab) {
                if (a == ab) {
                    System.out.println("Numarul ales face parte din Sirul lui Fibonaci");
                    break;
                }
                aux = a;
                a = a + b;
                b = aux;
            }
        }
    }
}

