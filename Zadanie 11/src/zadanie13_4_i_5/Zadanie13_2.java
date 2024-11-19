package zadanie13_4_i_5;

import java.util.Scanner;


public class Zadanie13_2 {

    final static int EMPTY_ELEMENT = -1;

    public static void main(String[] args) {
        int[] arr = new int[3];
        Scanner sc = new Scanner(System.in);

        for (int i=0;i<3;i++){
            arr[i] = EMPTY_ELEMENT;
        }


        Scanner val = new Scanner(System.in);
        int x = 0;
        int exp = 0;

        do {
            System.out.println("-----------------------------------");
            System.out.println("Wydaj polecenie quit, add lub get, a nastepnie");
            System.out.println("Podaj dowolna liczbe z przedialu [-5;5] --> ");

            System.out.print("[ ");
            for (int indx : arr) {
                if(indx!=EMPTY_ELEMENT)
                    System.out.print(indx + ", ");
            }
            System.out.print("]");

            System.out.println();

            switch (sc.next()) {
                case "quit" -> {
                    break;
                }
                case "add" -> {
                    int z = val.nextInt();
                    if (z < -5 || z > 5) {

                        System.out.print("[ ");
                        for (int indx : arr) {
                            if(indx!=EMPTY_ELEMENT)
                                System.out.print(indx + ", ");
                            exp++;
                        }
                        System.out.print("]");
                        System.out.println();
                        break;
                    }
                    if(x==arr.length){
                        int[] tmp = new int[arr.length+1];
                        for(int i=0;i<arr.length;i++){
                            tmp[i] = arr[i];
                        }
                        arr=tmp;
                    }
                    arr[x] = z;
                    x++;

                    System.out.print("[ ");
                    for (int indx : arr) {
                        if(indx!=EMPTY_ELEMENT)
                            System.out.print(indx + ", ");
                    }
                    System.out.print("]");

                    System.out.println();

                }
                case "get" -> {
                    if (x == 0) {
                        System.out.println("nie ma elementow do pobrania");
                        System.out.println("[]");
                        break;
                    } else {
                        arr[--x] = EMPTY_ELEMENT;

                        System.out.print("[ ");
                        for (int indx : arr) {
                            if(indx!=EMPTY_ELEMENT)
                                System.out.print(indx + ", ");
                        }
                        System.out.print("]");
                        System.out.println();
                    }
                }
                default -> {
                    System.out.print("[ ");
                    for (int indx : arr) {
                        if(indx!=EMPTY_ELEMENT)
                            System.out.print(indx + ", ");
                    }
                    System.out.print("]");
                    System.out.println();
                    exp++;
                    break;
                }
            }
        }while(exp < 1);

    }
}
