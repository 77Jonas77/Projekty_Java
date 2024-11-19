package Task_3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2 {
    public static void main(String[] args) {
        String text = "wieś w Polsce położona w województwie wielkopolskim, w powiecie kolskim, w gminie Olszówka. W latach 1975-1998 miejscowość położona była w województwie konińskim";
        Pattern pattern = Pattern.compile("\\d{2}");
        Matcher matcher = pattern.matcher(text);
        int counter = 0;

        while (matcher.find()) {
            counter++;
        }

        System.out.println("W podanym tekście wystąpiło " + counter + " par liczb");
    }
}
