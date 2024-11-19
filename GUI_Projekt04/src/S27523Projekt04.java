import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class S27523Projekt04
        implements EndGameListenerMain {

    private static Game game;

    public static void main(String[] args) {
        game = new Game(new S27523Projekt04());
    }

    @Override
    public void endGameMain(EndGameMainEvent evt) {
        game.startNewGame();
    }
}
class Game {
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

class GameFrame
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

class GameLogic
        extends Thread
        implements ChangeDirListener, SetInputUsernameListener {

    private static int SCORE = 0;

    private final int GAME_TICK = 200;
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

    private int scoreToOverrite;
    //1 - glowa, 2 - segment, 3 - jablko
    public GameLogic() {
        dir = 1;
        gameBoard[12][11] = 1; //glowa
        snake = new SnakeModel(gameBoard, dir, 12, 11, this);
        scoreToOverrite = 0;

        this.playerList = FileManager.readFromFile();
        running = true;
        logicThread = new Thread(() -> {
            while (running) {
                snake.moveSnake();
                if(SCORE!=0)
                    scoreToOverrite = SCORE;
                fireTick(new TickEvent(this, gameBoard));
                try {
                    Thread.sleep(GAME_TICK);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(scoreToOverrite);
            this.playerList.add(new Player(username, scoreToOverrite));
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

    public synchronized void refreshScore() {
        SCORE++;
        fireRefreshScore(new ScoreRefreshEvent(this, SCORE));
    }

    private synchronized void fireRefreshScore(ScoreRefreshEvent evt) {
        for (RefreshScoreListener listener : refreshScoreListeners) {
            listener.refreshScore(evt);
        }
    }

    private synchronized void fireTick(TickEvent evt) {
        for (TickEventListener listener : tickListeners) {
            listener.displayCurrBoardState(evt);
        }
    }

    private synchronized void fireEndGame(EndGameEvent evt) {
        for (EndGameListener listener : endGameListeners) {
            listener.endGame(evt);
        }
    }

    @Override
    public synchronized void updateDir(ChangeDirEvent evt) {
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

    public synchronized static void updateCurrBoardStateGL(int[][] gameBoardState) {
        gameBoard = gameBoardState;
    }

    public void endGame() {
        this.running = false;
        SCORE = 0;
        gameBoard = new int[25][16];
    }

    @Override
    public synchronized void setUsername(InputUsernameEvent evt) {
        this.username = evt.getUsername();
    }

    public synchronized void startLogicT() {
        this.logicThread.start();
    }
}


class GamePanel
        extends JPanel
        implements RefreshScoreListener, TickEventListener, EndGameListener {
    private JTable gameTable;
    private JPanel scorePanel;

    private static int SCORE = 0;
    private ImageIcon appleIcon = new ImageIcon("icons8-apple-64.png");
    private ImageIcon snakeHeadIcon = new ImageIcon("icons8-snake-96.png");
    private ImageIcon snakeBodyIcon = new ImageIcon("icons8-rounded-square-64.png");
    private ImageIcon backgroundImg = new ImageIcon("snakeBackground.png");
    private String username;
    private ArrayList<ChangeDirListener> changeDirListeners = new ArrayList<>();

    private ArrayList<SetInputUsernameListener> setNicknameListeners = new ArrayList<>();

    private ArrayList<EndGameListenerMain> endGameListenerMains = new ArrayList<>();

    public GamePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        username = showInputDialog();

        scorePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                Font font = new Font("Arial", Font.BOLD, 20);
                g2d.setFont(font);
                g2d.setColor(Color.PINK);

                String scoreText = "SCORE: " + SCORE;
                int x = getWidth() / 2 - 45;
                int y = getHeight() / 2 + 8;
                g2d.drawString(scoreText, x, y);
            }
        };

        scorePanel.setSize(new Dimension(getWidth(), 50));
        scorePanel.setBackground(new Color(75, 0, 130));
        repaint();

        gameTable = new JTable(new GameTableModel());
        gameTable.setBackground(Color.black);
        gameTable.setRowHeight(30);
        gameTable.setEnabled(false);
        gameTable.setDefaultRenderer(GameCell.class, new SnakeRenderer());

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addKeyListener(new KeyAdapter() {
            private int dir = 1;
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int dir = 0;
                if (keyCode == KeyEvent.VK_UP) {
                    dir = 1;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    dir = 3;
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    dir = 4;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    dir = 2;
                }
                if (dir != 0 && Math.abs(this.dir - dir)!=2) {
                    if(dir==1){
                        snakeHeadIcon = new ImageIcon("icons8-year-of-snake-48UP.png");
                    }else if(dir==2){
                        snakeHeadIcon =  new ImageIcon("icons8-year-of-snake-48-2.png");
                    }else if(dir==3){
                        snakeHeadIcon =  new ImageIcon("icons8-year-of-snake-48-3.png");
                    }else if(dir==4){
                        snakeHeadIcon =  new ImageIcon("icons8-year-of-snake-48.png");
                    }
                    this.dir = dir;
                    fireChangeDir(new ChangeDirEvent(this, dir));
                }
            }
        });

        this.add(gameTable, BorderLayout.CENTER);
        this.add(scorePanel, BorderLayout.SOUTH);
    }
    public synchronized void fireSetUsername(InputUsernameEvent evt) {
        System.out.println(setNicknameListeners.size());
        for (SetInputUsernameListener l : setNicknameListeners) {
            l.setUsername(evt);
        }
    }

    public void addSetUsernameListener(SetInputUsernameListener l) {
        this.setNicknameListeners.add(l);
    }

    public synchronized void fireEndGameMain(EndGameMainEvent evt) {
        for (EndGameListenerMain l : endGameListenerMains) {
            l.endGameMain(evt);
        }
    }

    public void addEndGameMainListener(EndGameListenerMain l) {
        this.endGameListenerMains.add(l);
    }

    public void fireChangeDir(ChangeDirEvent evt) {
        for (ChangeDirListener l : changeDirListeners) {
            l.updateDir(evt);
        }
    }

    public void addChangeDirListener(ChangeDirListener l) {
        this.changeDirListeners.add(l);
    }

    @Override
    public Dimension getPreferredSize() {
        Container parent = getParent();
        if (parent != null) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            return new Dimension(width, height);
        }
        return super.getPreferredSize();
    }

    @Override
    public synchronized void refreshScore(ScoreRefreshEvent evt) {
        SCORE = evt.getNewScore();
        scorePanel.repaint();
    }


    private void displayGameBoard(int[][] gameBoard) {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public synchronized void displayCurrBoardState(TickEvent evt) {
        int[][] currBoardState = evt.getboardState();
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 16; j++) {
                if (currBoardState[i][j] == 1) {
                    Image scaledImage = snakeHeadIcon.getImage().getScaledInstance(gameTable.getColumnModel().getColumn(j).getWidth(), gameTable.getRowHeight(i), Image.SCALE_SMOOTH);
                    snakeHeadIcon = new ImageIcon(scaledImage);
                    gameTable.setValueAt(new GameCell(GameCell.SNAKE_HEAD, snakeHeadIcon), i, j);
                } else if (currBoardState[i][j] == 2) {
                    Image scaledImage = snakeBodyIcon.getImage().getScaledInstance(gameTable.getColumnModel().getColumn(j).getWidth(), gameTable.getRowHeight(i), Image.SCALE_SMOOTH);
                    snakeBodyIcon = new ImageIcon(scaledImage);
                    gameTable.setValueAt(new GameCell(GameCell.SNAKE_BODY, snakeBodyIcon), i, j);
                } else if (currBoardState[i][j] == 3) {
                    Image scaledImage = appleIcon.getImage().getScaledInstance(gameTable.getColumnModel().getColumn(j).getWidth(), gameTable.getRowHeight(i), Image.SCALE_SMOOTH);
                    appleIcon = new ImageIcon(scaledImage);
                    gameTable.setValueAt(new GameCell(GameCell.APPLE, appleIcon), i, j);
                } else {
                    gameTable.setValueAt(new GameCell(GameCell.EMPTY, null), i, j);
                }
            }
        }
        this.repaint();
    }

    @Override
    public synchronized void endGame(EndGameEvent evt) {
        if (evt.getendGame()) {
            SwingUtilities.invokeLater(() -> {
                ImageIcon icon = new ImageIcon("icons8-sad-50.png");

                JOptionPane.showMessageDialog(this, createMessesageDialogCrash(), "CRASH", JOptionPane.INFORMATION_MESSAGE, icon);

                ArrayList<Player> players = evt.getPlayerList();

                players.sort(Comparator.comparingInt(Player::getScore).reversed());

                int numPlayersToSave = Math.min(players.size(), 10);

                players = new ArrayList<>(players.subList(0, numPlayersToSave));

                JTable table = createScoreTable(players);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                scrollPane.setBackground(new Color(106, 90, 205));

                JPanel panel = new JPanel(new FlowLayout());
                panel.setBackground(new Color(106, 90, 205));
                panel.add(scrollPane);

                JButton endGameButton = new JButton("Zakończ Grę");
                endGameButton.addActionListener(e -> {
                    System.exit(0);
                });

                JButton newGameButton = new JButton("Nowa Gra");
                JDialog dialog = new JDialog();
                newGameButton.addActionListener(e -> {
                    SCORE = 0;
                    dialog.dispose();
                    fireEndGameMain(new EndGameMainEvent(this,true));
                });

                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new Color(106, 90, 205));
                buttonPanel.add(endGameButton);
                buttonPanel.add(newGameButton);

                JPanel dialogPanel = new JPanel(new BorderLayout());
                dialogPanel.setBackground(new Color(106, 90, 205));
                dialogPanel.add(panel, BorderLayout.CENTER);
                dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

                dialog.setTitle("Tabela wyników");
                dialog.setModal(true);
                dialog.getContentPane().add(dialogPanel);
                dialog.pack();
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            });
        }
    }


