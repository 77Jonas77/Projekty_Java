package Zadanie21_1;

public class DrzewoOwocowe extends DrzewoLisciaste {
    protected String nazwaOwoca;

    public DrzewoOwocowe(boolean wiecznieZielone, int wysokosc, String przekrojDrzewa, int ksztaltLiscia, String nazwaOwoca) {
        super(wiecznieZielone, wysokosc, przekrojDrzewa, ksztaltLiscia);
        this.nazwaOwoca = nazwaOwoca;
    }

    public String toString() {
        return
                "DrzewoOwocowe("  +super.toString()
                        + ", nazwaOwoca: " + nazwaOwoca + ")";
    }
}
