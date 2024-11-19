package Zadanie21_3;

public
    class DrzewoIglaste extends Drzewo {

        protected int iloscIgiel;

        public DrzewoIglaste(int wysokosc, String przekrojDrzewa, int iloscIgiel) {
            super(wysokosc, przekrojDrzewa);
            this.iloscIgiel = iloscIgiel;
        }

        public String toString() {
            return
                    "DrzewoIglaste("  + super.toString()
                            + ", iloscIgiel " + iloscIgiel + ")";
        }
    public Owoc zerwijOwoc() throws DrzewoBezOwocoweException {
        throw new DrzewoBezOwocoweException("Drzewo nie jest drzewem owocowym");
    }

    }
