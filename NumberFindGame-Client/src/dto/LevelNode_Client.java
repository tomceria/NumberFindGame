package dto;

import GUI.Components.LevelNodeButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LevelNode_Client extends LevelNode {
    LevelNodeButton button;

    public LevelNode_Client(LevelNode levelNode) {
        super(levelNode);
    }

    // Static Functions

    /**
     * Cast danh sách LevelNode[] chứa các LevelNode_Client về LevelNode_Client[]
     * @param levelNodes PHẢI ĐẢM BẢO là LevelNode_Client
     * @return danh sách LevelNode_Client[]
     */
    public static ArrayList<LevelNode_Client> castToLevelNodeClients(ArrayList<LevelNode> levelNodes) {
        ArrayList<LevelNode_Client> levelNodeClients = new ArrayList<LevelNode_Client>();
        for (LevelNode lN : levelNodes) {
            levelNodeClients.add((LevelNode_Client) lN);
        }

        return levelNodeClients;
    }

    /**
     * Khác với cast, ép về LevelNode[chứa LevelNode_Client] để có thể gán setLevelNodes()
     * Dùng để biến đổi danh sách LevelNode[] nhận được từ Server để có thể thực hiện các thao tác Client
     * @param _level Danh sách LevelNode[] lấy từ Server
     * @return Danh sách LevelNode[] chứa các LevelNode_Client
     */
    public static ArrayList<LevelNode> convertLevelNodesToLevelNodeClients(ArrayList<LevelNode> _level) {
        ArrayList<LevelNode> level = new ArrayList<LevelNode>();

        for (LevelNode levelNode : _level) {
            LevelNode_Client levelNodeClient = new LevelNode_Client(levelNode);
            level.add(levelNodeClient);
        }

        return level;
    }

    /**
     * Gộp dữ liệu LevelNode[] mới và dữ liệu LevelNode_Client[] hiện tại
     * @param _level LevelNode[] lấy từ Server
     * @param level_OG LevelNode_Client[] lúc bấy giờ của Client (có các dữ liệu về UI)
     * @param matchPlayers Do có levelNode.pickingMatchPlayer, các MatchPlayer đó cũng cần có dữ liệu từ danh sách MatchPlayer[] đã gộp dữ liệu
     * @return LevelNode[] đã gộp dữ liệu
     */
    public static ArrayList<LevelNode> mergeLevelNodesAndLevelNodeClients(ArrayList<LevelNode> _level, ArrayList<LevelNode_Client> level_OG, ArrayList<MatchPlayer> matchPlayers) {
        ArrayList<LevelNode> level = convertLevelNodesToLevelNodeClients(_level);
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

        return level;
    }

    // Properties

    public LevelNodeButton getButton() { return button; }
    public void setButton(LevelNodeButton button) { this.button = button; }
}
