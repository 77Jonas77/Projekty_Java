package Events;

import java.util.EventObject;

public class WindowClosingEvent extends EventObject {
    private boolean closed;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public WindowClosingEvent(Object source, boolean closed) {
        super(source);
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }
}
