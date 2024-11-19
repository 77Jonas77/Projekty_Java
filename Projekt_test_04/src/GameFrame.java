import javax.swing.*;
import java.awt.*;

public class GameFrame
        extends JFrame {

    private final int WINDOW_WIDTH=600;
    private final int WINDOW_HEIGHT=815;

    private GamePanel gamePanel;
    public GameFrame() throws HeadlessException {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setTitle("SnakeGame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

}
