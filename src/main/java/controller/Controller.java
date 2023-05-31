package controller;

import model.Player;
import model.pieces.Color;
import view.View;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.Move;

public class Controller {
    Game game;
    View view;
    private int moveCounter;
    public Controller(View view) {
        this.game = new Game();
        this.view = view;
        this.moveCounter = 0;
    }

    public void drawCurrentGameState() {
        this.view.drawBoard();
        this.view.drawPieces(this.game.getBoard().getCellToPieceMap());
    }

    public void handleDragAndDrop(MouseEvent pressEvent, MouseEvent releaseEvent) {
        Point2D from = new Point2D((int) Math.floor(pressEvent.getSceneX() / View.CELL_SIZE),
                (int) Math.floor(pressEvent.getSceneY() / View.CELL_SIZE));
        Point2D to = new Point2D((int) Math.floor(releaseEvent.getSceneX() / View.CELL_SIZE),
                (int) Math.floor(releaseEvent.getSceneY() / View.CELL_SIZE));
        Move move = new Move(from, to, this.moveCounter);
        boolean madeMoveSuccess = this.game.getBoard().move(move, this.game.getCurrentPlayer());
        if (madeMoveSuccess) {
            this.drawCurrentGameState();
            this.game.changeTurns();
            if (this.game.getCurrentPlayer().equals(this.game.getBlackPlayer()) && this.game.getBoard().isMate(Color.WHITE)) {
                this.endGame(this.game.getWhitePlayer());
            }
            if (this.game.getCurrentPlayer().equals(this.game.getWhitePlayer()) && this.game.getBoard().isMate(Color.BLACK)) {
                this.endGame(this.game.getBlackPlayer());
            }
            this.moveCounter++;
        }
    }

    public void endGame(Player winner) {
        String endMessage = winner.color + " wins";
        this.view.drawEndMessage(endMessage);
    }

}
