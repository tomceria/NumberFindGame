package bus;

import Common.OrdinalNumber;
import Common.ViewBinder;
import GUI.Components.GameResultMatchPlayerCellRenderer;
import Run.GameMain;
import Socket.GameClient;
import Socket.Response.SocketResponse_GameResult;
import dto.MatchPlayer;
import dto.MatchPlayer_Client;
import dto.PlayerDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameResultBUS {
    public ArrayList<MatchPlayer> matchPlayers;
    public PlayerDTO clientPlayer;
    public PlayerDTO winner;
    public boolean clientPlayerIsWinner;

    public GameResultBUS_ViewBinder viewBinder;

    public GameResultBUS() {
        this.viewBinder = new GameResultBUS_ViewBinder();
    }

    // Functions

    public void action_ReturnToGameRoom() {
        ViewBUS.gotoGameRoomView();
        ViewBUS.gameResultView = null;
    }

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
        this.clientPlayer = response.clientPlayer;
        this.winner = response.winner;
        this.matchPlayers = matchPlayers;
        this.clientPlayerIsWinner = response.clientPlayerIsWinner;

        this.viewBinder.update();
    }

    public void ui_setLblWinner(JLabel label) {
        String result;
        if (this.clientPlayerIsWinner) {
            result = "Victory!";
        } else {
            int clientPlayerPlacing = this.matchPlayers
                    .stream().filter(mP -> mP.getPlayer().equals(this.clientPlayer))
                    .collect(Collectors.toList())
                    .get(0)
                    .getPlacing();
            result = String.format("You are %s place", OrdinalNumber.generate(clientPlayerPlacing));
        }

        label.setText(result);
    }

    public void ui_initPlayerList(JList list) {
        ArrayList<MatchPlayer> matchPlayers = new ArrayList<MatchPlayer>(this.matchPlayers);
        MatchPlayer_Client.orderMatchPlayersByPlacing(matchPlayers);

        DefaultListModel<MatchPlayer> listModel = new DefaultListModel<MatchPlayer>();
        for (MatchPlayer matchPlayer : matchPlayers) {
            listModel.addElement(matchPlayer);
        }
        list.setModel(listModel);
        list.setCellRenderer(new GameResultMatchPlayerCellRenderer());

        list.setPreferredSize(new Dimension(300, 50 * this.matchPlayers.size()));
    }

    public boolean isLoaded() {
        return this.matchPlayers != null &&
                this.clientPlayer != null;
    }

    // Inner Classes

    public class GameResultBUS_ViewBinder extends ViewBinder {
        public JLabel lblWinner;
        public JList listPlayers;

        public GameResultBUS_ViewBinder() {
            super();

            /**
             * Thực hiện vòng lặp update() (với startUpdatePeriod()) trong lúc đợi các Component được render
             * Khi render hoàn tất, các Component được gán dữ liệu của mình,
             * Kết thúc vòng lặp với stopUpdatePeriod()
             */
            this.update();
            this.startUpdatePeriod();
        }

        @Override
        public void update() {
            /**
             * ViewBinder này áp dụng cơ chế Update-once-initiated
             */

            if (!GameResultBUS.this.isLoaded()) {
                return;
            }
            if (lblWinner != null && listPlayers != null) {
                ui_setLblWinner(lblWinner);
                ui_initPlayerList(listPlayers);
                this.stopUpdatePeriod();
            }
        }
    }
}
