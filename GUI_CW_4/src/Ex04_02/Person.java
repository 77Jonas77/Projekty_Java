package Ex04_02;

public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person(" +
                "name: '" + name +
                ", age:" + age +
                ')';
    }

    public int compareTo(Person p) {
        int diff = this.getAge() - p.getAge();
        if (diff < 0) {
            return -1;
        } else if (diff == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public static void sort(Person[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                switch (arr[j].compareTo(arr[j + 1])) {
                    case -1 -> {}
                    case 0 -> {}
                    case 1 -> {
                        Person tmp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = tmp;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Person[] tab = {
                new Person("Jacek", 22),
                new Person("Ela", 21),
                new Person("Mela", 20),
                new Person("Mati", 19),
                new Person("Kris", 18),
        };

        sort(tab);

        for(Person p: tab){
            System.out.println(p);
        }
    }
}