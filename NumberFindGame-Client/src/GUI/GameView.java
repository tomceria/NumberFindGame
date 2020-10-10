package GUI;

import GUI.Components.LevelNodeButton;
import Models.LevelNode;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameView {
    // GameView.form's Components
    public JPanel contentPane;
    private JLabel lblPlayerScore;
    private JButton btnQuit;
    private JPanel gamePane;
    private JPanel infoPane;
    private JList listPlayers;
    private JLabel lblTimer;

    // Runtime Components
    private ArrayList<JButton> btnLevelArr;

    public GameView() {
        $$$setupUI$$$();

        bindListeners();
        gamePane.setLayout(null);
    }

    public void bindListeners() {
        gamePane.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
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

        ArrayList<LevelNode> level = generateLevel(100);                         // TODO: Fetched from match server
        for (LevelNode levelNode : level) {
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
            btn.addToContainer(gamePane);
        }
        gamePane.repaint();
    }

    private ArrayList<LevelNode> generateLevel(int count) {                  // TODO: Move to Server
        Random rand = new Random();                                                            // TODO: add Seed support
        ArrayList<LevelNode> levelNodes = new ArrayList<LevelNode>();

        ArrayList<Integer> valueList = new ArrayList<Integer>();                      // Danh sách các số từ 1 đến count
        for (int i = 1; i <= count; i++) {                      // TODO: Better algorithm to generate sequential numbers
            valueList.add(i);
        }

        //*** GRID-BASED NODES GENERATION by Lưu Minh Hoàng ***
        // Mục tiêu: Đảm bảo fill các nút tương đối đầy lắp màn hình; Đảm bảo các nút không bị chồng lên nhau
        // Chia màn hình trận đấu thành một Ma trận với số dòng bằng số cột
        // Các nút được đặt tuần tự vào các ô block. Các nút được xê dịch ngẫu nhiên trong phạm vi block + margin 4 cạnh
        int blockPerRow = (int) Math.ceil(Math.sqrt(count));  // Với count số thì sẽ có sqrt(n)Xsqrt(n) ô block; ceil để lấy dư rồi xử lý sau
        int countPerfect = (int) Math.pow(blockPerRow, 2);  // Lấy số Chính phương tiếp theo của count (trường hợp count ko là SCP)
        double blockSize = (double) blockPerRow / countPerfect;                   // Kích thước mỗi block theo trục X Y tương ứng
        double maxForX = Math.pow(30, 2), maxForY = Math.pow(25, 2); // 2 số này được tìm ra dựa trên tỉ lệ màn hình của Khung màn hình trận đấu
        double marginX = valueFromTwoRanges(countPerfect, 100, maxForX, 0.2, 0.5); // Với mỗi ô block, có margin ngang và dọc (điều chỉnh tương ứng với tỉ lệ màn hình)
        double marginY = valueFromTwoRanges(countPerfect, 100, maxForY, 0.125, 0.5); // 0.2, 0.125 là do Aspect Ratio của Màn hình trận đấu; Bắt đầu tăng dần margin từ 100

        for (int i = 0; i < blockPerRow; i++) {
            for (int j = 0; j < blockPerRow; j++) {                  // Lặp tuần tự từ trên xuống dưới, từ trái qua phải
                LevelNode levelNode = new LevelNode();      // LevelNode chứa thông tin mỗi nút số mà người chơi sẽ chọn

                levelNode.setCoord(new Point2D.Double(
                        (
                                i                    // VD n=10, i đi từ 0->9, i+(...) giúp đặt các LevelNode vào đúng ô
                                        + (
                                        marginX                 // Đặt LevelNode vào trong khoảng BẮT ĐẦU từ sau margin trái
                                                + (1 - marginX * 2)  // = (1 - margin trái phải) = khoảng trong ô với phạm vi margin
                                                * rand.nextDouble()                         // Lấy ngẫu nhiêu [0,1] với phạm vi trên
                                )                                   // Giá trị tại đây nằm trong khoảng [0, blockPerRow]
                        ) * blockSize, // Cuối cùng, đổi thành tỉ lệ phù hợp với count sao cho LevelNode nằm trong khoảng [0,1]
                        (
                                j
                                        + (
                                        marginY
                                                + (1 - marginY * 2)
                                                * rand.nextDouble()
                                )
                        ) * blockSize
                ));

                levelNodes.add(levelNode);
            }
        }

        //** EXCEPTIONS
        // Với các trường hợp số count không phải là Số Chính Phương (Math.sqrt(count) not int),
        // Ta phải loại bỏ ngẫu nhiên các LevelNode sao cho levelNodes.size() == count
        while (levelNodes.size() != count) {
            int i = rand.nextInt(levelNodes.size());
            levelNodes.remove(i);
        }

        Collections.shuffle(levelNodes, rand); // Đảo trộn danh sách LevelNode để tạo sự ngẫu nhiên trong luồng trận đấu
        Collections.shuffle(valueList, rand);    // Danh sách số giá trị cũng được đảo trộn. Dùng "rand" để đảm bảo Seed

        for (int i = 0; i < count; i++) {
            levelNodes.get(i).setValue(valueList.get(i));                           // Đẩy giá trị số vào từng LevelNode
        }

        return levelNodes;
    }

    private double valueFromTwoRanges(double value, double minA, double maxA, double minB, double maxB) {
        // Từ một value nằm trong khoảng (minA,maxA), cho ra giá trị tỉ lệ tương ứng trong khoảng (minB, maxB)
        double percent = ((value - minA) * 100) / (maxA - minA);

        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }

        double result = ((percent * (maxB - minB)) / 100) + minB;

        return result;
    }

    public void setData(GameView data) {
    }

    public void getData(GameView data) {
    }

    public boolean isModified(GameView data) {
        return false;
    }

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
        contentPane.setBackground(new Color(-1049857));
        gamePane = new JPanel();
        gamePane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gamePane.setBackground(new Color(-7411713));
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
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        infoPane.add(btnQuit, gbc);
        lblPlayerScore = new JLabel();
        Font lblPlayerScoreFont = this.$$$getFont$$$(null, -1, 32, lblPlayerScore.getFont());
        if (lblPlayerScoreFont != null) lblPlayerScore.setFont(lblPlayerScoreFont);
        lblPlayerScore.setText("10");
        lblPlayerScore.putClientProperty("html.disable", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(20, 0, 20, 0);
        infoPane.add(lblPlayerScore, gbc);
        listPlayers = new JList();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
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
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        infoPane.add(lblTimer, gbc);
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
