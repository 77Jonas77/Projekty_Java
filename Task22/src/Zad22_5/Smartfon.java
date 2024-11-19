package Zad22_5;

import java.util.Objects;

public class Smartfon extends Komorka {
    public Osoba[] znajomi;
    int counter = 0;

    public Smartfon(String interfejsKomunikacyjny, String color) {
        super(interfejsKomunikacyjny, color);
        this.znajomi = new Osoba[10];
    }

    public void dodajDoZnajomych(String imie, String naz, String nr) {
        if (counter == znajomi.length) {
            Osoba[] tmp = new Osoba[znajomi.length + 10];
            for (int i = 0; i < znajomi.length; i++) {
                znajomi[i] = tmp[i];
            }
            znajomi = tmp;
        }
        znajomi[counter++] = new Osoba(imie, naz, nr);
    }

    @Override
    public Osoba[] getZnajomi() {
        return znajomi;
    }

    @Override
    public void zadzwon(String nr) {
        super.zadzwon(nr);
    }

    public void wyswietlHistoriePolaczen() {
        System.out.println("=====HistoriaPolaczenSmartfon=====");
        for (int i = 0; i < 10; i++) {
            boolean czyp = false;
            for (int j = 0; j < counter && !czyp; j++) {
                if (znajomi[j].getNumer().equals(lastCalls[i])) {
                    System.out.println(
                            znajomi[j].getImie() + ", " + znajomi[j].getNazwisko() + ", " + this.lastCalls[i]
                    );
                    czyp = true;
                }
            }
            if (!czyp) {
                System.out.println(
                        this.lastCalls[i]
                );
            }
        }
    }

}
