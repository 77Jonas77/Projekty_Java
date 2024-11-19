import java.util.EventObject;

public class TickEvent extends EventObject {
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
