package Listy_w_druga_str;

public class MyList {
    Element head;

    public void addAtStart(String str){
        if(head == null){
            this.head = new Element(str);
        }else{
            Element tmp = new Element(str);
            tmp.next = this.head;
            this.head = tmp;
        }
    }

    public void show() {
        Element tmp = head;
        while(tmp != null) {
            System.out.println(tmp.text);
            tmp = tmp.next;
        }
    }

    public void addAtEnd(String str){
        if(this.head == null){
            this.head = new Element(str);
        }else{
            Element tmp = this.head;
            while(tmp.next!=null){
                tmp = tmp.next;
            }
            tmp.next = new Element(str);
        }
    }

}
