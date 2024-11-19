package Zad22_4;

import Zad22_3.Telefon;

public class Komorka extends Telefon {
    public String[] lastCalls;
    int c;

    public Komorka(String interfejsKomunikacyjny, String color) {
        super(interfejsKomunikacyjny, color);
        this.lastCalls = new String[10];
    }
    public void zadzwon(String nr){
        super.zadzwon(nr);
        lastCalls[c++] = nr;
        if(c==10) {
            String[] tmp = new String[11];
            for (int i = 9; i >= 0; i--) {
                tmp[9-i] = this.lastCalls[i];
            }
            this.lastCalls = tmp;
            c -= 10;
        }
    }

    public void show(){
        System.out.println("==========");
        for(int i=0;i<10;i++){
            System.out.println(this.lastCalls[i]);
        }
    }
}
