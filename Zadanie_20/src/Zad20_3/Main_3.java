package Zad20_3;

public class Main_3 {

    public static void main(String[] args) {
        String[] kA = {"PSG", "Atletico Madryt", "Sporting CP", "Inter", "Benfica", "Villarreal", "RB Salzburg", "Chelsea"};
        String[] kB = {"Manchester City", "Liverpool", "Ajax Amsterdam", "Real Madryt", "Bayern Monachium", "Manchester United", "Lille", "Juventus"};

        MyQueue kolejka = new MyQueue(kA.length+ kB.length);

        for (int i = 0; i < kB.length; i++) {
            kolejka.put(kA[i]);
            kolejka.put(kB[i]);
        }

        System.out.println("Kolejka: ");
        System.out.println("-------------------------------");

        while (!kolejka.isEmpty()) {
            System.out.println(kolejka.getClub() + " | " + kolejka.getClub());
        }
        System.out.println("-------------------------------");
    }

}
