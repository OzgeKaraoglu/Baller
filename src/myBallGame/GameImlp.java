package myBallGame;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameImlp extends JPanel implements ActionListener, Game, TCPMessageListener {

    private static final long serialVersionUID = 8832666404435550305L;
    private final GameResultListener gameResultListener;

    private int nutritionConstant = 1;

    private int dim_w, dim_h;
    private volatile double deltaX, deltaY;

    private BufferedImage backgroundImage;

    private volatile double srcx1, srcy1, srcx2, srcy2;
    private double rightLimitOnScreen;
    private double downLimitOnScreen;

    private Entity food;
    private ArrayList<Entity> entities;

    private Player currentPlayer;
    private Player remotePlayer;
    private Peer peer;
    private Timer timer;
    private JFrame frame;

    GameImlp(GameResultListener gameResultListener) {
        this.gameResultListener = gameResultListener;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(dim_w, dim_h);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final int _srcx1 = (int) srcx1;
        final int _srcy1 = (int) srcy1;
        final int _srcx2 = (int) srcx2;
        final int _srcy2 = (int) srcy2;
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), _srcx1, _srcy1, _srcx2, _srcy2, this);

        Iterator<Entity> iter = entities.iterator();
        while (iter.hasNext()) {
            Entity entity = iter.next();
            entity.calculateOnFrameLocation(_srcx1, _srcy1, _srcx2, _srcy2);
            if (currentPlayer.collides(entity)) {
                iter.remove();
            } else {
                entity.draw(g);
            }
        }
        currentPlayer.calculateOnFrameLocation(_srcx1, _srcy1, _srcx2, _srcy2);
        currentPlayer.draw(g);
        if (null != remotePlayer) {
            remotePlayer.calculateOnFrameLocation(_srcx1, _srcy1, _srcx2, _srcy2);
            remotePlayer.draw(g);
        }
        // http://stackoverflow.com/questions/19480076/java-animation-stutters-when-not-moving-mouse-cursor
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean shouldRepaint = false;
        double newXOfSrc1 = srcx1 + deltaX;
        if (newXOfSrc1 > 0 && newXOfSrc1 < rightLimitOnScreen) {
            srcx1 = newXOfSrc1;
            srcx2 += deltaX;
            shouldRepaint = true;
        }
        double newYOfSrc1 = srcy1 + deltaY;
        if (newYOfSrc1 > 0 && newYOfSrc1 < downLimitOnScreen) {
            srcy1 = newYOfSrc1;
            srcy2 += deltaY;
            shouldRepaint = true;
        }
        if (shouldRepaint) {
            repaint();
            int _x = (int) srcx1 + dim_w / 2;
            int _y = (int) srcy1 + dim_h / 2;
            currentPlayer.move(_x, _y);
            peer.send(currentPlayer);
            if (null != remotePlayer && currentPlayer.collides(remotePlayer)) {
                if (currentPlayer.isAlive()) {
                    over(currentPlayer, remotePlayer);
                } else {
                    over(remotePlayer, currentPlayer);
                }
            }
        }
    }

    @Override
    public void start(Player player, Peer peer, GameConfiguration conf) {
        this.peer = peer;
        this.dim_w = conf.dim_w;
        this.dim_h = conf.dim_h;
        this.backgroundImage = conf.backgroundImage;
        srcx1 = conf.gameStartingPosX;
        srcy1 = conf.gameStartingPosY;
        srcx2 = srcx1 + dim_w;
        srcy2 = srcy1 + dim_h;
        this.currentPlayer = player;
        this.rightLimitOnScreen = backgroundImage.getWidth() - this.dim_w;
        this.downLimitOnScreen = backgroundImage.getHeight() - this.dim_h;

        addMouseMotionListener(new Mover());

        Random r = new Random();
        entities = new ArrayList<Entity>();

        for (int i = 0; i < conf.cleanFoodSize; i++) {
            entities.add(new CleanFood(this.dim_w / 2 + r.nextInt(backgroundImage.getWidth() - this.dim_w), this.dim_h / 2 + r.nextInt(backgroundImage.getHeight() - this.dim_h), 10, nutritionConstant));
        }

        for (int i = 0; i < conf.poissonedFoodSize; i++) {
            entities.add(new PoissonedFood(this.dim_w / 2 + r.nextInt(backgroundImage.getWidth() - this.dim_w), this.dim_h / 2 + r.nextInt(backgroundImage.getHeight() - this.dim_h), 10, nutritionConstant));
        }
        frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setSize(this.dim_w, this.dim_h);
        frame.setLocation(0, 0);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void over(Player winner, Player loser) {
        timer.stop();
        gameResultListener.over(winner, loser);
        //close();
    }

    @Override
    public void onPlayerUpdate(Player player) {
        this.remotePlayer = player;
    }

    class Mover extends MouseInputAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            Point p = e.getPoint();
            double midPointX = dim_w / 2;
            double midPointY = dim_h / 2;
            double alpha = Math.atan2(p.y - midPointY, p.x - midPointX);
            deltaX = currentPlayer.getSpeed() * Math.cos(alpha);
            deltaY = currentPlayer.getSpeed() * Math.sin(alpha);
        }
    }
}
