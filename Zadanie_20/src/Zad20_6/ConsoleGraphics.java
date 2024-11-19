package Zad20_6;

public class ConsoleGraphics {
    public int width;
    public int height;

    public ConsoleGraphics(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public double[] generateRandomPoint() {
        double[] loc = new double[2];
        loc[0] = (int) (Math.random() * this.width);
        loc[1] = (int) (Math.random() * this.height);

        return loc;
    }

    public double[] calculateEquation(double[] p1, double[] p2) {
        double a, b;
        a = (p1[1] - p2[1]) / (p1[0] - p2[0]);
        b = p1[1] - a * p1[0];


        double[] equation = new double[2];
        equation[0] = a;
        equation[1] = b;

        return equation;
    }

    public void show(double[] point1) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (point1[1] == i && point1[0] == j) {
                    System.out.print("X");
                }
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void show(double[] point1, double[] point2) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (point1[1] == i && point1[0] == j) {
                    System.out.print("1");
                } else if (point2[1] == i && point2[0] == j) {
                    System.out.print("2");
                } else {
                    System.out.println(' ');
                }
            }
            System.out.println();
        }

    }

    public void show(double[] point1, double[] point2, double[] equation) {
        double a = equation[0];
        double b = equation[1];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (point1[1] == i && point1[0] == j) {
                    System.out.print("1");
                } else if (point2[1] == i && point2[0] == j) {
                    System.out.print("2");
                } else if (Math.round(a * j + b) == i) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void show(double[] point1, double[] point2, double[] point3, double[] point4, double[] eq1, double[] eq2, double[] eq3, double[] eq4) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (point1[0] == j && point1[1] == i) {
                    System.out.print("1");
                } else if (point2[0] == j && point2[1] == i) {
                    System.out.print("2");
                } else if (point3[0] == j && point3[1] == i) {
                    System.out.print("3");
                } else if (point4[0] == j && point4[1] == i) {
                    System.out.print("4");
                }else if (i >= (int)(eq1[0] * j + eq1[1]) && i >= (int)(eq2[0] * j + eq2[1]) && i <= (int)(eq3[0] * j + eq3[1]) && i >= (int)(eq4[0] * j + eq4[1]) && j >=3) {
                    System.out.print("*");
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }

    }

//    boolean isInTrapezoid(double[] equation1, double[] equation2, double[] point1, double[] point2, int x,int y){
//
//    }

}
