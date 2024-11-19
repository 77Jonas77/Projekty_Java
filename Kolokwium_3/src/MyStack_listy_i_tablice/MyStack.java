package MyStack_listy_i_tablice;

import Polimorfizm.Student;

public class MyStack {
    private int count;
    private int max_size;
    private Student[] arr;

    public MyStack(int max_size){
        this.arr = new Student[max_size];
        this.count = max_size;
        this.max_size = max_size;
    }

    public boolean isEmpty(){
        return count == max_size;
    }

    public void push(Student student){
        if(count < 1){
            Student[] tmp = new Student[arr.length + 1];
            for(int i=0;i<arr.length;i++){
                tmp[tmp.length-i-1] = arr[arr.length-i-1];
            }
            arr=tmp;
            this.max_size = arr.length;
            ++count;
        }
        arr[--count] = student;
    }

//    public Student pop(){
//        if(!isEmpty()){
//            Student last = arr[count];
//            Student[] tmp = new Student[arr.length + 1];
//            count++;
//            for(int i=0;i<arr.length;i++){
//                if(i<count){
//                    tmp[i] = null;
//                }else{
//                    tmp[i] = arr[i];
//                }
//            }
//            arr=tmp;
//            return last;
//        }else{
//            return new Student("NIE MA NIC NA STOSIE", 000);
//        }
//    }


}
