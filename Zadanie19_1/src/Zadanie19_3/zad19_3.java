package Zadanie19_3;

public class zad19_3 {
    public static void sortP(Person[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j].getAge() > arr[j + 1].getAge()) {
                    Person tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Person p1 = new Person("Maciek", 2000);
        Person p2 = new Person("Niko", 1999);
        Person p3 = new Person("Gary", 2003);
        Person p4 = new Person("Limo", 1929);


        Person[] ppl = {p1, p2, p3, p4};

        System.out.println(
                p1.getName() + " " +
                        p1.getAge() + " " +
                        Person.getOlder(p1, p2).getAge() + " " + Person.getOlder(p1, p2).getName()
        );

        Person[] tab = new Person[Person.index(ppl)];
        tab = Person.getOldest(ppl);

        for (int i = 0; i < tab.length; i++) {
            if (tab[i].getName() != null) {
                System.out.println("Name: " + tab[i].getName() + " " + "Wiek: " + tab[i].getAge());
            }
        }

        System.out.println("===========");

        for (int i = 0; i < ppl.length; i++) {
            System.out.println("Name: " + ppl[i].getName() + " " + "Wiek: " + ppl[i].getAge());
        }

        System.out.println("===========");

        sortP(ppl);

        for (int i = 0; i < ppl.length; i++) {
            System.out.println("Name: " + ppl[i].getName() + " " + "Wiek: " + ppl[i].getAge());
        }
    }
}
