import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class Main {
    public static void main(String[] args) {
        int[] arr1 = new int[10];
        int[] arr2 = new int[10];
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            arr1[i] = r.nextInt(10);
            arr2[i] = r.nextInt(10);
        }

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

        int[] arr3 = new int[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length + arr2.length; i++) {
            if (i < 10) {
                arr3[i] = arr1[i];
            } else {
                arr3[i] = arr2[i - arr1.length];
            }
        }

        Arrays.sort(arr3);
        System.out.print("[ ");
        for (int val : arr3
        ) {
            System.out.print(val + ", ");
        }
        System.out.print("]");

        System.out.println();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        int dl=0;

        for(int i=0;i<arr1.length-1;i++){
            if(arr1[i]!=arr1[i+1]){
                for(int j=0;j<arr2.length-1;j++){
                    if(arr1[i]==arr2[j]){
                        dl++;
                        break;
                    }
                }
            }
        }

        int[] arr4 = new int[dl];
        int counter=0;
        for(int i=0;i<arr1.length-1;i++){
            if(arr1[i]!=arr1[i+1]){
                for(int j=0;j<arr2.length-1;j++){
                    if(arr1[i]==arr2[j]){
                        arr4[counter] = arr1[i];
                        counter++;
                        break;
                    }
                }
            }
        }




        System.out.print("[ ");
        for (int val : arr4) {
            System.out.print(val + ", ");
        }
        System.out.print("]");

        System.out.println();

        int min = MAX_VALUE, max = MIN_VALUE;

        for(int i=0;i<arr3.length;i++){
            if(min > arr3[i])
                min = arr3[i];
            if(max < arr3[i])
                max = arr3[i];
        }

        System.out.println();

        System.out.println("maks: " + max + " min: " + min);

        System.out.println();

        int war=0;
        for(int i = 0;i<arr3.length-1;i++) {
           if(arr3[i]!=arr3[i+1] && arr3[i]!=max && arr3[i]!=min) {
               war++;
           }
        }
        int[] arr5 = new int[max-min-war-1];
        
        int indx = 0;
        for(int i = min+1 ;i<max;i++) {
            boolean czyp = true;
            for (int j = 0; j < arr3.length; j++) {
                if (arr3[j]==i) {
                    czyp = false;
                    break;
                }
            }
            if(czyp){
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
