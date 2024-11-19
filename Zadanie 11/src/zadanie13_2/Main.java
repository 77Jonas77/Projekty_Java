package zadanie13_2;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        //program dziala dla ponizszego przykladu, ale nie jest zoptymalizowany dla innych wymiarow
        int[][] tab = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        int count = 1;
        int OperationCounter = 1;

        for (int i = 0; i < tab.length; i++) {
            if (i == 0) {
                for (int j = 0; j < tab[0].length; j++) {
                    System.out.print(tab[0][j] + ", ");
                }
            }
            if (i != 0 && i != tab.length - 1) {
                if (count % 2 != 0) {
                    for (int x = OperationCounter; x < tab.length; x++) {
                        System.out.print(tab[x][tab.length - OperationCounter] + ", ");
                    }
                    for (int x = tab.length - 1 - OperationCounter; x >= 0; x--) {
                        System.out.print(tab[tab.length - OperationCounter][x]+ ", ");
                    }
                }
                if (count % 2 == 0) {
                    for (int x = tab.length - OperationCounter - 1; x >= OperationCounter; x--) {
                        System.out.print(tab[x][OperationCounter-1] + ", ");
                    }
                    for (int x = OperationCounter; x <= tab.length - 1 - OperationCounter; x++) {
                        System.out.print(tab[OperationCounter][x] + ", ");
                    }
                    OperationCounter++;
                }
                count++;
            }
            if (i == tab.length - 1) {
                if(tab.length%2==0){
                    System.out.print(tab[tab.length/2][tab.length/2] + ", " +tab[tab.length/2][tab.length/2-1]);
                }
                else{
                    System.out.println(tab[(tab.length/2)+1][(tab.length/2)+1]);
                }
            }
        }
    }
}