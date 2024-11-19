import org.apache.commons.math4.core.jdkmath.AccurateMath;

import java.io.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter("Towary.txt"));
        Random rand = new Random();
        double my_sum =0 ;
        // Pierwsza pętla zapisuje dane do pliku
        for(int i = 1; i <= 9000; i++) {
            double weight = AccurateMath.round(rand.nextDouble() * 1000 + 1);
            my_sum += weight;
            br.write(i + " " + weight);
            br.newLine();
        }

        // Wywołanie flush(), aby upewnić się, że dane są zapisane
        br.flush();

        // Druga pętla zapisuje kolejne dane do pliku
        for(int i = 9001; i <= 11000; i++) {
            double weight = AccurateMath.round(rand.nextDouble() * 1000 + 1);
            my_sum += weight;
            br.write(i + " " + weight);
            br.newLine();
        }

        // Ponowne wywołanie flush(), aby upewnić się, że wszystkie dane są zapisane
        br.flush();
        br.close();  // Nie zapomnij zamknąć BufferedWriter

        // Odczyt z pliku
        BufferedReader bre = new BufferedReader(new FileReader("Towary.txt"));
        String line;
        double sum = 0;
        int c = 0;
        while ((line = bre.readLine()) != null) {
            String[] seg = line.split(" ");
            sum += Double.parseDouble(seg[1]);
            c++;
        }
        bre.close();  // Nie zapomnij zamknąć BufferedReader

        System.out.println(sum + " " + c + " " + my_sum);
    }
}
