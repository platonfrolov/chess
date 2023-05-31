package model.pieces;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import model.Board;
import model.Move;

public class Rook extends ChessPiece{
    public Rook(Point2D initialPosition, Color color) {
        super(initialPosition, color);
        this.pieceName = "rook";
        this.color = color;
        if (color == Color.BLACK) {
            this.pieceImage = new Image("file:/home/plfro/programming/chess/src/main/resources/images/black/rook.png");
        } else {
            this.pieceImage = new Image("file:/home/plfro/programming/chess/src/main/resources/images/white/rook.png");
        }
    }

    public boolean canMakeMove(Move move, Board board) {
        if (this.moveIsInBounds(move) && Rook.isValidMove(move) && !Rook.moveBlockedByOtherPiece(move, board)) {
            return true;
        }
        return false;
    }

    public static boolean moveBlockedByOtherPiece(Move move, Board board) {
        Point2D diff = move.getTo().subtract(move.getFrom());
        int xStep = (int) Math.signum(diff.getX());
        int yStep = (int) Math.signum(diff.getY());
        int nrSteps = (int) Math.max(Math.abs(diff.getX()), Math.abs(diff.getY()));
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
        Point2D diff = move.getTo().subtract(move.getFrom());
        if ((diff.getX() == 0 && diff.getY() != 0) || (diff.getX() != 0 && diff.getY() == 0)) {
            return true;
        } else {
            return false;
        }
    }
}