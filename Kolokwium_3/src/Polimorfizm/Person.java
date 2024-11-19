package Polimorfizm;

import java.util.Objects;

public
class Person
        extends Object {

    protected String name;
    protected int birthYear;

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public void show() {
        System.out.println(
                "Person(name: " + name
                        + ", birthYear: " + birthYear + ")"
        );
    }

    public String toString() {
        return
                "Person(Object(" + super.toString()
                        + "), name: " + name +
                        ", birthYear: " + birthYear + ")";
    }
    Student createItem(){
        Student student = new Student("xd",2022,1111);
        return student;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return birthYear == person.birthYear && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthYear);
    }
}
