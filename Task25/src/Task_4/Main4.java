package Task_4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main4 {
    public static void main(String[] args) {
        String str = "A.B.C.D.";
        Pattern pattern = Pattern.compile("[A-Z].[A-Z].[A-Z].[A-Z].");
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.find());
    }

}
