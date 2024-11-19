package Zadanie18_4;

import static java.lang.Math.pow;

public class Walec {
    private static int promien;
    private static int wysokosc;
    public Walec(int wys, int r) {
        this.promien = r;
        this.wysokosc = wys;
    }

    public void show(){
        System.out.println("pp: "+  Math.PI*promien*promien + " obj: " + this.wysokosc*promien*promien);
    }
}

