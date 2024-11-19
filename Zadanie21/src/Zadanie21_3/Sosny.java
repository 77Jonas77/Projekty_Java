package Zadanie21_3;

import Zadanie21_2.Drzewo;

public class Sosny extends DrzewoIglaste {
    protected String nazwa;

    public Sosny() {
        super((int)(Math.random()*20),"duzy", (int)(Math.random()*10));
        this.nazwa = "Sosna";
    }

    public String toString() {
        return
                "Sosny("  +super.toString()
                        + ", nazwa: " + nazwa + ")";
    }

    public Owoc zerwijOwoc() throws DrzewoBezOwocoweException {
        throw new DrzewoBezOwocoweException("Drzewo nie jest drzewem owocowym");
    }
}
