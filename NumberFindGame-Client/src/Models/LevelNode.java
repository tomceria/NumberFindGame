package Models;

import GUI.Components.LevelNodeButton;

import java.awt.*;
import java.awt.geom.Point2D;

public class LevelNode {
    int value;
    Point2D.Double coord;
    LevelNodeButton button;

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

    public LevelNodeButton getButton() { return button; }
    public void setButton(LevelNodeButton button) { this.button = button; }
}
