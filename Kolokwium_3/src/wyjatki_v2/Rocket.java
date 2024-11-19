package wyjatki_v2;

public class Rocket {
    private String name;
    private int fullWeight;

    public Rocket(String name, int fullWeight){
        this.fullWeight = fullWeight;
        this.name = name;
    }

    public void refill(){
        this.fullWeight = (int)(Math.random()*1000+500);
    }

    public void start()throws LowFullException{
        if(fullWeight >= 1000){
            System.out.println("3...2...1...0...START");
        }else{
            throw new LowFullException("to jest ta wiadomosc");
        }
    }
}
