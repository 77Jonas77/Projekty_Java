package Zadanie21_3;

public class DrzewoOwocowe extends DrzewoLisciaste {
    protected String czypestki;

    public DrzewoOwocowe(int wysokosc, String przekrojDrzewa, int ksztaltLiscia) {
        super(wysokosc, przekrojDrzewa, ksztaltLiscia);
        this.czypestki = "xxx";
    }

    public String toString() {
        return
                "DrzewoOwocowe(" + super.toString()
                        + ", czypestki: " + czypestki + ")";
    }

}

