package zad4;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        InputStream strumienWe = System.in;

            try {
                int tmp;
                String ciag = "";
                while((tmp=strumienWe.read())!=10){
                    ciag += (char)tmp;
                }
                System.out.println(ciag);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
}
