package zadanie13_1;

import java.sql.SQLOutput;

public class Zadanie13_01{
    public static void main(String[] args) {
        int[] A = {1,5,8,9};
        int[] B = {9,7,5,1};
        int[] C = new int[A.length+B.length];

        int c=0;

        for(int i=0;i<A.length;i++){
            for(int j=B.length-1-i;j>=0;j--){
                if(A[i]<=B[j]){
                    if(c>0) {
                        int pom = C[c - 1];
                        if (A[i] < C[c - 1]) {
                            C[c - 1] = A[i];
                            C[c] = pom;
                        }
                    }
                        C[c] = A[i];
                        C[c + 1] = B[j];
                        c += 2;
                        break;
                }
                else{
                    C[c]=B[j];
                    C[c+1]=A[i];
                    c+=2;
                    break;
                }
            }
        }
        System.out.print("[");
        for(int i=0;i<C.length;i++){
            System.out.print(C[i]);
        }
        System.out.print("] ");

    }
}
