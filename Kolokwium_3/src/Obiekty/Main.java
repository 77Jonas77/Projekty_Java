package Obiekty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String str = "1123";
        int x = str.length();
        Pattern pattern = Pattern.compile("\\d{2}-[A-Z][a-z]{1,2}-\\d{4}");
        //Pattern pattern = Pattern.compile("\\d{2}-([A-Za-z][1-9])*-\\d{4}");
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.find());


        boolean f;
        int wtt = 0;
//        System.out.println(f);
//        System.out.println(wtt);
    }
    public void dodaj(String x){
        System.out.println(x);
    }

    private static String dodaj(String x, int y){
        return x;
    }

//    private void dodaj(String x, int z){
//        System.out.println(x);
//    }
}
