package Zad20_2;

public class Student {
    private int sNumber;
    private String name;

    public Student(int sNumber, String name){
        this.sNumber = sNumber;
        this.name = name;
    }

    public void show(){
        System.out.println(this.sNumber + " " + this.name);
    }

}
