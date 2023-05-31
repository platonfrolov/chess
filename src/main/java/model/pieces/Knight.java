package model.pieces;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import model.Board;
import model.Move;

public class Knight extends ChessPiece{
    public Knight(Point2D initialPosition, Color color) {
        super(initialPosition, color);
        this.pieceName = "knight";
        this.color = color;
        if (color == Color.BLACK) {
            this.pieceImage = new Image("file:/home/plfro/programming/chess/src/main/resources/images/black/knight.png");
        } else {
            this.pieceImage = new Image("file:/home/plfro/programming/chess/src/main/resources/images/white/knight.png");
        }

    }

    public boolean canMakeMove(Move move, Board board) {
        if (this.moveIsInBounds(move) && Knight.isValidMove(move) && !Knight.moveBlockedByOtherPiece(move, board)) {
            return true;
        }
        return false;
    }

    public static boolean moveBlockedByOtherPiece(Move move, Board board) {
        if (board.cellIsTaken(move.getTo()) && board.getPieceInCell(move.getTo()).color == board.getPieceInCell(move.getFrom()).color) {
            return true;
        }
        return false;
    }

    public static boolean isValidMove(Move move) {
        Point2D diff = move.getTo().subtract(move.getFrom());
        if (Math.abs(diff.getX()) == 1 && Math.abs(diff.getY()) == 2 || Math.abs(diff.getX()) == 2 && Math.abs(diff.getY()) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
