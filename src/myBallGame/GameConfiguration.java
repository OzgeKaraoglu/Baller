package myBallGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameConfiguration {
    final int dim_w;
    final int dim_h;
    final int gameStartingPosX;
    final int gameStartingPosY;
    final int cleanFoodSize;
    final int poissonedFoodSize;
    public BufferedImage backgroundImage;

    GameConfiguration(int dim_w, int dim_h, int cleanFoodSize, int poissonedFoodSize, int gameStartingPosX, int gameStartingPosY, BufferedImage backgroundImage) {
        this.dim_w = dim_w;
        this.dim_h = dim_h;
        this.cleanFoodSize = cleanFoodSize;
        this.poissonedFoodSize = poissonedFoodSize;
        this.gameStartingPosX = gameStartingPosX;
        this.gameStartingPosY = gameStartingPosY;
        this.backgroundImage = backgroundImage;
    }

    static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
