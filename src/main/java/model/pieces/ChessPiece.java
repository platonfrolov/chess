package model.pieces;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import model.Board;
import model.Move;

public abstract class ChessPiece implements Cloneable{
    public boolean taken;
    public Point2D position;
    public Color color;
    public String pieceName;
    public Image pieceImage;
    public boolean moved;
    public ChessPiece(Point2D initialPosition, Color color) {
        this.moved = false;
        this.taken = false;
        this.position = initialPosition;
        this.color = color;
    }

    public boolean move(Move move, Board board) {
        if (this.canMakeMove(move, board) && !board.moveCausesCheck(move)) {
            this.position = move.getTo();
            this.moved = true;
            return true;
        }
        return false;
    }

    public void setPosition(Point2D position) {
        this.position = position;
        this.moved = true;
    }

    public abstract boolean canMakeMove(Move move, Board board);

    public boolean moveIsInBounds(Move move) {
        Point2D to = move.getTo();
        return to.getX() >= 0 && to.getX() < 8 && to.getY() >= 0 && to.getY() < 8;
    }

    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException exception) {
            System.out.println("kut");
        }
        return null;
    }
}
