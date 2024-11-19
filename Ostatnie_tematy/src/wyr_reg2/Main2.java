package wyr_reg2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2 {
    public static void main(String[] args) {
        String liczba = "3AB";
        int test = liczba.length();

        Pattern pattern = Pattern.compile("(\\d|[A-F]){"+test+"}");
        Matcher matcher = pattern.matcher(liczba);
        System.out.println(matcher.find());
    }
}
