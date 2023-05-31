package model;

import javafx.geometry.Point2D;
import model.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board implements Cloneable{
    HashMap<Point2D, ChessPiece> cellToPieceMap;
    public Board() {
        this.cellToPieceMap = new HashMap<>();
    }
    public ChessPiece getPieceInCell(Point2D point) {
        if (this.cellIsTaken(point)) {
            return this.cellToPieceMap.get(point);
        } else {
            return null;
        }
    }

    public boolean cellIsTaken(Point2D point) {
        if (this.cellToPieceMap.get(point) == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addPieceToBoard(ChessPiece piece) {
        if (this.cellIsTaken(piece.position)) {
            return false;
        } else {
            this.cellToPieceMap.put(piece.position, piece);
            return true;
        }
    }

    public boolean addPiecesToBoard(List<ChessPiece> pieces) {
        boolean success = true;
        for (ChessPiece piece : pieces) {
            success = success & this.addPieceToBoard(piece);
        }
        return success;
    }

    public boolean move(Move move, Player currentPlayer) {
        ChessPiece currentPiece = this.getPieceInCell(move.getFrom());
        if (currentPiece != null && currentPiece.color == currentPlayer.color) {
            boolean success = currentPiece.move(move, this);
            if (success) {
                this.cellToPieceMap.remove(move.getFrom());
                this.cellToPieceMap.put(move.getTo(), currentPiece);
            }
            if (currentPiece instanceof King) {
                if (((King) currentPiece).isValidCastle(move, this)) {
                    this.performCastle(move);
                    success = true;
                }
            }
            if  (currentPiece instanceof Pawn) {
                if (((Pawn) currentPiece).isValidEnPassant(move, this)) {
                    this.performEnPassant(move);
                    success = true;
                }
            }
            return success;
        }
        return false;
    }

    public void performCastle(Move move) {
        // already checked validity castle so rook and king must be in place.
        King kingPiece = (King) this.getPieceInCell(move.getFrom());
        Rook rookPiece = null;
        Point2D newRookPosition = null;
        if (move.getTo().equals(new Point2D(2,0))) {
            rookPiece = (Rook) this.getPieceInCell(new Point2D(0,0));
            newRookPosition = new Point2D(3,0);
        } else if (move.getTo().equals(new Point2D(6,0))) {
            rookPiece = (Rook) this.getPieceInCell(new Point2D(7,0));
            newRookPosition = new Point2D(5,0);
        } else if (move.getTo().equals(new Point2D(2,7))) {
            rookPiece = (Rook) this.getPieceInCell(new Point2D(0,7));
            newRookPosition = new Point2D(3,7);
        } else if (move.getTo().equals(new Point2D(6,7))) {
            rookPiece = (Rook) this.getPieceInCell(new Point2D(7,7));
            newRookPosition = new Point2D(5,7);
        }
        this.cellToPieceMap.remove(move.getFrom());
        this.cellToPieceMap.put(move.getTo(), kingPiece);
        this.cellToPieceMap.remove(rookPiece.position);
        this.cellToPieceMap.put(newRookPosition, rookPiece);
        kingPiece.setPosition(move.getTo());
        rookPiece.setPosition(newRookPosition);
    }

    public void performEnPassant(Move move) {
        // already checked validity of en passant so the opponent pawn must be under the goal cell of the pawn.
        ChessPiece attackingPawn = this.getPieceInCell(move.getFrom());
        Point2D enPassantPoint = new Point2D(move.getTo().getX(), move.getFrom().getY());
        this.cellToPieceMap.remove(enPassantPoint);
        this.cellToPieceMap.remove(move.getFrom());
        this.cellToPieceMap.put(move.getTo(), attackingPawn);
        attackingPawn.setPosition(move.getTo());
    }



    public List<ChessPiece> getPiecesCausingCheck(Color color) {
        List<ChessPiece> piecesCausingCheck = new ArrayList<ChessPiece>();
        Point2D oppositeKingPosition = this.getKingPosition(color == Color.BLACK ? Color.WHITE : Color.BLACK);
        for (Point2D key : this.cellToPieceMap.keySet()) {
            ChessPiece piece = cellToPieceMap.get(key);
            Point2D piecePosition = piece.position;
            Move takeQueen = new Move(piecePosition, oppositeKingPosition, -1);
            if (piece.color == color && piece.canMakeMove(takeQueen, this)) {
                piecesCausingCheck.add(piece);
            }
        }
        return piecesCausingCheck;
    }

    public boolean isMate(Color color) {
        List<ChessPiece> piecesCausingCheck = this.getPiecesCausingCheck(color);
        Color oppositeColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        if (piecesCausingCheck.size() == 0) {
            return false;
        } else if (piecesCausingCheck.size() == 1) {
            Point2D pieceCausingCheckPosition = piecesCausingCheck.get(0).position;
            Point2D oppositeKingPosition = this.getKingPosition(oppositeColor);
            List<Point2D> inBetweenCells = this.getInBetweenCells(oppositeKingPosition, pieceCausingCheckPosition);
            for (Point2D key : this.cellToPieceMap.keySet()) {
                ChessPiece piece = cellToPieceMap.get(key);
                Point2D piecePosition = piece.position;
                Move takeAttackingPiece = new Move(piecePosition, pieceCausingCheckPosition, -1);
                if (piece.color == oppositeColor &&
                        piece.canMakeMove(takeAttackingPiece, this) &&
                        !this.moveCausesCheck(takeAttackingPiece)) {
                    return false;
                }
                for (Point2D blockPosition : inBetweenCells) {
                    Move blockAttackingPiece = new Move(piecePosition, blockPosition, -1);
                    if (piece.color == oppositeColor &&
                            piece.canMakeMove(blockAttackingPiece, this) &&
                            !this.moveCausesCheck(blockAttackingPiece)) {
                        return false;
                    }
                }
            }
        }
        Point2D[] kingMoveDirections = new Point2D[]{
                new Point2D(1,1),
                new Point2D(1,-1),
                new Point2D(-1,1),
                new Point2D(-1,-1),
                new Point2D(0,1),
                new Point2D(0,-1),
                new Point2D(1,0),
                new Point2D(-1,0)
        };
        Point2D oppositeKingPosition = this.getKingPosition(color == Color.BLACK ? Color.WHITE : Color.BLACK);
        King oppositeKing = (King) this.cellToPieceMap.get(oppositeKingPosition);
        for (Point2D moveDirection : kingMoveDirections) {
            Move escape = new Move(oppositeKingPosition, oppositeKingPosition.add(moveDirection), -1);
            if (oppositeKing.canMakeMove(escape, this) && !this.moveCausesCheck(escape)) {
                return false;
            }
        }
        System.out.println("MATE");
        return true;
    }

    public Point2D getKingPosition(Color color) {
        Point2D kingPosition = null;
        for (Point2D key : this.cellToPieceMap.keySet()) {
            ChessPiece piece = cellToPieceMap.get(key);
            // A king HAS to be on the board
            if (piece instanceof King && piece.color == color) {
                kingPosition = piece.position;
            }
        }
        return kingPosition;
    }

    public List<Point2D> getInBetweenCells(Point2D p1, Point2D p2) {
        List<Point2D> inBetweenCells = new ArrayList<Point2D>();
        Point2D diff = p1.subtract(p2);
        if (p1.equals(p2)) {
            return inBetweenCells;
        }
        if (diff.getX() == 0) {
            for (int i = (int) Math.min(p1.getY(), p2.getY()) + 1; i < (int) Math.max(p1.getY(), p2.getY()); i++) {
                inBetweenCells.add(new Point2D(p1.getX(), i));
            }
        } else if (diff.getY() == 0) {
            for (int i = (int) Math.min(p1.getX(), p2.getX()) + 1; i < (int) Math.max(p1.getX(), p2.getX()); i++) {
                inBetweenCells.add(new Point2D(i, p1.getY()));
            }
        } else if (Math.abs(diff.getX()) == Math.abs(diff.getY())) {
            Point2D singleStep = new Point2D(Math.signum(diff.getX()), Math.signum(diff.getY()));
            for (int i = 1; i < Math.abs(diff.getX()); i++) {
                inBetweenCells.add(p2.add(singleStep.multiply(i)));
            }
        }
        return inBetweenCells;
    }

    public HashMap<Point2D, ChessPiece> getCellToPieceMap() {
        return this.cellToPieceMap;
    }

    public boolean moveCausesCheck(Move move){
        Board copyOfBoard = new Board();
        List<ChessPiece> piecesOnBoard = new ArrayList<>();
        for (Point2D key : this.cellToPieceMap.keySet()) {
            ChessPiece val = this.cellToPieceMap.get(key);
            ChessPiece valCopy = (ChessPiece) val.clone();
            piecesOnBoard.add(valCopy);
        }
        copyOfBoard.addPiecesToBoard(piecesOnBoard);
        ChessPiece currentPiece = copyOfBoard.getPieceInCell(move.getFrom());
        currentPiece.setPosition(move.getTo());
        copyOfBoard.cellToPieceMap.remove(move.getFrom());
        copyOfBoard.cellToPieceMap.put(move.getTo(), currentPiece);
        return copyOfBoard.getPiecesCausingCheck(currentPiece.color == Color.BLACK ? Color.WHITE : Color.BLACK).size() > 0;
    }

    public boolean containsKing(Color color) {
        for (Point2D key : this.cellToPieceMap.keySet()) {
            ChessPiece piece = cellToPieceMap.get(key);
            if (piece instanceof King && piece.color == color) {
                return true;
            }
        }
        return false;
    }


}
