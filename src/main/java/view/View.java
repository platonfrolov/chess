package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import model.pieces.ChessPiece;

import java.util.HashMap;


public class View extends Application {
    boolean gameOver = false;
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = PANEL_WIDTH;
    private static final int BOARD_ROWS = 8;
    private static final int BOARD_COLS = BOARD_ROWS;
    public static final int CELL_SIZE = PANEL_WIDTH / BOARD_COLS;
    private GraphicsContext gc;
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller(this);
        primaryStage.setTitle("CHESS");
        Group root = new Group();
        this.canvas = new Canvas(PANEL_WIDTH, PANEL_HEIGHT);
        root.getChildren().add(this.canvas);
        Scene scene = new Scene(root);
        scene.setOnMousePressed(pressEvent -> {
            scene.setOnMouseReleased(releaseEvent -> {
                controller.handleDragAndDrop(pressEvent, releaseEvent);
            });
        });
        primaryStage.setScene(scene);
        primaryStage.show();
        this.gc = canvas.getGraphicsContext2D();
        controller.drawCurrentGameState();
    }


    public void drawPieces(HashMap<Point2D, ChessPiece> cellToPieceMap) {
        for (Point2D key : cellToPieceMap.keySet()) {
            ChessPiece piece = cellToPieceMap.get(key);
            this.gc.drawImage(piece.pieceImage, piece.position.getX() * CELL_SIZE, piece.position.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public void drawBoard() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if ((i+j) % 2 == 0) {
                    this.gc.setFill(Color.WHITE);
                } else {
                    this.gc.setFill(Color.GRAY);
                }
                this.gc.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public void drawEndMessage(String endMessage) {
        this.gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        this.gc.setStroke(Color.BLACK);
        this.gc.strokeText(endMessage, 400, 400);
    }


    public static void main(String[] args) {
        launch(args);
    }
}