package org.example.palochkiwithinterface;

import javafx.scene.control.Alert;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements remoteSticks {
    private static final int port = 8080;
    private static final String name = "SticksGameServer";
    private final int size = 2; // Размер сетки
    private final String[][] horizontalLines;
    private final String[][] verticalLines;
    private final String[][] squares;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int clientID = 0;
    private Queue<String> queueClients = new LinkedList<>();
    private List<remoteSticksClient> clients = new ArrayList<>();
    private String currentTurnID;
    private boolean gameOver = false;
   // private boolean[] readyArray = new boolean[2];

    public Server() throws RemoteException {
        super();
        horizontalLines = new String[size + 1][size];
        verticalLines = new String[size][size + 1];
        squares = new String[size][size];
    }
    @Override
    public String getSquare(int i, int j){ //  это безопасно, поскольку строки неизменяемы.
        return squares[i][j];
    }

    @Override
    public int getID() throws RemoteException {
        clientID++;
        System.out.println("Присвоено ID: " + clientID);
        String clientIDStr = String.valueOf(clientID);

        //if (!queueClients.contains(clientIDStr)) { // лишняя проверка(для потенциального расширения)
            queueClients.add(clientIDStr);
        //}

        if (currentTurnID == null) {
            currentTurnID = clientIDStr;
        }

        return clientID;
    }

    @Override
    public boolean isItMyMove(String clientID) throws RemoteException {
        // Если очередь пуста, добавляем клиента и назначаем его текущим игроком
        if (queueClients.isEmpty()) {
            queueClients.add(clientID);
            currentTurnID = clientID; // Устанавливаем первого игрока
            return true;
        }
        // Проверяем, является ли текущий клиент тем, кто должен ходить
        else if (Objects.equals(queueClients.peek(), clientID)) {
            return true; // Текущий игрок может делать ход
        }
        // Если игрок не в очереди, добавляем его
        else if (!queueClients.contains(clientID)) {
            queueClients.add(clientID);
            return false; // Новый игрок, который не в очереди
        }
        return false; // Если очередь не совпадает
    }



    @Override
    public void drawLine(int x1, int y1, int x2, int y2, String clientID) throws RemoteException {
        // Проверка, что линия допустима (расстояние = 1)
        if (Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)) != 1) {
            System.out.println("Расстояние должно быть = 1");
           // return false;
            return;
        }

        // Проверка на очередь
        if (!isItMyMove(clientID)) {
            System.out.println("Не ваш ход: " + clientID);
           // return false; // Если не ваш ход, выходим
            return;
        }

        // Проверка на занятость линии
        if (x1 == x2) { // Вертикальная линия
            if (verticalLines[Math.min(y1, y2)][x1] == null) {
                verticalLines[Math.min(y1, y2)][x1] = clientID;
            } else {
                System.out.println("Линия уже занята");
               // return false;
                return;
            }
        } else { // Горизонтальная линия
            if (horizontalLines[y1][Math.min(x1, x2)] == null) {
                horizontalLines[y1][Math.min(x1, x2)] = clientID;
            } else {
                System.out.println("Линия уже занята");
               // return false;
                return;
            }
        }

        boolean gainedSquare = checkForCompletedSquares(clientID); // проверяем закрасился ли квадрат
        if (!gainedSquare) {
            // Убираем игрока из очереди, передавая ход следующему
            String previousTurnID = queueClients.poll(); // Убираем текущего игрока из очереди
            currentTurnID = queueClients.peek(); // Обновляем текущего игрока
            queueClients.add(previousTurnID);

            // Логи для отладки
            System.out.println("Предыдущий ход: " + previousTurnID);
            System.out.println("Текущий ход: " + currentTurnID);

            if (currentTurnID != null) {
                System.out.println("Теперь ход игрока: " + currentTurnID);
            } else {
                System.out.println("Текущий ход: null");
            }
        } else {
            System.out.println("Игрок " + clientID + " делает ход снова");
        }

        notifyClients(); // Уведомляем клиентов о новом состоянии игры
       // return gainedSquare;
    }





    private boolean checkForCompletedSquares(String clientID) {
        boolean squareAdded = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squares[i][j] == null &&
                        horizontalLines[i][j] != null &&
                        horizontalLines[i + 1][j] != null &&
                        verticalLines[i][j] != null &&
                        verticalLines[i][j + 1] != null) {

                    squares[i][j] = clientID;
                    if (clientID.equals("1")) playerOneScore++;
                    else playerTwoScore++;
                    squareAdded = true;
                }
            }
        }
        return squareAdded;
    }


    private void notifyClients() throws RemoteException {
        String gameState = getGameState();
        String score = getScore();
        String[][] horizontalLinesCopy = horizontalLines.clone(); // Копируем состояние линий
        String[][] verticalLinesCopy = verticalLines.clone();
        for (remoteSticksClient client : clients) {
            try {
                client.updateGameState(gameState, score, horizontalLinesCopy, verticalLinesCopy, currentTurnID);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String[][] getHorizontalLines() throws RemoteException {
        return horizontalLines;
    }

    @Override
    public String[][] getVerticalLines() throws RemoteException {
        return verticalLines;
    }

    @Override
    public String getCurrentTurnID() throws RemoteException {
        return currentTurnID;
    }


    @Override
    public String getGameState() throws RemoteException {
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                state.append(horizontalLines[i][j] == null ? "   " : "---").append(" ");
            }
            state.append("\n");
            for (int j = 0; j < size; j++) {
                state.append(verticalLines[i][j] == null ? " " : "|");
                state.append(squares[i][j] == null ? "   " : " " + squares[i][j] + " ");
            }
            state.append(verticalLines[i][size] == null ? " " : "|");
            state.append("\n");
        }
        for (int j = 0; j < size; j++) {
            state.append(horizontalLines[size][j] == null ? "   " : "---").append(" ");
        }
        return state.toString();
    }

    @Override
    public String getScore() throws RemoteException {
        return "Счет: Игрок 1 - " + playerOneScore + ", Игрок 2 - " + playerTwoScore;
    }

    @Override
    public boolean isGameOver() throws RemoteException {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squares[i][j] == null) return false;
            }
        }
        if (!gameOver) { // Проверка флага и окончания игры
            gameOver = true;
        System.out.println("Игра завершена. " + getScore());
        }
        return true;
    }

    @Override
    public void registerClient(remoteSticksClient client, int playerID) throws RemoteException {
        clients.add(client);
        System.out.println("Клиент зарегистрирован: игрок " + playerID);
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            Server sticksServer = new Server();
            registry.bind(name, sticksServer);
            System.out.println("Сервер запущен");
        } catch (Exception e) {
            System.out.println("Ошибка сервера: " + e);
        }
    }
}
