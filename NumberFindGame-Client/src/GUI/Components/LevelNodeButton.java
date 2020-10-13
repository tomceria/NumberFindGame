package GUI.Components;

import Common.IComponent;
import Models.MatchPlayer;

import javax.swing.*;
import java.awt.*;

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
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setMargin(new Insets(0, SIZE * -1, 0, SIZE * -1));
        this.setBorderPainted(false);
        this.setBorder(null);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    public void setPicked(MatchPlayer matchPlayer) {
        this.setOpaque(true);
        this.setBackground(matchPlayer.getUiColor());
        this.setForeground(Color.BLACK);
    }
}
