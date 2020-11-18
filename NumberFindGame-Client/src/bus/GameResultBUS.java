package bus;

import Common.ViewBinder;
import GUI.Components.GameResultMatchPlayerCellRenderer;
import Run.GameMain;
import Socket.Client;
import Socket.GameClient;
import Socket.Response.SocketResponse_GameResult;
import dto.*;

import javax.swing.*;
import java.util.ArrayList;

public class GameResultBUS {
    public ArrayList<MatchPlayer> matchPlayers;
    public PlayerDTO winner;
    public boolean clientPlayerIsWinner;

    public GameResultBUS_ViewBinder viewBinder;

    public GameResultBUS() {
        this.viewBinder = new GameResultBUS_ViewBinder();
    }

    // Functions

    public void listen_showResult(SocketResponse_GameResult response) {
        /**
         * Lấy danh sách người chơi của Game instance
         * Đảm bảo rằng Game lúc này chưa bị huỷ
         */
        ArrayList<MatchPlayer_Client> matchPlayers_OG = MatchPlayer_Client.castToMatchPlayerClients(
                ((GameClient) GameMain.client).getGameRoom().getGame().getMatchPlayers()
        );
        /**
         * Gộp với Kết quả trận đấu (thông tin người chơi) cuối cùng
         */
        ArrayList<MatchPlayer> matchPlayers = MatchPlayer_Client
                .mergeMatchPlayersAndMatchPlayerClients(response.matchPlayers, matchPlayers_OG);
        this.winner = response.winner;
        this.matchPlayers = matchPlayers;
        this.clientPlayerIsWinner = response.clientPlayerIsWinner;

        this.viewBinder.update();
    }

    public void ui_initPlayerList(JList list) {
        DefaultListModel<MatchPlayer> listModel = new DefaultListModel<MatchPlayer>();
        for (MatchPlayer matchPlayer : this.matchPlayers) {
            listModel.addElement(matchPlayer);
        }
        list.setModel(listModel);
        list.setCellRenderer(new GameResultMatchPlayerCellRenderer());
    }

    // Inner Classes

    public class GameResultBUS_ViewBinder extends ViewBinder {
        public JLabel lblWinner;
        public JList listPlayers;

        public GameResultBUS_ViewBinder() {
            super();
        }

        @Override
        public void update() {
            if (listPlayers != null) {
                ui_initPlayerList(listPlayers);
            }
        }
    }
}
