package Zadanie_4;

public class Zad17_4 {
    public static int[] bubbleSortIt(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int pom = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = pom;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 5, 2, 1, 4, 6, 7, 5, 9, 1, 2, 3};

        for(int val : bubbleSortIt(arr)){
            System.out.print(val + " ");
        }
    }
}
