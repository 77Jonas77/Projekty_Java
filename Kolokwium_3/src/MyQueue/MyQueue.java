package MyQueue;

public class MyQueue {
    private int pocz;
    private int koniec;
    private int max_size;
    private String[] arr;

     public MyQueue(int max_size){
         arr = new String[max_size];
         this.koniec = -1;
         this.pocz = -1;
     }

     public boolean isEmpty(){
         return this.koniec == -1 && this.pocz == -1;
     }

     public void dodaj(String word){
         if(isEmpty()){
             this.pocz = 0;
             this.koniec = 0;
             arr[koniec++] = word;
         }else if(arr.length-1>=koniec){
             arr[koniec++] = word;
         }else{
             System.out.println("brak miejsca");
         }
     }

    public void getClub(){
         if(isEmpty()) {
             System.out.println("jest pusta");
         }else if(this.pocz == this.koniec){
             int tmp = this.pocz;
             this.pocz = -1;
             this.koniec = -1;
             System.out.println(arr[tmp]);
         }else{
             System.out.println(arr[pocz++]);
         }
    }
}
