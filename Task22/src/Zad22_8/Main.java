package Zad22_8;

public class Main {
    public static void main(String[] args) {

        int[][] maze = {
                {1, 1, 1, 1, 1, 1},
                {0, 0, 1, 1, 2, 1},
                {1, 0, 1, 0, 0, 1},
                {1, 1, 1, 0, 1, 1},
                {1, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1}
        };

        Maze lab1 = new Maze(maze);

        ConsoleGraphics rys = new ConsoleGraphics();
        rys.draw(new Maze(maze), maze);
        rys.show();
    }
}
