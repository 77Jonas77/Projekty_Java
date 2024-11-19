package Zad20_1;

public class Main_1 {
    public static void main(String[] args) {
        Corn paczka = new Corn(13);
        Popcorn[] tab = paczka.makePopcorn();

        for(int i=0;i<tab.length;i++){
            System.out.println(tab[i]);
        }
    }
}
