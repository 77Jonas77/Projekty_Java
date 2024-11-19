package wyr_reg1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main1 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\d{4}-0[1-9]|1[0-2]-[0-2][1-9]|3[01]");
        Matcher matcher = pattern.matcher("2003-11-02");
        System.out.println(matcher.find());
    }
}
