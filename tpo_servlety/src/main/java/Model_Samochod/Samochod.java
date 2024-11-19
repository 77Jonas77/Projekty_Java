package Model_Samochod;

public class Samochod {

    public int id;
    public String marka;
    public String typ;
    public int rokProdukcji;

    public Samochod(int id, String marka, String rodzaj, int rokProdukcji) {
        this.id = id;
        this.marka = marka;
        this.typ = rodzaj;
        this.rokProdukcji = rokProdukcji;
    }

    @Override
    public String toString() {
        return "Samochod: " +
                "id=" + id +
                ", marka='" + marka + '\'' +
                ", rokProdukcji='" + rokProdukcji + '\'' +
                ", typ=" + typ + "::";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public void setRokProdukcji(int rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

}