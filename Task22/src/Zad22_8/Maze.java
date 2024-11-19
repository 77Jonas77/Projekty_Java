package Zad22_8;

import java.io.Console;

public class Maze {

    public int[][] maze;
    public int[] pos;

    public Maze(int[][] maze) {
        this.maze = maze;
        pos = new int[2];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 2) {
                    pos[0] = j;
                    pos[1] = i;
                }
            }
        }
        ;
    }

    public int[] getPos() {
        return pos;
    }

}
