package Zadanie14_2;

import java.util.Scanner;

public class zad14_2 {

    final static int EMPTY_ELEMENT = -1;

    public static void main(String[] args) {
        int[] arr = new int[2];
        Scanner com = new Scanner(System.in);
        for (int i = 0; i < 2; i++)
            arr[i] = EMPTY_ELEMENT;

        int counter = 0;
        boolean czyp = true;

        do {
            System.out.println("=============================");
            System.out.println("Podaj komende (s)how lub (r)oll ");
            switch (com.next()) {
                case "s", "S" -> {
                    System.out.print("[ ");
                    for (int val : arr) {
                        if (val != EMPTY_ELEMENT)
                            System.out.print(val + ", ");
                    }
                    System.out.print("]");
                    System.out.println();
                }
                case "r", "R" -> {
                    if (arr.length == counter+1) {
                        int[] tmp = new int[arr.length + 2];
                        for (int i = 0; i < arr.length; i++) {
                            tmp[i] = arr[i];
                        }
                        arr = tmp;
                        arr[++counter] = (int)(Math.random() * 6 + 1);
                        arr[++counter] = (int)(Math.random() * 6 + 1);

                    } else {
                        for (int i = 0; i < 2; i++) {
                            arr[i] = (int) (Math.random() * 6 + 1);
                        }
                        counter = 1;
                    }
                }
                default -> czyp = false;
            }
        } while (czyp);
    }
}
