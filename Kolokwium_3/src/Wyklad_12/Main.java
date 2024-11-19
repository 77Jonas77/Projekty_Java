package Wyklad_12;

public
class Main {

    public static void main(String[] args) {
        Person person = new Student("Jan", 1867, 001);

        person.show();
        //person.fun();

        Person[] tab = {
                new Student("Ewa", 1950, 2465),
                new Person("Ala", 2006),
                person
        };

        for(Person p : tab){
            System.out.println("========");
            p.show();
        }
// ======================================
        Object[] arr = {
                new Student("Ewa", 1950, 2465),
                new Person("Ala", 2006),
                person
        };
        System.out.println("Object arr");
        for(Object obj : arr){
            System.out.println("============");
            System.out.println(obj);
        }
// ==============================================


        Student s1 = new Student("Kazik", 2001, 245678);
        Student s2 = new Student("Kazik", 2001, 245678);

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));

        System.out.println(
                s1.hashCode() == s2.hashCode()
        );
//==========================================================

        int[] dataTab = { 1, 2, 3};
        for(int i=0;i<6;i++) {
            try {
                System.out.println(dataTab[i] + " " + i);
                System.out.println("tu");
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("problem wielkosci tablicy");
                ex.printStackTrace();
            }
        }
        System.out.println("===================");
//==========================================================

        Object[] dataObj = new Object[500];
        for(int i=0; i<dataObj.length; i++)
            dataObj[i] = Math.random() > 0.2 ? new Object() : null;
        for(int i=0; i<dataObj.length; i++) {
            try {
                System.out.println(dataObj[i].toString());
            } catch (NullPointerException ex) {
                System.out.println("problem @ " + i);
            }
        }
    }

}
