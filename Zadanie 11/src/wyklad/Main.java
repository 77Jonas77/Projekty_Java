package wyklad;

public
    class Main {

        final static int EMPTY_ELEMENT = -1;

    public static void main(String[] args) {
        {
            int[] tab = new int[20];
            int fill = 0;

            for(int i=0; i<10; i++)
                tab[fill++] = (int)(Math.random()*10);

            for(int i=0; i<10; i++)
                tab[fill++] = (int)(Math.random()*10);


            System.out.print("[");
            for(int i = 0; i<fill; i++)
                System.out.print(tab[i]+", ");
            System.out.println("]");

            //usuwanie wartosci z tablicy

            for(int i = 5; i < fill-1; i++)
                tab[i]= tab[i+1];
            tab[--fill] = EMPTY_ELEMENT;

            System.out.print("[");
            for(int i = 0; i<fill; i++)
                System.out.print(tab[i]+", ");
            System.out.println("]");

            // wstawianie wartosci do tablicy

            if(fill < tab.length){
                for(int i = fill; i >= 5; i--)
                    tab[i] = tab[i-1];

                tab[5] = 5;
                fill++;

                System.out.print("[");
                for(int i = 0; i<fill; i++)
                    System.out.print(tab[i]+", ");
                System.out.println("]");
            }

            // zamiana wartosci na indeksach 0 i 5
            {
                int tmp = tab[0];
                tab[0] = tab[5];
                tab[5] = tmp;
            }
            System.out.print("[");
            for(int i = 0; i<fill; i++)
                System.out.print(tab[i]+", ");
            System.out.println("]");

            //znajdz najmniejszy element
            {
                int tmpMinIndex = 0;
                for( int i = 0 + 1; i < fill ;i++)
                    if(tab[tmpMinIndex] > tab[i])
                        tmpMinIndex = i;

                System.out.println("tmpMin: "+tab[tmpMinIndex] +"@"+tmpMinIndex);

                int tmp = tab[0];
                tab[0] = tab[tmpMinIndex];
                tab[tmpMinIndex] = tmp;
            }
            System.out.print("[");
            for(int i = 0; i<fill; i++)
                System.out.print(tab[i]+", ");
            System.out.println("]");

            //sort
            {
                for(int j = 0; j < fill-1; j++) {
                    int tmpMinIndex = j;
                    for (int i = j + 1; i < fill; i++)
                        if (tab[tmpMinIndex] > tab[i])
                            tmpMinIndex = i;

                    //System.out.println("tmpMin: " + tab[tmpMinIndex] + "@" + tmpMinIndex);

                    int tmp = tab[j];
                    tab[j] = tab[tmpMinIndex];
                    tab[tmpMinIndex] = tmp;
                }
            }
            System.out.print("[");
            for(int i = 0; i<fill; i++)
                System.out.print(tab[i]+", ");
            System.out.println("]");
        }

        {
            int[] arr = new int[10];

            int[] tmp = new int[arr.length-1];
            for(int i=0; i<5; i++)
                tmp[i] = arr[i];
            for(int i = 5+1; i < arr.length; i++)
                tmp[i-1] = arr[i];
            arr = tmp;

        }

        {
            int[][][][][][][][][][][][][][][] tab[];
        }

        {
            int[][] arr2D = new int[10][10];
            for(int i=0; i<arr2D.length; i++)
                for(int j=0; j<arr2D.length; j++)
                    arr2D[i][j] = (int)(Math.random()*10);

            System.out.print("[");
            for(int[] arr : arr2D) {
                for (int val : arr)
                    System.out.print(val+", ");
                System.out.println();
            }
            System.out.println("]");
        }

        {
            int[][] arr2D = new int[10][];
            for(int i=0; i< arr2D.length; i++)
                arr2D[i] = new int[(int)(Math.random()*10)];

            System.out.print("[");
            for(int[] arr : arr2D) {
                for (int val : arr)
                    System.out.print(val+", ");
                System.out.println();
            }
            System.out.println("]");
        }

    }
}
