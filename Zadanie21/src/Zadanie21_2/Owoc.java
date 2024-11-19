package Zadanie21_2;

public class Owoc {
    private String nazwa;
    private double masa;

    public Owoc(String nazwa, double masa){
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
