package Task25_2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String str = "A3";
        int test = str.length();

        Pattern pattern = Pattern.compile("(\\d|[ABCDEF]){" + test + "}");
        Matcher matcher = pattern.matcher(str);

        System.out.println(matcher.find());
    }
}
