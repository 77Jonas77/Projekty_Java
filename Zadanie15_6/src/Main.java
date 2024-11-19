import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;
import java.util.Scanner;

import static java.lang.Math.pow;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int s = scan.nextInt();

        int size = s * 2 + 1;

        char[][] arr = new char[size][size];

        for (int i = 0; i < size; i++) {
            if (i == 0) {
                arr[i][0] = '\u250F';
                for (int j = 1; j < size; j++) {
                    arr[i][j] = '\u2500';
                }
                arr[0][size - 1] = '\u2513';
            }
            if (i != 0 && i != size - 1) {
                arr[i][0] = '\u2503';
                for (int x = 1; x < size - 1; x++) {
                    if (i % 2 == 0 && x % 2 == 0) {
                        char znak = (char) (Math.random() * 128 + '\u2500');
                        arr[i][x] = znak;
                    } else {
                        arr[i][x] = ' ';
                    }
                }
                arr[i][size - 1] = '\u2503';
            }
            if (i == size - 1) {
                arr[i][0] = '\u2517';
                for (int j = 1; j < size; j++) {
                    arr[i][j] = '\u2500';
                }

                arr[i][size - 1] = '\u251B';
            }
        }

        char dir = 0;
        char[] tab = {'\u2191', '\u2192', '\u2193','\u2190'};
        int[][] move = {{-1, 0}, {0, 1}, {1,0}, {0, -1}};

        int x1 = (int) (Math.random() * size);
        int y1 = (int) (Math.random() * size);

        while (arr[x1][y1] != ' ') {
            x1 = (int) (Math.random() * size);
            y1 = (int) (Math.random() * size);
        }
        arr[x1][y1] = tab[0];

        for (char val[] : arr
        ) {
            System.out.println(val);
        }

        Scanner com = new Scanner(System.in);

        Boolean czyp = false;
        do {
            String command = com.next();
            switch (command) {
                case "W":
                    if (arr[x1 + move[dir % 4][0]][y1 + move[dir % 4][1]] == ' ') {
                        arr[x1 + move[dir % 4][0]][y1 + move[dir % 4][1]] = tab[dir % 4];
                        arr[x1][y1] = ' ';
                        x1 = x1 + move[dir % 4][0];
                        y1 = y1 + move[dir % 4][1];
                    } else {
                        System.out.println("TUTAJ ZNAJDUJE SIE PRZESZKODA!");
                    }
                    for (char[] val : arr
                    ) {
                        System.out.println(val);
                    }
                    break;
                case "A":
                    --dir;
                    arr[x1][y1] = tab[dir % 4];
                    for (char[] val : arr
                    ) {
                        System.out.println(val);
                    }
                    break;
                case "D":
                    ++dir;
                    arr[x1][y1] = tab[dir % 4];
                    for (char[] val : arr
                    ) {
                        System.out.println(val);
                    }
                    break;
                case "Q":
                    czyp = true;
                    break;
                default:
                    czyp = true;
                    break;
            }
        } while (!czyp);
        System.out.println("KONIEC");
    }
}