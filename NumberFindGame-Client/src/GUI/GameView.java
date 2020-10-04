package GUI;

import javax.swing.*;

public class GameView {
    public JPanel contentPane;
    private JLabel lblTest1;
    private JTextField txtTest1;

    private String texter = "Hello now!";

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameView");
        frame.setContentPane(new GameView().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public String getTexter() {
        return texter;
    }

    public void setTexter(String texter) {
        this.texter = texter;
    }

    public void setData(GameView data) {
    }

    public void getData(GameView data) {
    }

    public boolean isModified(GameView data) {
        return false;
    }
}
