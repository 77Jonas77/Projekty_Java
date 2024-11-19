package whatever;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    public static String metoda(double a){ return "x";}
    public static void main(String[] args) {
        System.out.println(metoda(0.5));
        String str = "\nword";
//            Pattern pattern = Pattern.compile("[(](\\d+)[)] -> (.*)");
        Pattern pattern = Pattern.compile("^[^\\d]");
//            Pattern pattern = Pattern.compile("[(](\\d+)[)] -> ([a-zA-Z0-9]*)");
//            Pattern pattern = Pattern.compile("[(](\\d+)[)] -> (\\D*)");
        Matcher matcher = pattern.matcher(str);

        System.out.println(str);

        System.out.println(matcher.find());
    }
}
