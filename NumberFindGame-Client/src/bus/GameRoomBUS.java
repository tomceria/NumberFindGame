package bus;

import Common.ViewBinder;
import Socket.GameClient;
import Socket.Response.SocketResponse_GameRoomProps;
import Socket.Response.SocketResponse_GameInit;
import Socket.Response.SocketResponse_GameRoomPlayerJoin;
import dto.*;

import javax.swing.*;

public class GameRoomBUS {
    GameRoom gameRoom; // PARENT
    public GameRoomBUS_ViewBinder viewBinder;

    public GameRoomBUS(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
        this.viewBinder = new GameRoomBUS_ViewBinder();
    }

    // Functions

    public void listen_setGameRoomProps(SocketResponse_GameRoomProps response) {
        ((GameRoom_Client) this.gameRoom).setPlayers(response.players);
        this.gameRoom.setMatchConfig(response.matchConfig);
        this.gameRoom.setStatus(response.status);

        /**
         * Force lệnh update của ViewBinder
         */
        this.viewBinder.update();
    }

    public void listen_startGame(SocketResponse_GameInit response) {
        MatchPlayer clientPlayer = ((GameRoom_Client) gameRoom).getClientPlayer();

        Game_Client game = new Game_Client(response.game, this.getGameRoom().getClient(), clientPlayer);
        this.gameRoom.setGame(game);
        game.getGameBUS().initGame();

        ViewBUS.gotoGameView(game.getGameBUS());
    }

    public void ui_loadPlayerCount(JLabel lblPlayerCount) {
        lblPlayerCount.setText(String.format(
                "%s / %s",
                this.getGameRoom().getPlayers().size(),
                this.getGameRoom().getMatchConfig().getMaxPlayer()
        ));
    }

    // Privates

    private Game_Client getGame() {
        return (Game_Client) this.gameRoom.getGame();
    }

    private GameRoom_Client getGameRoom() {
        return ((GameRoom_Client) this.gameRoom);
    }

    // Inner Classes

    public class GameRoomBUS_ViewBinder extends ViewBinder {
        public JLabel lblPlayerCount;

        public GameRoomBUS_ViewBinder() {
            super();
            this.update();
            this.startUpdatePeriod();
        }

        @Override
        public void update() {
            if (gameRoom != null) {
                if (lblPlayerCount != null) {
                    ui_loadPlayerCount(lblPlayerCount);
                }
            }
        }
    }
}
