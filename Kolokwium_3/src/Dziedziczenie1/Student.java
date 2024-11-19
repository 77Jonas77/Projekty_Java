package Dziedziczenie1;

import Dziedziczenie1.Person;

public
class Student
        extends Person {

    protected int sNumber;

    public Student(String name, int birthYear, int sNumber) {
        super(name, birthYear);
        this.sNumber = sNumber;
    }

    public void show() {
        System.out.println(
                "Student(" +
                        "Person(name: " + super.name + ")," +
                        "sNumber: " + sNumber + ")"
        );
    }

    //    public String xd(){
//        return "heheeehhehehe";
//    }
    public String xd(Object e) {
        return "heheeehhehehe";
    }

    public String toString() {
        return "Student("
                + super.toString()
                + ", sNumber: " + sNumber + ")";
    }
}
