package probne_kolokwium;

public class Cukierek {
    public String smakc;
    public  int nrcukierka;
    public static int lacznie = 0;

    public Cukierek(String smakc){
        this.smakc=smakc;
        this.lacznie = this.lacznie+1;
        this.nrcukierka = lacznie;
    }

    @Override
    public String toString(){
        return "Cukierek nr " + this.nrcukierka + "smak: " + this.smakc;
    }
}