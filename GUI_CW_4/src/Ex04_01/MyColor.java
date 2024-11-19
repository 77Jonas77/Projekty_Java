package Ex04_01;

import java.awt.*;

public class MyColor
        extends java.awt.Color implements Comparable<MyColor>{
    private int red;
    private int green;
    private int blue;
    private int sum;

    public MyColor(int red, int green, int blue) {
        super(red,green,blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.sum = red+green+blue;
    }

    @Override
    public int getRed() {
        return red;
    }

    @Override
    public int getGreen() {
        return green;
    }

    @Override
    public int getBlue() {
        return blue;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "(" + this.red +"," +this.green + ","+this.blue + ")";
    }

    @Override
    public int compareTo(MyColor o) {
        return this.getSum() - o.getSum();
    }
}
