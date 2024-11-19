package Zad22_5;

public class Telefon {
    public String interfejsKomunikacyjny;
    public String color;

    public Telefon(String interfejsKomunikacyjny, String color){
        this.color = color;
        this.interfejsKomunikacyjny = interfejsKomunikacyjny;
    }

    public void zadzwon(String numer){
        System.out.println("dryn dryn... : " + numer);
    }
    public void dodajDoZnajomych(String imie, String naz, String nr){
        System.out.println("Mozna dodac tylko z poziomu Smartfon'a ");
    }
    public void wyswietlHistoriePolaczen(){
        System.out.println("brak historii polaczen");
    }

    public Osoba[] getZnajomi() {
        return null;
    }
}
