import java.util.EventObject;

public class ChangeDirEvent
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
