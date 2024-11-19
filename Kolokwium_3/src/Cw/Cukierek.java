package Cw;

public class Cukierek {

    private String smakc;
    private int nr;

    public Cukierek(int nr, String smakc){
        this.nr = nr;
        this.smakc = smakc;
    }

    public String toString(){
        return "Cukierek nr " + this.nr + " smak: " + smakc;
    }
}
