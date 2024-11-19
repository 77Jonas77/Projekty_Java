package Zadanie21_3;

public class Modrzew extends DrzewoIglaste{
    protected String nazwa;

    public Modrzew() {
        super((int)(Math.random()*20),"sredni", (int)(Math.random()*10));
        this.nazwa = "Modrzew";
    }

    public String toString() {
        return
                "Modrzew("  +super.toString()
                        + ", nazwa: " + nazwa + ")";
    }

    public Owoc zerwijOwoc() throws DrzewoBezOwocoweException {
        throw new DrzewoBezOwocoweException("Drzewo nie jest drzewem owocowym");
    }

}
