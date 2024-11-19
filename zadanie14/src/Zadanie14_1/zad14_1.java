package Zadanie14_1;

public class zad14_1 {
    public static void main(String[] args) {
        float[][] arr = new float[8][8];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arr[i][j] = (int)(Math.random() * 10);
            }
        }
        int x = 1;
        for(int i=0;i < arr.length;i++){
            for(int j=0;j < arr.length;j++){
                System.out.print(arr[i][j] + ", ");
            }
            System.out.println();
        }
        float l_przek = 0;
        float p_przek = 0;

        for(int i=0;i < arr.length;i++){
            for(int j=0;j < arr.length;j++){
                if(i==j){
                    l_przek += arr[i][j];
                }
                if(arr.length-i-1==j){
                    p_przek+=arr[i][j];
                }
            }
        }
        System.out.println();
        System.out.println("Odp: ");
        System.out.println("lewa: " + l_przek + " prawa: " + p_przek);
    }
}
