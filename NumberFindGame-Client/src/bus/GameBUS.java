package bus;

import Common.ColorScheme;
import Common.ViewBinder;
import GUI.Components.GameMatchPlayerCellRenderer;
import GUI.Components.LevelNodeButton;
import Run.GameMain;
import Socket.Request.SocketRequest_GameQuit;
import Socket.Request.SocketRequest_GameSubmitLevelNode;
import Socket.Response.SocketResponse_GameProps;
import dto.*;
import util.Interpolate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class GameBUS {
    private Game_Client game; // PARENT
    private java.util.Timer blindingTimer; // để tránh bị trùng với Timer của java swing
    private Color defaultColor = ColorScheme.INDIGO;

    public GameBUS_ViewBinder viewBinder;

    public GameBUS(Game_Client game) {
        this.game = game;
        this.viewBinder = new GameBUS_ViewBinder();
    }

    // Functions

    public void initGame() {
        // TODO: Kiểm tra nếu các property rỗng (chưa dc gán dữ liệu từ Server) thì throw exception

//        int clientPlayerId = this.game.getClientPlayer().getPlayer().getId();
//        MatchPlayer clientPlayer = null;

        /**
         * Ép danh sách level từ ArrayList<LevelNode> thành ArrayList<LevelNode_Client>
         * Vì game.level chỉ nhận ArrayList<LevelNode>, ta phải tạo thêm 1 mảng và add các ArrayList<LevelNode_Client> đó vào
         */
        ArrayList<LevelNode> generatedLevel = game.getLevel();
        game.setLevel(
                LevelNode_Client.convertLevelNodesToLevelNodeClients(generatedLevel)
        );

        // Get Room's players info
        ArrayList<MatchPlayer> matchPlayers = this.game.getMatchPlayers();      // Also used for placings, by sort order
        matchPlayers = MatchPlayer_Client
                .convertMatchPlayersToMatchPlayerClients(matchPlayers);
        game.setMatchPlayers(matchPlayers);
        MatchPlayer_Client.setColorForMatchPlayers(game.getMatchPlayers());

        this.viewBinder.startUpdatePeriod();

        // timer để che số của client
        this.blindingTimer = new Timer();
    }

    public void renderLevel(JPanel gamePane) {
        int btnSize = 30;                                                              // Kích thước của Button hiển thị
        int screenMargin = 15;     // Margin của Màn hình trận đấu, giúp cho Nút không bị che khuất bởi phạm vi hiển thị

        Rectangle gameRect = new Rectangle();  // gameRect lưu trữ vị trí, kích thước của khung MÀN HÌNH TRẬN ĐẤU lúc bấy giờ => Không cho phép resize
        gameRect.setRect(gamePane.getX(), gamePane.getY(), gamePane.getWidth(), gamePane.getHeight());

        for (LevelNode levelNode : this.game.getLevel()) {
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
            ((LevelNode_Client) levelNode).setButton(btn);
            btn.addToContainer(gamePane);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action_ClientChooseLevelNode(levelNode);
                }
            });
        }
        gamePane.repaint();
    }

    public void action_ClientChooseLevelNode(LevelNode levelNode) {
        /**
         * Client-side Validation
         */
        if (levelNode.getValue() != this.getCurrentLevelNodeValue()) {
            System.out.println("Wrong number.");
            return;
        }
        if (game.getLevel().stream()
                .filter(lN -> lN.getValue() == levelNode.getValue())
                .collect(Collectors.toList()).size() <= 0
        ) {
            throw new IllegalArgumentException("Selected LevelNode does not belong to this Game's context");
        }

        /**
         * Kiểm tra hoàn tất => Gửi đến Server để xác nhận
         */
        LevelNode levelNodeSerializable = new LevelNode(levelNode);
        this.game.getClient().sendRequest(
                new SocketRequest_GameSubmitLevelNode(levelNodeSerializable, game.getClientPlayer())
        );

    }

    public void action_QuitGame() {
        this.game.getClient().sendRequest(
                new SocketRequest_GameQuit()
        );
    }

    public void listen_GameUpdated(SocketResponse_GameProps response) {
        /**
         * 1. Gán Current Level
         */
        game.setCurrentLevel(response.currentLevel);

        /**
         * 2. Gán MatchPlayer[], phải gộp với dữ liệu của MatchPlayer_Client[] cũ
         */
        ArrayList<MatchPlayer_Client> matchPlayers_OG = MatchPlayer_Client
                .castToMatchPlayerClients(game.getMatchPlayers());
        ArrayList<MatchPlayer> matchPlayers = MatchPlayer_Client
                .mergeMatchPlayersAndMatchPlayerClients(response.players, matchPlayers_OG);
        game.setMatchPlayers(matchPlayers);

        /**
         * 3. Gán LevelNode[], phải gộp với dữ liệu của LevelNode_Client[] cũ
         */
        ArrayList<LevelNode_Client> level_OG = LevelNode_Client
                .castToLevelNodeClients(game.getLevel());
        ArrayList<LevelNode> level = LevelNode_Client
                .mergeLevelNodesAndLevelNodeClients(response.level, level_OG, matchPlayers);
        game.setLevel(level);

        /**
         * 4. Cập nhật GUI
         */

        /**
         * 4.1. Thay đổi màu của LevelNodeButton được chọn
         */
        ArrayList<LevelNode> levelNodes = game.getLevel();
        for (LevelNode levelNode : levelNodes) {
            MatchPlayer pickingMatchPlayer = levelNode.getPickingMatchPlayer();
            if (pickingMatchPlayer != null) {
                ((LevelNode_Client) levelNode).getButton().setPicked(pickingMatchPlayer);
            }
        }

        // nếu index >= 0 (chưa kết thúc game)
        // kết thúc game thì currentLevel.value = 1
        int prevLevelIndex = game.getCurrentLevel().getValue() - 1 - 1;
        if (prevLevelIndex >= 0) {
            /**
             * 4.2. Blind (ẩn) các LevelNodeButton của client
             */
            LevelNode prevLevelNode = levelNodes.get(game.getCurrentLevel().getValue() - 1 - 1);
            if (prevLevelNode.getMutation() == LevelNode.Mutation.BLINDING) {
                String clientPlayerUsername = game.getClientPlayer().getPlayer().getUsername();
                String pickingPlayerUsername = prevLevelNode.getPickingMatchPlayer().getPlayer().getUsername();
                // nếu client khác username của player chọn đúng nút ưu tiên thì bị che
                if (!clientPlayerUsername.equals(pickingPlayerUsername)) {
                    ui_blindingLevelNodeButton(3000);
                }
            }
            /**
             * 4.3. Chớp màn hình (gamePane.background) bằng màu của MatchPlayer
             */
            this.ui_gamePaneBlink(
                this.getMatchPlayerClient(prevLevelNode.getPickingMatchPlayer()).getUiColor()
            );
        }


        /**
         * CUỐI CÙNG: Force lệnh update của ViewBinder để cập nhật ngay lập tức
         */
        this.viewBinder.update();
    }

    public void listen_GameEnd() {
        ViewBUS.gotoGameResultView();
        ViewBUS.gameView = null;
    }

    public void ui_gamePaneBlink(Color matchPlayerColor) {
        this.viewBinder.gamePane.setBackground(defaultColor);
        Thread runThread = new Thread(() -> {
            double ratio = 1;
            while (ratio >= 0) {
                ratio -= 0.05;
                int red = Interpolate.interpolateInt(ratio, matchPlayerColor.getRed(), defaultColor.getRed());
                int green = Interpolate.interpolateInt(ratio, matchPlayerColor.getGreen(), defaultColor.getGreen());
                int blue = Interpolate.interpolateInt(ratio, matchPlayerColor.getBlue(), defaultColor.getBlue());
                Color newColor = new Color(red, green, blue);
                GameBUS.this.viewBinder.gamePane.setBackground(newColor);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        runThread.start();
    }

    public void ui_blindingLevelNodeButton(int milliseconds) {
        // ẩn button khỏi client
        ArrayList<LevelNode_Client> levelNodes = LevelNode_Client.castToLevelNodeClients(game.getLevel());
        for (LevelNode_Client lnc : levelNodes) {
            lnc.getButton().setVisible(false);
        }

        TimerTask unblindBtn = new TimerTask() {
            @Override
            public void run() {
                for (LevelNode_Client lnc : levelNodes) {
                    lnc.getButton().setVisible(true);
                    // kết thúc timer task và cả timer của
                    cancel();
                    blindingTimer.cancel();
                }
            }
        };
        // hủy timer hiện tại
        blindingTimer.cancel();
        blindingTimer = new Timer();
        blindingTimer.schedule(unblindBtn, milliseconds);
    }

    public String ui_getTimerClock() {
        int timeInMillis = game.getMatchConfig().getTime();
        LocalDateTime timeEnd = game.getStartTime().plus(timeInMillis, ChronoUnit.MILLIS);
        LocalDateTime timeDiff = timeEnd.minusNanos(LocalDateTime.now().toLocalTime().toNanoOfDay());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");

        return timeDiff.format(dtf);
    }

    public void ui_initPlayerList(JList list) {
        ArrayList<MatchPlayer> matchPlayers = new ArrayList<MatchPlayer>(game.getMatchPlayers());
        MatchPlayer_Client.orderMatchPlayersByPlacing(matchPlayers);

        DefaultListModel<MatchPlayer> listModel = new DefaultListModel<MatchPlayer>();
        for (MatchPlayer matchPlayer : matchPlayers) {
            listModel.addElement(matchPlayer);
        }
        list.setModel(listModel);
        list.setCellRenderer(new GameMatchPlayerCellRenderer());
    }

    public void ui_initLblFindThis(JLabel label) {
        label.setText(GameBUS.this.getCurrentLevelNodeValue() + "");

        // đổi button thành màu xanh lá nếu là số may mắn, màu đỏ nếu là số ưu tiên
        if (GameBUS.this.getCurrentLevelNodeMutation() == LevelNode.Mutation.LUCKY) {
            label.setForeground(Color.GREEN.darker());
        } else if (GameBUS.this.getCurrentLevelNodeMutation() == LevelNode.Mutation.BLINDING) {
            label.setForeground(Color.RED);
        } else {
            label.setForeground(Color.BLACK);
        }
    }

    // Privates

    private int getCurrentLevelNodeValue() {
        int currentLevelNodeIndex = this.getGame().getCurrentLevel().getValue() - 1;
        int value = this.getGame().getLevel()
                .get(currentLevelNodeIndex)
                .getValue();

        return value;
    }

    private LevelNode.Mutation getCurrentLevelNodeMutation() {
        int currentLevelNodeIndex = this.getGame().getCurrentLevel().getValue() - 1;
        return this.getGame().getLevel()
                .get(currentLevelNodeIndex)
                .getMutation();
    }

    private MatchPlayer_Client getMatchPlayerClient(MatchPlayer matchPlayer) {
        return (MatchPlayer_Client) this.game.getMatchPlayers().stream()
                .filter(mP -> mP.getPlayer().getUsername().equals(
                        matchPlayer.getPlayer().getUsername()
                ))
                .collect(Collectors.toList())
                .get(0);
    }

    // Properties

    public Game_Client getGame() {
        return game;
    }

    // Inner Classes

    public class GameBUS_ViewBinder extends ViewBinder {
        public JPanel gamePane;
        public JLabel lblFindThis;
        public JLabel lblTimer;
        public JList listPlayers;

        public void update() {
            if (game != null) {
                if (lblFindThis != null) {
                    ui_initLblFindThis(lblFindThis);
                }
                if (lblTimer != null) {
                    lblTimer.setText(ui_getTimerClock());
                }
                if (listPlayers != null) {
                    ui_initPlayerList(listPlayers);
                }
            }
        }
    }

}
