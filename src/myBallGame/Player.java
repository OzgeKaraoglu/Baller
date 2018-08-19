package myBallGame;

import java.awt.*;
import java.io.Serializable;

public class Player extends Entity implements Serializable {

    private static final long serialVersionUID = -7196560885356994705L;

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private double speed = 5;
    private double speedIncreaseRate = .1;
    private int radiusIncreaseRate = 3;
    private volatile boolean alive = true;

    Player() {
    }

    Player(String name, int xCoor, int yCoor, int radius) {
        super(xCoor, yCoor, radius, Color.RED);
        this.name = name;
    }

    public String getName() {
        return name;
    }


    private void getKilledBy(Player player) {
        this.alive = false;
    }

    @Override
    public boolean collides(Entity entity) {
        boolean collides = super.collides(entity);
        if (collides)
            entity.collisionOccured(this);
        return collides;
    }

    @Override
    void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.WHITE);
        g.drawString(name, onFrameX + radius, onFrameY + radius);
    }

    @Override
    public void collisionOccured(Player enemy) {
        Player me = this;
        if (me.getRadius() > enemy.getRadius()) {
            enemy.getKilledBy(me);
        } else if(me.getRadius() < enemy.getRadius()){
            me.getKilledBy(enemy);
        }
    }

    @Override
    public void collisionOccured(CleanFood entity) {
        this.speed -= speedIncreaseRate * entity.nutritionalValue;
        setRadius(getRadius() + radiusIncreaseRate * entity.nutritionalValue);
    }

    @Override
    public void collisionOccured(PoissonedFood entity) {
        this.speed += speedIncreaseRate * entity.nutritionalValue;
        setRadius(getRadius() - radiusIncreaseRate * entity.nutritionalValue);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "myBallGame.Player{" +
                "name='" + name + '\'' +
                "x='" + x + '\'' +
                "y='" + y + '\'' +
                ", speed=" + speed +
                '}';
    }

    public boolean isAlive() {
        return alive;
    }
}
