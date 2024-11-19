package Zadanie21_2;

public class Drzewo {


    public Owoc zerwijOwoc(){
        int z = (int)(Math.random()*3);
        switch(z){
            case 0 -> {
                return new Jablko();
            }
            case 1 ->{
                return new Gruszka();
            }
            default -> {
                return new Pomarancza();
            }
        }
    }


}
