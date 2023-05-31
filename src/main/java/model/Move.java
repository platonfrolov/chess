package model;

import javafx.geometry.Point2D;

public class Move {
    private Point2D from;
    private Point2D to;
    private int moveNumber;
    public Move(Point2D from, Point2D to, int moveNumber) {
        this.from = from;
        this.to = to;
        this.moveNumber = moveNumber;
    }

    public Point2D getTo() {
        return this.to;
    }

    public Point2D getFrom() {
        return this.from;
    }

    public int getMoveNumber() {
        return this.moveNumber;
    }
}
