import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//dla pliku Pana Profesora 1 to biale, kiedy u mnie 0 to biale, stad moga wynikac problemy z ruchami w przypadku takiej specyfikacji (ktora nie byla okreslona w projekcie)

public class s27523Projekt02 {
    //todo try catch

    public static void main(String[] args) {
        new gameManager(new CommunicationPanel());
        gameManager.MainManager();
    }
}

class gameManager {

    public static CommunicationInterface CI;

    public gameManager(CommunicationInterface CI) {
        gameManager.CI = CI;
    }

    // 0 to biale, 1 to czarne
    public static boolean castling = false;
    public static boolean isCheck = false;
    public static boolean enPassant = false;
    public static int countRounds = 1;
    public static Bierka lastMove;
    public static boolean lastMovePawn2 = false;
    public static ArrayList<Bierka> capturedFigures = new ArrayList<>();

    public static void MainManager() {
        CI.HelloMessesage();
        Scanner sc = new Scanner(System.in);
        String ifGameLoading = sc.next();
        boolean checkOptionLoading = false;
        Bierka[][] chessBoard = null;

        //czy wczytujemy
        while (!checkOptionLoading) {
            switch (ifGameLoading) {
                case "yes" -> {
                    CI.GameState("Loading... ");
                    String path = "/Users/jonaszsojka/Downloads/chessTest.bin";
                    try {
                        FileInputStream fis = new FileInputStream(path);
                        chessBoard = FileManager.LoadGame(fis);
                        fis.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    checkOptionLoading = true;
                }
                case "no" -> {
                    chessBoard = NewChessBoard();
                    checkOptionLoading = true;
                }
                default -> {
                    CI.InvalidInput();
                    ifGameLoading = sc.next();
                }
            }
        }
        Game(chessBoard);
        try {
            FileManager.fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Bierka[][] NewChessBoard() {
        Bierka[][] defaultChessBoard = {
                {new Rook(1, 0, 0), new Knight(1, 1, 0), new Bishop(1, 2, 0), new Queen(1, 3, 0), new King(1, 4, 0), new Bishop(1, 5, 0), new Knight(1, 6, 0), new Rook(1, 7, 0)},
                {new Pawn(1, 0, 1), new Pawn(1, 1, 1), new Pawn(1, 2, 1), new Pawn(1, 3, 1), new Pawn(1, 4, 1), new Pawn(1, 5, 1), new Pawn(1, 6, 1), new Pawn(1, 7, 1)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Pawn(0, 0, 6), new Pawn(0, 1, 6), new Pawn(0, 2, 6), new Pawn(0, 3, 6), new Pawn(0, 4, 6), new Pawn(0, 5, 6), new Pawn(0, 6, 6), new Pawn(0, 7, 6)},
                {new Rook(0, 0, 7), new Knight(0, 1, 7), new Bishop(0, 2, 7), new Queen(0, 3, 7), new King(0, 4, 7), new Bishop(0, 5, 7), new Knight(0, 6, 7), new Rook(0, 7, 7)}
        };
        return defaultChessBoard;
    }

    public static void Game(Bierka[][] chessBoard) {
        CI.CustomMessesage("GAME STARTS IN 3...2...1... GL HAVE FUN");
        Scanner sc = new Scanner(System.in);
        //gra
        boolean checkIfContinue = true;
        CI.printBoard(gameManager.convertBoardToString(chessBoard));
        //opcje stanu gry
        boolean checkIfOptionContinue = false;
        char option;

        while (checkIfContinue) {
            CI.EveryRoundCheckIfContinue();
            option = sc.next().charAt(0);
            while (!checkIfOptionContinue) {
                switch (option) {
                    case 'L' -> {
                        if (gameManager.countRounds % 2 == 1) {
                            CI.GameState("White surrenders");
                        } else {
                            CI.GameState("Black surrender");
                        }
                        checkIfContinue = false;
                        checkIfOptionContinue = true;
                    }
                    case 'Q' -> {
                        CI.GameState("You quit the game");
                        checkIfContinue = false;
                        checkIfOptionContinue = true;
                    }
                    case 'S' -> {
                        CI.GameState("You saved the game");
                        try {
                            FileManager.saveGame(chessBoard);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        checkIfOptionContinue = true;
                    }
                    case 'R' -> {
                        CI.GameState("Loading game...");
                        String path = sc.next();
                        try {
                            FileInputStream fis = new FileInputStream(path);
                            chessBoard = FileManager.LoadGame(fis);
                            fis.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        //wczytana gra --> warunki do wyzerowania itd
                        checkIfOptionContinue = true;
                    }
                    case 'M' -> {
                        checkIfOptionContinue = true;
                    }
                    case 'D' -> {
                        CI.GameState("Does the 2nd player want to draw?");
                        boolean checkIfDrawContinue = false;
                        while (!checkIfDrawContinue) {
                            char drawoption = sc.next().charAt(0);
                            switch (drawoption) {
                                case 'Y' -> {
                                    CI.GameState("DRAW AGREED");
                                    checkIfContinue = false;
                                    checkIfOptionContinue = true;
                                    checkIfDrawContinue = true;
                                }
                                case 'N' -> {
                                    CI.GameState("DRAW NOT AGREED");
                                    checkIfOptionContinue = true;
                                    checkIfDrawContinue = true;
                                }
                                default -> {
                                    CI.InvalidInput();
                                }
                            }
                        }
                    }
                    default -> {
                        CI.InvalidInput();
                        option = sc.next().charAt(0);
                    }
                }
            }
            int destRow, destCol, row = 0, col = 0;
            boolean correctColor = false;
            if (checkIfContinue) {
                //sprawdzenie czy kolor jest adekwatny do rundy
                while (!correctColor) {
                    CI.EveryRoundChooseFigure(gameManager.countRounds % 2);
                    CI.printBoard(gameManager.convertBoardToString(chessBoard));

                    col = sc.nextInt();
                    row = sc.nextInt();
                    if (col >= 1 && col <= 8 && row >= 1 && row <= 8 && chessBoard[row-1][col-1] != null && checkFigureColor(chessBoard, row, col)) {
                        correctColor = true;
                    }
                }
                //destination
                CI.EveryRoundChooseDestination(gameManager.countRounds % 2);
                //zagapilem sie i nie odjalem odrazu 1, stad wszedzie niepotrzebnie -1, niestety nie ma czasu na poprawe :(
                destCol = sc.nextInt();
                destRow = sc.nextInt();

                if (destCol >= 1 && destCol <= 8 && destRow <= 8 && destRow >= 1) {
                    //rozwazenie przypadku w ktorym jest i nie jest szachowany + sprawdzanie poprawnosci wykonywanego ruchu
                    if (chessBoard[row - 1][col - 1] != null && !isCheck) {
                        //czy ruch nie bedzie szachowal swojego wlasnego krola
                        Bierka[][] newBoard = copyBoard(chessBoard);
                        if (newBoard[row - 1][col - 1].isMovePoss(newBoard, destRow - 1, destCol - 1)) {
                            Bierka tmp = newBoard[row - 1][col - 1];
                            newBoard[row - 1][col - 1] = null;
                            newBoard[destRow - 1][destCol - 1] = tmp;
                            newBoard[destRow - 1][destCol - 1].setPos(new int[]{destRow - 1, destCol - 1});
                            if (!checkIsCheck(newBoard, gameManager.countRounds % 2 == 0 ? 1 : 0)) {
                                chessBoard = chessBoard[row - 1][col - 1].move(chessBoard, destCol - 1, destRow - 1);
                            } else {
                                CI.CustomMessesage("It would check ur king");
                            }
                        } else {
                            gameManager.CI.IncorrectMove();
                        }
                        //czy ruch odszachuje krola
                    } else if (chessBoard[row - 1][col - 1] != null && isCheck) {
                        Bierka[][] newBoard = copyBoard(chessBoard);
                        if (newBoard[row - 1][col - 1].isMovePoss(newBoard, destRow - 1, destCol - 1)) {
                            Bierka tmp = newBoard[row - 1][col - 1];
                            newBoard[row - 1][col - 1] = null;
                            newBoard[destRow - 1][destCol - 1] = tmp;
                            newBoard[destRow - 1][destCol - 1].setPos(new int[]{destRow - 1, destCol - 1});
                            if (!checkIsCheck(newBoard, gameManager.countRounds % 2 == 0 ? 1 : 0)) {
                                chessBoard = chessBoard[row - 1][col - 1].move(chessBoard, destCol - 1, destRow - 1);
                            } else if (checkIsCheck(newBoard, gameManager.countRounds % 2 == 0 ? 1 : 0) && checkIsCheckMate(newBoard, gameManager.countRounds % 2 == 0 ? 0 : 1)) {
                                //sprawdzenie czy mimo ze jest szach, to czy ruch daje szach mat na przeciwniku
                                chessBoard = chessBoard[row - 1][col - 1].move(chessBoard, destCol - 1, destRow - 1);
                            } else {
                                CI.GameState("Break check");
                            }
                        } else {
                            gameManager.CI.IncorrectMove();
                        }
                    } else {
                        CI.CustomMessesage("The pole you've chosen is empty");
                    }
                }

                //sprawdzanie czy jest szach po wykonaniu ruchu i jak jest, to czy odrazu pozniej jest mat
                if ((gameManager.countRounds - 1) % 2 == 1) {
                    if (checkIsCheck(chessBoard, 1)) {
                        CI.GameState("CHECK ON BLACK");
                        isCheck = true;
                        if (checkIsCheckMate(chessBoard, 1)) {
                            CI.GameState("CHECK MATE! WHITE WON");
                            checkIfContinue = false;
                        }
                    }
                } else {
                    if (checkIsCheck(chessBoard, 0)) {
                        CI.GameState("CHECK ON WHITE");
                        isCheck = true;
                        if (checkIsCheckMate(chessBoard, 0)) {
                            CI.GameState("CHECK MATE! BLACK WON");
                            checkIfContinue = false;
                        }
                    }
                }
                CI.printBoard(gameManager.convertBoardToString(chessBoard));
                checkIfOptionContinue = false;
            }
            //runda
        }
    }

    public static boolean checkIsCheck(Bierka[][] currBoardState, int color) {
        int destX = 0;
        int destY = 0;
        //szukanie krola
        for (int i = 0; i < currBoardState.length; i++) {
            for (int j = 0; j < currBoardState[i].length; j++) {
                if (currBoardState[i][j] != null) {
                    if (currBoardState[i][j].getId() == 1 && currBoardState[i][j].getColor() == color) {
                        destX = j;
                        destY = i;
                    }
                }
            }
        }

        //sprawdzanie czy cos nie moze atakowac tego krola / czy szach
        for (int i = 0; i < currBoardState.length; i++) {
            for (int j = 0; j < currBoardState[i].length; j++) {
                if (currBoardState[i][j] != null) {
                    if (currBoardState[i][j].getColor() != color && currBoardState[i][j].checkMoveSzach(currBoardState, destY, destX)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkIsCheckMate(Bierka[][] currBoardState, int color) {
        //sprawdzanie mata   :/
        for (int i = 0; i < currBoardState.length; i++) {
            for (int j = 0; j < currBoardState[i].length; j++) {
                if (currBoardState[i][j] != null && currBoardState[i][j].getColor() == color) {
                    for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                            Bierka[][] newBoard = copyBoard(currBoardState);
                            if (newBoard[i][j].isMovePoss(newBoard, row, col)) {
                                Bierka tmp = newBoard[i][j];
                                newBoard[i][j] = null;
                                newBoard[row][col] = tmp;
                                newBoard[row][col].setPos(new int[]{col, row});
                                if (!checkIsCheck(newBoard, color)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private static Bierka[][] copyBoard(Bierka[][] srcBoard) {
        Bierka[][] copiedBoard = new Bierka[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (srcBoard[row][col] != null) {
                    copiedBoard[row][col] = srcBoard[row][col].copyFigure(col, row);
                } else {
                    copiedBoard[row][col] = null;
                }
            }
        }
        return copiedBoard;
    }

    public static boolean checkFigureColor(Bierka[][] currBoard, int row, int col) {
        if (currBoard[row - 1][col - 1] != null) {
            if (gameManager.countRounds % 2 == 1 && currBoard[row - 1][col - 1].getColor() == 0) {
                return true;
            } else if (gameManager.countRounds % 2 == 0 && currBoard[row - 1][col - 1].getColor() == 1) {
                return true;
            } else {
                CI.GameState((gameManager.countRounds % 2 == 0 ? "Black's turn now" : "White's turn now!"));
            }
        } else {
            CI.CustomMessesage("The pole you've chosen is empty");
            return false;
        }
        return false;
    }

    public static StringBuilder convertBoardToString(Bierka[][] board) {
        StringBuilder convertBoardToString = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                //kolorowanie klatek
                if ((row + col) % 2 == 0) {
                    //bialy
                    convertBoardToString.append("\u001B[48;5;250m");
                } else {
                    //szary
                    convertBoardToString.append("\u001B[47m");
                }
                if (board[row][col] == null) {
                    convertBoardToString.append("   ");
                } else {
                    if (board[row][col].getColor() == 0) {
                        convertBoardToString.append("\u001B[38;5;231m");
                    } else {
                        convertBoardToString.append("\u001B[30m");
                    }
                    convertBoardToString.append(" ").append(board[row][col].getIcon()).append(" ");
                }
            }
            convertBoardToString.append("\u001B[0m");
            convertBoardToString.append(row + 1 + "\n");
        }
        convertBoardToString.append(" A  B  C  D  E  F  G  H\n");
        return convertBoardToString;
    }
}

class CommunicationPanel implements CommunicationInterface {
    @Override
    public void printBoard(StringBuilder board) {
        System.out.println(board);
    }

    @Override
    public void HelloMessesage() {
        System.out.println("====CHESS GAME====");
        System.out.println("Do you want to load a saved game? : yes/no");
    }

    @Override
    public void CustomMessesage(String s) {
        System.out.println(s);
    }

    @Override
    public void EveryRoundChooseFigure(int color) {
        System.out.println("====ROUND " + gameManager.countRounds + "====");
        if (color == 1) {
            System.out.println("White moves. Choose a figure: (col,row)");
            System.out.println();
        } else {
            System.out.println("Black moves. Choose a figure: (col, row)");
            System.out.println();
        }
    }

    @Override
    public void EveryRoundChooseDestination(int color) {
        if (color == 1) {
            System.out.println("White chooses destination. Choose a pole: (col,row)");
        } else {
            System.out.println("Black chooses destination. Choose a pole: (col, row)");
        }
    }

    @Override
    public void EveryRoundCheckIfContinue() {
        System.out.println("L - Surrender");
        System.out.println("S - Save the game");
        System.out.println("Q - Quit the game");
        System.out.println("D - Draw");
        System.out.println("R - Load a game");
        System.out.println((gameManager.countRounds == 1 ? "M - Let's play" : "M - Move"));
    }

    @Override
    public void IncorrectMove() {
        System.out.println("Incorrect move!");
    }

    @Override
    public void GameState(String s) {
        System.out.println(s);
    }

    @Override
    public void InvalidInput() {
        System.out.println("Invalid input. Please enter correct answer!");
    }
}

class FileManager {
    public static FileOutputStream fos;

    static {
        try {
            fos = new FileOutputStream(new File("/Users/jonaszsojka/IdeaProjects/Project_test_2/out/chessGameSaved.bin"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveGame(Bierka[][] currBoardState) throws IOException {
        //zapisywanie figur znajdujacych sie na planszy
        for (int i = 0; i < currBoardState.length; i++) {
            for (int j = 0; j < currBoardState[i].length; j++) {
                if (currBoardState[i][j] != null) {
                    writeTwoBytes(fos, convertBierkaIntoBinData(currBoardState[i][j]));
                }
            }
        }
        //zapisywanie figur zbitych
        for (Bierka b : gameManager.capturedFigures) {
            writeTwoBytes(fos, convertBierkaIntoBinData(b));
        }
    }

    public static Bierka[][] LoadGame(FileInputStream fis) throws IOException {
        Bierka[][] LoadedBoard = new Bierka[8][8];
        gameManager.capturedFigures.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Bierka Figure = convertBinDataIntoBierka(readTwoBytes(fis));
                if (Figure != null) {
                    if (Figure.getPos()[1] != -1 && Figure.getPos()[0] != -1) {
                        LoadedBoard[Figure.getPos()[1]][Figure.getPos()[0]] = Figure;
                    } else {
                        gameManager.capturedFigures.add(Figure);
                    }
                }
            }
        }
        return LoadedBoard;
    }

    public static Bierka convertBinDataIntoBierka(int binData) {
        //pobranie rodzaju figury
        int id = (binData & 0b111);
        binData = binData >> 3;

        //pobranie poz poz
        int x = (binData & 0b1111) - 1;
        binData = binData >> 4;

        //pobranie poz pion
        int y = (binData & 0b1111) - 1;
        binData = binData >> 4;

        int color = (binData & 0b1);

        return switch (id) {
            case 0 -> new Pawn(color, x, y);

            case 1 -> new King(color, x, y);

            case 2 -> new Queen(color, x, y);

            case 3 -> new Rook(color, x, y);

            case 4 -> new Bishop(color, x, y);

            case 5 -> new Knight(color, x, y);

            default -> null;
        };
    }

    public static int convertBierkaIntoBinData(Bierka figure) {
        //zapisanie na 1 bicie koloru, ktory bd na MSB
        int binData = figure.getColor();

        //przesuniecie ze wzgledu na porwnywanie 4 ost bitow i zapisanie poz pion na 4 bitach
        binData = binData << 4;
        binData |= (figure.getPos()[1] + 1) & 0b1111;

        //to samo ale dla poz
        binData = binData << 4;
        binData |= (figure.getPos()[0] + 1) & 0b1111;

        //zapisanie rodzaju figury na 3 bitach, ktore bd na LSB
        binData = binData << 3;
        binData |= (figure.getId());

        return binData;
    }

    public static void writeTwoBytes(FileOutputStream fos, int binData) throws IOException {
        //wyslanie 16 bitow inforamcji
        for (int i = 1; i >= 0; i--) {
            fos.write(binData >> 8 * i);
        }
    }

    public static int readTwoBytes(FileInputStream fis) throws IOException {
        int binData = 0;
        //przepisanie 16 bitow informacji
        for (int i = 0; i < 2; i++) {
            binData = binData << 8;
            binData |= fis.read();
        }
        return binData;
    }

}

abstract class Bierka {
    private int id;
    private int color;
    private int[] pos;
    private char icon;

    public Bierka(int id, int color, int x, int y, char icon) {
        this.id = id;
        this.color = color;
        this.pos = new int[]{x, y};
        this.icon = icon;
    }

    public final Bierka[][] moves(Bierka[][] currBoardState, int x, int y, Movable FigureConCanMove) {
        if (FigureConCanMove.canMove(currBoardState, x, y)) {
            int currRow = this.getPos()[1];
            int currCol = this.getPos()[0];
            if (gameManager.castling) {
                if (currBoardState[currRow][currCol].getId() == 1) {
                    King tmp = (King) currBoardState[currRow][currCol];
                    currBoardState[currRow][currCol] = currBoardState[y][x];
                    currBoardState[currRow][currCol].setPos(new int[]{currCol, currRow});

                    currBoardState[y][x] = tmp;
                    currBoardState[y][x].setPos(new int[]{x, y});

                } else {
                    Rook tmp = (Rook) currBoardState[currRow][currCol];
                    currBoardState[currRow][currCol] = currBoardState[y][x];
                    currBoardState[currRow][currCol].setPos(new int[]{currCol, currRow});

                    currBoardState[y][x] = tmp;
                    currBoardState[y][x].setPos(new int[]{x, y});
                }
                gameManager.lastMovePawn2 = false;
                gameManager.castling = false;
            } else if (gameManager.enPassant) {
                //chyba niemozliwa sytuacja
                if (currBoardState[y][x] != null) {
                    currBoardState[y][x].setPos(new int[]{-1, -1});
                    gameManager.capturedFigures.add(currBoardState[y][x]);
                }
                currBoardState[y][x] = this;
                currBoardState[currRow][currCol] = null;
                currBoardState[currRow][x] = null;
                this.setPos(new int[]{x, y});
                gameManager.enPassant = false;
                //wymiana pionka na dowolna figure
            } else if (this instanceof Pawn && (this.getColor() == 0 && y == 0 || this.getColor() == 1 && y == 7)) {
                Scanner scanner = new Scanner(System.in);
                gameManager.CI.GameState("Enter the type of figure you want to promote to Q/R/N/B:  ");
                char figureType = scanner.next().charAt(0);
                boolean checkFigureTypeOption = false;

                while (!checkFigureTypeOption) {
                    switch (figureType) {
                        case 'Q':
                            currBoardState[y][x] = new Queen(this.getColor(), x, y);
                            checkFigureTypeOption = true;
                            break;
                        case 'R':
                            currBoardState[y][x] = new Rook(this.getColor(), x, y);
                            checkFigureTypeOption = true;
                            break;
                        case 'N':
                            currBoardState[y][x] = new Knight(this.getColor(), x, y);
                            checkFigureTypeOption = true;
                            break;
                        case 'B':
                            currBoardState[y][x] = new Bishop(this.getColor(), x, y);
                            checkFigureTypeOption = true;
                            break;
                        default:
                            gameManager.CI.InvalidInput();
                            figureType = scanner.next().charAt(0);
                            break;
                    }
                    currBoardState[currRow][currCol] = null;
                }
            } else {
                if (currBoardState[y][x] != null) {
                    currBoardState[y][x].setPos(new int[]{-1, -1});
                    gameManager.capturedFigures.add(currBoardState[y][x]);
                }
                currBoardState[y][x] = this;
                currBoardState[currRow][currCol] = null;
                this.setPos(new int[]{x, y});
                gameManager.lastMovePawn2 = false;

                if (this instanceof Pawn) {
                    int steps = Math.abs(y - currRow);
                    if (steps == 2)
                        gameManager.lastMovePawn2 = true;
                }
            }
            gameManager.lastMove = this;
            gameManager.countRounds++;
        }
        return currBoardState;
    }

    public abstract Bierka[][] move(Bierka[][] currBoardState, int destRow, int destCol);

    public abstract boolean checkMoveSzach(Bierka[][] currBoardState, int destRow, int destCol);

    public abstract Bierka copyFigure(int col, int row);

    public abstract boolean isMovePoss(Bierka[][] currBoardState, int destRow, int destCol);

    public static boolean isOnBoard(int destCol, int destRow) {
        if (destRow < 0 || destRow > 7 || destCol < 0 || destCol > 7) {
            return false;
        }
        return true;
    }

    public static boolean isDiagonal(int destCol, int destRow, int currCol, int currRow, int id) {
        if (!(Math.abs(destRow - currRow) == Math.abs(destCol - currCol))) {
            return false;
        }
        return true;
    }

    protected static boolean isStraight(int destX, int destY, int currCol, int currRow) {
        if ((currRow == destY && currCol != destX) || (currCol == destX && currRow != destY)) {
            return true;
        } else {  // ruch nie jest w jednej linii
            return false;
        }
    }

    public boolean isPoleFree(Bierka[][] currBoardState, int destX, int destY) {
        int row = this.getPos()[1];
        int col = this.getPos()[0];

        if (currBoardState[destY][destX] != null && currBoardState[destY][destX].getColor() == currBoardState[row][col].getColor()) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public int[] getPos() {
        return pos;
    }

    public char getIcon() {
        return icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
    }

    public void setIcon(char icon) {
        this.icon = icon;
    }

}

class King extends Bierka {
    //powinno byc prywatne i inicjowane przy tworzeniu nowej bierki
    private boolean hasMoved = false;

    King(int color, int col, int row) {
        super(1, color, col, row, color == 0 ? '\u2654' : '\u265A');

    }

    public Bierka[][] move(Bierka[][] currBoardState, int destX, int destY) {
        return super.moves(currBoardState, destX, destY, (board, destx, desty) -> {
            if (!isOnBoard(destX, destY)) {
                return false;
            }
            if (!isPathValid(currBoardState, destX, destY)) {
                return false;
            }
            this.setHasMoved(true);
            return true;
        });
    }

    @Override
    public boolean checkMoveSzach(Bierka[][] currBoardState, int destRow, int destCol) {
        return false;
    }

    private boolean isPathValid(Bierka[][] currBoardState, int destX, int destY) {
        int col = this.getPos()[0];
        int row = this.getPos()[1];

        int hor = Math.abs(col - destX);
        int ver = Math.abs(row - destY);

        //wykluczenie ruchow wiekszych od 4
        if (hor > 4 || ver > 4)
            return false;

        //sprawdzenie zwyklego ruchu
        if (hor <= 1 && ver <= 1) {
            return isPoleFree(currBoardState, destX, destY);
        }

        //sprawdzenie roszady
        if (!this.hasMoved && currBoardState[destY][destX] instanceof Rook && !(((Rook) currBoardState[destY][destX]).ifHasMoved())) {
            //sprawdzenie krotkiej roszady
            if (hor == 3) {
                if (currBoardState[destY][destX - 1] != null || currBoardState[destY][destX - 2] != null || isPathBetweenAttacked(currBoardState, col, row, destX)) {
                    return false;
                } else {
                    gameManager.castling = true;
                    return true;
                }
            }
            //sprawdzenie dlugiej roszady
            if (hor == 4) {
                if (currBoardState[destY][destX + 1] != null || currBoardState[destY][destX + 2] != null || currBoardState[destY][destX + 3] != null || isPathBetweenAttacked(currBoardState, col, row, destX)) {
                    return false;
                } else {
                    gameManager.castling = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Bierka copyFigure(int col, int row) {
        return new King(this.getColor(), col, row);
    }

    @Override
    public boolean isMovePoss(Bierka[][] currBoardState, int destRow, int destCol) {
        int col = this.getPos()[0];
        int row = this.getPos()[1];

        int hor = Math.abs(col - destCol);
        int ver = Math.abs(row - destRow);

        if (!this.hasMoved && currBoardState[destRow][destCol] instanceof Rook && !(((Rook) currBoardState[destRow][destCol]).ifHasMoved())) {
            //sprawdzenie krotkiej roszady
            if (hor == 3) {
                if (currBoardState[destRow][destCol - 1] != null || currBoardState[destRow][destCol - 2] != null || isPathBetweenAttacked(currBoardState, col, row, destCol)) {
                    return false;
                } else {
                    gameManager.castling = true;
                    return true;
                }
            }
            //sprawdzenie dlugiej roszady
            if (hor == 4) {
                if (currBoardState[destRow][destCol + 1] != null || currBoardState[destRow][destCol + 2] != null || currBoardState[destRow][destCol + 3] != null || isPathBetweenAttacked(currBoardState, col, row, destCol)) {
                    return false;
                } else {
                    gameManager.castling = true;
                    return true;
                }
            }
        }

        //wykluczenie ruchow wiekszych od 4
        if (hor > 4 || ver > 4)
            return false;

        //sprawdzenie zwyklego ruchu
        if (hor <= 1 && ver <= 1) {
            return isPoleFree(currBoardState, destCol, destRow);
        }

        return false;
    }

    private boolean isPathBetweenAttacked(Bierka[][] currBoardState, int kingX, int kingY, int rookX) {
        int minX = Math.min(kingX, rookX);
        int maxX = Math.max(kingX, rookX);

        for (int pole = minX; pole <= maxX; pole++) {
            for (int i = 0; i < currBoardState.length; i++) {
                for (int j = 0; j < currBoardState[i].length; j++) {
                    if (currBoardState[i][j] != null) {
                        if (currBoardState[i][j].getColor() != this.getColor() && currBoardState[i][j].isMovePoss(currBoardState, kingY, pole)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean ifHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}

class Queen extends Bierka {
    Queen(int color, int col, int row) {
        super(2, color, col, row, color == 0 ? '\u2655' : '\u265B');
    }

    @Override
    public boolean checkMoveSzach(Bierka[][] currBoardState, int destRow, int destCol) {
        if (!Bierka.isStraight(destCol, destRow, getPos()[0], getPos()[1]) && !Bierka.isDiagonal(destCol, destRow, getPos()[0], getPos()[1], this.getId())) {
            return false;
        }
        if (!isPathClear(currBoardState, destCol, destRow)) {
            return false;
        }
        return true;
    }

    public Bierka[][] move(Bierka[][] currBoardState, int destX, int destY) {
        return super.moves(currBoardState, destX, destY, (board, destx, desty) -> {
            if (!isOnBoard(destX, destY)) {
                return false;
            }
            if (!Bierka.isStraight(destX, destY, getPos()[0], getPos()[1]) && !Bierka.isDiagonal(destX, destY, this.getPos()[0], this.getPos()[1], this.getId())) {
                return false;
            }
            if (!isPathClear(board, destX, destY)) {
                return false;
            }
            return true;
        });
    }


    private boolean isPathClear(Bierka[][] currBoardState, int destX, int destY) {
        int row = this.getPos()[1];
        int col = this.getPos()[0];

        if (Bierka.isDiagonal(destX, destY, getPos()[0], getPos()[1], this.getId())) {
            //okreslamy kierunek
            int dirX = destX > col ? 1 : -1;
            int dirY = destY > row ? 1 : -1;

            //sprawdzamy pola pomiedzy
            for (int i = 1; i < Math.abs(row - destY); i++) {
                int checkX = col + (i * dirX);
                int checkY = row + (i * dirY);
                if (currBoardState[checkY][checkX] != null) {
                    return false;
                }
            }
        } else {
            int dirX = Integer.compare(destX, col);
            int dirY = Integer.compare(destY, row);

            int steps = Math.max(Math.abs(destX - col), Math.abs(destY - row));

            //spr pol pomiedzy
            for (int i = 1; i < steps; i++) {
                if (currBoardState[row + (dirY * i)][col + (dirX * i)] != null) {
                    return false;
                }
            }
        }
        //czy pole jest wolne
        return this.isPoleFree(currBoardState, destX, destY);
    }

    @Override
    public Bierka copyFigure(int col, int row) {
        return new Queen(this.getColor(), col, row);
    }

    @Override
    public boolean isMovePoss(Bierka[][] currBoardState, int destRow, int destCol) {
        if (!(Bierka.isStraight(destCol, destRow, getPos()[0], getPos()[1]) || Bierka.isDiagonal(destCol, destRow, getPos()[0], getPos()[1], this.getId()))) {
            return false;
        }
        if (!isPathClear(currBoardState, destCol, destRow)) {
            return false;
        }
        return true;
    }
}

class Rook extends Bierka {
    private boolean hasMoved = false;

    Rook(int color, int col, int row) {
        super(3, color, col, row, color == 0 ? '\u2656' : '\u265C');

    }

    @Override
    public boolean checkMoveSzach(Bierka[][] currBoardState, int destRow, int destCol) {
        if (!Bierka.isStraight(destCol, destRow, getPos()[0], getPos()[1])) {
            return false;
        }

        int row = this.getPos()[1];
        int col = this.getPos()[0];

        int hor = Math.abs(col - destRow);
        int ver = Math.abs(row - destRow);

        int dirX = Integer.compare(destCol, col);
        int dirY = Integer.compare(destRow, row);

        int steps = Math.max(hor, ver); //wybor wartosci ktora sie zmienila, mozna bylo inaczej

        //spr pol pomiedzy
        for (int i = 1; i < steps; i++) {
            if (currBoardState[row + dirY * i][col + dirX * i] != null) {
                return false;
            }
        }
        return true;
    }

    public Bierka[][] move(Bierka[][] currBoardState, int destX, int destY) {
        return this.moves(currBoardState, destX, destY, (board, destx, desty) -> {
            if (!isOnBoard(destX, destY)) {
                return false;
            }
            if (!Bierka.isStraight(destX, destY, getPos()[0], getPos()[1])) {
                return false;
            }
            if (!isPathClear(board, destX, destY)) {
                return false;
            }
            this.setHasMoved(true);
            return true;
        });

    }

    private boolean isPathClear(Bierka[][] currBoardState, int destX, int destY) {
        int row = this.getPos()[1];
        int col = this.getPos()[0];

        int hor = Math.abs(col - destX);
        int ver = Math.abs(row - destY);

        //sprawdzenie roszady
        if (!this.hasMoved && currBoardState[destY][destX] instanceof King && !((King) currBoardState[destY][destX]).ifHasMoved()) {
            //sprawdzenie krotkiej roszady
            if (hor == 3) {
                if (currBoardState[destY][destX + 1] != null || currBoardState[destY][destX + 2] != null || isPathBetweenAttacked(currBoardState, destX, col, row)) {
                    return false;
                } else {
                    gameManager.castling = true;
                    return true;
                }
            }
            //sprawdzenie dlugiej roszady
            else if (hor == 4) {
                if (currBoardState[destY][destX - 1] != null || currBoardState[destY][destX - 2] != null || currBoardState[destY][destX - 3] != null || isPathBetweenAttacked(currBoardState, destX, col, row)) {
                    return false;
                } else {
                    gameManager.castling = true;
                    return true;
                }
            }
        }

        //kierunek
        int dirX = Integer.compare(destX, col);
        int dirY = Integer.compare(destY, row);

        int steps = Math.max(hor, ver); //wybor wartosci ktora sie zmienila, mozna bylo inaczej

        //spr pol pomiedzy
        for (int i = 1; i < steps; i++) {
            if (currBoardState[row + dirY * i][col + dirX * i] != null) {
                return false;
            }
        }
        //czy pole jest wolne
        return this.isPoleFree(currBoardState, destX, destY);
    }

    private boolean isPathBetweenAttacked(Bierka[][] currBoardState, int kingX, int rookX, int rookY) {
        int minX = Math.min(kingX, rookX);
        int maxX = Math.max(kingX, rookX);

        for (int pole = minX; pole <= maxX; pole++) {
            for (int i = 0; i < currBoardState.length; i++) {
                for (int j = 0; j < currBoardState[i].length; j++) {
                    if (currBoardState[i][j] != null) {
                        if (currBoardState[i][j].getColor() != this.getColor() && currBoardState[i][j].isMovePoss(currBoardState, rookY, pole)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Bierka copyFigure(int col, int row) {
        return new Rook(this.getColor(), col, row);
    }

    @Override
    public boolean isMovePoss(Bierka[][] currBoardState, int destRow, int destCol) {
        int row = this.getPos()[1];
        int col = this.getPos()[0];

        int hor = Math.abs(col - destCol);
        int ver = Math.abs(row - destRow);

        if (!this.hasMoved && currBoardState[destRow][destCol] instanceof King && !((King) currBoardState[destRow][destCol]).ifHasMoved()) {
            //sprawdzenie krotkiej roszady
            if (hor == 3) {
                if (currBoardState[destRow][destCol + 1] != null || currBoardState[destRow][destCol + 2] != null || isPathBetweenAttacked(currBoardState, destCol, col, row)) {
                    return false;
                } else {
                    gameManager.castling = true;
                    return true;
                }
            }
            //sprawdzenie dlugiej roszady
            else if (hor == 4) {
                if (currBoardState[destRow][destCol - 1] != null || currBoardState[destRow][destCol - 2] != null || currBoardState[destRow][destCol - 3] != null || isPathBetweenAttacked(currBoardState, destCol, col, row)) {
                    return false;
                } else {
                    gameManager.castling = true;
                    return true;
                }
            }
        }

        int dirX = Integer.compare(destCol, col);
        int dirY = Integer.compare(destRow, row);

        int steps = Math.max(hor, ver); //wybor wartosci ktora sie zmienila, mozna bylo inaczej

        //spr pol pomiedzy
        for (int i = 1; i < steps; i++) {
            if (currBoardState[row + dirY * i][col + dirX * i] != null) {
                return false;
            }
        }
        //czy pole jest wolne
        return this.isPoleFree(currBoardState, destCol, destRow);
    }

    public boolean ifHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}

class Bishop extends Bierka {
    Bishop(int color, int col, int row) {
        super(4, color, col, row, color == 0 ? '\u2657' : '\u265d');
    }

    @Override
    public boolean checkMoveSzach(Bierka[][] currBoardState, int destRow, int destCol) {
        if (!Bierka.isDiagonal(destCol, destRow, getPos()[0], getPos()[1], this.getId())) {
            return false;
        }
        if (!isPathClear(currBoardState, destCol, destRow)) {
            return false;
        }
        return true;
    }

    public Bierka[][] move(Bierka[][] currBoardState, int destX, int destY) {
        return this.moves(currBoardState, destX, destY, (board, destx, desty) -> {
            if (!isOnBoard(destX, destY)) {
                return false;
            }
            if (!Bierka.isDiagonal(destX, destY, getPos()[0], getPos()[1], this.getId())) {
                return false;
            }
            if (!isPathClear(board, destX, destY)) {
                return false;
            }
            return true;
        });
    }

    private boolean isPathClear(Bierka[][] currBoardState, int destX, int destY) {
        int row = this.getPos()[1];
        int col = this.getPos()[0];

        //okreslamy kierunek
        int dirX = destX > col ? 1 : -1;
        int dirY = destY > row ? 1 : -1;

        //sprawdzamy pola pomiedzy
        for (int i = 1; i < Math.abs(row - destY); i++) {
            int checkX = col + (i * dirX);
            int checkY = row + (i * dirY);
            if (currBoardState[checkY][checkX] != null) {
                return false;
            }
        }
        //sprawdzamy docelowe pole --> kto stoi -> czy mozna wejsc
        if (!this.isPoleFree(currBoardState, destX, destY)) {
            return false;
        }
        return true;
    }

    @Override
    public Bierka copyFigure(int col, int row) {
        return new Bishop(this.getColor(), col, row);
    }

    @Override
    public boolean isMovePoss(Bierka[][] currBoardState, int destRow, int destCol) {
        if (!Bierka.isDiagonal(destCol, destRow, getPos()[0], getPos()[1], this.getId())) {
            return false;
        }
        if (!isPathClear(currBoardState, destCol, destRow)) {
            return false;
        }
        return true;
    }
}

class Knight extends Bierka {
    Knight(int color, int col, int row) {
        super(5, color, col, row, color == 0 ? '\u2658' : '\u265E');
    }

    @Override
    public boolean checkMoveSzach(Bierka[][] currBoardState, int destRow, int destCol) {
        //czy wykonany poprawny ruch dla figury
        if (!isPathValid(currBoardState, destCol, destRow)) {
            return false;
        }
        return true;
    }

    public Bierka[][] move(Bierka[][] currBoardState, int destX, int destY) {
        //PRZYKLAD WYKORZYSTANIA KLASY ANONIMOWEJ ZAMIAST LAMBDY
        Movable knightMove = new Movable() {
            @Override
            public boolean canMove(Bierka[][] currBoardState, int destRow, int destCol) {
                //czy na planszy
                if (!isOnBoard(destX, destY)) {
                    return false;
                }
                //czy wykonany poprawny ruch dla figury
                if (!isPathValid(currBoardState, destX, destY)) {
                    return false;
                }
                return true;
            }
        };
        return super.moves(currBoardState, destX, destY, knightMove);
    }

    private boolean isPathValid(Bierka[][] currBoardState, int destX, int destY) {
        if (!this.isPoleFree(currBoardState, destX, destY)) {
            return false;
        }

        int row = this.getPos()[1];
        int col = this.getPos()[0];

        //sprawdzenie warunkow ruchu dla knight --> dwa pion + jeden poz LUB jeden pion + dwa poz
        return (Math.abs(row - destY) == 2 && Math.abs(col - destX) == 1) || (Math.abs(row - destY) == 1 && Math.abs(col - destX) == 2);
    }

    @Override
    public Bierka copyFigure(int col, int row) {
        return new Knight(this.getColor(), col, row);
    }

    @Override
    public boolean isMovePoss(Bierka[][] currBoardState, int destRow, int destCol) {
        if (!isPathValid(currBoardState, destCol, destRow)) {
            return false;
        }
        return true;
    }
}

class Pawn extends Bierka {
    private boolean hasMoved = false;

    Pawn(int color, int col, int row) {
        super(0, color, col, row, color == 0 ? '\u2659' : '\u265F');
    }

    @Override
    public boolean checkMoveSzach(Bierka[][] currBoardState, int destRow, int destCol) {
        if (!this.isPoleFree(currBoardState, destCol, destRow)) {
            return false;
        }

        int col = this.getPos()[0];
        int row = this.getPos()[1];

        int dirY = (currBoardState[row][col].getColor() == 0 ? -1 : 1);
        int steps = dirY * (destRow - row);

        if (this.getColor() == 0 && (destRow - row) >= 0) {
            return false;
        } else if (this.getColor() == 1 && (destRow - row) <= 0) {
            return false;
        }

        if (steps > 2)
            return false;

        //sprawdzenie czy ruch wynosi 1 lub 2
        if (steps != dirY && steps != dirY * 2 && steps * -1 != dirY && steps * -1 != dirY * 2) {
            return false;
        }

        //sprawdzenie ruchu o2
        if (steps == dirY * 2 || steps == dirY * -2) {
            // czy cos blokuje pomiedzy i czy sie ruszyl wczensiej
            if (this.ifHasMoved() || currBoardState[row + dirY][col] != null) {
                return false;
            }

        }

        //sprawdzenie czy na ukosie znajduje sie przeciwnik
        if ((steps == dirY || steps == dirY * -1) && Math.abs(destCol - col) == 1) {
            if (currBoardState[destRow][destCol] == null || currBoardState[destRow][destCol].getColor() == this.getColor()) {
                return false;
            }
        } else {
            return isStraight(destCol, destRow, this.getPos()[0], this.getPos()[1]);
        }
        return true;
    }

    public Bierka[][] move(Bierka[][] currBoardState, int destX, int destY) {
        return super.moves(currBoardState, destX, destY, (board, destx, desty) -> {
            if (!isOnBoard(destX, destY)) {
                return false;
            }
            if (!isPathValid(currBoardState, destX, destY)) {
                return false;
            }
            this.setHasMoved(true);
            return true;
        });
    }

    private boolean isPathValid(Bierka[][] currBoardState, int destX, int destY) {
        //czy pole na ktore sie ruszamy jest zajete przez naszego pionka
        if (!this.isPoleFree(currBoardState, destX, destY)) {
            return false;
        }

        int col = this.getPos()[0];
        int row = this.getPos()[1];

        int dirY = (currBoardState[row][col].getColor() == 0 ? -1 : 1);
        int steps = dirY * (destY - row);

        if (this.getColor() == 0 && (destY - row) >= 0) {
            return false;
        } else if (this.getColor() == 1 && (destY - row) <= 0) {
            return false;
        }

        if (steps > 2)
            return false;

        //sprawdzenie czy ruch wynosi 1 lub 2
        if (steps != dirY && steps != dirY * 2 && steps * -1 != dirY && steps * -1 != dirY * 2) {
            return false;
        }

        //sprawdzenie ruchu o2
        if (steps == dirY * 2 || steps == dirY * -2) {
            // czy cos blokuje pomiedzy i czy sie ruszyl wczensiej
            if (this.ifHasMoved() || currBoardState[row + dirY][col] != null) {
                return false;
            }

        }

        //sprawdzenie bicia w locie
        if (ifEnPassant(currBoardState, destX, destY, row, steps, dirY)) {
            gameManager.enPassant = true;
            return true;
        }

        //sprawdzenie czy na ukosie znajduje sie przeciwnik
        if ((steps == dirY || steps == dirY * -1) && Math.abs(destX - col) == 1) {
            if (currBoardState[destY][destX] == null || currBoardState[destY][destX].getColor() == this.getColor()) {
                return false;
            }
        } else {
            return isStraight(destX, destY, this.getPos()[0], this.getPos()[1]);
        }
        return true;
    }

    private boolean ifEnPassant(Bierka[][] currBoardState, int destX, int destY, int row, int steps, int dirY) {
        if (steps == dirY || steps == dirY * -1) {
            //jesli pionek chcacy wykonac BWL juz sie ruszyl i pole docelowe jest puste
            if (hasMoved && currBoardState[destY][destX] == null) {
                //sprawdzenie czy na polu obok jest pionek przeciwnika i czy on byl ostatnio ruszony i czy o 2 + czy sa roznego koloru nwm czy potrzebne
                if (currBoardState[row][destX] != null && currBoardState[row][destX] instanceof Pawn &&
                        currBoardState[row][destX].getColor() != this.getColor() && gameManager.lastMove.getPos()[0] == currBoardState[row][destX].getPos()[0] && currBoardState[row][destX].getPos()[1] == this.getPos()[1] && gameManager.lastMovePawn2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isMovePoss(Bierka[][] currBoardState, int destRow, int destCol) {
        if (!this.isPoleFree(currBoardState, destCol, destRow)) {
            return false;
        }

        int col = this.getPos()[0];
        int row = this.getPos()[1];

        int dirY = (currBoardState[row][col].getColor() == 0 ? -1 : 1);
        int steps = dirY * (destRow - row);

        if (this.getColor() == 0 && (destRow - row) >= 0) {
            return false;
        } else if (this.getColor() == 1 && (destRow - row) <= 0) {
            return false;
        }

        if (steps > 2)
            return false;

        //sprawdzenie czy ruch wynosi 1 lub 2
        if (steps != dirY && steps != dirY * 2 && steps * -1 != dirY && steps * -1 != dirY * 2) {
            return false;
        }

        //sprawdzenie ruchu o2
        if (steps == dirY * 2 || steps == dirY * -2) {
            // czy cos blokuje pomiedzy i czy sie ruszyl wczensiej
            if (this.ifHasMoved() || currBoardState[row + dirY][col] != null) {
                return false;
            }

        }

        //enpas
        if (ifEnPassant(currBoardState, destCol, destRow, row, steps, dirY)) {
            gameManager.enPassant = true;
            return true;
        }
        //sprawdzenie czy na ukosie znajduje sie przeciwnik
        if ((steps == dirY || steps == dirY * -1) && Math.abs(destCol - col) == 1) {
            if (currBoardState[destRow][destCol] == null || currBoardState[destRow][destCol].getColor() == this.getColor()) {
                return false;
            }
        } else {
            return isStraight(destCol, destRow, this.getPos()[0], this.getPos()[1]);
        }
        return true;
    }

    @Override
    public Bierka copyFigure(int col, int row) {
        return new Pawn(this.getColor(), col, row);
    }


    public boolean ifHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}

interface Movable {
    boolean canMove(Bierka[][] currBoardState, int destRow, int destCol);
}

interface CommunicationInterface {
    void HelloMessesage();

    void CustomMessesage(String s);

    void printBoard(StringBuilder board);

    void EveryRoundChooseFigure(int color);

    void EveryRoundChooseDestination(int color);

    void EveryRoundCheckIfContinue();

    void IncorrectMove();

    void GameState(String s);

    void InvalidInput();

}