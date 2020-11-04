package bus;

import Common.ViewBinder;
import GUI.Components.MatchPlayerCellRenderer;
import Socket.Request.SocketRequest_SubmitLevelNode;
import Socket.Response.SocketResponse_GameProps;
import dto.*;

import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
        ArrayList<LevelNode> generatedLevel = game.getLevel();
        game.setLevel(
                LevelNode_Client.convertLevelNodesToLevelNodeClients(generatedLevel)
        );

        // Get Room's players info
        ArrayList<MatchPlayer> matchPlayers = this.game.getMatchPlayers();      // Also used for placings, by sort order
        matchPlayers = MatchPlayer_Client
                .convertMatchPlayersToMatchPlayerClients(matchPlayers);
        game.setMatchPlayers(matchPlayers);

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
            new SocketRequest_SubmitLevelNode(levelNodeSerializable, game.getClientPlayer())
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
        for (LevelNode levelNode : game.getLevel()) {
            MatchPlayer pickingMatchPlayer = levelNode.getPickingMatchPlayer();
            if (pickingMatchPlayer != null) {
                ((LevelNode_Client) levelNode).getButton().setPicked(pickingMatchPlayer);
            }
        }

        /**
         * CUỐI CÙNG: Force lệnh update của ViewBinder để cập nhật ngay lập tức
         */
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

    // Privates

    // Properties

    public Game_Client getGame() {
        return game;
    }

    // Inner Classes

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

}
