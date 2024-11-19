import java.util.ArrayList;
import java.util.EventObject;

public class EndGameMainEvent extends EventObject {
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