package Wyklad_2;

public
class Element {

    private Cookie data;
    private Element next;

    public Element(Cookie cookie){
        this.data = cookie;
        this.next = null;
    }

    public Cookie getData() {
        return data;
    }

    public Element getNext() {
        return next;
    }

    public void setNext(Element next) {
        this.next = next;
    }
}
