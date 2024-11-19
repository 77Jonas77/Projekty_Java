package Ex01;

import java.awt.*;
import java.util.EventObject;

public class ColorEvent extends EventObject {
    private Color color;

    public ColorEvent(Color color) {
        super(source);
        this.color = color;
    }
}
