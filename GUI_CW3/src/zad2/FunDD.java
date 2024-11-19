package zad2;

@FunctionalInterface
interface FunDD {

    double fun(double x);

    static double xminim(FunDD f, double a, double b) {
        double min = f.fun(a);
        for(double i=a+1;i<=b;i+=1e-5){
            if(f.fun(i)<min){
                min = f.fun(i);
            }
        }
        return min;
    }
}
