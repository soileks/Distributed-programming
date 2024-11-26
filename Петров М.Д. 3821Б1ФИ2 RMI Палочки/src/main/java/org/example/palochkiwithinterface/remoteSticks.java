package org.example.palochkiwithinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface remoteSticks extends Remote {
    int getID() throws RemoteException;
    boolean isItMyMove(String clientID) throws RemoteException;
    void drawLine(int x1, int y1, int x2, int y2, String clientID) throws RemoteException;
    String getGameState() throws RemoteException;
    String getScore() throws RemoteException;
    boolean isGameOver() throws RemoteException;
    void registerClient(remoteSticksClient client, int playerID) throws RemoteException;
    String[][] getHorizontalLines() throws RemoteException;
    String[][] getVerticalLines() throws RemoteException;
    String getCurrentTurnID() throws RemoteException;
    String getSquare(int i, int j) throws RemoteException;

}
