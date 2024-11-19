public class Sortowanie_bubble {
    private static void b_sort(int tab[]) {
        for (int i = 0; i <tab.length; i++) {
            for(int j=0;j<tab.length;j++){
                 int temp;
                 if (tab[i] > tab[j]) {
                     temp = tab[j];
                     tab[j] = tab[i];
                     tab[i] = temp;
                }
            }
        }
    }
//    public static void bbl_sort(int tab[]){
//
//
    public static void main(String[] args) {
        int[] tab = {5,3,2,1,5,7,9,3,5,6,6};
        b_sort(tab);
        for (int val: tab
             ) {
            System.out.println(val + " ");
        }
    }
}
