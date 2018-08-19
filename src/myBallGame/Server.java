package myBallGame;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Peer {
    private int counter = 1;

    Server(TCPMessageListener listener,Runnable onConnected) {
        super(listener,onConnected);
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(12345);
            while (true) {
                try {
                    System.out.println("Waiting for connection");
                    connection = server.accept();
                    onConnected.run();
                    System.out.println("\nConnection " + counter + " received "
                            + "from: "
                            + connection.getInetAddress().getHostName());
                    getStreams();
                    processConnection();
                } catch (EOFException e) {
                    System.out.println("\nmyBallGame.Server terminated connection");
                } finally {
                    closeConnection();
                    ++counter;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


