package Task24_3;

import java.io.*;

public class Main3 {
    public static void main(String[] args) {
        File file = new File("/Users/jonaszsojka/IdeaProjects/Task24/Data/plik2");
        try {
            FileInputStream fis = new FileInputStream(file);
            int tmp;
            int number = 0;
            while((tmp=fis.read())!=-1){
                number = number << 1;
                number |= (tmp-'0');
            }
            System.out.println(number);
            FileOutputStream fos = new FileOutputStream("/Users/jonaszsojka/IdeaProjects/Task24/Data/wyn.txt");
            fos.write(number);
            fos.write(number >> 8);
            fos.write(number >> 16);
            fos.write(number >> 24);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
