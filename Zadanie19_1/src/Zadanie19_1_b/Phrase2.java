package Zadanie19_1_b;

public class Phrase2 {
    Element head;

    void addWordAtEnd(String word) {
        if(head==null){
            this.head = new Element(word);
        }else{
            Element tmp = new Element(word);
            tmp.setNext(head);
            this.head = tmp;
        }
    }
    void show(){
        Element tmp = this.head;
        while(tmp!=null){
            System.out.println(tmp.getWord());
            tmp = tmp.getNext();
        }
    }

}
