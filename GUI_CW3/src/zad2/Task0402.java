package zad2;

public class Task0402 {
    public static void main(String[] args) {
        FunDD p = new Parabola(1,-1,0);
        FunDD.xminim(p,0,1);
        FunDD anon = new FunDD() {
            @Override
            public double fun(double x) {
                return Math.sqrt(Math.pow(x-0.75,2)+1);
            }
        };
        FunDD.xminim(anon,0,2);
        FunDD.xminim((x)-> Math.pow(x,2)*(x-2),0,2);
    }
}
