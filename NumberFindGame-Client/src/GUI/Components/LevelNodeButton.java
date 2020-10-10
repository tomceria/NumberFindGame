package GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class LevelNodeButton extends JButton implements IComponent {
    final int SIZE = 30;

    public LevelNodeButton() {
        super();
        init();
    }

    public LevelNodeButton(int value, Point position) {
        super();
        init();
        this.setText(String.format("%d", value));
        this.setLocation(position);
    }

    private void init() {
        this.setSize(SIZE, SIZE);
    }

}
