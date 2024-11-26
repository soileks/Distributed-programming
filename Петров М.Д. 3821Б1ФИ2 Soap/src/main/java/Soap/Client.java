package Soap;

import Soap.service.ServerService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Client {
    static Soap.service.Server server;

    public static void connectToServer() throws MalformedURLException {
        ServerService service = new ServerService(new URL("http://localhost:8080/Server?wsdl"));
        server = service.getServerPort();
    }
    public static void startGame() throws InterruptedException {

        int currentID = server.getID();
        if(currentID==3){
            System.out.println("Извините, игра только для двух игроков. Попробуйте позже.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String currentClient = String.valueOf(currentID);

        while (!server.isGameOver()) {

            // Печать состояния игры и счета
            System.out.println(server.getGameState());
            System.out.println(server.getScore());

            if (server.isItMyMove(currentClient)) {

                // Ход игрока
                System.out.println("Ваш ход");
                System.out.println("Введите точки (x1, y1, x2, y2): ");
                int x1 = scanner.nextInt();
                int y1 = scanner.nextInt();
                int x2 = scanner.nextInt();
                int y2 = scanner.nextInt();
                String gainedSquare = server.drawLine(x1, y1, x2, y2, currentClient);

                System.out.println(gainedSquare);

            } else {
                // Если не ваш ход, ждем
                System.out.println("Ожидание хода другого игрока...");
                while (!server.isItMyMove(currentClient)) {
                    Thread.sleep(1000); // Задержка на 1 секунду, чтобы уменьшить нагрузку
                }
            }
        }

        System.out.println("Игра завершена!");
        System.out.println(server.getScore());
        System.out.println(server.getGameState());
    }

    public static void main(String[] args) throws Exception {
        connectToServer();
        startGame();
    }
}
