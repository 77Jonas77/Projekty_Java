package Zadanie_1;

public class Zad16_1 {
    public static boolean jestRowna(int[][] tab1, int[][] tab2){

        for(int i=0;i<tab1.length;i++){
            for(int j=0;j<tab2[i].length;j++) {
                if(tab1[i][j]!=tab2[i][j])
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int size = 10;
        int size2 = 6;
        int[][] arr1 = new int[size][size2];
        int[][] arr2 = new int[size][size2];
        int[][] arr3 = new int[size][size2];

        System.out.println(arr1.length);
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[i].length; j++) {
                arr1[i][j] = (int) (Math.random() * 10);
                arr3[i][j] = (int) (Math.random() * 10);
            }
        }

        //zawsze bedzie rowna
        arr2 = arr1;
        // arr3 nie bd

        System.out.println(
                jestRowna(arr1, arr2) + " " + jestRowna(arr1, arr3)
        );
    }
}
