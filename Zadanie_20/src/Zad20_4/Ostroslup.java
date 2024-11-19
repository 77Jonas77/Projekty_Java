package Zad20_4;

public class Ostroslup extends Trojkat{
    private double Pp;
    private double V;
    private double H;
    private double H2;

    public Ostroslup(double a, double h, double H, double H2) {
        super(a,h);
        this.H = H;
        this.Pp = super.getP()+3*a*this.H;
        this.V = super.getP()*this.H2;
    }

    @Override
    public void show() {
        System.out.println(Pp);
        System.out.println(V);
    }
}
