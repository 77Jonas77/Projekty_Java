package Zad22_5;

import java.util.Objects;

public class Osoba {
    public String imie;
    public String nazwisko;
    public String numer;

    public Osoba(String imie, String nazwisko, String numer){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numer = numer;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getNumer() {
        return numer;
    }

}
