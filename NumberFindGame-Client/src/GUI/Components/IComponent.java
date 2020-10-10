package GUI.Components;

import java.awt.*;

interface IComponent {
    default public void addToContainer(Container container) {
        container.add((Component) this);
    }
}
