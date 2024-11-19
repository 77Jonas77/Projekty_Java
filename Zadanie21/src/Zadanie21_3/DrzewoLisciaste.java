package Zadanie21_3;

public class DrzewoLisciaste extends Drzewo {

    protected int ksztaltLiscia;

    public DrzewoLisciaste(int wysokosc, String przekrojDrzewa, int ksztaltLiscia) {
        super(wysokosc, przekrojDrzewa);
        this.ksztaltLiscia = ksztaltLiscia;
    }

    public String toString() {
        return
                "DrzewoLisciaste(" + super.toString()
                        + ", ksztaltLiscia: " + ksztaltLiscia + ")";
    }

    public Owoc zerwijOwoc() throws DrzewoBezOwocoweException {
        throw new DrzewoBezOwocoweException("Drzewo nie jest drzewem owocowym");
    }
}
