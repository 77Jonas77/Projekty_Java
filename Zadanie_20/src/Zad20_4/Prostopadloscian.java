package Zad20_4;

public class Prostopadloscian extends Prostokat{

    private double Pp;
    private double V;
    private double H;

    public Prostopadloscian(double x,double y, double H){
        super(x,y);
        this.H = H;
        this.Pp = 2*super.getP()+2*x*this.H+2*y*this.H;
        this.V = super.getP()*this.H;
    }

    @Override
    public void show() {
        System.out.println(Pp);
        System.out.println(V);
    }
}
