package bus;

import Socket.ClientHandler;
import Socket.Request.SocketRequest_AccessLogin;
import Socket.Request.SocketRequest_AccessRegister;
import Socket.Response.SocketResponse;
import dto.MatchPlayer_Server;
import dto.PlayerDTO;

public class IdentityBUS {
    ClientHandler clientHandler;
    PlayerBUS playerBUS;

    public IdentityBUS(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.playerBUS = new PlayerBUS();
    }

    public boolean performLogin(SocketRequest_AccessLogin request) {
        PlayerDTO player = null;

        if (playerBUS.login(request.username, request.password)) {
            player = playerBUS.getOneByUsername(request.username);
        }

        if (player != null) {   // Validate thành công => có player
            this.clientHandler.setClientIdentifier(new MatchPlayer_Server(player));
            this.clientHandler.setLoggedIn(true);
            this.clientHandler.sendResponse(new SocketResponse(
                    SocketResponse.Status.SUCCESS,
                    SocketResponse.Action.MSG,
                    "Logged in.")
            );
        } else {
            this.clientHandler.sendResponse(new SocketResponse(
                    SocketResponse.Status.FAILED,
                    SocketResponse.Action.MSG,
                    "Invalid username or password.")
            );
        }

        return player != null;
    }

    public boolean performRegister(SocketRequest_AccessRegister request) {
        PlayerDTO player = new PlayerDTO(
                request.username,
                request.password,
                request.email,
                request.firstName,
                request.lastName
        );

        boolean result = playerBUS.register(player);

        return result;
    }
}
