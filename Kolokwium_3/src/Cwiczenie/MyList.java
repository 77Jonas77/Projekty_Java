package Cwiczenie;

public class MyList {
    private Element head;

    public MyList(){
    }

    public void add(Cookie cookie){
        if(head==null){
            head = new Element(cookie);
        }else{
            Element tmp = new Element(cookie);
            tmp.setNext(head);
            this.head = tmp;
        }
    }

    public void show(){
        Element tmp = head;
        if(head == null){
            System.out.println("pusta");
        }else{
            while(tmp!=null){
                tmp.getData().show();
                tmp = tmp.getNext();
            }
        }
    }

    public void addAtEnd(Cookie cookie){
        if(head == null){
            head = new Element(cookie);
        }else{
            Element tmp = head;
            while(tmp.getNext()!=null) {
                tmp = tmp.getNext();
            }
            tmp.setNext(new Element(cookie));
        }
    }
}
