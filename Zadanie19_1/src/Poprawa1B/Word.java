package Poprawa1B;

public class Word {
    private String str;
    private Word nextWord;

    public Word(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public Word getNextWord() {
        return nextWord;
    }

    public void setNextWord(Word nextWord) {
        this.nextWord = nextWord;
    }
}
