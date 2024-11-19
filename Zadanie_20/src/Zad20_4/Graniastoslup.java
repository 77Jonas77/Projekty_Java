package Zad20_4;

public class Graniastoslup extends Trojkat{

    private double Pp;
    private double V;
    private double H;

    public Graniastoslup(double a, double h, double H) {
        super(a,h);
        this.H = H;
        this.Pp = 2*super.getP()+3*a*H;
        this.V = H*super.getP();
    }

    public void show() {
        System.out.println(Pp);
        System.out.println(V);
    }

}
