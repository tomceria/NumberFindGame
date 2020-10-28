package bus;

import Common.ViewBinder;
import GUI.Components.MatchPlayerCellRenderer;
import Socket.Request.SocketRequest_SubmitLevelNode;
import dto.*;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;

public class GameBUS {
    private Game_Client game; // PARENT
    public GameBUS_ViewBinder viewBinder = new GameBUS_ViewBinder();

    public GameBUS(Game_Client game) {
        this.game = game;
    }

    // Functions

    public void initGame() {
        // TODO: Kiểm tra nếu các property rỗng (chưa dc gán dữ liệu từ Server) thì throw exception

        int clientPlayerId = this.game.getClientPlayer().getPlayer().getId();
        MatchPlayer clientPlayer = null;

        /**
         * Ép danh sách level từ ArrayList<LevelNode> thành ArrayList<LevelNode_Client>
         * Vì game.level chỉ nhận ArrayList<LevelNode>, ta phải tạo thêm 1 mảng và add các ArrayList<LevelNode_Client> đó vào
         */
        ArrayList<LevelNode> generatedLevel_OG = game.getLevel();
        ArrayList<LevelNode_Client> generatedLevel_Client = new ArrayList<LevelNode_Client>();
        ArrayList<LevelNode> generatedLevel = new ArrayList<LevelNode>();
        for (LevelNode levelNode : generatedLevel_OG) {
            generatedLevel_Client.add(new LevelNode_Client(levelNode));
        }
        for (LevelNode levelNode : generatedLevel_Client) {                           // TODO: Common.ArrayListUtil.Cast
            generatedLevel.add(levelNode);
        }
        game.setLevel(generatedLevel);

        // Get Room's players info
        ArrayList<MatchPlayer> matchPlayers = new ArrayList<MatchPlayer>();     // Also used for placings, by sort order
        for (MatchPlayer matchPlayer_ : this.game.getMatchPlayers()) {                                // TODO: Get room's player from Server
            MatchPlayer_Client matchPlayer = new MatchPlayer_Client(matchPlayer_.getPlayer());
            matchPlayers.add(matchPlayer);
//            if (clientPlayerId == matchPlayer.getPlayer().getId()) {
//                clientPlayer = matchPlayer;
//            }
        }
        game.setMatchPlayers(matchPlayers);
//        if (clientPlayer == null) {
//            throw new RuntimeException("PlayerDTO ID mismatch");
//        }
//        game.setClientPlayer(clientPlayer);


        this.viewBinder.startUpdatePeriod();
    }

    public void action_ClientChooseLevelNode(LevelNode levelNode) {
        /**
         * Client-side Validation
         */
        if (levelNode.getValue() != game.getCurrentLevelNodeValue()) {
            System.out.println("Wrong number.");
            return;
        }
        if (game.getLevel().indexOf(levelNode) < 0) {
            throw new IllegalArgumentException("Selected LevelNode does not belong to this Game's context");
        }

        /**
         * Kiểm tra hoàn tất => Gửi đến Server để xác nhận
         */
        LevelNode levelNodeSerializable = new LevelNode(levelNode);
        this.game.getClient().sendRequest(
            new SocketRequest_SubmitLevelNode(levelNodeSerializable, game.getClientPlayer())
        );

        // TODO: Receive new Data from Server
//        this.listen_GameUpdated();
    }

    public void listen_GameUpdated() {
        // Update colors of LevelNodeButtons
        for (LevelNode levelNode : game.getLevel()) {
            MatchPlayer pickingMatchPlayer = levelNode.getPickingMatchPlayer();
            if (pickingMatchPlayer != null) {
                ((LevelNode_Client) levelNode).getButton().setPicked(pickingMatchPlayer);
            }
        }

        this.viewBinder.update();
    }

    public String ui_getTimerClock() {
        int timeInMillis = game.getMatchConfig().getTime();
        LocalTime timeEnd = LocalTime.from(game.getStartTime()).plus(timeInMillis, ChronoUnit.MILLIS);
        LocalTime timeDiff = timeEnd.minusNanos(LocalTime.now().toNanoOfDay());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");

        return timeDiff.format(dtf);
    }

    public void ui_initPlayerList(JList list) {
        DefaultListModel<MatchPlayer> listModel = new DefaultListModel<MatchPlayer>();
        for (MatchPlayer matchPlayer : game.getMatchPlayers()) {
                listModel.addElement(matchPlayer);
        }
            list.setModel(listModel);
            list.setCellRenderer(new MatchPlayerCellRenderer());
    }

    public class GameBUS_ViewBinder extends ViewBinder {
        public JLabel lblFindThis;
        public JLabel lblTimer;
        public JList listPlayers;

        @Override
        public void startUpdatePeriod() {
            super.startUpdatePeriod();

        }

        public void update() {
            if (game != null) {
                if (lblFindThis != null) {
                    lblFindThis.setText(game.getCurrentLevelNodeValue() + "");
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

    // Properties

    public Game_Client getGame() {
        return game;
    }

}
