package dto;

import GUI.Components.LevelNodeButton;

import java.util.ArrayList;

public class LevelNode_Client extends LevelNode {
    LevelNodeButton button;

    public LevelNode_Client(LevelNode levelNode) {
        super(levelNode);
    }

    public LevelNodeButton getButton() { return button; }
    public void setButton(LevelNodeButton button) { this.button = button; }

    /**
     * Danh sách nhập vào phải chứa MỌI phần tử là LevelNode_Client (được polymorph về LevelNode)
     * @param levelNodes
     * @return
     */
    public static ArrayList<LevelNode_Client> getLevelNodeClients(ArrayList<LevelNode> levelNodes) {
        ArrayList<LevelNode_Client> levelNodeClients = new ArrayList<LevelNode_Client>();
        for (LevelNode lN : levelNodes) {
            levelNodeClients.add((LevelNode_Client) lN);
        }

        return levelNodeClients;
    }
}
