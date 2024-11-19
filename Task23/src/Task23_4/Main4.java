package Task23_4;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main4 {
    public static void main(String[] args) {

        InputStream strumienWe = System.in;

        try {
            int tmp;
            String ciag ="";
            while((tmp = strumienWe.read())!=10){
                ciag += (char)(tmp);
            }
            System.out.println(ciag);

            strumienWe.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
