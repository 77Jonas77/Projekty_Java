package Probne_v2;

public class Fabryka {
    public static int count;
    public String smak;

    public Fabryka(String smak) {
        this.smak = smak;
        this.count = 0;
    }

    public Cukierek[] produkuj(int ile) {
        System.out.println("Produkcja " + ile + " cukierkow o smaku: " + this.smak);
        Cukierek[] arr = new Cukierek[ile];
        for (int i = 0; i < ile; i++) {
            if (++count % 5 == 0) {
                System.out.println("Wyprodukowano 5 cukierkow");
            }
            if (i <= 4) {
                arr[i] = new Cukierek("truskawkowy11");
            }
            if(i>4)
                arr[i] = new Cukierek("truskawkowy");
        }
        return arr;
    }
}
