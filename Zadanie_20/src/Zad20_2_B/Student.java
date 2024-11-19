package Zad20_2_B;

public class Student {

    public Student(int sNumber, String name){
        this.sNumber = sNumber;
        this.name = name;
    }
    private int sNumber;
    private String name;
    private Student NextStudent;

    public void show(){
        System.out.println(this.sNumber + " " + this.name);
    }

    public Student getNextStudent() {
        return NextStudent;
    }

    public void setNextStudent(Student NextStudent) {
        this.NextStudent = NextStudent;
    }

    public void deleteLastStudent(){ this.NextStudent = null;}
}
