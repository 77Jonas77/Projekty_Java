package Probne_v2;

public class Cukierek {
    public String smakc;
    public static int nrcukierka;

    public Cukierek(String smakc){
        this.smakc = smakc;
        this.nrcukierka = this.nrcukierka + 1;
    }

    @Override
    public String toString(){
        return "Cukierek nr " + this.nrcukierka + " smak: " + this.smakc;
    }
}
