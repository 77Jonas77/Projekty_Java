package Zadanie19_2;

public class Donkey {
    private static double mass;
    private static double tab[];

    public static void addBalloon(Balloon b){
        if(tab==null){
            double[] tmp = new double[1];
            tmp[0] = b.getLoad();
            tab=tmp;
            mass+=tab[0];
        }else{
            double[] tmp = new double[tab.length + 1];
            for(int i=0;i<tab.length;i++){
                tmp[i] = tab[i];
            }
            tab = tmp;
            tab[tab.length-1] = b.getLoad();
            mass+=tab[tab.length-1];
        }
    }

    public static boolean isFlying(double weight){
        if(mass<weight)
            return true;
        return false;
    }

}
