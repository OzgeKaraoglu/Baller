package myBallGame;

public interface Game extends GameResultListener {
    void start(Player player, Peer peer, GameConfiguration conf);
}
