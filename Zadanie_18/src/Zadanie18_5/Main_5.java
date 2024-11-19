package Zadanie18_5;

import java.util.Random;
import java.util.Scanner;


public class Main_5 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();

        //dla rekurencji --> jeszcze nie zoptymalizowalem
        char[][] maze = new char[size][size];
        int total = ((size * size) / 5);
        int[] posMove = new int[4];
        for (int i = 0; i < 4; i++)
            posMove[i] = 5;
        int[][] dir = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};
        int[][] moves = new int[10][2];
        int pos = 0;
        int lenmoves = 10;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = 'X';
            }
        }

        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);

        while ((x + 1) % 2 != 0 && (y + 1) % 2 == 0) {
            x = (int) (Math.random() * size);
            y = (int) (Math.random() * size);
        }
        maze[x][y] = ' ';

        for (int i = 0; i < 4; i++)
            posMove[i] = 5;
        moves[0][0] = x;
        moves[0][1] = y;

        //ehhhhhhh

        MazeGenerator labirytnt = new MazeGenerator();
        int[][] tab = new int[size][size];

        System.out.println("przed zamiana: ");
        labirytnt.show_stan(MazeGenerator.getMazeI(size));
        System.out.println("po zamianie: ");
        labirytnt.show(MazeGenerator.getMazeI(size));

    }
}
