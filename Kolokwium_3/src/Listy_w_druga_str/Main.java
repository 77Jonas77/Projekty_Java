package Listy_w_druga_str;

public class Main {
    public static void main(String[] args) {
        MyList myList = new MyList();
//        myList.addAtStart("Ala");
//        myList.addAtStart("ma");
//        myList.addAtStart("kota");
//        myList.show();

        myList.addAtEnd("ma");
        myList.addAtStart("Ala");
        myList.addAtEnd("kota");
        myList.show();
    }
}
