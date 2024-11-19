public class XXX {
    public class Main {

        public static int chuj(int[][] tab, int poz) {
            int val = 0;
            for(int i=0; i<tab.length; i++) {
                if(tab[i].length > poz)
                    val++;
                else
                    return val;
            }

            return val;
        }

        public static void main(String[] args) {

            int[][] tab = {
                    {5,7,3},
                    {1,2,8},
                    {10,2},
                    {3}
            };
            int[][] newTab = new int[tab[0].length][];

            for(int i=0; i<newTab.length; i++) {
                int[] tmp = new int[chuj(tab, i)];

                for(int  j=0; j<tmp.length; j++) {
                    tmp[j] = tab[j][i];
                }
                newTab[i] = tmp;
            }

            for(int i=0; i<newTab.length; i++) {
                for(int j=0; j<newTab[i].length; j++) {
                    System.out.print(newTab[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
