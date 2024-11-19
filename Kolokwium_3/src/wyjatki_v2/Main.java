package wyjatki_v2;

public class Main {
    public static void main(String[] args) {
        Rocket r1 = new Rocket("Strzala", 100);
        try {
            r1.start();
        }catch(LowFullException e){
            String s = e.toString();
            System.out.println(s);
        }

        r1.refill();

        try{
            r1.start();
        }catch(LowFullException ex){
            System.out.println(ex);
        }

    }


}
