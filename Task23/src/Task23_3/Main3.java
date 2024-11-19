package Task23_3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Math.pow;

public class Main3 {
    public static void main(String[] args) {
        File file = new File("/Users/jonaszsojka/IdeaProjects/Task23/data/zad1.txt");
        int sum = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            int tmp;
            int sumtmp=0;
            while((tmp = fis.read())!=-1){
                if(tmp !=' ') {
                    sumtmp = sumtmp * 10 + (tmp - '0');
                }else{
                    sum+=sumtmp;
                    sumtmp = 0;
                }
            }
            sum += sumtmp;
            fis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(sum);

    }
}
