package Zadanie21_3;

public class Deby extends DrzewoLisciaste{
    protected String nazwa;

    public Deby() {
        super((int)(Math.random()*20),"duzy", (int)(Math.random()*10));
        this.nazwa = "Dab";
    }

    public String toString() {
        return
                "Deby("  +super.toString()
                        + ", nazwa: " + nazwa + ")";
    }
    public Owoc zerwijOwoc() throws DrzewoBezOwocoweException {
        throw new DrzewoBezOwocoweException("Drzewo nie jest drzewem owocowym");
    }
}
