package Dziedziczenie1;

public class x extends Student{

    public x(String name, int birthYear, int sNumber) {
        super(name, birthYear, sNumber);
    }

    public String toString(){
        return "Student("
                + super.toString()
                + ", sNumber: "+sNumber+")";
    }
}
