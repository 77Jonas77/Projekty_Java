package Zadanie19_2;

public class Main2 {
    public static void main(String[] args) {
        int donkey_weight = 10;
        Donkey first = new Donkey();

        while(Donkey.isFlying(donkey_weight)){
            Balloon nowy = new Balloon();
            Donkey.addBalloon(nowy);
        }
        if(!Donkey.isFlying(donkey_weight)){
            System.out.println("Ja latam!!!");
        }
    }
}
