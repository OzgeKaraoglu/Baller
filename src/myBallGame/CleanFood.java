package myBallGame;

import java.awt.*;

public class CleanFood extends Food {
    public CleanFood(int xCoor, int yCoor, int radius, int nutritionConstant) {
        super(xCoor, yCoor, radius, Color.BLUE, nutritionConstant);
    }

    @Override
    public boolean collides(Entity entity) {
        boolean collides = super.collides(entity);
        entity.collisionOccured(this);
        return collides;
    }

    @Override
    public void collisionOccured(Player player) {
        player.collisionOccured(this);
    }

    @Override
    public void collisionOccured(CleanFood entity) {
        throw new UnsupportedOperationException("Food collision is not implemented yet! Maybe later on,this could be the case");
    }

    @Override
    public void collisionOccured(PoissonedFood entity) {
        throw new UnsupportedOperationException("Food collision is not implemented yet! Maybe later on,this could be the case");
    }
}
