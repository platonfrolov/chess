package model.pieces;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import model.Board;
import model.Move;

public class Bishop extends ChessPiece{
    public Bishop(Point2D initialPosition, Color color) {
        super(initialPosition, color);
        this.pieceName = "bishop";
        this.color = color;
        if (color == Color.BLACK) {
            this.pieceImage = new Image("file:/home/plfro/programming/chess/src/main/resources/images/black/bishop.png");
        } else {
            this.pieceImage = new Image("file:/home/plfro/programming/chess/src/main/resources/images/white/bishop.png");
        }
    }

    public boolean canMakeMove(Move move, Board board) {
        if (this.moveIsInBounds(move) && Bishop.isValidMove(move) && !Bishop.moveBlockedByOtherPiece(move, board)) {
            return true;
        }
        return false;
    }

    public static boolean moveBlockedByOtherPiece(Move move, Board board) {
        Point2D diff = move.getTo().subtract(move.getFrom());
        int xStep = (int) Math.signum(diff.getX());
        int yStep = (int) Math.signum(diff.getY());
        int nrSteps = (int) Math.abs(diff.getX());
        for (int i = 1; i < nrSteps; i++) {
            Point2D pointToCheck = move.getFrom().add(new Point2D(xStep, yStep).multiply(i));
            if (board.cellIsTaken(pointToCheck)) {
                return true;
            }
        }
        if (board.cellIsTaken(move.getTo()) && board.getPieceInCell(move.getTo()).color == board.getPieceInCell(move.getFrom()).color) {
            return true;
        }
        return false;
    }

    public static boolean isValidMove(Move move) {
        if (move.getFrom().equals(move.getTo())) {
            return false;
        } else {
            if (Math.abs(move.getTo().subtract(move.getFrom()).getX()) == Math.abs(move.getTo().subtract(move.getFrom()).getY())) {
                return true;
            } else {
                return false;
            }
        }
    }


}
