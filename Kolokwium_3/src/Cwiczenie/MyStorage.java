package Cwiczenie;

public class MyStorage {

    private int count;
    private Cookie[] arr;
    public MyStorage(int max_size){
        arr = new Cookie[max_size];
        count = 0;
    }

    public void add(Cookie cookie){
        if(this.count > arr.length - 1) {
            Cookie[] tmp = new Cookie[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                tmp[i] = arr[i];
            }
            arr=tmp;
            arr[count++] = cookie;
        }else{
            arr[count++] = cookie;
        }
    }

    public void show(){
        for(int i=0;i<count;i++){
            arr[i].show();
        }
    }

}
