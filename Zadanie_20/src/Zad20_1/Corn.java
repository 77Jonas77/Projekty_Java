package Zad20_1;

public class Corn {
    public static int cornCount;

    public Corn(int cornCount){
        this.cornCount = cornCount;
    }

    Popcorn[] makePopcorn(){
        int ile = (int)(Math.random()*this.cornCount);
        Popcorn[] tab = new Popcorn[ile];
        for(int i=0;i<ile;i++){
            tab[i] = new Popcorn();
        }
        return tab;
    }
}
