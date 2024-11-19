import java.util.EventObject;

public class InputUsernameEvent
        extends EventObject {
    private String username;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public InputUsernameEvent(Object source,String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

