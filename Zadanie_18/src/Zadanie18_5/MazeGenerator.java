package Zadanie18_5;

import java.util.Random;
import java.util.Scanner;

public class MazeGenerator {

    public static char[][] getMazeR(char maze[][], int start, int total, int[][] moves, int pos, int[][] dir, int x, int y, int[] posMove, int size) {
        if (start == total) {
            return maze;
        }
        if (start < total) {
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
                start++;

                if (pos < moves.length) {
                    moves[pos][0] = x;
                    moves[pos][1] = y;
                    maze[x][y] = ' ';
                } else {
                    int indx = pos + 1;
                    int[][] pom = new int[indx][indx];
                    for (int n = 0; n < moves.length; n++) {
                        for (int m = 0; m < moves[n].length; m++) {
                            pom[n][m] = moves[n][m];
                        }
                    }
                    moves = pom;
                    moves[pos][0] = x;
                    moves[pos][1] = y;
                    maze[x][y] = ' ';
                }

                maze[x - (dir[posMove[rand]][0]) / 2][y - (dir[posMove[rand]][1]) / 2] = ' ';

            } else {
                int posp = pos - 1;
                int countMoveP = 0;
                boolean czyp = true;

                while (czyp) {
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
                        if (posp == 0)
                            czyp = false;
                    }
                }
            }
        }
        return getMazeR(maze, start, total, moves, pos, dir, x, y, posMove, size);
    }

    public static char[][] getMazeI(int size) {

        char[][] maze = new char[size][size];
        int total = ((size * size) / 5);
        int[] posMove = new int[4];
        for (int i = 0; i < 4; i++)
            posMove[i] = 5;
        int[][] dir = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};
        int[][] moves = new int[10][2];
        int pos = 0;
        int lenmoves = 10;

        //wypel
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = 'X';
            }
        }

        long seed = 0;
        Random generator = new Random(seed);

        int x = (int) (generator.nextDouble() * size);
        int y = (int) (generator.nextDouble() * size);

        while ((x + 1) % 2 != 0 && (y + 1) % 2 == 0) {
            x = (int) (generator.nextDouble() * size);
            y = (int) (generator.nextDouble() * size);
        }
        maze[x][y] = ' ';

        int visited = 0;

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
                int rand = (int) (generator.nextDouble() * countMove);
                x = dir[posMove[rand]][0] + x;
                y = dir[posMove[rand]][1] + y;
                visited++;

                if (pos < moves.length) {
                    moves[pos][0] = x;
                    moves[pos][1] = y;
                    maze[x][y] = ' ';
                } else {
                    int indx = pos + 1;
                    int[][] pom = new int[indx][indx];
                    for (int n = 0; n < moves.length; n++) {
                        for (int m = 0; m < moves[n].length; m++) {
                            pom[n][m] = moves[n][m];
                        }
                    }
                    moves = pom;
                    moves[pos][0] = x;
                    moves[pos][1] = y;
                    maze[x][y] = ' ';
                }

                maze[x - (dir[posMove[rand]][0]) / 2][y - (dir[posMove[rand]][1]) / 2] = ' ';

            } else {
                int posp = pos - 1;
                int countMoveP = 0;
                boolean czyp = true;

                while (czyp) {
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
                        if (posp == 0)
                            czyp = false;
                    }
                }
            }
        }
        System.out.println();

        return maze;
    }

    public static void show_stan(char maze[][]) {
        for (int s = 0; s < maze.length; s++) {
            for (int g = 0; g < maze[s].length; g++) {
                System.out.print(maze[s][g]);
            }
            System.out.println();
        }
    }
    public void show(char maze[][]){

        for(int i=0;i<maze.length;i++){

            for(int j=0;j<maze[i].length;j++){
                if(maze[i][j]!=' '){
                    int sum = 0;
                    if(i - 1 >= 0 && maze[i-1][j]!=' ')
                        sum+=1;
                    if(i + 1 <= maze.length-1 && maze[i+1][j]!=' ')
                        sum+=2;
                    if(j - 1 >= 0 && maze[i][j-1]!=' ')
                        sum+=4;
                    if(j + 1 <= maze[i].length - 1 && maze[i][j+1]!= ' ')
                        sum+=8;

                    System.out.print(
                            switch (sum){
                                case 1,2,3 -> '\u2503';
                                case 4,8,12  -> '\u2501';
                                case 10 -> '\u250F';
                                case 6 -> '\u2513';
                                case 5 -> '\u251B';
                                case 9 -> '\u2517';
                                case 11 -> '\u2523';
                                case 7 -> '\u252B';
                                case 14 -> '\u2533';
                                case 13 -> '\u253B';
                                case 15 -> '\u254B';
                                default -> '.';
                            }
                    );
                }else{
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
    }
}
