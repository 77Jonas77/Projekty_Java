package Wyjatki_Cw;

public class SmokeDetector {


    public void check() {
        if(Math.random()>=0.2){
            throw new Alarm("pali sie");
        }else{
            System.out.println("wszystko git");
        }
    }
}
