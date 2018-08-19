package myBallGame;

import java.awt.*;
import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 2519667966197439170L;
    protected int x, y;
    protected int radius;
    protected int onFrameX, onFrameY;
    private volatile boolean inFrame;
    private Color color;

    Entity() {

    }

    Entity(int xCoor, int yCoor, int radius, Color color) {
        this.x = xCoor;
        this.y = yCoor;
        this.radius = radius;
        this.color = color;
    }

    public void move(int x, int y) {
        this.x = x - radius;
        this.y = y - radius;
    }

    void calculateOnFrameLocation(int srcx1, int srcy1, int srcx2, int srcy2) {
        onFrameX = x - srcx1;
        onFrameY = y - srcy1;
        inFrame = x > srcx1 && x < srcx2 && y > srcy1 && y < srcy2;
    }

    void draw(Graphics g) {
        if (isInsight()) {
            g.setColor(color);
            int diameter = radius * 2;
            g.fillOval(onFrameX, onFrameY, diameter, diameter);
        }
    }

    boolean isInsight() {
        return inFrame;
    }

    public boolean collides(Entity entity) {
        double ycoord = (double) Math.abs(getCentery() - entity.getCentery());
        double xcoord = (double) Math.abs(getCenterx() - entity.getCenterx());
        double distance = Math.sqrt(ycoord * ycoord + xcoord * xcoord);
        return distance <= (double) radius + (double) entity.getRadius();
    }

    public abstract void collisionOccured(Player entity);

    public abstract void collisionOccured(CleanFood entity);

    public abstract void collisionOccured(PoissonedFood entity);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    protected int getCenterx() {
        return x + radius;
    }

    protected int getCentery() {
        return y + radius;
    }

    public int getRadius() {
        return radius;
    }


    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void changeRadiusTo(int radius) {
        this.radius = radius;
    }

}
