package Zad22_5;

public class Main_5 {
    public static void main(String[] args) {

        Telefon[] arr = {new Smartfon("Ekran","ladnyhah"), new Komorka("miedz","czarny")};
        arr[1].dodajDoZnajomych("Maria", "Bez ", "123-123-123");
        arr[1].dodajDoZnajomych("Maria", "Bsdsd ", "123-123-122");
        arr[1].dodajDoZnajomych("Maasda", "Bsdsd ", "123-123-125");
        arr[1].dodajDoZnajomych("Magfgfg", "Bhgghgh ", "123-123-121");
        arr[1].dodajDoZnajomych("Maqweqwe", "Bghgh ", "123-123-126");

        arr[0].dodajDoZnajomych("Anna", "Bs ", "223-123-124");
        arr[0].dodajDoZnajomych("Antek", "Hej ", "523-123-125");
        arr[0].dodajDoZnajomych("Marian", "maly ", "723-123-122");
        arr[0].dodajDoZnajomych("Aneta", "sss ", "923-123-121");
        arr[0].dodajDoZnajomych("Aneta", "mloda", "926-126-126");

        System.out.println("=====Komorka=====");
        arr[1].zadzwon("123-123-727");
        arr[1].zadzwon("123-113-123");
        arr[1].zadzwon("123-113-625");
        arr[1].zadzwon("623-123-122");
        arr[1].zadzwon("123-123-523");
        arr[1].zadzwon("123-123-125");
        arr[1].zadzwon("123-113-223");
        arr[1].zadzwon("123-113-121");
        arr[1].zadzwon("123-113-921");
        arr[1].zadzwon("623-123-126");

        System.out.println("=====Smartfon=====");
        arr[0].zadzwon("123-123-123");
        arr[0].zadzwon("223-123-124");
        arr[0].zadzwon("123-123-122");
        arr[0].zadzwon("523-123-125");
        arr[0].zadzwon("123-113-123");
        arr[0].zadzwon("723-123-122");
        arr[0].zadzwon("123-113-125");
        arr[0].zadzwon("923-123-121");
        arr[0].zadzwon("623-123-126");
        arr[0].zadzwon("926-126-126");

        arr[1].wyswietlHistoriePolaczen();
        arr[0].wyswietlHistoriePolaczen();


    }
}
