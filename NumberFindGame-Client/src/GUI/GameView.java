package GUI;

import bus.GameBUS;
import GUI.Components.LevelNodeButton;
import dto.LevelNode;
import dto.MatchPlayer;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView {
    // GameView.form's Components
    public JPanel contentPane;
    private JLabel lblFindThis;
    private JButton btnQuit;
    private JPanel gamePane;
    private JPanel infoPane;
    private JList<MatchPlayer> listPlayers;
    private JLabel lblTimer;

    private GameBUS gameBUS;

    public GameView() {
        $$$setupUI$$$();
        customizeComponents();
        bindListeners();
    }

    private void customizeComponents() {
        gamePane.setLayout(null);
    }

    private void bindListeners() {
        gamePane.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {                       // Trigger when game screen is loaded
                // Start game
                gameBUS = new GameBUS();
                gameBUS.viewBinder.lblFindThis = lblFindThis;
                gameBUS.viewBinder.lblTimer = lblTimer;
                gameBUS.viewBinder.listPlayers = listPlayers;
                gameBUS.viewBinder.update();
                renderLevel();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });

        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("NotImplemented: Quit Game");
            }
        });
    }

    private void renderLevel() {
        int btnSize = 30;                                                              // Kích thước của Button hiển thị
        int screenMargin = 15;     // Margin của Màn hình trận đấu, giúp cho Nút không bị che khuất bởi phạm vi hiển thị

        Rectangle gameRect = new Rectangle();  // gameRect lưu trữ vị trí, kích thước của khung MÀN HÌNH TRẬN ĐẤU lúc bấy giờ => Không cho phép resize
        gameRect.setRect(gamePane.getX(), gamePane.getY(), gamePane.getWidth(), gamePane.getHeight());

        for (LevelNode levelNode : gameBUS.getGame().getLevel()) {
            int posX =     // Vị trí mà LevelNode được đặt trên màn hình tương ứng với tỉ lệ của LevelNode.coord ([0,1])
                    (int) (
                            (levelNode.getCoord().x                    // coord.x là vị trí x của LevelNode, nằm trong [0,1]
                                    * (gameRect.width - screenMargin * 2))       // Chiều rộng màn hình trừ margin trái phải
                                    +
                                    (gameRect.x + screenMargin)  // Cộng với vị trí TopLeft của Màn hình trận đầu, Cộng với Margin trái
                    )
                            -
                            (btnSize / 2);  // Việc trừ cho nửa kích thước Button hiển thị giúp cho Button hiển thị đúng ở Center thay vì ở TopLeft
            int posY =
                    (int) (
                            (levelNode.getCoord().y
                                    * (gameRect.height - screenMargin * 2))
                                    +
                                    (gameRect.y + screenMargin)
                    )
                            -
                            (btnSize / 2);

            LevelNodeButton btn = new LevelNodeButton(levelNode.getValue(), new Point(posX, posY));
            levelNode.setButton(btn);
            btn.addToContainer(gamePane);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handle_OnClick_NodeLevelButton(levelNode);
                }
            });
        }
        gamePane.repaint();
    }

    // Event Handlers

    private void handle_OnClick_NodeLevelButton(LevelNode levelNode) {
        gameBUS.action_ClientChooseLevelNode(levelNode);
    }

    /**
     * IDE Generated methods
     */

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(new Color(-1));
        gamePane = new JPanel();
        gamePane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gamePane.setBackground(new Color(-15064194));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(gamePane, gbc);
        infoPane = new JPanel();
        infoPane.setLayout(new GridBagLayout());
        infoPane.setBackground(new Color(-1));
        infoPane.setForeground(new Color(-16777216));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(infoPane, gbc);
        btnQuit = new JButton();
        btnQuit.setText("Quit");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        infoPane.add(btnQuit, gbc);
        lblFindThis = new JLabel();
        Font lblFindThisFont = this.$$$getFont$$$(null, -1, 32, lblFindThis.getFont());
        if (lblFindThisFont != null) lblFindThis.setFont(lblFindThisFont);
        lblFindThis.setText("10");
        lblFindThis.putClientProperty("html.disable", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(20, 0, 20, 0);
        infoPane.add(lblFindThis, gbc);
        listPlayers = new JList();
        listPlayers.setBackground(new Color(-1));
        listPlayers.setForeground(new Color(-16777216));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        infoPane.add(listPlayers, gbc);
        lblTimer = new JLabel();
        Font lblTimerFont = this.$$$getFont$$$(null, -1, 21, lblTimer.getFont());
        if (lblTimerFont != null) lblTimer.setFont(lblTimerFont);
        lblTimer.setText("3:00");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        infoPane.add(lblTimer, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        infoPane.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        infoPane.add(spacer2, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
