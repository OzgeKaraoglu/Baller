package myBallGame;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class Client extends Peer {
    private String remoteAddress;

    public Client(TCPMessageListener listener, Runnable onConnected, String host) {
        super(listener,onConnected);
        remoteAddress = host;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Attempting connection");
                connection = new Socket(InetAddress.getByName(remoteAddress), 12345);
                onConnected.run();
                getStreams();
                processConnection();
            } catch (EOFException e) {
                System.out.println("\nmyBallGame.Client terminated connection");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

