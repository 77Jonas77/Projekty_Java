package Zadanie21_1;

public class Drzewo extends Object {
    protected boolean wiecznieZielone;
    protected int wysokosc;
    protected String przekrojDrzewa;

    public Drzewo(boolean wiecznieZielone, int wysokosc, String przekrojDrzewa) {
        this.przekrojDrzewa = przekrojDrzewa;
        this.wysokosc = wysokosc;
        this.wiecznieZielone = wiecznieZielone;
    }


    public String toString() {
        return

                "Drzewo(Object(" + super.toString()
                        + ")" + " wiecznieZielone: " + wiecznieZielone +
                        ", wysokosc: " + wysokosc + ", przekrojDrzewa: " + przekrojDrzewa + ")";
    }

}
