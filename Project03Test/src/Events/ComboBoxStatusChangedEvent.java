package Events;

import java.util.EventObject;

public class ComboBoxStatusChangedEvent extends EventObject {

    private boolean status;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ComboBoxStatusChangedEvent(Object source, boolean status) {
        super(source);
        this.status = status;
    }

    public boolean isPaused() {
        return status;
    }
}
