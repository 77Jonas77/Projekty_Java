public class Liczba_p {

//    public static boolean l_pierwsza(int tab[]){
//        int licz = 0;
//        for(int i=0;i<tab.length;i++){
//            boolean czyp = true;
//            if(tab[i] < 2)
//                czyp = false;
//            for(int j=2;j<tab[i];j++){
//                if(tab[i]%j==0){
//                    czyp = false;
//                    break;
//                }
//            }
//            if(czyp)
//                licz++;
//        }
//        if(licz>=2)
//            return true;
//        return false;
//    }
//

    public static boolean l_pierwsza(int tab){
            if(tab < 2)
                return false;
            for(int j=2;j<tab;j++){
                if(tab%j==0){
                    return false;
                }
            }
            return true;
    }

    public static int[] l_pierwsze(int[] tab){
        int count=0;
        for(int i=0;i<tab.length;i++) {
            boolean czyp = true;
            if (tab[i] < 2) {
                czyp = false;
            }
            for (int x = 2; x < tab[i] && czyp; x++) {
                if (tab[i] % x == 0) {
                    czyp = false;
                }
            }
            if(czyp)
                count++;
        }
            int[] spel = new int[count];
            int indx = 0;

            for (int i = 0; i < tab.length; i++) {
                boolean czyp = true;
                if (tab[i] < 2) {
                    czyp = false;
                }
                for (int x = 2; x < tab[i]; x++) {
                    if (tab[i] % x == 0) {
                        czyp = false;
                    }
                }
                if (czyp)
                    spel[indx++] = tab[i];
                }
        return spel;
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3};
        for(int val : l_pierwsze(arr)){
            System.out.println(
                    val
            );
        }
    }
}
