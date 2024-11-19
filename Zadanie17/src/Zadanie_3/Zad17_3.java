package Zadanie_3;

public class Zad17_3 {
    public static boolean wypiszOdSrodka(char[] arr, int start){
        if(start<0)
            return true;
        if(arr[start] == arr[arr.length-1-start]) {
            return wypiszOdSrodka(arr, start - 1);
        }
        return false;
    }

    public static void main(String[] args) {
        char[] word = {'A','B','B','A'};
        System.out.println(
        wypiszOdSrodka(word, word.length == 1 ? 0 : word.length/2 - 1)
        );
    }
}
