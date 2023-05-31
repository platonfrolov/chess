package model.pieces;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import model.Board;
import model.Move;

public class Queen extends ChessPiece{
    public Queen(Point2D initialPosition, Color color) {
        super(initialPosition, color);
        this.pieceName = "queen";
        this.color = color;
        if (color == Color.BLACK) {
            this.pieceImage = new Image("file:src/main/resources/images/black/queen.png");
        } else {
            this.pieceImage = new Image("file:src/main/resources/images/white/queen.png");
        }

    }

    public boolean canMakeMove(Move move, Board board) {
        if (this.moveIsInBounds(move) && Bishop.isValidMove(move) && !Bishop.moveBlockedByOtherPiece(move, board)) {
            return true;
        } else if (Rook.isValidMove(move) && !Rook.moveBlockedByOtherPiece(move, board)) {
            return true;
        }
        return false;
    }

}
