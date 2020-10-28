package dto;

import GUI.Components.LevelNodeButton;

public class LevelNode_Client extends LevelNode {
    LevelNodeButton button;

    public LevelNode_Client(LevelNode levelNode) {
        super(levelNode);
    }

    public LevelNodeButton getButton() { return button; }
    public void setButton(LevelNodeButton button) { this.button = button; }
}
