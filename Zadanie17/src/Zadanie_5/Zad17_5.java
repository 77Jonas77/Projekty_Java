package Zadanie_5;

public class Zad17_5 {

//    public static int[][] rekArr(int[][] arr, int start) {
//        if (start - 1 == arr.length / 2) {
//            return arr;
//        }
//        int l2 = 1;
//        for (int i = start - 1; i < arr.length - start + 1; i++) {
//            arr[start - 1][i] = start;
//        }
//        int l = 1;
//        for (int j = 0; j < start; j++) {
//            arr[start][arr.length - 1 - j] = l;
//            arr[start][j] = l++;
//            return rekArr(arr, start + 1);
//
//        }
//        return arr;
//    }

    public static int[][] rekArr(int[][] arr, int start) {
        if(start -1 == arr.length/2){
            return arr;
        }
        for(int i=start-1 ;i<arr.length - start +1;i++){
            arr[start-1][i] = start; //gora
            arr[i][start-1] = start; //lewo
            arr[arr.length - start][i] = start; //prawo
            arr[i][arr.length - start] = start; // dol
            rekArr(arr, start + 1);
        }
        return arr;
    }
    public static void main(String[] args) {
        int size = 8;
        int[][] tab = new int[size][size];
        rekArr(tab, 1);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(tab[i][j]);
            }
            System.out.println();
        }
    }
}
