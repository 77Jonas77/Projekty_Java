package zad6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class mAIn {
    public static void main(String[] args) {
        File file = new File("/Users/jonaszsojka/IdeaProjects/Ostatnie_tematy/src/zad2/plik1");

        try {
            PNWReader ez = new PNWReader(new FileReader(file),2);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
