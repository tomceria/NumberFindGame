package GUI.Components;

import Common.IComponent;
import Models.MatchPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelNodeButton extends JButton implements IComponent {
    boolean isPicked = false;
    final int SIZE = 30;
    Color assignedColor;

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
        this.setFocusPainted(false);
        this.setBorderPainted(true);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#191E80")));
        this.setForeground(Color.WHITE);
        this.setBackground(Color.decode("#1A237E"));
        this.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Hover
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                setBackground(Color.decode("#2E41EC"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Color ogColor = isPicked ? assignedColor : Color.decode("#1A237E");
                setBackground(ogColor);
            }
        });
    }

    public void setPicked(MatchPlayer matchPlayer) {
        this.setOpaque(true);
        this.setBackground(matchPlayer.getUiColor());
        this.setForeground(Color.BLACK);
        this.isPicked = true;
        this.assignedColor = matchPlayer.getUiColor();
        System.out.println("PICKED");
    }
}
