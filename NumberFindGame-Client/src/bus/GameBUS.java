package bus;

import Common.ViewBinder;
import GUI.Components.MatchPlayerCellRenderer;
import Socket.Request.SocketRequest_SubmitLevelNode;
import Socket.Response.SocketResponse_GameProps;
import dto.*;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
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
//        ArrayList<LevelNode> generatedLevel_OG = game.getLevel();
//        ArrayList<LevelNode_Client> generatedLevel_Client = new ArrayList<LevelNode_Client>();
//        ArrayList<LevelNode> generatedLevel = new ArrayList<LevelNode>();
//        for (LevelNode levelNode : generatedLevel_OG) {
//            generatedLevel_Client.add(new LevelNode_Client(levelNode));
//        }
//        for (LevelNode levelNode : generatedLevel_Client) {                           // TODO: Common.ArrayListUtil.Cast
//            generatedLevel.add(levelNode);
//        }
        ArrayList<LevelNode> generatedLevel = game.getLevel();
        game.setLevel(convertLevelNodesToLevelNodeClients(generatedLevel));

        // Get Room's players info
        ArrayList<MatchPlayer> matchPlayers = this.game.getMatchPlayers();      // Also used for placings, by sort order
        matchPlayers = convertMatchPlayersToMatchPlayerClients(matchPlayers);
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

    public void listen_GameUpdated(SocketResponse_GameProps response) {
        // Gán các value mới cho Game; TODO: OPTIMIZEEEEEE
        game.setCurrentLevel(response.currentLevel);

        ArrayList<MatchPlayer_Client> matchPlayers_OG = MatchPlayer_Client
                .getMatchPlayerClients(game.getMatchPlayers());
        ArrayList<MatchPlayer> matchPlayers = convertMatchPlayersToMatchPlayerClients(response.players);
        for (int i = 0; i < matchPlayers.size(); i++) {
            MatchPlayer matchPlayer = matchPlayers.get(i);
            MatchPlayer matchPlayer_OG = matchPlayers_OG.get(i);
            ((MatchPlayer_Client) matchPlayer).setUiColor(
                    ((MatchPlayer_Client) matchPlayer_OG).getUiColor()
            );
        }
        game.setMatchPlayers(matchPlayers);

        ArrayList<LevelNode_Client> level_OG = LevelNode_Client
                .getLevelNodeClients(game.getLevel());
        ArrayList<LevelNode> level = convertLevelNodesToLevelNodeClients(response.level);
        for (int i = 0; i < level.size(); i++) {
            LevelNode levelNode = level.get(i);
            LevelNode levelNode_OG = level_OG.get(i);
            ((LevelNode_Client) levelNode).setButton(
                    ((LevelNode_Client) levelNode_OG).getButton()
            );
            if (levelNode.getPickingMatchPlayer() != null) {
                levelNode.setPickingMatchPlayer(
                        matchPlayers.stream()
                                .filter(mP -> mP.getPlayer()
                                        .equals(levelNode.getPickingMatchPlayer().getPlayer())
                                )
                                .collect(Collectors.toList())
                                .get(0)
                );
            }
        }
        game.setLevel(level);

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

    // Privates

    private ArrayList<MatchPlayer> convertMatchPlayersToMatchPlayerClients(ArrayList<MatchPlayer> matchPlayers) {
        ArrayList<MatchPlayer> newMatchPlayers = new ArrayList<MatchPlayer>();

        for (MatchPlayer matchPlayer : matchPlayers) {
            MatchPlayer_Client matchPlayerClient = new MatchPlayer_Client(matchPlayer.getPlayer());
            newMatchPlayers.add(matchPlayerClient);
        }

        return newMatchPlayers;
    }

    private ArrayList<LevelNode> convertLevelNodesToLevelNodeClients(ArrayList<LevelNode> levelNodes) {
        ArrayList<LevelNode> newLevelNodes = new ArrayList<LevelNode>();

        for (LevelNode levelNode : levelNodes) {
            LevelNode_Client levelNodeClient = new LevelNode_Client(levelNode);
            newLevelNodes.add(levelNodeClient);
        }

        return newLevelNodes;
    }

    // Properties

    public Game_Client getGame() {
        return game;
    }

}
