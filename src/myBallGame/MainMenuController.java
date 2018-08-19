package myBallGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainMenuController implements ActionListener, GameResultListener {
    private GameImlp game;
    private MenuView menuView;
    private GameConfiguration conf;
    private Peer peer;
    private Player currentPlayer;
    private Thread connectionThread;

    public static void main(String[] args) {
        new MainMenuController().run();
    }

    private void run() {
        menuView = new MenuView(this);
        game = new GameImlp(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage backgroundImage = GameConfiguration.loadImage("C:\\Users\\ozgee\\IdeaProjects\\MyBallGame\\src\\background2.jpg");

        switch (e.getActionCommand()) {
            case "Server":
                conf = new GameConfiguration(800, 600, 50, 10, 0, 0, backgroundImage);
                currentPlayer = new Player(menuView.getUserName(), conf.gameStartingPosX + conf.dim_w / 2, conf.gameStartingPosY + conf.dim_h / 2, 20);
                peer = new Server(game, new Runnable() {
                    @Override
                    public void run() {
                        game.start(currentPlayer, peer, conf);
                    }
                });
                connectionThread = new Thread(peer);
                connectionThread.start();
                break;
            case "Client":
                conf = new GameConfiguration(800, 600, 50, 10, backgroundImage.getWidth() - 800, backgroundImage.getHeight() - 600, backgroundImage);
                currentPlayer = new Player(menuView.getUserName(), conf.gameStartingPosX + conf.dim_w / 2, conf.gameStartingPosY + conf.dim_h / 2, 20);
                peer = new Client(game, new Runnable() {
                    @Override
                    public void run() {
                        game.start(currentPlayer, peer, conf);
                    }
                }, menuView.getRemoteAddress());
                connectionThread = new Thread(peer);
                connectionThread.start();
                break;
            case "Exit":
                menuView.close();
                break;
        }
    }

    @Override
    public void over(Player winner, Player loser) {
        if (null != peer) {
            peer.closeConnection();
            connectionThread.interrupt();
        }
        if (winner == currentPlayer) {
            new ResultView("You Win! " + currentPlayer.getName() + " , " + loser.getName() + " loose");
        } else if (loser == currentPlayer) {
            new ResultView("You Lose!" + currentPlayer.getName() + " , " + winner.getName() + " win");
        }


    }
}
