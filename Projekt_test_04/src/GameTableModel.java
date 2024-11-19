import javax.swing.table.AbstractTableModel;

public class GameTableModel extends AbstractTableModel {
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
