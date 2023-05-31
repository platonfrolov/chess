package model.pieces;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import model.Board;
import model.Move;

public class King extends ChessPiece{
    public King(Point2D initialPosition, Color color) {
        super(initialPosition, color);
        this.pieceName = "king";
        this.color = color;
        if (color == Color.BLACK) {
            this.pieceImage = new Image("file:src/main/resources/images/black/king.png");
        } else {
            this.pieceImage = new Image("file:src/main/resources/images/white/king.png");
        }
    }

    public boolean canMakeMove(Move move, Board board) {
        if (this.moveIsInBounds(move) && King.isValidMove(move) && !King.moveBlockedByOtherPiece(move, board)) {
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
        if (move.getFrom().equals(move.getTo())) {
            return false;
        }
        Point2D diff = move.getTo().subtract(move.getFrom());

        if ((Math.abs(diff.getX()) <= 1 && Math.abs(diff.getY()) <= 1) || (Math.abs(diff.getX()) != 1 && Math.abs(diff.getY()) == 1)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidCastle(Move move, Board board){
        if (this.moved || board.getPiecesCausingCheck(board.getPieceInCell(move.getFrom()).color.equals(Color.BLACK) ? Color.WHITE : Color.BLACK).size() > 0) {
            return false;
        }
        if (!board.cellIsTaken(move.getTo()) && move.getTo().equals(new Point2D(6,7))) {
            if (board.cellIsTaken(new Point2D(7,7)) &&
                    board.getPieceInCell(new Point2D(7,7)) instanceof Rook &&
                    !((Rook)board.getPieceInCell(new Point2D(7,7))).moved &&
                    !board.cellIsTaken(new Point2D(6,7)) &&
                    !board.cellIsTaken(new Point2D(5,7))) {
                return true;
            }
        }
        if (!board.cellIsTaken(move.getTo()) && move.getTo().equals(new Point2D(2,7))) {
            if (board.cellIsTaken(new Point2D(0,7)) &&
                    board.getPieceInCell(new Point2D(0,7)) instanceof Rook &&
                    !((Rook)board.getPieceInCell(new Point2D(0,7))).moved &&
                    !board.cellIsTaken(new Point2D(1,7)) &&
                    !board.cellIsTaken(new Point2D(2,7)) &&
                    !board.cellIsTaken(new Point2D(3,7))) {
                return true;
            }
        }
        if (!board.cellIsTaken(move.getTo()) && move.getTo().equals(new Point2D(6,0))) {
            if (board.cellIsTaken(new Point2D(7,0)) &&
                    board.getPieceInCell(new Point2D(7,0)) instanceof Rook &&
                    !((Rook)board.getPieceInCell(new Point2D(7,0))).moved &&
                    !board.cellIsTaken(new Point2D(6,0)) &&
                    !board.cellIsTaken(new Point2D(5,0))) {
                return true;
            }
        }
        if (!board.cellIsTaken(move.getTo()) && move.getTo().equals(new Point2D(2,0))) {
            if (board.cellIsTaken(new Point2D(0,0)) &&
                    board.getPieceInCell(new Point2D(0,0)) instanceof Rook &&
                    !((Rook)board.getPieceInCell(new Point2D(0,0))).moved &&
                    !board.cellIsTaken(new Point2D(1,0)) &&
                    !board.cellIsTaken(new Point2D(2,0)) &&
                    !board.cellIsTaken(new Point2D(3,0))) {
                return true;
            }
        }
        return false;
    }
}
