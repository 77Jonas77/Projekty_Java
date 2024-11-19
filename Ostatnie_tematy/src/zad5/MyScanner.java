package zad5;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

public class MyScanner {
    private InputStream ips;

    public MyScanner(InputStream ips) {
        this.ips = ips;
    }

    public String readWord() {
        String word = "";

        try {
            int tmp;
            while ((tmp = ips.read()) != ' ' && tmp != '\n' && tmp != '\t' && tmp != '\r' && tmp != '\f') {
                word += (char)(tmp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return word;
    }

    public String readLine(){
        String phrase = "";
        try {
            int tmp;
            while ((tmp = ips.read()) != '\n') {
                phrase += (char)(tmp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return phrase;
    }

    public int readIntegerNumber(){
        int tmp;
        int liczba=0;
        boolean czyuj = false;
        int c=0;
        try{
            while((tmp = ips.read())>='0' && tmp <='9' || tmp =='-'){
                if(tmp=='-' && c==0){
                    czyuj = true;
                    c++;
                }else if(tmp!='-'){
                    liczba = liczba*10 +(tmp-'0');
                    c++;
                }
            }
            if(czyuj){
                return liczba*-1;
            }else{
                return liczba;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int readPosIntegerNumber() throws Exception{
        int tmp;
        int liczba = 0;
        if((tmp = ips.read())=='-')
            throw new Exception("nie jest dodatnia");
        while((tmp=ips.read())!='-'){
            liczba = liczba*10 + (tmp-'0');
        }
        return liczba;
    }
}
