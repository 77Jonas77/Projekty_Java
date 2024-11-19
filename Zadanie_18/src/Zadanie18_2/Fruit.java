package Zadanie18_2;

public class Fruit {
    public static String name;
    public static double weight;

    public Fruit(String nazwa){
        this.weight = (Math.random()*0.8 +0.5);
        this.name = nazwa;
    }

    public void show(){
        System.out.println(this.name + " " + this.weight);
    }
}