    private JPanel createMessesageDialogCrash() {

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(75, 0, 130));
        messagePanel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Niestety uderzyłeś w ciało/ścianę");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setBackground(new Color(75, 0, 130));
        messageLabel.setForeground(Color.red);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        messagePanel.add(messageLabel, BorderLayout.CENTER);

        return messagePanel;
    }

    private JTable createScoreTable(ArrayList<Player> players) {
        Object[][] tableData = new Object[players.size()][2];
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            tableData[i][0] = player.getUsername();
            tableData[i][1] = player.getScore();
        }

        String[] columnNames = {"Username", "Score"};

        DefaultTableModel model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.getTableHeader().setBackground(new Color(75, 0, 130));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(new Color(106, 90, 205));
        table.setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 16));

        return table;
    }

    private String showInputDialog() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(106, 90, 205));

        JLabel messageLabel = new JLabel("Wprowadź nazwę użytkownika:");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JTextField inputField = new JTextField(20);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));

        panel.add(messageLabel);
        panel.add(inputField);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(106, 90, 205));
        JLabel titleLabel = new JLabel("SNAKE");
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 48));
        titleLabel.setForeground( new Color(0, 100, 0));
        titlePanel.add(titleLabel);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(titlePanel, BorderLayout.NORTH);
        wrapperPanel.add(panel, BorderLayout.CENTER);

        UIManager.put("OptionPane.okButtonText", "ZAGRAJ");
        UIManager.put("OptionPane.cancelButtonText", "WYJDŹ Z GRY");

        int result = JOptionPane.showOptionDialog(
                null,
                wrapperPanel,
                "Utwórz profil",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon("icons8-person-64.png"),
                null,
                null
        );

        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.cancelButtonText", "CANCEL");

        if (result == JOptionPane.OK_OPTION) {
            return inputField.getText();
        } else {
            System.exit(0);
            return null;
        }
    }



    public synchronized String getUsername() {
        return username;
    }

}

