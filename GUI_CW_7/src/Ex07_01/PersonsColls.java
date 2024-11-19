package Ex07_01;

import java.util.*;

import static java.lang.System.out;

public class PersonsColls {
    public static void main (String[] args) {

        List<Person> list = Arrays.asList(
                new Person("Alice", 2000),
                new Person("Brenda", 2001),
                new Person("Cecilia", 2002)
        );

        out.println(Person.isInColl(list, "Brenda", 2001));
        out.println(Person.isInColl(list, "Debby", 2001));

        Set<Person> tSet = new TreeSet<>(list);
        out.println(Person.isInColl(tSet, "Brenda", 2001));
        out.println(Person.isInColl(tSet, "Debby", 2001));

        Set<Person> hSet = new HashSet<>(list);
        out.println(Person.isInColl(hSet, "Brenda", 2001));
        out.println(Person.isInColl(hSet, "Debby", 2001));
    }
}

class Person implements Comparable<Person>{
    private String name;
    private int dateBirth;

    public Person(String name, int dateBirth) {
        this.name = name;
        this.dateBirth = dateBirth;
    }

    public static boolean isInColl(Collection<Person> coll, String name, int year){
        return coll.contains(new Person(name,year));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return dateBirth == person.dateBirth && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateBirth);
    }

    @Override
    public int compareTo(Person other) {
        if (this.dateBirth != other.dateBirth) {
            return Integer.compare(this.dateBirth, other.dateBirth);
        }
        return this.name.compareTo(other.name);
    }

}


