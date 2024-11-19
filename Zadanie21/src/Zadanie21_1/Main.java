package Zadanie21_1;

public class Main {

    public static void main(String[] args) {
        Drzewo d1 = new Drzewo(true, 15, "siedem");
        System.out.println(d1.toString());
        DrzewoIglaste d2 = new DrzewoIglaste(true, 15, "siedem", 1.2, 300);
        System.out.println(d2.toString());
        Drzewo d3 = new DrzewoLisciaste(true, 15, "siedem", 2);
        System.out.println(d3.toString());
        Drzewo d4 = new DrzewoOwocowe(true, 15, "siedem", 2, "jablko");
        System.out.println(d4.toString());
    }

}
