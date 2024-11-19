package Events;

import java.util.EventObject;

public class BscEvent extends EventObject {

    private boolean AddRemove;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public BscEvent(Object source, boolean AddRemove) {
        super(source);
        this.AddRemove = AddRemove;
    }

    public boolean isAddRemove() {
        return AddRemove;
    }
}
