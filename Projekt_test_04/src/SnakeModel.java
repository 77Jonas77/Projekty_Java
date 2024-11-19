import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class SnakeModel {
    private int[][] gameBoardState;
    private static final int ROWS = 25;
    private static final int COLS = 16;

    private LinkedList<int[]> snakeBody;
    private int headRow;
    private int headCol;
    private int dir;

    private int appleCol;
    private int appleRow;

    private final GameLogic gameLogic;

    public SnakeModel(int[][] gameBoardState, char dir, int headRow, int headCol, GameLogic gameLogic) {
        this.gameLogic = gameLogic;

        this.headCol = headCol;
        this.headRow = headRow;

        this.gameBoardState = gameBoardState;
        this.dir = dir;

        newApple();

        snakeBody = new LinkedList<>();
        snakeBody.addFirst(new int[]{headRow, headCol});
        this.gameBoardState[headRow][headCol] = 1;
    }

    private boolean checkApple(int row, int col) {
        return gameBoardState[row][col] == 3;
    }

    private boolean isMoveOutOfBoard(int row, int col) {
        return row < 0 || row >= ROWS || col < 0 || col >= COLS;
    }

    public void moveSnake() {
        int headRow = this.headRow;
        int headCol = this.headCol;

        if (dir == 1) {
            headRow--;
        } else if (dir == 3) {
            headRow++;
        } else if (dir == 4) {
            headCol--;
        } else if (dir == 2) {
            headCol++;
        }

        if (!isMoveOutOfBoard(headRow, headCol) && !checkCollision(headRow, headCol)) {
            if (checkApple(headRow, headCol)) {
                if(snakeBody.size()==1) {
                    gameBoardState[headRow][headCol] = 1;

                    snakeBody.removeLast();
                    gameBoardState[this.headRow][this.headCol] = 2;
                    snakeBody.addFirst(new int[]{this.headRow, this.headCol});
                }else{
                    gameBoardState[headRow][headCol] = 1;
                    gameBoardState[this.headRow][this.headCol] = 2;
                }
                gameLogic.refreshScore();
                newApple();
            } else {
                if (snakeBody.size() == 1) {
                    int[] tail = snakeBody.removeLast();
                    int tailRow = tail[0];
                    int tailCol = tail[1];
                    gameBoardState[tailRow][tailCol] = 0;
                    gameBoardState[headRow][headCol] = 1;
                } else {
                    int[] tail = snakeBody.removeLast();
                    int tailRow = tail[0];
                    int tailCol = tail[1];
                    gameBoardState[tailRow][tailCol] = 0;
                    gameBoardState[this.headRow][this.headCol] = 2;
                    gameBoardState[headRow][headCol] = 1;
                }
            }
            snakeBody.addFirst(new int[]{headRow, headCol});

            this.headRow = headRow;
            this.headCol = headCol;
        }else{
            gameLogic.endGame();
        }

        GameLogic.updateCurrBoardStateGL(gameBoardState);
        updateGameBoardState(gameBoardState);
    }

//    public void showList(){
//        for(int[] x : snakeBody){
//            System.out.print(Arrays.toString(x) + " ");
//        }
//        System.out.println();
//    }

    public void newApple() {
        Random random = new Random();
        int newAppleRow = random.nextInt(ROWS);
        int newAppleCol = random.nextInt(COLS);
        while (gameBoardState[newAppleRow][newAppleCol] != 0) {
            newAppleRow = random.nextInt(ROWS);
            newAppleCol = random.nextInt(COLS);
        }
        updateApplePos(newAppleRow, newAppleCol);
        gameBoardState[newAppleRow][newAppleCol] = 3;
    }

    private boolean checkCollision(int headRow, int headCol) {
        return gameBoardState[headRow][headCol] == 2;
    }

    private void updateGameBoardState(int[][] gameBoardState) {
        this.gameBoardState = gameBoardState;
    }

    public void updateDir(int dir) {
        if(Math.abs(this.dir - dir)!=2)
            this.dir = dir;
    }

    private void updateApplePos(int newAppleRow, int newAppleCol) {
        this.appleRow = newAppleRow;
        this.appleCol = newAppleCol;
    }
}
