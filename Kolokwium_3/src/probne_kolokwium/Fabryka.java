package probne_kolokwium;

public class Fabryka{
    String smak;
    int produkcja;

    public Fabryka(String smak){
        this.smak = smak;
    }

    public Cukierek[] produkuj(int ile){
        Cukierek[] arr = new Cukierek[ile];
        int count=0;
        System.out.println(("Produkcja " + ile  + " " + "cukierkow o smaku " + this.smak));
        for(int i=0;i<ile;i++){
            if(++count%5==0)
                System.out.println("Wyprodukowano 5 cukierków");
            if(i<=4){
                arr[i] = new Cukierek("truskawkowy11");
            }else{
                arr[i] = new Cukierek("truskawkowy");
            }
        }
        return arr;
    }
}
