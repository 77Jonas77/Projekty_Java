package Events;

import java.util.EventObject;

public class ButtonEndEvent extends EventObject {

    private boolean end;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ButtonEndEvent(Object source, boolean end) {
        super(source);
        this.end = end;
    }

    public boolean isEnd() {
        return end;
    }
}
