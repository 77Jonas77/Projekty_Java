package Zadanie19_3;

public class Person {
    private String name;
    private int birthYear;

    public Person(String name, int birthYear){
        this.name = name;
        this.birthYear = birthYear;
    }

    public Person(String name){
        this.name = name;
        this.birthYear = 1990;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return 2022-this.birthYear;
    }
    public static Person getOlder(Person o1, Person o2){
        if(o1.birthYear < o2.birthYear)
            return o1;
        return o2;
    }

    public static int index(Person[] ppl) {
        int max = 0;
        int indx = 0;
        for (int i = 0; i < ppl.length; i++) {
            if (ppl[i].getAge() > max) {
                max = ppl[i].getAge();
            }
        }
        for (int i = 0; i < ppl.length; i++) {
            if (ppl[i].getAge() == max) {
                indx++;
            }
        }
        return indx;
    }

    public static Person[] getOldest(Person[] ppl){
        int max = 0;
        for (int i = 0; i < ppl.length; i++) {
            if (ppl[i].getAge() > max) {
                max = ppl[i].getAge();
            }
        }

        Person[] theOldest = new Person[index(ppl)];

        int spel = 0;
        for(int i=0;i<ppl.length;i++){
            if(ppl[i].getAge() == max){
                theOldest[spel++]=ppl[i];
            }
        }
        return theOldest;
    }

}
