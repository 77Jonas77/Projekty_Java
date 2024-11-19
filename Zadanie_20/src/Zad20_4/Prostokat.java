package Zad20_4;

public class Prostokat {
    private double x;
    private double y;
    private double P;

    public Prostokat(double x, double y){
        this.x = x;
        this.y = y;
        this.P = this.x*this.y;
    }
    public void show(){
        System.out.println(P);
    }
    public double getP() {
        return P;
    }
}
