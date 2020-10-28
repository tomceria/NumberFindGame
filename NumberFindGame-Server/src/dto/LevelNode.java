package dto;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class LevelNode implements Serializable {
    int value;
    Point2D.Double coord;
    MatchPlayer pickingMatchPlayer;

    public LevelNode() {

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
}
