package zad2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Pliki {
    public static void main(String[] args) {
        File file = new File("/Users/jonaszsojka/IdeaProjects/Ostatnie_tematy/src/zad2/plik1");

        int[] count = new int[256];

        try {
            FileInputStream fis = new FileInputStream(file);
            int tmp;
            while((tmp=fis.read())!=-1)
                count[tmp]++;
            fis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int i=0;i<256;i++){
            if(count[i]!=0)
                System.out.println((char)i + ": " + count[i]);
        }
    }
}
