package Task25_5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main5 {
    public static void main(String[] args) {
        String str = "01010";
        Pattern pattern = Pattern.compile("[01]{"+(str.length()%2!=0?str.length():str.length()+1)+"}");
        Matcher matcher = pattern.matcher(str);

        System.out.println(matcher.find());
    }
}
