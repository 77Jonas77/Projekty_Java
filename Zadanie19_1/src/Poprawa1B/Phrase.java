package Poprawa1B;

public class Phrase {

    public static void main(String[] args) {
        Phrase phrase = new Phrase();
        phrase.addWordAtEnd("Ala");
        phrase.addWordAtEnd("ma");
        phrase.addWordAtEnd("kota");
        phrase.addWordAtEnd("a");
        phrase.addWordAtEnd("kot");
        phrase.addWordAtEnd("ma");
        phrase.addWordAtEnd("Ale");

        phrase.show();
    }

    private Word firstWord;

    public Phrase() {
        this.firstWord = null;
    }

    public void addWordAtEnd(String str) {
        if (this.firstWord == null)
            this.firstWord = new Word(str);
        else {
            Word tmp = this.firstWord;
            while (tmp.getNextWord() != null)
                tmp = tmp.getNextWord();
            tmp.setNextWord(new Word(str));
        }
    }

    public void show() {
        Word tmp = this.firstWord;
        while (tmp != null) {
            System.out.println(tmp.getStr());
            tmp = tmp.getNextWord();
        }
    }

}
