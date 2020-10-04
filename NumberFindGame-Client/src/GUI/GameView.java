package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView {
    public JPanel contentPane;
    private JLabel lblTest1;
    private JButton button1;
    private JPanel gamePane;
    private JPanel infoPane;

    public GameView() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello there");
                System.out.println(lblTest1.getLocation().x + lblTest1.getLocation().y);
                lblTest1.setText("Hello now");
                System.out.println("Before: " + lblTest1.getLocation().x + lblTest1.getLocation().y);
                System.out.println("After: " + lblTest1.getLocation().x + lblTest1.getLocation().y);
            }
        });
    }

    public void setData(GameView data) {
    }

    public void getData(GameView data) {
    }

    public boolean isModified(GameView data) {
        return false;
    }
}
