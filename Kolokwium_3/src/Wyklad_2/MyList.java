package Wyklad_2;

public
class MyList {

    private Element head;

    public void add(Cookie cookie){
        if(head == null)
            this.head = new Element(cookie);
        else {
            Element tmp = new Element(cookie);
            tmp.setNext(this.head);
            this.head = tmp;
        }
    }

    public void show(){
        Element tmp = head;
        while(tmp!=null){
            tmp.getData().show();
            tmp = tmp.getNext();
        }
    }


    public MyList filter(int weight){
        MyList tmpStorage = new MyList();
        Element tmp = head;
        while(tmp!=null){
            if(tmp.getData().getWeight() == weight)
                tmpStorage.add(tmp.getData());
            tmp = tmp.getNext();
        }
        return tmpStorage;
    }

    public MyList filter(String ingr){
        MyList tmpStorage = new MyList();
        Element tmp = head;
        while(tmp!=null){
            if(tmp.getData().getIngr().equals(ingr))
                tmpStorage.add(tmp.getData());
            tmp = tmp.getNext();
        }
        return tmpStorage;
    }

}
