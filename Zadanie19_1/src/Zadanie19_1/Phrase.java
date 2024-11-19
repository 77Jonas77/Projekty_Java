package Zadanie19_1;

public class Phrase {

    //pierwszy sposob
    private String[] tab;
    void addWordAtEnd(String word) {
        if (tab != null) {
            String[] tmp = new String[tab.length + 1];
            for (int i = 0; i < tab.length; i++) {
                tmp[i] = tab[i];
            }
            tab = tmp;
            tab[tab.length - 1] = word;
        } else {
            String[] tmp = new String[1];
            tab = tmp;
            tab[0] = word;
        }

    }
    void show() {
        if (tab.length != 0) {
            for (String w : tab) {
                System.out.print(w + " ");
            }
        } else {
            System.out.println("BŁĄD");
        }
    }

}
