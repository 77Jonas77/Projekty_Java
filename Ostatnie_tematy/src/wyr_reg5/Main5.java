package wyr_reg5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main5 {
    public static void main(String[] args) {
        String str ="1000";
        Pattern pattern = Pattern.compile("[01]{"+(str.length()%2==0?str.length()+1:str.length()) +"}");
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.find());
    }
}
