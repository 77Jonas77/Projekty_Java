package Zadanie14_3;

import java.util.Random;

public class zad14_3 {
    public static void main(String[] args) {
        int size = (int) (Math.random() * 10 + 2);
        int[][] arr = new int[size][size];
        int errcount = 0;
        
        while (errcount != 20) {
            int x = (int) (Math.random() * (size * (1.4)) - (0.2 * size));
            int y = (int) (Math.random() * (size * (1.4)) - (0.2 * size));

            if (x > size - 1 || x < 0) {
                System.out.println("coordinates outside array range(x,y)"  + ": " + ++errcount);

            }
        }
    }
}
