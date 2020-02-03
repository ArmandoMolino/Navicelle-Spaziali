package Obstacle;

import java.awt.*;

/**
 * la classe Heart è una sottoclasse di Polygon e contiene i metodi
 * utili l disegno del cuore. Ogni cuore è disegnato usando
 * 2 array contenenti i set di punti per le cordinate (x,y).
 * @see ObstacleAdapter
 * @see java.awt.Polygon
 */
public class Heart extends Polygon {

    //array contenenti i set di punti per le cordinate (x,y).
    private static final int[] sPolyXArray = {20,31,31,30,28,25,23,20,17,15,12,10,9,9};
    private static final int[] sPolyYArray = {-5,-15,-20,-22,-25,-25,-22,-20,-22,-25,-25,-22,-20,-15};

    //Costruttore
    public Heart(int[] xpoints, int[] ypoints, int npoints) {
        super(xpoints, ypoints, npoints);
    }

    //Getter
    public static int[] getXArray() {
        return sPolyXArray;
    }
    public static int[] getYArray() {
        return sPolyYArray;
    }
}
