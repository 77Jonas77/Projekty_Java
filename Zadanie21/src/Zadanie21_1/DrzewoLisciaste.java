package Zadanie21_1;

public class DrzewoLisciaste extends Drzewo {

    protected int ksztaltLiscia;

    public DrzewoLisciaste(boolean wiecznieZielone, int wysokosc, String przekrojDrzewa, int ksztaltLiscia) {
        super(wiecznieZielone, wysokosc, przekrojDrzewa);
        this.ksztaltLiscia = ksztaltLiscia;
    }

    public String toString() {
        return
                "DrzewoLisciaste(" + super.toString()
                        + ", ksztaltLiscia: " + ksztaltLiscia + ")";
    }
}
