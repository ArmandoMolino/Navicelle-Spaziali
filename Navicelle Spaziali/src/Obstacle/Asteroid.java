package Obstacle;

import Enum.Direction;

import java.awt.Rectangle;


/**
 * la classe Asteroid è una sottoclasse di ObstacleAdapter e contiene i metodi
 * utili allo spostamento ed al disegno dell'asteroide. Ogni asteroide è disegnato usando
 * 2 array contenenti i set di punti per le cordinate (x,y).
 * @see ObstacleAdapter
 * @see java.awt.Polygon
 */
public class Asteroid extends ObstacleAdapter {

    //array contenenti i set di punti per le cordinate (x,y).
    private static final int[] sPolyXArray = {10,13,26,30,29,31,26,22,8,1,2,1,4};
    private static final int[] sPolyYArray = {0,2,1,8,15,20,31,31,29,22,16,7,0};

    //costruttore
    public Asteroid(int[] polyXArray, int[] polyYArray, int pointsInPoly) {
        super(polyXArray, polyYArray, pointsInPoly);
    }

    //Getter
    public static int[] getXArray() {
        return sPolyXArray;
    }
    public static int[] getYArray() {
        return sPolyYArray;
    }

    @Override
    public Rectangle getHitBox(){
        return new Rectangle(super.xpoints[0]-12, super.ypoints[0]-2, super.obstacleWidth, super.obstacleHeight);
    }

    //Usato per lo spostamento verso giu dell'asteroide
    @Override
    public void move() {
        for(int i = 0; i < this.ypoints.length; i++){
            this.ypoints[i] += Direction.DOWN.getYDirection();
        }
    }
}
