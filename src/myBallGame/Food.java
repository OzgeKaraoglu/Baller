package myBallGame;

import java.awt.*;

public abstract class Food extends Entity{
    Food(int xCoor, int yCoor, int radius, Color color,int nutritionalValue) {
        super(xCoor, yCoor, radius, color);
        this.nutritionalValue = nutritionalValue;
    }
    public final int nutritionalValue;
}
