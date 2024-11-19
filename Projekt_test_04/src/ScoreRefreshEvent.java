import java.util.EventObject;

public class ScoreRefreshEvent
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
