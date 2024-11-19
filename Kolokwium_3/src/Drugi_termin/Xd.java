package Drugi_termin;

public class Xd {
    public static void sameLetters(char[][] tab){
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[i].length-1;j++){
                char litera = tab[i][j];
                for(int k=j+1;k<tab[i].length;k++){
                    if(litera == tab[i][k]){
                        System.out.println(litera);
                        break;
                    }
                }
            }
        }
    }
}
