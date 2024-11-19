import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class SnakeRenderer extends JPanel implements TableCellRenderer {
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
