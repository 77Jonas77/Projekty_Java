package Zadanie_4_rek;

public class Zad17_4_rek {
    public static int[] bubbleSortIt(int[] arr, int indx) {
        if (indx == arr.length - 1) {
            return arr;
        }
        for (int i = 0; i < arr.length - 1 - indx; i++) {
            if (arr[i] > arr[i + 1]) {
                int pom = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = pom;
            }
        }
        return bubbleSortIt(arr, indx + 1);
    }
        public static void main (String[]args){
            int[] arr = {2, 4, 5, 2, 1, 4, 6, 7, 5, 9, 1, 2, 3};

            for (int val : bubbleSortIt(arr, 0)) {
                System.out.print(val + " ");
            }
        }
    }