class SnakeModel {
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

    private synchronized boolean checkCollision(int headRow, int headCol) {
        return gameBoardState[headRow][headCol] == 2;
    }

    private synchronized void updateGameBoardState(int[][] gameBoardState) {
            this.gameBoardState = gameBoardState;

    }

    public synchronized void updateDir(int dir) {
        if(Math.abs(this.dir - dir)!=2)
            this.dir = dir;
    }

    private synchronized void updateApplePos(int newAppleRow, int newAppleCol) {
        this.appleRow = newAppleRow;
        this.appleCol = newAppleCol;
    }
}

class SnakeRenderer extends JPanel implements TableCellRenderer {
    private JLabel iconLabel;
    private static final ImageIcon DEFAULT_ICON = null;

    public SnakeRenderer() {
        setOpaque(false);
        iconLabel = new JLabel(DEFAULT_ICON);
        add(iconLabel);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(new Color(0, 0, 0, 0));
        this.iconLabel.setBackground(new Color(0, 0, 0, 0));
        GameCell gameCell = (GameCell) value;

        int cellWidth = table.getColumnModel().getColumn(column).getWidth();
        int cellHeight = table.getRowHeight(row);

        iconLabel.setPreferredSize(new Dimension(cellWidth, cellHeight));

        if (gameCell.getIcon() != null) {
            Image scaledImage = gameCell.getIcon().getImage().getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            iconLabel.setIcon(scaledIcon);
        } else {
            iconLabel.setIcon(DEFAULT_ICON);
        }

        return iconLabel;
    }

}

