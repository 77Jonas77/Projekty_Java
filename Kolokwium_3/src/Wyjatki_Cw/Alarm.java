package Wyjatki_Cw;

public class Alarm extends RuntimeException{
    public Alarm(){}

    public Alarm(String msg){
        super(msg);
    }
}
