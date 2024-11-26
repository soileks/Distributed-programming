package org.example.palochkiwithinterface;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class ClientApp extends Application {
    private static final String host = "localhost";
    private static final int port = 8080;
    private static final String name = "SticksGameServer";
    private String currentTurnID; // Переменная для отслеживания очереди хода
    private static final int SIZE = 2; // Размер сетки должен совпадать с размером на сервере
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private static final int CELL_SIZE = 100;  // Размер клетки
    private static final int OFFSET_X = 20; // Смещение вправо
    private static final int OFFSET_Y = 20; // Смещение вниз
    private boolean gameOver = false;

    private remoteSticks server;
    private int playerID;
    private Label scoreLabel = new Label(); // Метка для отображения счета
    private Label gameStateLabel = new Label(); // Метка для отображения состояния игры
    private Map<Line, int[]> linesMap = new HashMap<>(); // Хранение линий с их координатами
    private Map<String, Rectangle> squaresMap = new HashMap<>();
    //private boolean ready = false;



    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        createGrid(root);

        scoreLabel.setLayoutX(OFFSET_X);
        scoreLabel.setLayoutY((SIZE + 1) * CELL_SIZE + OFFSET_Y + 10);
        root.getChildren().add(scoreLabel);

        // Добавляем метку для отображения состояния игры
        gameStateLabel.setLayoutX(OFFSET_X);
        gameStateLabel.setLayoutY((SIZE + 2) * CELL_SIZE + OFFSET_Y + 10);
        root.getChildren().add(gameStateLabel);

        Scene scene = new Scene(root, (SIZE + 1) * CELL_SIZE + OFFSET_X * 2, (SIZE + 2) * CELL_SIZE + OFFSET_Y * 2 + 100);


        connectToServer();
        primaryStage.setTitle("Палочки" + " Игрок " + playerID);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            server = (remoteSticks) registry.lookup(name);
            playerID = server.getID();

            ClientProxy clientProxy = new ClientProxy(this);
            server.registerClient(clientProxy, playerID);

            // Инициализируем состояние игры
            updateGameState(server.getGameState(), server.getScore(), server.getHorizontalLines(), server.getVerticalLines(), server.getCurrentTurnID());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createGrid(Pane root) {
        // Создаем горизонтальные линии
        for (int i = 0; i <= SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Line hLine = new Line(j * CELL_SIZE + OFFSET_X, i * CELL_SIZE + OFFSET_Y, (j + 1) * CELL_SIZE + OFFSET_X , i * CELL_SIZE + OFFSET_Y);
                hLine.setStroke(Color.BLACK);
                hLine.setStrokeWidth(8);
                linesMap.put(hLine, new int[]{j, i, j + 1, i}); // Сохраняем координаты линии
                hLine.setOnMouseClicked(event -> handleMove(hLine));
                root.getChildren().add(hLine);
            }
        }

        // Создаем вертикальные линии
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <= SIZE; j++) {
                Line vLine = new Line(j * CELL_SIZE+ OFFSET_X , i * CELL_SIZE + OFFSET_Y, j * CELL_SIZE + OFFSET_X, (i + 1) * CELL_SIZE + OFFSET_Y);
                vLine.setStroke(Color.BLACK);
                vLine.setStrokeWidth(8);
                linesMap.put(vLine, new int[]{j, i, j, i + 1}); // Сохраняем координаты линии
                vLine.setOnMouseClicked(event -> handleMove(vLine));
                root.getChildren().add(vLine);
            }
        }

        // Создаем клетки (квадраты)
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Rectangle square = new Rectangle(j * CELL_SIZE + OFFSET_X, i * CELL_SIZE+ OFFSET_Y , CELL_SIZE , CELL_SIZE );
                square.setFill(null);
                square.setStroke(Color.TRANSPARENT);
                String key = i + "-" + j;
                squaresMap.put(key, square);
                root.getChildren().add(square);
            }
        }
    }

    private void handleMove(Line line) {
        // Проверка очередности хода
        if (!String.valueOf(playerID).equals(currentTurnID)) {
            System.out.println("Сейчас не ваш ход.");
            return;
        }

        if (line.getStroke() != Color.BLACK) {
            System.out.println("Линия занята, выберите другую!");

            return;
        }

        int[] coordinates = linesMap.get(line); // Получаем координаты линии
        int x1 = coordinates[0];
        int y1 = coordinates[1];
        int x2 = coordinates[2];
        int y2 = coordinates[3];

        try {
            server.drawLine(x1, y1, x2, y2, String.valueOf(playerID));

            // Обновляем состояние игры и счет на клиенте
            updateGameState(server.getGameState(), server.getScore(), server.getHorizontalLines(), server.getVerticalLines(), server.getCurrentTurnID());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void updateGameState(String gameState, String score, String[][] horizontalLines, String[][] verticalLines, String currentTurnID) throws RemoteException {
        this.currentTurnID = currentTurnID; // Обновляем очередь хода
        Platform.runLater(() -> { //поток для интерфейса
            scoreLabel.setText("Счёт: " + score);
            gameStateLabel.setText("Состояние игры: \n" + gameState); // Обновляем состояние игры

            // Обновление линий по состоянию игры
            for (Line line : linesMap.keySet()) {
                int[] coordinates = linesMap.get(line);
                int x1 = coordinates[0];
                int y1 = coordinates[1];
                int x2 = coordinates[2];
                int y2 = coordinates[3];

                // установка цвета
                if (x1 == x2) { // Вертикальная линия
                    if (verticalLines[Math.min(y1, y2)][x1] != null) {
                        line.setStroke(verticalLines[Math.min(y1, y2)][x1].equals("1") ? Color.RED : Color.BLUE);
                    }
                } else { // Горизонтальная линия
                    if (horizontalLines[y1][Math.min(x1, x2)] != null) {
                        line.setStroke(horizontalLines[y1][Math.min(x1, x2)].equals("1") ? Color.RED : Color.BLUE);
                    }
                }
            }

            // Обновляем квадраты в зависимости от состояния
            for (Map.Entry<String, Rectangle> entry : squaresMap.entrySet()) {
                String squareKey = entry.getKey();
                String[] coords = squareKey.split("-");
                int i = Integer.parseInt(coords[0]);
                int j = Integer.parseInt(coords[1]);

                try {
                    String square = server.getSquare(i, j);
                    if(square!=null){
                        if(square.equals("1"))
                            entry.getValue().setFill(Color.RED);
                        else entry.getValue().setFill(Color.BLUE);

                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }
            try {
                if (!gameOver && server.isGameOver()) { // Проверка флага и окончания игры
                    gameOver = true;
                    System.out.println("Игра завершена. " + server.getScore());

                    alert.setTitle("Игра завершена");
                    alert.setHeaderText(null);
                    alert.setContentText("Игра окончена! " + server.getScore());
                    alert.show();

                    //stop();

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    }





    public static void main(String[] args) {
        launch(args);
    }
}