class GameTableModel extends AbstractTableModel {
    private static GameCell[][] gameBoard = new GameCell[25][16];

    public GameTableModel() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 16; j++) {
                gameBoard[i][j] = new GameCell();
            }
        }
    }

    @Override
    public int getRowCount() {
        return gameBoard.length;
    }

    @Override
    public int getColumnCount() {
        return gameBoard[0].length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return gameBoard[row][column];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (value instanceof GameCell) {
            gameBoard[row][column] = (GameCell) value;
            fireTableCellUpdated(row, column);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return GameCell.class;
    }
}

class FileManager {

    private static File file = new File("leaderboard.bin");
    public static void saveToFile(ArrayList<Player> playersList) {
        try {
            FileOutputStream fos = new FileOutputStream(file);

            Comparator<Player> scoreComparator = Comparator.comparingInt(Player::getScore).reversed();
            playersList.sort(scoreComparator);

            int numPlayersToSave = Math.min(playersList.size(), 10);

            for (int i = 0; i < numPlayersToSave; i++) {
                fos.write(playersList.get(i).getUsername().length());
                fos.write(playersList.get(i).getUsername().getBytes());
                for (int j = 0; j < 4; j++) {
                    fos.write(playersList.get(i).getScore() >> (8 * j));
                }
            }

            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Player> readFromFile() {
        ArrayList<Player> playersList = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(file);
            while (fis.available() > 0) {
                int nameLength = fis.read();
                byte[] nameBytes = new byte[nameLength];
                fis.read(nameBytes);
                String username = new String(nameBytes);

                int score = 0;
                for (int j = 0; j < 4; j++) {
                    score |= fis.read() << (8 * j);
                }

                Player player = new Player(username, score);
                playersList.add(player);
            }

            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        playersList.sort(Comparator.comparingInt(Player::getScore));

        if (playersList.size() > 10) {
            playersList = new ArrayList<>(playersList.subList(0, 10));
        }

        return playersList;
    }

}

class GameCell {
    public static final String APPLE = "APPLE";
    public static final String SNAKE_BODY = "SNAKE_BODY";
    public static final String SNAKE_HEAD = "SNAKE_HEAD";
    public static final String EMPTY = "EMPTY";
    private String content;
    private ImageIcon icon;

    public GameCell() {
        this.content = EMPTY;
        this.icon = null;
    }

    public GameCell(String content, ImageIcon icon) {
        this.content = content;
        this.icon = icon;
    }

    public synchronized String getContent() {
        return content;
    }

    public synchronized ImageIcon getIcon() {
        return icon;
    }

    public synchronized void setContent(String content) {
        this.content = content;
    }

    public synchronized void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}

class Player {
    private String username;
    private int score;

    public Player(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

class ChangeDirEvent
        extends EventObject {
    private int dir;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ChangeDirEvent(Object source,int dir) {
        super(source);
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

}
interface ChangeDirListener {
    void updateDir(ChangeDirEvent evt);
}
interface EndGameListener {
    void endGame(EndGameEvent evt);
}


class EndGameEvent
        extends EventObject {
    private boolean endGame;
    private ArrayList<Player> playerList;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EndGameEvent(Object source, boolean endGame, ArrayList<Player> playerList) {
        super(source);
        this.endGame = endGame;
        this.playerList=playerList;
    }

    public boolean getendGame() {
        return endGame;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}

interface EndGameListenerMain {
    void endGameMain(EndGameMainEvent evt);
}


class InputUsernameEvent
        extends EventObject {
    private String username;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public InputUsernameEvent(Object source,String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

class EndGameMainEvent
        extends EventObject {
    private boolean endGame;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EndGameMainEvent(Object source, boolean endGame) {
        super(source);
        this.endGame = endGame;
    }

    public boolean getendGame() {
        return endGame;
    }

}

interface SetInputUsernameListener {
    void setUsername(InputUsernameEvent evt);
}
interface RefreshScoreListener {
    void refreshScore(ScoreRefreshEvent evt);
}

class ScoreRefreshEvent
        extends EventObject {
    private int newScore;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ScoreRefreshEvent(Object source, int newScore) {
        super(source);
        this.newScore=newScore;
    }

    public int getNewScore() {
        return newScore;
    }
}
interface TickEventListener {
    void displayCurrBoardState(TickEvent evt);
}

class TickEvent extends EventObject {
    private int[][] boardState;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public TickEvent(Object source,int[][] boardState) {
        super(source);
        this.boardState = boardState;
    }

    public int[][] getboardState() {
        return boardState;
    }
}
