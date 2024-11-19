package Zadanie21_3;

public class Morelowiec extends Owoc {
    protected String nazwa;
    public Morelowiec() {
        super((int)(Math.random()*20),"duzy", (int)(Math.random()*10),"morela", (int)(Math.random()*250+100));
        this.nazwa = "Morelowiec";
    }

    public String toString() {
        return
                "Morelowiec("  +super.toString()
                        + ", nazwa: " + nazwa + ")";
    }

    public Owoc zerwijOwoc(){
        return new Morelowiec();
    }

    @Override
    public String getNazwa() {
        return super.nazwa;
    }
}
