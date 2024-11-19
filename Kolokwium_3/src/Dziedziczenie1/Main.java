package Dziedziczenie1;

public class Main {
    public static void main(String[] args) {

        Person osoba = new Person("Mala", 2003);
        Person osoba1 = new Student("Ala", 2002,27321);
        Person osoba2 = new x("xd",2222,223);

        System.out.println(osoba.xd());
        System.out.println(osoba1.xd());
        System.out.println(osoba2.xd());
        //jesli nie istnieje metoda w obrebie wytworzonego obiektu, to wywoluje sie metoda z klasy nadrzedej, jesli tam jest

        System.out.println(osoba.toString());
        System.out.println(osoba1.toString());
    }
}
