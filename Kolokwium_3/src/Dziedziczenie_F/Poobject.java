package Dziedziczenie_F;

import Polimorfizm.Person;
import Polimorfizm.Student;

import java.util.Objects;

public class Poobject extends Object{

    public String name;
    public Poobject(String name){
        this.name = name;
    }

    public String toString(){
        return "Poobject(" + "Object(" + super.toString() + ")" + name +")";
    }
    //=================================
//    @Override
//    public boolean equals(Object o){
//        if(this == o) return true;
//        if(o == null || getClass() != o.getClass()) return false;
//        Person person = (Person) o;
//        return birthyear == person.birthyear && Object.equals(name,person.name);
//    }

    //=================================
//    @Override
//    public boolean equals(Object o){
//        if(o == null)
//            return false;
//        if(this == o){
//            return true;
//        }
//        if(o instanceof Student){
//            Student s = (Student) o;
//            return(this.sNumber == s.sNumber && super.equals(s));
//        }else{
//            return false;
//        }
//    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}