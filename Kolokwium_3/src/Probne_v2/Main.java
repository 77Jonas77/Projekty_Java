package Probne_v2;

public class Main {
    public static void main(String[] args) {
        Fabryka fabryka = new Fabryka("truskawkowy");
        Cukierek[] tab = fabryka.produkuj(10);
        try {
            for (int i = 0; i < tab.length; i++)
                System.out.println(tab[i]);
            System.out.println(fabryka.produkuj(10)[9]);
            System.out.println(fabryka.produkuj(5)[5]);
        } catch (Exception ex) {
            System.out.println("My fault");
        }
    }
}