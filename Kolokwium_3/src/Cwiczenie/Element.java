package Cwiczenie;

public class Element {
    private Cookie data;
    private Element next;

    public Element(Cookie data){
        this.data = data;
        this.next = null;
    }

    public void setNext(Element next) {
        this.next = next;
    }

    public Cookie getData() {
        return data;
    }

    public Element getNext() {
        return next;
    }
}
