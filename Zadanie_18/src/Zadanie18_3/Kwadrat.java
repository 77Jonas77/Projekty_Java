package Zadanie18_3;

public class Kwadrat {
    private static int bok;
    public Kwadrat(int dL_bok){
        this.bok = dL_bok;

    }
    //ewentualnie storzenie pol odpowiadajacych obj i pp --> wtedy reszta
    public static void show(){
        System.out.println("pole powierzchni: " + bok*bok + " objetosc szescianu zbudow na podst tego kwadratu: " + bok*bok*bok);
    }
}
