package Zad20_2_B;

public class Mein_Stack {

    private Student firstStudent;

    private int max_size;

    public Mein_Stack(int size) {
        this.firstStudent = null;
        this.max_size = size;
    }

    public void push(int sNumber, String name) {
        if (this.firstStudent == null)
            this.firstStudent = new Student(sNumber, name);
        else {
            Student tmp = this.firstStudent;
            while (tmp.getNextStudent() != null)
                tmp = tmp.getNextStudent();
            tmp.setNextStudent(new Student(sNumber, name));
        }
    }

    public Student pop() {
        Student last = firstStudent;
        if(firstStudent!=null) {
            Student tmp = this.firstStudent;
            while (tmp.getNextStudent() != null) {
                tmp = tmp.getNextStudent();
                last = tmp;
            }
            Student tmp2 = firstStudent;
            if (tmp2.getNextStudent() != null) {
                while (tmp2.getNextStudent() != last)
                    tmp2 = tmp2.getNextStudent();
                tmp2.deleteLastStudent();
            } else {
                firstStudent = null;
            }
        }else {
            return new Student(00000, "Brak obiektow na stosie");
        }
        return last;
    }

    public boolean if_empty() {
        return firstStudent == null;
    }

}
