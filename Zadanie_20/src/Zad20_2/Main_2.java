package Zad20_2;

public class Main_2 {
    public static void main(String[] args) {
        MyStack stos = new MyStack(3);
        stos.push(new Student(4234, "Kamil"));
        stos.push(new Student(4232, "Ala"));
        stos.push(new Student(4233, "Patrycja"));
        stos.push(new Student(42344, "Arek"));
        stos.push(new Student(4255, "Marek"));

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
        stos.pop().show();
    }
}
