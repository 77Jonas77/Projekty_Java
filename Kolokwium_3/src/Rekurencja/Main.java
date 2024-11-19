package Rekurencja;

public class Main {
    public static void main(String[] args) {
        String str = "abccba";
        czypRek(str,0,str.length()-1);
    }
    public static boolean czypRek(String word, int first, int last){
        if(first>last){
            return true;
        }else{
            if(word.charAt(first)!=word.charAt(last)){
                return false;
            }else{
                return czypRek(word, first+1, last-1);
            }
        }

    }
}
