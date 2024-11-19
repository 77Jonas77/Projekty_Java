package Task24_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PNWReader {
    public static final int lp = 1;
    public static final int ln = 2;
    public static final int w = 4;

    private static int count = 0;
    public static int arg;
    public static File file;

    public PNWReader(File file, int arg){
        if(arg != ln && arg!=lp && arg!=w)
            throw new IllegalArgumentException();
        this.arg = arg;
        this.file = file;
    }

    public void show(){
        if(this.count > 0)
            throw new IllegalStateException();
        try {
            FileReader fr = new FileReader(file);
            int tmp;
            while((tmp = fr.read())!= '\n'){
                if(arg==w && (tmp>='A' && tmp <='Z' || tmp>='a' && tmp <='z')) {
                    String word = "" + (char)(tmp);
                    while((tmp = fr.read())!=' ')
                        word += (char)(tmp);
                    System.out.println(word);
                }
                if(arg == ln || arg==lp && !(tmp>='A' && tmp <='Z' || tmp>='a' && tmp <='z')){
                    int liczba = (tmp-'0');
                    while((tmp = fr.read())!= ' ' && tmp !=-1){
                        liczba = liczba*10 + (tmp - '0');
                    }
                    if(arg == lp ){
                        if(czypierwsza(liczba)){
                            System.out.println(liczba);
                        }
                    }
                    if(arg == ln){
                        if(czyArmstrong(liczba)){
                            System.out.println(liczba);
                        }
                    }
                }
                this.count++;
                fr.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean czypierwsza(int l){
        if(l < 2)
            return false;
        for(int i=2;i<l;i++) {
            if (l % i == 0)
                return false;
        }
        return true;
    }

    public boolean czyArmstrong(int number) {
        if(number <= 0)
            return false;
        int size = (int) Math.log10(number) + 1;
        int tmp = number, sum = 0;

        while (tmp > 0) {
            sum += Math.pow(tmp % 10, size);
            tmp /= 10;
        }

        return number == sum;
    }

}
