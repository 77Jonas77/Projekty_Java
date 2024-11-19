package Zad20_2_B;

import Zad20_2.MyStack;
import Zad20_2.Student;

public class Main2_B {
    public static void main(String[] args) {
        Mein_Stack stos = new Mein_Stack(3);
        stos.push(2312, "Ania");
        stos.push(2322, "Marek");
        stos.push(2362, "Mirek");
        stos.push(2372, "Arek");
        stos.push(2352, "Romek");
        System.out.println("start usuwania");

        stos.pop().show();
        stos.pop().show();
        stos.pop().show();
        stos.pop().show();
        stos.pop().show();
        System.out.println(
                stos.if_empty()
        );
        //mam nadzieje, ze to o to chodzilo
    }
}
