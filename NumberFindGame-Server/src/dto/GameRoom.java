package dto;

import Socket.ClientHandler;

import java.util.HashMap;
import java.util.UUID;

public class GameRoom {
    private HashMap<UUID, ClientHandler> playerClients = new HashMap<UUID, ClientHandler>();

    public HashMap<UUID, ClientHandler> getPlayerClients() {
        return playerClients;
    }

    public void joinRoom(ClientHandler playerClient) {
        playerClients.put(playerClient.getId(), playerClient);  // Copy ClientHandler từ ClientManager.clientConnections sang GameRoom.playerClients
        MatchPlayer matchPlayer = (MatchPlayer) playerClient.getClientIdentifier();
        System.out.println(String.format("%s joined the room.", matchPlayer.getPlayer().getUsername()));
    }
}
