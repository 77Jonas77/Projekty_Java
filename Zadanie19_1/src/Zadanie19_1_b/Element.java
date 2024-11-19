package Zadanie19_1_b;

public class Element {
    Element next;
    String word;

    public Element(String Word){
        this.next = null;
        this.word = Word;
    }

    public Element getNext() {
        return next;
    }

    public void setNext(Element next) {
        this.next = next;
    }

    public String getWord() {
        return word;
    }

}