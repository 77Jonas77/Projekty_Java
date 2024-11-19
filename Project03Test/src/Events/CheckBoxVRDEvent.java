package Events;

import java.util.EventObject;

public class CheckBoxVRDEvent extends EventObject {

    private boolean deleteevery10sec;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CheckBoxVRDEvent(Object source, boolean deleteevery10sec) {
        super(source);
        this.deleteevery10sec = deleteevery10sec;
    }

    public boolean isDeleteEvery10sec() {
        return deleteevery10sec;
    }
}
