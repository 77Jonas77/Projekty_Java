package Zadanie21_3;

public class Main_3 {
    public static void main(String[] args) throws DrzewoBezOwocoweException {
        Drzewo[] las = {new Morelowiec(), new Deby(), new Modrzew(), new Sosny()};

        for(int i=0;i< las.length;i++){
            System.out.println(las[i].toString());
            System.out.println();
        }
        System.out.println("============");

        for(int i=0;i< las.length;i++){
            System.out.println(las[i].zerwijOwoc());
        }

    }
}
