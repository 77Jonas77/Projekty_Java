public class Game {
    private S27523Projekt04 main;
    private GameLogic gameLogic;
    private GameFrame gameFrame;

    public Game(S27523Projekt04 main) {
        gameLogic = new GameLogic();
        gameFrame = new GameFrame();
        this.main = main;

        gameLogic.addRefreshScoreListener(gameFrame.getGamePanel());
        gameLogic.addEndGameListener(gameFrame.getGamePanel());
        gameFrame.getGamePanel().addChangeDirListener(gameLogic);
        gameFrame.getGamePanel().addSetUsernameListener(gameLogic);
        gameLogic.addTickListener(gameFrame.getGamePanel());
        gameFrame.getGamePanel().addEndGameMainListener(main);

        gameFrame.getGamePanel().fireSetUsername(new InputUsernameEvent(gameFrame.getGamePanel(), gameFrame.getGamePanel().getUsername()));
        gameLogic.startLogicT();
    }

    public void startNewGame() {
        if (gameFrame != null) {
            gameFrame.dispose();
        }
        if (gameLogic != null) {
            gameLogic.endGame();
        }

        gameLogic = new GameLogic();
        gameFrame = new GameFrame();

        gameLogic.addRefreshScoreListener(gameFrame.getGamePanel());
        gameLogic.addEndGameListener(gameFrame.getGamePanel());
        gameFrame.getGamePanel().addChangeDirListener(gameLogic);
        gameFrame.getGamePanel().addSetUsernameListener(gameLogic);
        gameLogic.addTickListener(gameFrame.getGamePanel());
        gameFrame.getGamePanel().addEndGameMainListener(main);

        gameFrame.getGamePanel().fireSetUsername(new InputUsernameEvent(gameFrame.getGamePanel(), gameFrame.getGamePanel().getUsername()));
        gameLogic.startLogicT();
    }


}
