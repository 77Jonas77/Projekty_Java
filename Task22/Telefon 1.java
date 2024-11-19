package Zad22_4;

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

}
