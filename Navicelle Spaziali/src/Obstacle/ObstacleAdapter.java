package Obstacle;

import java.awt.*;

public abstract class ObstacleAdapter extends Polygon implements Obstacle {

    protected final int obstacleWidth = 34;
    protected final int obstacleHeight = 34;


    public ObstacleAdapter(int[] xpoints, int[] ypoints, int npoints) {
        super(xpoints, ypoints, npoints);
    }

    @Override
    public abstract Rectangle getHitBox();

    @Override
    public void move(){}

}
