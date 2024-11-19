package Zad20_4;

public class Trojkat {
    private double a;
    private double b;
    private double P;

    public Trojkat(double a, double h){
        this.a = a;
        this.b = h;
        this.P = a*h/2;
    }
    public void show(){
        System.out.println(P);
    }

    public double getP() {
        return P;
    }
}
