package model;

import model.Board;
import javafx.geometry.Point2D;
import model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<ChessPiece> pieces;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;
    public Game() {
        this.pieces = new ArrayList<>();
        //TODO mave initializing pieces to constructor of the board.
        this.board = new Board();
        this.pieces.add(new Bishop(new Point2D(2,7), Color.WHITE));
        this.pieces.add(new Bishop(new Point2D(5,7), Color.WHITE));
        this.pieces.add(new Bishop(new Point2D(2,0), Color.BLACK));
        this.pieces.add(new Bishop(new Point2D(5,0), Color.BLACK));
        this.pieces.add(new Rook(new Point2D(0,7), Color.WHITE));
        this.pieces.add(new Rook(new Point2D(7,7), Color.WHITE));
        this.pieces.add(new Rook(new Point2D(0,0), Color.BLACK));
        this.pieces.add(new Rook(new Point2D(7,0), Color.BLACK));
        this.pieces.add(new Queen(new Point2D(3,7), Color.WHITE));
        this.pieces.add(new Queen(new Point2D(3,0), Color.BLACK));
        this.pieces.add(new Knight(new Point2D(1,0), Color.BLACK));
        this.pieces.add(new Knight(new Point2D(6,0), Color.BLACK));
        this.pieces.add(new Knight(new Point2D(1,7), Color.WHITE));
        this.pieces.add(new Knight(new Point2D(6,7), Color.WHITE));
        this.pieces.add(new King(new Point2D(4,0), Color.BLACK));
        this.pieces.add(new King(new Point2D(4,7), Color.WHITE));
        for (int i = 0; i < 8; i++) {
            this.pieces.add(new Pawn(new Point2D(i,1), Color.BLACK));
            this.pieces.add(new Pawn(new Point2D(i,6), Color.WHITE));
        }
        board.addPiecesToBoard(this.pieces);
        this.whitePlayer = new Player(Color.WHITE);
        this.blackPlayer = new Player(Color.BLACK);
        this.currentPlayer = whitePlayer;
    }

    public Board getBoard() {
        return this.board;
    }

    public void changeTurns() {

        if (this.currentPlayer == this.whitePlayer) {
            System.out.println(this.board.isMate(Color.WHITE));
            this.currentPlayer = this.blackPlayer;
        } else {
            System.out.println(this.board.isMate(Color.BLACK));
            this.currentPlayer = this.whitePlayer;
        }
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }
}
