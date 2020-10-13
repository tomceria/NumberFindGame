package Common;

import java.awt.*;

public interface IComponent {
    default public void addToContainer(Container container) {
        container.add((Component) this);
    }
}
