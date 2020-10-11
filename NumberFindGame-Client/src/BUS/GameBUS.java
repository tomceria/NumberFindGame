package BUS;

import Models.Game;
import Models.LevelNode;
import Models.MatchPlayer;
import Models.Player;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class GameBUS {
    // TODO: Placeholder - Dump Players data
    private ArrayList<Player> DUMPPLAYERS = new ArrayList<Player>() {
        {
            this.add(new Player(1, "luuminhhoang", "lala@gmail.com", "Hoàng", "Lưu"));
            this.add(new Player(2, "vohoanghuy", "lala2@gmail.com", "Huy", "Võ"));
            this.add(new Player(3, "huathianhngan", "lala3@gmail.com", "Ngân", "Hứa"));
            this.add(new Player(4, "tranthuythuyan", "lala4@gmail.com", "An", "Trần"));
        }
    };

    // Runtime Components
    private Game game;
    private HashMap<String, String> settings;

    public GameBUS() {
        this.game = initGame(1);
    }

    public Game getGame() {
        return game;
    }

    private Game initGame(int clientPlayerId) {
        Game game = new Game();
        MatchPlayer clientPlayer = null;

        game.setLevel(generateLevel(100));                                          // TODO: Get level from Server
        ArrayList<MatchPlayer> matchPlayers = new ArrayList<MatchPlayer>();
        for (Player player : getPlayersInRoom()) {                                // TODO: Get room's player from Server
            MatchPlayer matchPlayer = new MatchPlayer(player);
            matchPlayers.add(matchPlayer);
            if (player.getId() == clientPlayerId) {
                clientPlayer = matchPlayer;
            }
        }

        if (clientPlayer == null) {
            throw new RuntimeException("Player ID mismatch");
        }
        game.setClientPlayer(clientPlayer);
        return game;
    }

    public void handle_OnClick_NodeLevelButton(LevelNode levelNode) {
        if (game.getLevel().indexOf(levelNode) < 0) {
            throw new IllegalArgumentException("Selected LevelNode does not belong to this Game's context");
        }

        System.out.println(levelNode.getValue());
    }

    // TODO: SERVER-SIDE

    private ArrayList<LevelNode> generateLevel(int count) {                                      // TODO: Move to server
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

    private ArrayList<Player> getPlayersInRoom() {
        return DUMPPLAYERS;
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

}
