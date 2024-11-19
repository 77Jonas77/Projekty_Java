import com.sun.tools.javac.Main;

public class S27523Projekt04
        implements EndGameListenerMain {

    private static Game game;

    public S27523Projekt04() {

    }

    public static void main(String[] args) {
        game = new Game(new S27523Projekt04());
    }

    @Override
    public void endGameMain(EndGameMainEvent evt) {
        game.startNewGame();
    }
}

