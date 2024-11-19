package Cw;

public class Fabryka {
    private String smak;
    private int count;
    public Fabryka(String smak){
        this.smak = smak;
        this.count = 0;
    }

    public Cukierek[] produkuj(int ile){
        System.out.println("Produkcja " + ile + " " + "cukierkow o smaku: " + this.smak);
        Cukierek[] arr = new Cukierek[ile];
        for(int i=1;i<=ile;i++){
            if(++count%5==0){
                System.out.println("Wyprodukowano 5 cukierkow");
            }
            if(i<=5){
                arr[i-1] = new Cukierek(count,"truskawkowy11");
            }
            if(i>5){
                arr[i-1] = new Cukierek(count,"truskawkowy");
            }
        }
        return arr;
    }
}
