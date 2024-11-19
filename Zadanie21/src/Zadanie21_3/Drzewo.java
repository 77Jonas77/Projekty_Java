package Zadanie21_3;

import Zadanie21_2.Gruszka;
import Zadanie21_2.Jablko;
import Zadanie21_2.Owoc;
import Zadanie21_2.Pomarancza;

public class Drzewo {

    protected boolean wiecznieZielone;
    protected int wysokosc;
    protected String przekrojDrzewa;

    public Drzewo(int wysokosc, String przekrojDrzewa) {
        this.przekrojDrzewa = przekrojDrzewa;
        this.wysokosc = wysokosc;
    }

    public String toString() {
        return
                "Drzewo("  + super.toString() +",wysokosc: " + wysokosc + ", przekrojDrzewa: " +przekrojDrzewa+ ")";
    }

    public DrzewoOwocowe zerwijOwoc() throws DrzewoBezOwocoweException {
        throw new DrzewoBezOwocoweException("Drzewo nie jest drzewem owocowym");
    }
}
