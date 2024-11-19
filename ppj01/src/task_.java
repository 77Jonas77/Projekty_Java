import java.util.Random;

public class task_{
    public static void main(String[] args){
        int min = 10;
        int max = 15;
        int size = (int)((Math.random() * (max - min)) + min);
        int[] tab = new int[size];

        for(int i=0;i<size;i++){
            tab[i] = i;
        }

        System.out.println("[ ");
        for (int val: tab
             ) {
            System.out.println(val + ", ");
        }
        System.out.println("] ");

        Random r = new Random(size);

        for(int i=0;i<tab.length;i++){
            tab[i] = tab[r.nextInt(size)];
        }

        System.out.print("[ ");
        for (int val: tab
        ) {
            System.out.print(val + ", ");
        }
        System.out.print("] ");
        System.out.println();

        for(int i=0;i<tab.length;i++){
            for(int j=size;j>0;j--){
                if(tab[i]==j){
                    System.out.print("*");
                }
                System.out.print(".");
            }
            System.out.println();
        }

    }
}
