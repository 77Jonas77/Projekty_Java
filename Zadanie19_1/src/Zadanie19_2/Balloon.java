package Zadanie19_2;

public class Balloon {

    private double capas;

    public Balloon() {
        this.capas = Math.random()*0.009 + 0.005;
    }

    public double getLoad(){
        return (capas/0.007)*6;
    }

}