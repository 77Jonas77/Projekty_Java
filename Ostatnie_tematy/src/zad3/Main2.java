package zad3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.Math.pow;

public class Main2 {
    public static void main(String[] args) {
        File file = new File("/Users/jonaszsojka/IdeaProjects/Ostatnie_tematy/src/zad3/zad1");
        int suma = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            int tmp;
            int nr=0;
            while((tmp=fis.read())!=-1){
                if(tmp!=' '){
                    nr = nr*10 + (tmp - '0');
                }else{
                    suma = suma+nr;
                    System.out.println(suma);
                    nr = 0;
                }
            }
            suma = suma + nr;
            fis.close();
        }catch(IOException e){
            System.out.println(e);
        }
        System.out.println(suma);
    }
}
