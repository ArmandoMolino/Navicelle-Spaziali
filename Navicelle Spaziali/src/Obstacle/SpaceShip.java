package Obstacle;

import Screen.GameScreen;
import Enum.Direction;
import Obstacle.Repositioning.Strategy.Repositioning;

import java.awt.*;
import java.util.Arrays;

/**
 * la classe SpaceShip è una sottoclasse di ObstacleAdapter e contiene i metodi
 * utili allo spostamento ed al disegno della navicella. Ogni navicella è disegnato usando
 * 2 array contenenti i set di punti per le cordinate (x,y).
 * @see ObstacleAdapter
 * @see java.awt.Polygon
 */
public class SpaceShip extends ObstacleAdapter {

    //array contenenti i set di punti per le cordinate (x,y).
    private static final int[] polyXArray = {14,-1,-14,-1,14};
    private static final int[] polyYArray = {13,-14,13,5,13};

    private final int maxHeart = 3;
    private int actualHeart = maxHeart;

    private final int fuelCapacity = 250;
    private int actualFuel = fuelCapacity;

    //Costruttore
    public SpaceShip(int[] polyXArray, int[] polyYArray, int pointsInPoly) {
        super(polyXArray, polyYArray, pointsInPoly); // Call the constructor of Polygon
    }

    //Usato per lo spostamento verso la direzione data dalla strategia di riposizionamento
    public void move(Direction direction) {
        int shift = 36 * direction.getXDirection();
        int actualWidth = Arrays.stream(this.xpoints).max().getAsInt() + shift;
        if( actualWidth > 18 && actualWidth < GameScreen.screenWidthForObstacle ){
            for(int i = 0; i < this.xpoints.length; i++)
                    this.xpoints[i] += shift;
        }
    }

    //Diminuisce il carburante attuale
    public void decreseFuel(){
        actualFuel -= 1;
    }

    //Aggiunge il 25% del carburante massimo a quello attuale
    public void reFull(){
        actualFuel += (int) (fuelCapacity * 0.25);
        if(actualFuel > fuelCapacity){
            actualFuel = fuelCapacity;
        }
    }

    //Getter e Setter
    public int getActualHeart() {
        return actualHeart;
    }
    public void setActualHeart(int actualHeart) {
        this.actualHeart = actualHeart;
    }

    public int getActualFuel() {
        return actualFuel;
    }
    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public static int[] getXArray() {
        return polyXArray;
    }
    public static int[] getYArray() {
        return polyYArray;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(super.xpoints[2] - 4, super.ypoints[1] - 4, super.obstacleWidth, super.obstacleHeight);
    }
}
