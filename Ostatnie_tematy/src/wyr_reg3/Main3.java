package wyr_reg3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main3 {
    public static void main(String[] args) {
        String str = "wieś w Polsce położona w województwie wielkopolskim, w powiecie kolskim, w gminie Olszówka. W latach 1975-1998 miejscowość położona była w województwie konińskim";
        Pattern pattern = Pattern.compile("\\d{2}");
        Matcher matcher = pattern.matcher(str);
        int count = 0;

        while(matcher.find()){
            count++;
        }
        System.out.println(count);
    }
}
