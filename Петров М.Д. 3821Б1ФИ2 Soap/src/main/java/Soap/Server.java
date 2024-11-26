package Soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Objects;

@WebService
public class Server {
    private final int size = 1; // количество клеток на стороне
    private final String[][] horizontalLines;
    private final String[][] verticalLines;
    private final String[][] squares; // кто завершил конкретный квадрат в игре
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int clientID = 0;
    private Queue<String> queueClients = new LinkedList<>();
    boolean gameOver = false;

    public Server() {
        horizontalLines = new String[size + 1][size]; // для хранения горизонтальных линий (линия, клетка)
        verticalLines = new String[size][size + 1];
        squares = new String[size][size];
    }

    @WebMethod
    public boolean isItMyMove(String client) {
        if (queueClients.isEmpty()) {
            queueClients.add(client);
            return true;
        } else if (Objects.equals(queueClients.peek(), client)) {
            return true;
        } else if (!queueClients.contains(client)) {
            queueClients.add(client);
            return false;
        }
        return false;
    }

    @WebMethod
    public int getID() {
        if(clientID + 1 == 3 )
            return 3;
        clientID++;
        System.out.println("Присвоено ID: " + clientID);
        return clientID;
    }

    @WebMethod
    public String drawLine(int x1, int y1, int x2, int y2, String clientID) {
        if (Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)) != 1) { // проверка расстояния
            System.out.println("Расстояние должно быть = 1");
            return "Расстояние должно быть = 1";
        }
        if (x1 == x2) { // Вертикальная линия
            if (verticalLines[Math.min(y1, y2)][x1] == null) {
                verticalLines[Math.min(y1, y2)][x1] = clientID; // Запоминаем линию
            } else {
                System.out.println("Линия уже есть");
                return "Линия уже есть";
                //return false;
            }
        } else { // Горизонтальная линия
            if (horizontalLines[y1][Math.min(x1, x2)] == null) {
                horizontalLines[y1][Math.min(x1, x2)] = clientID; // Запоминаем линию
            } else {
                System.out.println("Линия уже есть");
                return "Линия уже есть";
               // return false;
            }
        }

        boolean gainedSquare = checkForCompletedSquares(clientID);

        if (!gainedSquare) {
            queueClients.remove();
            return "Переход очереди!";
        }
        if(isGameOver()) return "Вы завершили последний квадрат!";
        return "Еще один ход! Вы завершили квадрат.";
       // return gainedSquare;
    }

    private boolean checkForCompletedSquares(String clientID) { // проверка на завершение квадрата
        boolean squareAdd = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squares[i][j] == null && horizontalLines[i][j] != null && horizontalLines[i + 1][j] != null
                        && verticalLines[i][j] != null && verticalLines[i][j + 1] != null) {
                    squares[i][j] = clientID;
                    if (clientID.equals("1")) {
                        playerOneScore++;
                    } else {
                        playerTwoScore++;
                    }
                    squareAdd = true;
                }
            }
        }
        return squareAdd;
    }

    @WebMethod
    public String getGameState() {
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                state.append(horizontalLines[i][j] == null ? "   " : "---");
                state.append(" ");
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
            state.append(horizontalLines[size][j] == null ? "   " : "---");
            state.append(" ");
        }
        return state.toString();
    }

    @WebMethod
    public String getScore() {
        return "Счет: Игрок 1 - " + playerOneScore + ", Игрок 2 - " + playerTwoScore;
    }

    @WebMethod
    public boolean isGameOver() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squares[i][j] == null) return false;
            }
        }

        if(!gameOver){
        queueClients.remove();
        System.out.println("Игра завершена!");
        System.out.println(getScore());
        System.out.println(getGameState());
        }
        gameOver=true;
        return true;
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            Endpoint.publish("http://localhost:8080/Server?wsdl", server);
            System.out.println("Сервер запущен");
        } catch (Exception e) {
            System.out.println("Ошибка сервера: " + e);
        }
    }
}
