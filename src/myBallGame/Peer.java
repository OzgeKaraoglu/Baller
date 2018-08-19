package myBallGame;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class Peer implements Runnable{
    final TCPMessageListener listener;
    protected final Runnable onConnected;
    protected ObjectOutputStream output;
    protected ObjectInputStream input;
    protected Socket connection;

    Peer(TCPMessageListener listener, Runnable onConnected) {
        this.listener = listener;
        this.onConnected = onConnected;
    }

    protected void getStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    protected void closeConnection() {
        try {
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void processConnection() {
        do {
            try {
                Player player = (Player) input.readObject();
                onPlayerUpdate(player);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private void onPlayerUpdate(final Player player) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listener.onPlayerUpdate(player);
            }
        });
    }

    void send(Player player) {
        try {
            if (player != null) {
                output.reset();
                output.writeObject(player);
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
