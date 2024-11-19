package zad6;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.pow;

public class PNWReader {
    private FileReader fr;
    private int war;
    private final static int w = 1;
    private final static int lp = 2;
    private final static int ln = 4;
    private boolean state = false;

    public PNWReader(FileReader fr, int war){
        this.fr = fr;
        if (war > 7 || war < 1) {
            throw new IllegalArgumentException();
        }
        this.war = war;
    }

    public boolean czyp(int liczba) {
        if (liczba < 2)
            return false;
        for (int i = 2; i < liczba; i++) {
            if (liczba % i == 0)
                return false;
        }
        return true;
    }

    public void show() {
        if (state)
            throw new IllegalStateException();
        else {
            int tmp;
            boolean czypierwsza = false;
            boolean czynarcystyczna = false;
            boolean czywyraz = false;
            try {
                int nr = 0;
                String word = "";
                //JAPIERDOLE TU JEST CALE ROZWIAZNIE
                boolean w1 = ((this.war & w)==w);
                boolean w2 = ((this.war & lp)==lp);
                boolean w3 = ((this.war & ln)==ln);
                while ((tmp = fr.read()) != -1) {
                    if ((tmp >= 'a' && tmp <= 'z' || tmp >= 'A' && tmp <= 'Z')) {
                        czywyraz = true;
                        word += (char) (tmp);
                    } else if ( !czywyraz) {
                        System.out.println(word);
                        word = "";
                        czywyraz = false;
                    } else {
                        czywyraz = false;
                        if (tmp != ' ') {
                            nr = nr * 10 + (tmp - '0');
                        } else {
//                            if (){
//
//                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean czyn(int liczba) {
        int licz = 0;
        int tmp = liczba;
        int suma = 0;
        while (tmp != 0) {
            licz++;
            tmp /= 10;
        }
        int tmp2 = liczba;
        while (tmp2 != 0) {
            suma = suma + (int) (pow(tmp2 % 10, licz));
            tmp2 /= 10;
        }
        return liczba == suma;
    }
}
