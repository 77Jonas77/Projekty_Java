package Dziedziczenie1;

public
class Person
        extends Object {

    protected String name;
    protected int birthYear;

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public Person(){
    }
    public void show(){
        System.out.println(
                "Person(name: "+name
                        + ", birthYear: "+birthYear+")"
        );
    }
    public void show(int x){
        System.out.println(x);
    }
    public String xd(){
        return "haha";
    }

    public static String xd(int x){
        return "c";
    }
    public String toString(){
        return "Person(Object("+"name: "+name+ "), birthYear: "+birthYear+")";
    }
}
