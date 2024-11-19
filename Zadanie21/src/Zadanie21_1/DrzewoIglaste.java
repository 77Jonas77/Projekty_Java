package Zadanie21_1;

public
class DrzewoIglaste extends Drzewo {

    protected int iloscIgiel;
    protected double dlugoscSzyszki;

    public DrzewoIglaste(boolean wiecznieZielone, int wysokosc, String przekrojDrzewa, double dlugoscSzyszki, int iloscIgiel) {
        super(wiecznieZielone, wysokosc, przekrojDrzewa);
        this.dlugoscSzyszki = dlugoscSzyszki;
        this.iloscIgiel = iloscIgiel;
    }

    public String toString() {
        return
                        "DrzewoIglaste("  + super.toString()
                        +  " dlugoscSzyszki: " + dlugoscSzyszki + ", iloscIgiel " + iloscIgiel + ")";
    }
}
