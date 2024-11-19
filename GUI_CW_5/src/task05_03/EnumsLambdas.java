package task05_03;

import java.util.Arrays;
import java.util.Comparator;

enum Country {
    PL, NL, DE
}

enum Sex {
    F, M
}

enum Size {
    XS(1), S(2), M(3), L(4), XL(5);

    private int value;

    Size(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class Person {
    private String name;
    private Sex sex;
    private Size size;
    private Country country;

    public Person(String name, Sex sex, Size size, Country country) {
        this.name = name;
        this.sex = sex;
        this.size = size;
        this.country = country;
    }

    @Override
    public String toString() {
        return switch (country) {
            case PL -> name + "(" + sex + "," + size + "," + "Polska)";
            case NL -> name + "(" + sex + "," + size + "," + "Nederland)";
            case DE -> name + "(" + sex + "," + size + "," + "Deutschland)";
        };
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public int getSize() {
        return size.getValue();
    }

    public Country getCountry() {
        return country;
    }
}

public class EnumsLambdas {

    public static <T> void printArray(String title, T[] persons) {
        System.out.println("     *** " + title + " ***");
        for (T p : persons) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) {
        Person[] persons = {
                new Person("Max", Sex.M, Size.XL, Country.NL),
                new Person("Jan", Sex.M, Size.S, Country.PL),
                new Person("Eva", Sex.F, Size.XS, Country.NL),
                new Person("Lina", Sex.F, Size.L, Country.DE),
                new Person("Mila", Sex.F, Size.S, Country.DE),
                new Person("Ola", Sex.F, Size.M, Country.PL),
        };

        Comparator<Person> sexThenSize = (p1, p2) -> {
            int res = p1.getSex().compareTo(p2.getSex());
            if (res == 0) {
                res = p1.getSize() - p2.getSize();
            }
            return res;
        };

        Arrays.sort(persons, sexThenSize);
        printArray("Persons by sex and then size", persons);

        Arrays.sort(persons, (p1, p2) -> {
            int res = p1.getSize() - p2.getSize();
            if (res == 0) {
                res = p1.getSex().compareTo(p2.getSex());
            }
            return res;
        });
        printArray("Persons by size and then name", persons);

        Country[] countries = Country.values();
        Arrays.sort(countries, (c1, c2) -> {
            return c1.compareTo(c2);
        });
        printArray("Countries by name", countries);
    }
}
