package Zad20_2;

public class MyStack {
    private int count;
    private int max_size;
    private Student[] arr;

    public MyStack(int max_size) {
        arr = new Student[max_size];
        count = max_size;
        this.max_size = max_size;
    }

    public void push(Student student) {
        if (count < 1) {
            Student[] tmp = new Student[arr.length+1];
            for (int i = 0; i < arr.length; i++) {
                tmp[tmp.length-1-i] = arr[arr.length-1-i];
            }
            arr = tmp;
            this.max_size = arr.length;
            ++count;
        }
        arr[--count] = student;
    }

    public Student pop() {
        if (!if_empty()) {
            System.out.println(count);
            Student last = arr[count];
            Student[] tmp = new Student[arr.length+1];
            ++count;
            for(int i=0;i<arr.length;i++){
                if(i<count)
                    tmp[i] = null;
                else{
                    tmp[i] = arr[i];
                }
            }
            arr=tmp;
            return last;
        }else {
            return new Student(00000, "Brak obiektow na stosie");
        }
    }
    public boolean if_empty() {
        return count == this.max_size;
    }

}
