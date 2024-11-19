package Zadanie_2;

import java.util.Scanner;

public class Zad16_2 {
    public static int[] splitToDigits(int x) {
        int size = 0, pom = x;
        while (pom > 0) {
            size++;
            pom /= 10;
        }
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = x % 10;
            x /= 10;
        }
        return arr;
    }

    public static void main(String[] args) {
        int size = 0;
        // jak to sie po kolei i czym jest co ==> obiekt?
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        //przypisanie tablicy (nabycie)
        int[] obtain = splitToDigits(num);

        //to mogla byc metoda
        while (num > 0) {
            size++;
            num /= 10;
        }

        for (int i = 0; i < size; i++) {
            System.out.print(obtain[i] + " ");
        }
    }
}
