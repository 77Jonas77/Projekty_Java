import javax.swing.*;

public class GameCell {
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

    public String getContent() {
        return content;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}

