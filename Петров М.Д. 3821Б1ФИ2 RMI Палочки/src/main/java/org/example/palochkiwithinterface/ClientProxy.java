package org.example.palochkiwithinterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientProxy extends UnicastRemoteObject implements remoteSticksClient { // нужен, чтобы сервер смог оповещать клиента об изменениях
    private final ClientApp clientApp;

    public ClientProxy(ClientApp clientApp) throws RemoteException {
        this.clientApp = clientApp;
    }
    // это обновление игры вызывается на сервере
    @Override
    public void updateGameState(String gameState, String score, String[][] horizontalLines, String[][] verticalLines, String currentTurnID) throws RemoteException {
        clientApp.updateGameState(gameState, score, horizontalLines, verticalLines, currentTurnID);
    }
}
