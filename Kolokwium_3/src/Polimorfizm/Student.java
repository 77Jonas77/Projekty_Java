package Polimorfizm;

import java.util.Objects;

public
class Student
        extends Person {

    protected int sNumber;

    public Student(String name, int birthYear, int sNumber) {
        super(name, birthYear);
        this.sNumber = sNumber;
    }

    public void show(){
        super.show();
        System.out.println(
                "Student(" +
                        "Person(name: "+super.name+"),"+
                        "sNumber: "+sNumber+")"
        );
    }

    public void fun(){}

    public String toString(){
        return "Student("
                + super.toString()
                + ", sNumber: "+sNumber+")";
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if( this == obj)
            return true;

        if(obj instanceof Student) {
            Student s = (Student) obj;
//            if(
//                this.sNumber == s.sNumber
//                && this.name.equals(s.name)
//                && this.birthYear == s.birthYear
//            )
//                return true;
//            else
//                return false;
            return (
                    this.sNumber == s.sNumber
                            && super.equals(s)
            );
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sNumber);
    }
}
