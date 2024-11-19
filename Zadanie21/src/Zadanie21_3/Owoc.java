package Zadanie21_3;

public class Owoc extends DrzewoOwocowe{
    protected String nazwa;
    protected double masa;

    public Owoc(int wysokosc, String przekrojDrzewa, int ksztaltLiscia, String nazwa, double masa) {
        super(wysokosc, przekrojDrzewa, ksztaltLiscia);
        this.masa = masa;
        this.nazwa = nazwa;
    }

    public double getMasa() {
        return masa;
    }

    public String getNazwa() {
        return nazwa;
    }
}
