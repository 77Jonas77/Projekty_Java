import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel
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
    public void fireSetUsername(InputUsernameEvent evt) {
        System.out.println(setNicknameListeners.size());
        for (SetInputUsernameListener l : setNicknameListeners) {
            l.setUsername(evt);
        }
    }

    public void addSetUsernameListener(SetInputUsernameListener l) {
        this.setNicknameListeners.add(l);
    }

    public void fireEndGameMain(EndGameMainEvent evt) {
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
    public void refreshScore(ScoreRefreshEvent evt) {
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
    public void displayCurrBoardState(TickEvent evt) {
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
    public void endGame(EndGameEvent evt) {
        if (evt.getendGame()) {
            SwingUtilities.invokeLater(() -> {
                ImageIcon icon = new ImageIcon("icons8-sad-50.png");

                JOptionPane.showMessageDialog(this, createMessesageDialogCrash(), "CRASH", JOptionPane.INFORMATION_MESSAGE, icon);

                ArrayList<Player> players = evt.getPlayerList();
                players.sort(Comparator.comparingInt(Player::getScore).reversed());

                players = new ArrayList<>(players.subList(0, 10));

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



    public String getUsername() {
        return username;
    }

}
