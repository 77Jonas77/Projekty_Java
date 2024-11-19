import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //ROK PRZESTEPNY
        int miesiac = 3;
        int rok = 2003;
        int ile_uplynelo = 0;

        if (miesiac == 2) {
            if (rok % 4 == 0 && rok % 100 != 0 || rok % 400 == 0) {
                ile_uplynelo += 29;
            } else {
                ile_uplynelo += 28;
            }
        } else {
            if (miesiac == 1 || miesiac == 3 || miesiac == 5 || miesiac == 7
                    || miesiac == 8 || miesiac == 10 || miesiac == 12) {
                ile_uplynelo += 31;
            } else {
                ile_uplynelo += 30;
            }
        }

        //wyswietlanie znakow z ASCII
        int z = 92;
        System.out.println((char) z);

        //kierunki swiata --wlasciwosc typu char

        String[] tab = {"North", "East", "South", "West"};
        char dir = 0;
        Scanner sc = new Scanner(System.in);
        String move;

        while ((move = sc.next()) != "Q") {
            dir += switch (move) {
                case "a", "A" -> -1;
                case "d", "D" -> 1;
                default -> 0;
            };
            System.out.println(
                    switch (dir % 4) {
                        case 0 -> "North";
                        case 1 -> "East";
                        case 2 -> "South";
                        case 3 -> "West";
                        default -> "";
                    }
            );
        }

        //wypelnianie: losowe znaki z zakresu
        char[] tab1 = new char[10];
        Random r = new Random();

        for(int i=0;i<10;i++){
            tab1[i] = (char)(r.nextInt('z'-'a'+1)+'a');
        }
        //wypelnianie: tab wartosciami true/false
        boolean[] tab2 = new boolean[10];
        for (int i = 0; i < tab.length; i++) {
            switch (r.nextInt(2)) {
                case 1 -> tab2[i] = true;
                case 0 -> tab2[i] = false;
            }
        }

        //
        long[] tab3 = {1,2,3,4,5,6,7,8,9,10};
        boolean state;

        do {
            int x = r.nextInt(tab3.length)+1, y = r.nextInt(tab3.length)+1;
            long temp = tab3[x];
            tab3[x] = tab3[y];
            tab3[y] = temp;
            state = false;
            for(int i=0;i<tab.length-1 && !state;i++) {
                if (tab3[i] + 1 == tab3[i + 1]) {
                    state = true;
                    break;
                }
            }
        } while (state);

        //dodawanie elementow z dwoch tablic
        int[] arr1, arr2;

        arr1 = new int[10];
        arr2 = new int[15];

        for(int i=0; i<arr1.length; i++)
            arr1[i] = (int)(Math.random()*10);
        for(int i=0; i<arr2.length; i++)
            arr2[i] = (int)(Math.random()*10);



        int[] res1 = new int[arr1.length+arr2.length];
        for(int i=0; i<arr1.length; i++)
            res1[i] = arr1[i];
        for(int i=0; i<arr2.length; i++)
            res1[arr1.length+i] = arr2[i];

    //
        //dodawanie do tablicy wartosci powt sie w innych tablicach bez ich powtarzania
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
                for(int j=0;j<arr2.length;j++){
                    if(arr1[i]==arr2[j]){
                        arr4[counter] = arr1[i];
                        counter++;
                        break;
                    }
                }
            }
        }

        //palindrom
        int[] tab5 = {1,2,3,3,2,1};
        for (int val: tab5
        ) {
            System.out.print(val + " ");
        }
        boolean czyp = true;
        for(int i=0;i<tab5.length;i++){
            if(tab5[i]!=tab5[tab5.length-1-i]){
                czyp = false;
                break;
            }
        }
        int[] arr = {153, 333, 370, 515, 407, 80};

        for(int i=0;i<arr.length;i++) {
            int pom1 = arr[i];
            int pot = 0;

            while(pom1>0){
                pot++;
                pom1/=10;
            }

            int pom2 = arr[i];
            int wynik = 0;

            while(pom2>0){
                int pot_wyn = 1;
                for(int j=0;j<pot;j++){
                    pot_wyn*=pom2%10;
                }
                wynik+=pot_wyn;
                pom2/=10;
            }
            if (arr[i] == wynik)
                System.out.println(arr[i]);
        }

        //dodawanie tablic 1d do iles D (2d...)
        // int[][] arr4 = {arr1, arr2, arr3};

        //dodawanie z 2 tablic do jednej posortowanych elementow w zad13_01

        //dodawanie elementow do tablicy
//        int x;
//        if(x==arr.length){
//            int[] tmp = new int[arr.length+1];
//            for(int i=0;i<arr.length;i++){
//                tmp[i] = arr[i];
//            }
//            arr=tmp;
//        }
//        arr[x] = z;
//        x++;
//
//        // usuwanie elementow + static tutaj nizej
//        final  int EMPTY_ELEMENT = -1;
//        arr[--x] = EMPTY_ELEMENT;

    }
}