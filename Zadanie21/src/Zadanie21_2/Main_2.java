package Zadanie21_2;

import java.util.Objects;

public class Main_2 {
    public static void main(String[] args) {

        Owoc[] owoce = new Owoc[100];
        double weight = 0;
        Drzewo d1 = new Drzewo();
        int i = 0;

        while(weight<=5000 || i==99) {
            owoce[i] = d1.zerwijOwoc();
            weight += owoce[i].getMasa();
            i++;
        }


        int[] tab = new int[3];
        for(int j=0;j<tab.length;j++)
            tab[j] = 0;
        
        for(int j=0;j < i;j++){
            if(Objects.equals(owoce[j].getNazwa(), "Jablko")){
                tab[0]++;
            }else if(Objects.equals(owoce[j].getNazwa(), "Gruszka")){
                tab[1]++;
            }else if(Objects.equals(owoce[j].getNazwa(), "Pomarancza")){
                tab[2]++;
            }
        }

        System.out.println("Jablek: " + tab[0]);
        System.out.println("Gruszek: " + tab[1]);
        System.out.println("Pomaranczy: " + tab[2]);
    }
}
