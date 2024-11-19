package Zadanie_4;

import java.util.Scanner;

import static java.lang.Math.pow;

public class Zad16_4 {
    public static void main(String[] args) {
        //pobr
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();

        char[][] maze = new char[size][size];
        //wypel
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = 'X';
            }
        }
        //losowanie i przypisanie pozycji
        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);

        while ((x+1) % 2 != 0 && (y+1) % 2 == 0) {
            x = (int)(Math.random() * size);
            y = (int)(Math.random() * size);
        }
        maze[x][y] = ' ';

        //start
        System.out.println("START");
        for (int s = 0; s < maze.length; s++) {
            for (int g = 0; g < maze[s].length; g++) {
                System.out.print(maze[s][g]);
            }
            System.out.println();
        }

        //deklarowanie zmiennych
        int total = ((size * size) / 5);
        int visited = 1;
        int[] posMove = new int[4];
        for(int i = 0 ; i< 4 ;i++)
            posMove[i] = 5;

        // (y,x) --> dla tablic dwuwym
        int[][] dir = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};
        int[][] moves = new int[10][2];
        int pos = 0;
        int lenmoves = 10;
        moves[0][0] = x;
        moves[0][1] = y;
        //program --> labirynt

        while (visited < total) {
            int countMove = 0;
            x = moves[pos][0];
            y = moves[pos][1];
            for (int i = 0; i < 4; i++) {
                if (x + dir[i % 4][0] < size && y + dir[i % 4][1] < size && y + dir[i % 4][1] >= 0 && x + dir[i % 4][0] >= 0) {
                    if (maze[x + dir[i % 4][0]][y + dir[i % 4][1]] == 'X') {
                        if (i == 0) {
                            posMove[countMove] = 0;
                            countMove++;
                        }
                        if (i == 1) {
                            posMove[countMove] = 1;
                            countMove++;
                        }
                        if (i == 2) {
                            posMove[countMove] = 2;
                            countMove++;
                        }
                        if (i == 3) {
                            posMove[countMove] = 3;
                            countMove++;
                        }
                    }
                }
            }
            if (countMove > 0) {
                ++pos;
                int rand = (int) (Math.random() * countMove);
                x = dir[posMove[rand]][0] + x;
                y = dir[posMove[rand]][1] + y;
                visited++;

                if(pos<moves.length) {
                    moves[pos][0] = x;
                    moves[pos][1] = y;
                    maze[x][y] = ' ';
                }else{
                    int indx = pos + 1;
                    int[][] pom = new int[indx][indx];
                    for(int n = 0;n < moves.length;n++){
                        for(int m = 0; m < moves[n].length;m++){
                            pom[n][m] = moves[n][m];
                        }
                    }
                    moves=pom;
                    moves[pos][0] = x;
                    moves[pos][1] = y;
                    maze[x][y] = ' ';
                }

                maze[x - (dir[posMove[rand]][0])/2][y - (dir[posMove[rand]][1])/2] = ' ';

            } else {
                int posp = pos-1;
                int countMoveP = 0;
                boolean czyp = true;

                while (czyp){
                    posp--;
                    int xp = moves[posp][0];
                    int yp = moves[posp][1];
                    for (int k = 0; k < 4; k++) {
                        if (xp + dir[k % 4][0] < size && yp + dir[k % 4][1] < size && yp + dir[k % 4][1] >= 0 && xp + dir[k % 4][0] >= 0) {
                            if (maze[xp + dir[k % 4][0]][yp + dir[k % 4][1]] == 'X') {
                                countMoveP++;
                            }
                        }
                        if (countMoveP > 0) {
                            pos = posp;
                            czyp = false;
                            break;
                        }
                        if(posp == 0)
                            czyp = false;
                    }
                }
            }
        }
        System.out.println();
        System.out.println( "KONIEC");
        for(int l = 0; l < maze.length; l++) {
            for(int b = 0; b < maze[l].length; b++) {
                System.out.print(maze[l][b]);
            }
            System.out.println();
        }

    }
}
