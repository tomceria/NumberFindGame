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
     * Danh sách nhập vào phải chứa MỌI phần tử là LevelNode_Client (được polymorph về LevelNode)
     * @param levelNodes
     * @return
     */
    public static ArrayList<LevelNode_Client> castToLevelNodeClients(ArrayList<LevelNode> levelNodes) {
        ArrayList<LevelNode_Client> levelNodeClients = new ArrayList<LevelNode_Client>();
        for (LevelNode lN : levelNodes) {
            levelNodeClients.add((LevelNode_Client) lN);
        }

        return levelNodeClients;
    }

    public static ArrayList<LevelNode> convertLevelNodesToLevelNodeClients(ArrayList<LevelNode> _level) {
        ArrayList<LevelNode> level = new ArrayList<LevelNode>();

        for (LevelNode levelNode : _level) {
            LevelNode_Client levelNodeClient = new LevelNode_Client(levelNode);
            level.add(levelNodeClient);
        }

        return level;
    }

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
