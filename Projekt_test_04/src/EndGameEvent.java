import java.util.ArrayList;
import java.util.EventObject;

public class EndGameEvent
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

