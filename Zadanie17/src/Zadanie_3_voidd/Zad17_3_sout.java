package Zadanie_3_voidd;

public class Zad17_3_sout {
    public static void wypiszOdSrodka(char[] word, int start){
        if(start>=0){
            System.out.print(word[start] + " ");
            wypiszOdSrodka(word,start-1);
            System.out.print(word[word.length-1-start] + " ");
        }
    }


    public static void main(String[] args) {
        char[] word = {'A','B','B','B','A'};

        wypiszOdSrodka(word, word.length == 1 ? 0 : word.length/2 - 1);
    }
}
