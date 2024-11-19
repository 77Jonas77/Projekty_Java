import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic
        extends Thread
        implements ChangeDirListener, SetInputUsernameListener {

    private static int SCORE = 0;

    private final int GAME_TICK = 200; //na koniec petli we thread wysylac co chce odwiezyc czyli tablice czy cos
    private static int[][] gameBoard = new int[25][16];

    private boolean running;
    private char dir;
    private SnakeModel snake;
    private ArrayList<RefreshScoreListener> refreshScoreListeners = new ArrayList<>();

    private ArrayList<TickEventListener> tickListeners = new ArrayList<>();

    private ArrayList<EndGameListener> endGameListeners = new ArrayList<>();

    private String username;
    private ArrayList<Player> playerList;
    private Thread logicThread;

    //1 - glowa, 2 - segment, 3 - jablko
    public GameLogic() {
        dir = 1;
        gameBoard[12][11] = 1; //glowa
        snake = new SnakeModel(gameBoard, dir, 12, 11, this);

        this.playerList = FileManager.readFromFile();
        running = true;
        logicThread = new Thread(() -> {
            while (running) {
                snake.moveSnake();
                fireTick(new TickEvent(this, gameBoard));
                try {
                    Thread.sleep(GAME_TICK);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.playerList.add(new Player(username, SCORE));
            FileManager.saveToFile(playerList);
            fireEndGame(new EndGameEvent(this, true, playerList));
        });
    }



    private void displayGameBoard() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void refreshScore() {
        SCORE++;
        fireRefreshScore(new ScoreRefreshEvent(this, SCORE));
    }

    private void fireRefreshScore(ScoreRefreshEvent evt) {
        for (RefreshScoreListener listener : refreshScoreListeners) {
            listener.refreshScore(evt);
        }
    }

    private void fireTick(TickEvent evt) {
        for (TickEventListener listener : tickListeners) {
            listener.displayCurrBoardState(evt);
        }
    }

    private void fireEndGame(EndGameEvent evt) {
        for (EndGameListener listener : endGameListeners) {
            listener.endGame(evt);
        }
    }

    @Override
    public void updateDir(ChangeDirEvent evt) {
        snake.updateDir(evt.getDir());
    }

    public void addRefreshScoreListener(RefreshScoreListener l) {
        this.refreshScoreListeners.add(l);
    }

    public void addEndGameListener(EndGameListener l) {
        this.endGameListeners.add(l);
    }

    public void addTickListener(TickEventListener l) {
        this.tickListeners.add(l);
    }

    public static void updateCurrBoardStateGL(int[][] gameBoardState) {
        gameBoard = gameBoardState;
    }

    public void endGame() {
        this.running = false;
        SCORE = 0;
        gameBoard = new int[25][16];
    }

    @Override
    public void setUsername(InputUsernameEvent evt) {
        this.username = evt.getUsername();
    }

    public void startLogicT() {
        this.logicThread.start();
    }
}
