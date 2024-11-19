package Zad22_5;

public class Komorka extends Telefon {
    public String[] lastCalls;
    int c;

    public Komorka(String interfejsKomunikacyjny, String color) {
        super(interfejsKomunikacyjny, color);
        this.lastCalls = new String[10];
    }
    public void zadzwon(String nr){
        super.zadzwon(nr);
        this.lastCalls[c++] = nr;
        if(c==10) {
            String[] tmp = new String[11];
            for (int i = 9; i >= 0; i--) {
                tmp[9-i] = this.lastCalls[i];
            }
            this.lastCalls = tmp;
            c -= 10;
        }
    }

    public void dodajDoZnajomych(String imie, String naz, String nr){
        System.out.println("Brak takiej funkcji :( - komorka ");
    }
    public void wyswietlHistoriePolaczen(){
        System.out.println("=====HistoriaPolaczenKomorka=====");
        for(int i=9;i>=0;i--){
            System.out.println(this.lastCalls[i]);
        }
    }

    public Osoba[] getZnajomi() {
        return null;
    }
}
