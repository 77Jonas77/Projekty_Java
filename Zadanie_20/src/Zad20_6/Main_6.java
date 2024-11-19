package Zad20_6;

public class Main_6 {
    public static void main(String[] args) {
        ConsoleGraphics ex = new ConsoleGraphics(15,15);

        double[] point1 = {3,14};
        double[] point2 = {10,5};
        double[] point3 = {10,10};
        double[] point4 = {3,0};

        double[] eq1 = ex.calculateEquation(point2,point4);
        double[] eq2 = ex.calculateEquation(point2,point3);
        double[] eq3 = ex.calculateEquation(point1,point3);
        double[] eq4 = ex.calculateEquation(point1,point4);

        double[] rand = ex.generateRandomPoint();


        ex.show(rand);
        System.out.println("====================");
        ex.show(point1,point2);
        System.out.println("====================");
        ex.show(point1, point2, eq1);
        System.out.println("====================");
        ex.show(point1, point2, point3, point4, eq1, eq2, eq3, eq4);
        System.out.println("====================");

    }
}
