package zad7;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("/Users/jonaszsojka/IdeaProjects/Ostatnie_tematy/src/zad7/zad1");

        try {
            FileInputStream fis = new FileInputStream(file);
            int tmp;
            int binary=0;

            while((tmp=fis.read())!=-1){
                binary = binary << 1;
                binary = binary | (tmp - '0');
            }
            System.out.println((int)'J');
            System.out.println(binary);

            FileOutputStream fos = new FileOutputStream("/Users/jonaszsojka/IdeaProjects/Ostatnie_tematy/src/zad7/Wyn.txt");
            fos.write(binary);
            fos.write(binary >> 8);
            fos.write(binary >> 16);
            fos.write(binary >> 24);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
