package bus;

import Socket.ClientHandler;
import Socket.ClientManager;
import Socket.GameServer;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponse_GameEnd;
import Socket.Response.SocketResponse_GameProps;
import Socket.Response.SocketResponse_GameResult;
import dto.*;

import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static util.Maths.valueFromTwoRanges;

public class GameBUS {
    private Game_Server game;  // PARENT
    private Timer gameTimer;

    public GameBUS(Game_Server game) {
        this.game = game;
    }

    public void initGame() {
        game.setLevel(generateLevel(game.getMatchConfig().getNumberQty()));
        this.mutateLevel(game.getLevel());
        this.performPlacingPlayers();

        // Timer-related statements. These has to be the LAST STATEMENT in the init() to provide fair gameplay
        game.setStartTime(LocalTime.now());
        game.setCurrentLevelAndResetTimer(1);                                                    // also reset timer for CurrentLevel
        this.gameTimer = new Timer();
        this.gameTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        GameBUS.this.performEndGame();
                    }
                },
                this.getGame().getMatchConfig().getTime()
        );
    }

    /**
     * Client gửi số Đã chọn đúng. Phải là synchronized tránh 2 player gửi 1 số cùng lúc
     *
     * @param levelNode
     * @param sendingPlayer
     * @return
     */
    public synchronized boolean req_sendLevelNodeForValidation(LevelNode levelNode, MatchPlayer_Server sendingPlayer) {
        boolean accept = false;

        if (this.getCurrentLevelNodeValue() == levelNode.getValue()) {  // Kiểm tra xem số gửi từ Client có đúng với CurrentLevel của Server hay ko
            accept = true;

            performSuccessLevelNodeValidation(levelNode, sendingPlayer);

            /**
             * Tiến hành thủ tục chuyển sang Level tiếp theo. Nếu đã vượt qua LevelNode cuối cùng thì end game
             */
            if (this.getGame().getCurrentLevel().getValue() < this.getGame().getLevel().size()) {
                performGoNextLevel(levelNode, sendingPlayer);
            } else {
                performEndGame();
            }
        }

        return accept;
    }

    // Privates

    private GameServer getServer() {
        return this.game.getServer();
    }

    /**
     * Trong Level có pickingMatchPlayer, prop đó có thể là MatchPlayer_Server => Clone object mới ko có ref đó
     *
     * @param level Danh sách Level gốc (có thể chứa pickingMatchPlayer là MatchPlayer_Server)
     * @return Danh sách LevelNode đã được "thanh tẩy"
     */
    private ArrayList<LevelNode> cleanseLevelForTransfer(ArrayList<LevelNode> level) {
        ArrayList<LevelNode> newLevel = new ArrayList<LevelNode>();
        for (LevelNode lN : level) {
            newLevel.add(new LevelNode(lN, true));
        }
        return newLevel;
    }

    /**
     * Ép MatchPlayer_Server về MatchPlayers (Không có reference gì đến ClientHandler) để có thể chuyển gói dữ liệu về cho Client
     *
     * @param matchPlayers Danh sách MatchPlayer gốc (là MatchPlayer_Server)
     * @return Danh sách MatchPlayer đã được "thanh tẩy"
     */
    private ArrayList<MatchPlayer> cleanseMatchPlayersForTransfer(ArrayList<MatchPlayer> matchPlayers) {
        ArrayList<MatchPlayer> newMatchPlayers = new ArrayList<MatchPlayer>();
        for (MatchPlayer mP : matchPlayers) {
            newMatchPlayers.add(new MatchPlayer(mP));
        }
        return newMatchPlayers;
    }

    private int getCurrentLevelNodeValue() {
        int currentLevelNodeIndex = this.getGame().getCurrentLevel().getValue() - 1;
        int value = this.getGame().getLevel()
                .get(currentLevelNodeIndex)
                .getValue();

        return value;
    }

    // Privates CONNECTION Methods

    private void sendResponseToPlayer(SocketResponse response, UUID clientHandlerId) {
        ClientManager clientManager = this.getServer().getClientManager();
        clientManager.sendResponseToClient(clientHandlerId, response);
    }

    private void broadcastResponseToPlayers(SocketResponse response) {
        ClientManager clientManager = this.getServer().getClientManager();
        clientManager.sendResponseToBulkClients(
                ((Game_Server) this.getGame()).getPlayerClients(),
                response);
    }

    /**
     * Mutate level, set lucky level node
     *
     * @param level
     */
    private void mutateLevel(ArrayList<LevelNode> level) {
        int levelSize = level.size();
        // phần trăm các số lucky
        double percent  = 10;
        // các số sẽ biến đổi trên tổng số node
        double mutatedNumbers = Math.ceil((double) levelSize * percent / 100);
        // mảng các số từ 1 đến levelsize
        ArrayList<Integer> indexArr = new ArrayList<>();

        // thêm vị trí của level node
        for (int i = 0;  i < levelSize; i++) {
            indexArr.add(i);
        }
        // shuffle mảng
        Random rand = new Random();
        Collections.shuffle(indexArr, rand);

        // lấy 10% số đầu sau khi đã shuffle mảng vị trí
        for (int i = 0; i < mutatedNumbers; i++) {
            level.get(indexArr.get(i)).addMutation(LevelNode.Mutations.LUCKY);
        }

        int x = 0;
    }

    // Private BUSINESS Methods
    private ArrayList<LevelNode> generateLevel(int count) {
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

    private void performSuccessLevelNodeValidation(LevelNode levelNode, MatchPlayer sendingPlayer) {
        /**
         * Tăng điểm cho sendingPlayer
         */
        int addScore = 1;
        // nếu là số may mắn (logic if sẽ còn thay đổi, hiện tại chỉ check size của mutations)
        if (levelNode.getMutations().size() > 0) {
            addScore = 3;
        }
        this.performOneUpScore(sendingPlayer, game.getCurrentLevel().getTimeStart(), addScore);

        /**
         * Gán thứ hạng mới cho các player
         */
        this.performPlacingPlayers();
    }

    private void performGoNextLevel(LevelNode levelNode, MatchPlayer sendingPlayer) {
        /**
         * Gán levelNode.picker = sendingPlayer (lọc theo levelNode value)
         */
        this.getGame().getLevel()
                .stream().filter(lN -> levelNode.getValue() == lN.getValue())
                .collect(Collectors.toList()).get(0)
                .setPickingMatchPlayer(sendingPlayer);

        /**
         * Tăng Level cho Game
         */
        if (this.getGame().getCurrentLevel().getValue() < this.getGame().getLevel().size()) {
            game.setCurrentLevelAndResetTimer(
                    this.getGame().getCurrentLevel().getValue() + 1
            );
        } else {
            // TODO: Reach Game-ending phase
            game.setCurrentLevelAndResetTimer(1);  // Reset
        }

        /**
         * CUỐI CÙNG: Thông báo cho TẤT CẢ người chơi với dữ liệu Game mới
         */
        broadcastResponseToPlayers(
                new SocketResponse_GameProps(
                        new CurrentLevel(this.getGame().getCurrentLevel()),
                        cleanseLevelForTransfer(this.getGame().getLevel()),
                        cleanseMatchPlayersForTransfer(this.getGame().getMatchPlayers())
                )
        );
    }

    private void performOneUpScore(MatchPlayer matchPlayer, LocalTime timeStart, int addScore) {
        matchPlayer.setScore(matchPlayer.getScore() + addScore);
        matchPlayer.newAvgTime(timeStart);
    }

    private void performPlacingPlayers() {
        // Sắp xếp thứ hạng của người chơi trong trận đầu dựa vào:
        // Điểm tìm số (score); Thời gian trung bình tìm ra số (avgTime)
        // Nếu 2 người chơi có score bằng nhau, sẽ dựa vào avgTime để chọn người chơi thứ hạng cao hơn

        // Chuẩn bị: Tạo ra 2 danh sách giành riêng cho Người chơi có điểm và Người chơi ko có điểm
        ArrayList<MatchPlayer> matchPlayersWithScore = new ArrayList<MatchPlayer>(
                game.getMatchPlayers().stream().filter(mP -> mP.getScore() > 0).collect(Collectors.toList())
        );
        int matchPlayersWithScoreSize = matchPlayersWithScore.size();
        ArrayList<MatchPlayer> matchPlayersNoScore = new ArrayList<MatchPlayer>(
                game.getMatchPlayers().stream().filter(mP -> mP.getScore() <= 0).collect(Collectors.toList())
        );

        // Bước 1: Sắp xếp theo điểm
        Collections.sort(matchPlayersWithScore, Comparator.comparingInt(MatchPlayer::getScore));
        Collections.reverse(matchPlayersWithScore);

        // Bước 2: Sắp xếp theo avg time
        for (int i = 0; i < matchPlayersWithScoreSize; i++) {
            for (int j = i + 1; j < matchPlayersWithScoreSize; j++) {
                if ((matchPlayersWithScore.get(i).getAvgTime() > matchPlayersWithScore.get(j).getAvgTime()) && (matchPlayersWithScore.get(i).getScore() == matchPlayersWithScore.get(j).getScore())) {
                    Collections.swap(matchPlayersWithScore, i, j);
                }
            }
        }

        // Bước 3: Với danh sách tạm đã có thứ tự thứ hạng => gán Placing
        for (int i = 0; i < matchPlayersWithScoreSize; i++) {
            matchPlayersWithScore.get(i).setPlacing(i+1);
        }

        // Bước 4: Gán Placing = LAST cho danh sách Người chơi ko có điểm
        for (int i = 0; i < matchPlayersNoScore.size(); i++) {
            matchPlayersNoScore.get(i).setPlacing(game.getMatchPlayers().size());
        }
    }

    private void performEndGame() {
        /**
         * Ngưng timer của game. Ưu tiên thực hiện trước hết
         */
        this.gameTimer.cancel();

        /**
         * 1. Thông báo với players rằng game đã kết thúc
         * => Quá trình xử lý kết quả có thể kéo dài, việc gửi thông báo này tránh player gửi thêm các request dư thừa
         */
        this.broadcastResponseToPlayers(
                new SocketResponse_GameEnd()
        );

        /**
         * 2. Lưu thông tin trận đấu và điểm của players
         */

        // TODO: LƯU THÔNG TIN TRẬN ĐẤU VÀ ĐIỂM

        /**
         * 3. Gửi Kết quả Trận đấu cho các players
         * Các lệnh bên dưới có thể dùng broadcast,
         * Nhưng vì yêu cầu mỗi player nhận về response khác nhau (clientPlayerIsWinner)
         * => Mỗi clientHandler được xử lý khác nhau
         */
        PlayerDTO winner = null;
        List<MatchPlayer> matchPlayersWithFirstPlace = this.getGame().getMatchPlayers().stream()
                .filter(mP -> mP.getPlacing() == 1).collect(Collectors.toList());
        if (matchPlayersWithFirstPlace.size() > 0) {
            winner = matchPlayersWithFirstPlace.get(0).getPlayer();
        }
        for (ClientHandler clientHandler : this.getServer().getClientManager().getClientConnections().values()) {
            PlayerDTO clientPlayer = ((MatchPlayer_Server) clientHandler.getClientIdentifier()).getPlayer();
            boolean clientPlayerIsWinner = clientPlayer.equals(winner);

            sendResponseToPlayer(
                    new SocketResponse_GameResult(
                            cleanseMatchPlayersForTransfer(this.getGame().getMatchPlayers()),
                            clientPlayer,
                            winner,
                            clientPlayerIsWinner
                    ),
                    clientHandler.getId()
            );
        }

        /**
         * 5. Xoá Điểm số của các MatchPlayer
         */
        for (MatchPlayer mP : this.getGame().getMatchPlayers()) {
            mP.setScore(0);
            mP.setPlacing(0);
            ((MatchPlayer_Server) mP).setAvgTime(0);
        }

        /**
         * 6. (OPTIONAL) Báo cho GameRoom biết rằng game đã KẾT THÚC, cập nhật GameRoom. Gửi GameRoom mới đến players
         * Optional vì: Có thể có trường hợp Game được khởi động độc lập không có GameRoom
         */
        List<GameRoom_Server> gameRoomOfGameAsList = this.getServer().getGameRooms().stream()
                .filter(o -> o.getId() == this.getGame().getGameRoomInfo().getId())
                .collect(Collectors.toList());
        if (gameRoomOfGameAsList.size() >= 1) {
            GameRoom_Server gameRoom = gameRoomOfGameAsList.get(0);
            gameRoom.getGameRoomBUS().endGame();
            gameRoom.getGameRoomBUS().notifyUpdateGameRoomProps();
        }
    }

    // Properties

    public Game getGame() {
        return game;
    }

}
