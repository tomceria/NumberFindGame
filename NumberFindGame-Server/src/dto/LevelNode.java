package dto;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

public class LevelNode implements Serializable {
    public enum Mutations {
        LUCKY
    }

    int value;
    Point2D.Double coord;
    MatchPlayer pickingMatchPlayer;
    ArrayList<Mutations> mutations;

    public LevelNode() {
        mutations = new ArrayList<>();
    }

    public LevelNode(LevelNode levelNode) {
        this.value = levelNode.value;
        this.coord = levelNode.coord;
        this.pickingMatchPlayer = levelNode.pickingMatchPlayer;
    }

    public LevelNode(LevelNode levelNode, boolean willClearServerRefs) {
        this.value = levelNode.value;
        this.coord = levelNode.coord;
        if (levelNode.pickingMatchPlayer != null) {
            if (willClearServerRefs == true) {
                this.pickingMatchPlayer = new MatchPlayer(levelNode.pickingMatchPlayer);
            } else {
                this.pickingMatchPlayer = levelNode.pickingMatchPlayer;
            }
        }
    }

    public LevelNode(int value, Point2D.Double coord) {
        this.value = value;
        this.coord = coord;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public Point2D.Double getCoord() {
        return coord;
    }
    public void setCoord(Point2D.Double coord) {
        this.coord = coord;
    }

    public MatchPlayer getPickingMatchPlayer() {
        return pickingMatchPlayer;
    }
    public void setPickingMatchPlayer(MatchPlayer pickingMatchPlayer) {
        this.pickingMatchPlayer = pickingMatchPlayer;
    }

    public void addMutation(Mutations mutation) {
        this.mutations.add(mutation);
    }
}
