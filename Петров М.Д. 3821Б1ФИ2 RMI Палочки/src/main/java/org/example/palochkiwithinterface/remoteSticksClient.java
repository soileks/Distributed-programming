package org.example.palochkiwithinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface remoteSticksClient extends Remote{ // Интерфейс скрывает реализацию от сервера(инкапсуляция)
    void updateGameState(String gameState, String score, String[][] horizontalLines, String[][] verticalLines, String currentTurnID) throws RemoteException;
}
