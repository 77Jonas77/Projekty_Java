public class Test_2 {

    public static void main(String[] args) {

        int[] arr = {153, 333, 370, 515, 407, 80};

        for(int i=0;i<arr.length;i++) {
         int pom1 = arr[i];
         int pot = 0;

         while(pom1>0){
             pot++;
             pom1/=10;
         }

         int pom2 = arr[i];
         int wynik = 0;

         while(pom2>0){
             int pot_wyn = 1;
             for(int j=0;j<pot;j++){
                pot_wyn*=pom2%10;
             }
             wynik+=pot_wyn;
             pom2/=10;
         }
         if (arr[i] == wynik)
             System.out.println(arr[i]);
        }
    }
}
