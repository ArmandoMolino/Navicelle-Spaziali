package Obstacle;

import Enum.Direction;

import java.awt.*;

/**
 * la classe Fuel è una sottoclasse di ObstacleAdapter e contiene i metodi
 * utili allo spostamento ed al disegno del carburante. Ogni carburante è disegnato usando
 * 2 array contenenti i set di punti per le cordinate (x,y).
 * @see ObstacleAdapter
 * @see java.awt.Polygon
 */
public class Fuel extends ObstacleAdapter {

    //array contenenti i set di punti per le cordinate (x,y).
    private static final int[] sPolyXArray = {30,10,5,4,10,10,30};
    private static final int[] sPolyYArray = {-27,-27,-30,-27,-25,-5,-5};

    //Costruttore
    public Fuel(int[] polyXArray, int[] polyYArray, int pointsInPoly) {
        super(polyXArray, polyYArray, pointsInPoly);
    }

    //Getter
    public static int[] getXArray() {
        return sPolyXArray;
    }
    public static int[] getYArray() {
        return sPolyYArray;
    }

    public Rectangle getHitBox(){
        return new Rectangle(super.xpoints[3]-6, super.ypoints[2]-3, super.obstacleWidth, super.obstacleHeight);
    }

    //Usato per lo spostamento verso giu del carburante
    @Override
    public void move() {
        for(int i = 0; i < this.ypoints.length; i++){
            this.ypoints[i] += Direction.DOWN.getYDirection();
        }
    }
}
