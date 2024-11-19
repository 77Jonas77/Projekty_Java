import static java.lang.Math.pow;

public class Zamiana_systemow {
    public static double zamien(int ls, int sys){
        double ld = 0;
        int pom = 0;
        while(ls>0){
            ld = ld + pow(sys,pom)*ls%10;
            ls/=10;
            pom++;
        }
        return ld;
    }

    public static String zamien_sys(int ld, int sys){
        String ls = "";
        boolean czyujemna = false;
        if(ld<0){
            ld*=-1;
            czyujemna = true;
        }
        while(ld>0){
            ls = ld%sys + ls;
            ld/=sys;
        }
        if(czyujemna){
            ls = '-' + ls;
        }
        return ls;
    }

    public static void main(String[] args) {
        int x = 1111;
        System.out.println(zamien_sys(-255,8));
    }
}
