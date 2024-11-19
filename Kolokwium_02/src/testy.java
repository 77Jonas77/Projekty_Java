import java.util.Arrays;
import java.util.Random;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class testy {
    public static void main(String[] args) {

        //11-03
        int[] arr1 = {2, 3, 4, 9, 5, 9, 6, 8, 3, 2};
        int[] arr2 = {2, 3, 2, 5, 6, 2, 1, 5, 7, 9};
        int[] arr3 = {2, 3, 4, 5, 6, 7, 9, 9, 9};
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        System.out.print("[ ");
        for (int val : arr1
        ) {
            System.out.print(val + ", ");
        }
        System.out.print("]");

        System.out.println();

        System.out.print("[ ");
        for (int val : arr2
        ) {
            System.out.print(val + ", ");
        }
        System.out.print("]");

        System.out.println();
        System.out.println();

        int dl = 1;

        for (int i = 0; i < arr1.length - 1; i++) {
            if (arr1[i] != arr1[i + 1]) {
                for (int j = 0; j < arr2.length; j++) {
                    if (arr1[i] == arr2[j]) {
                        dl++;
                        break;
                    }
                }
            }
        }
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        int[] arr4 = new int[dl];
        int counter = 0;

        for (int i = 0; i < arr1.length - 1; i++) {
            if (arr1[i] != arr1[i + 1]) {
                for (int j = 0; j < arr2.length; j++) {
                    if (arr1[i] == arr2[j]) {
                        arr4[counter++] = arr1[i];
                        break;
                    }
                }
            }
            for (int k = 0; k < arr2.length; k++) {
                if (arr1[arr1.length - 1] == arr2[k]) {
                    arr4[dl - 1] = arr1[arr1.length - 1];
                    break;
                }
            }
        }

        if (dl != 1) {
            if (arr4[dl - 1] != 0) {
                System.out.print("[ ");
                for (int val : arr4) {
                    System.out.print(val + ", ");
                }
                System.out.print("]");
            } else {
                for (int i = 0; i < arr4.length - 1; i++) {
                    System.out.print(arr4[i] + " ");
                }
            }
        }
        int min = MAX_VALUE, max = MIN_VALUE;

        for (int i = 0; i < arr3.length; i++) {
            if (min > arr3[i])
                min = arr3[i];
            if (max < arr3[i])
                max = arr3[i];
        }

        System.out.println();

        System.out.println("maks: " + max + " min: " + min);

        System.out.println();

        int war = 0;
        for (int i = 0; i < arr3.length - 1; i++) {
            if (arr3[i] != arr3[i + 1] && arr3[i] != max && arr3[i] != min) {
                war++;
            }
        }
        int[] arr5 = new int[max - min - war - 1];

        int indx = 0;
        for (int i = min + 1; i < max; i++) {
            boolean czyp = true;
            for (int j = 0; j < arr3.length && czyp; j++) {
                if (arr3[j] == i) {
                    czyp = false;
                }
            }
            if (czyp) {
                arr5[indx] = i;
                indx++;
            }
        }

        System.out.print("[ ");
        for (int val : arr5
        ) {
            System.out.print(val + ", ");
        }
        System.out.print("] ");
    }
}

