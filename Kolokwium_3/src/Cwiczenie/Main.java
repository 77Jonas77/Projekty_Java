package Cwiczenie;

public class Main {
    public static void main(String[] args) {
        MyList lista = new MyList();
        lista.add(new Cookie("placek",9));
        lista.add(new Cookie("wisniowiec",10));
        lista.add(new Cookie("makowiec",11));

        lista.show();

        MyStorage s = new MyStorage(2);
        s.add(new Cookie("essa1", 2));
        s.add(new Cookie("essa2", 3));
        s.add(new Cookie("essa3", 4));
        s.add(new Cookie("essa4", 6));
        s.add(new Cookie("essa5", 7));
        s.add(new Cookie("essa6", 9));

        s.show();
    }
}
