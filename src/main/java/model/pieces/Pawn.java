package model.pieces;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import model.Board;
import model.Move;

public class Pawn extends ChessPiece{
    public int moveIdDoubleStep = -1;
    public Pawn(Point2D initialPosition, Color color) {
        super(initialPosition, color);
        this.pieceName = "king";
        this.color = color;
        this.moved = false;
        if (color == Color.BLACK) {
            this.pieceImage = new Image("file:src/main/resources/images/black/pawn.png");
        } else {
            this.pieceImage = new Image("file:src/main/resources/images/white/pawn.png");
        }

    }

    public boolean move(Move move, Board board) {
        if (this.moveIsInBounds(move) && this.canMakeMove(move, board) && !board.moveCausesCheck(move)) {
            if (Math.abs(move.getTo().subtract(move.getFrom()).getY()) == 2) {
                this.moveIdDoubleStep = move.getMoveNumber();
            }
            this.position = move.getTo();
            this.moved = true;
            return true;
        }
        return false;
    }

    public boolean canMakeMove(Move move, Board board) {
        if ((this.isValidMove(move) && !this.moveBlockedByOtherPiece(move, board)) || this.isValidTake(move, board)) {
            return true;
        }
        return false;
    }

    public boolean moveBlockedByOtherPiece(Move move, Board board) {
        Point2D diff = move.getTo().subtract(move.getFrom());
        int yStep = (int) Math.signum(diff.getY());
        int nrSteps = (int) Math.abs(diff.getY());
        for (int i = 1; i <= nrSteps; i++) {
            Point2D pointToCheck = move.getFrom().add(new Point2D(0, yStep).multiply(i));
            if (board.cellIsTaken(pointToCheck)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidMove(Move move) {
        Point2D diff = move.getTo().subtract(move.getFrom());
        if (!this.moved && this.color == Color.BLACK && diff.getY() == 2 && diff.getX() == 0) {
            return true;
        } else if (this.color == Color.BLACK && diff.getY() == 1 && diff.getX() == 0){
            return true;
        }
        if (!this.moved && this.color == Color.WHITE && diff.getY() == -2 && diff.getX() == 0) {
            return true;
        } else if (this.color == Color.WHITE && diff.getY() == -1 && diff.getX() == 0){
            return true;
        }
        return false;
    }

    public boolean isValidTake(Move move, Board board) {
        Point2D diff = move.getTo().subtract(move.getFrom());
        if ((this.color == Color.BLACK && diff.getY() == 1 && Math.abs(diff.getX()) == 1) ||
                (this.color == Color.WHITE && diff.getY() == -1 && Math.abs(diff.getX()) == 1)) {
            if (board.cellIsTaken(move.getTo()) && board.getPieceInCell(move.getTo()).color != this.color) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidEnPassant(Move move, Board board) {
        // Create the case for en passant
        Point2D enPassantPoint = new Point2D(move.getTo().getX(), move.getFrom().getY());
        if (board.cellIsTaken(enPassantPoint) &&
                board.getPieceInCell(enPassantPoint).color != this.color &&
                board.getPieceInCell(enPassantPoint) instanceof Pawn &&
                ((Pawn) board.getPieceInCell(enPassantPoint)).moveIdDoubleStep == move.getMoveNumber() - 1) {
            return true;
        }
        return false;
    }
}
