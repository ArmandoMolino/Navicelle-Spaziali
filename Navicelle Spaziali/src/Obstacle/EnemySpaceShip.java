package Obstacle;

import Screen.GameScreen;
import Enum.Direction;
import Obstacle.Repositioning.Strategy.LeftRepositioning;
import Obstacle.Repositioning.Strategy.RightRepositioning;

import java.awt.*;
import java.util.Arrays;

/**
 * la classe SpaceShip è una sottoclasse di ObstacleAdapter e contiene i metodi
 * utili allo spostamento ed al disegno della navicella. Ogni navicella è disegnato usando
 * 2 array contenenti i set di punti per le cordinate (x,y).
 * @see ObstacleAdapter
 * @see java.awt.Polygon
 */
public class EnemySpaceShip extends ObstacleAdapter  {

    private int counter = 36;
    private Direction spaceShipDirection = Direction.STOP;

    //array contenenti i set di punti per le cordinate (x,y).
    private final static int[] polyXArray = {14,-1,-14,-1,14};
    private final static int[] polyYArray = {-13,14,-13,-5,-13};

    //Costruttore
    public EnemySpaceShip(int[] xpoints, int[] ypoints, int npoints) {
        super(xpoints, ypoints, npoints);
    }

    //Getter
    public static int[] getXArray() {
        return polyXArray;
    }
    public static int[] getYArray() {
        return polyYArray;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(super.xpoints[2] - 4, super.ypoints[0] - 4, super.obstacleWidth, super.obstacleHeight);
    }

    //Usato per lo spostamento verso giu e verso la direzione
    //data dalla strategia di riposizionamento della navicella
    @Override
    public void move() {
        //se non si è arrivati alla profondità y = 575 si sceglie in quale direzione andare
        if ( counter == 36 && this.getHitBox().y < 575 ){
            if ((int) (Math.random() * 100) <= 50) {
                spaceShipDirection = new RightRepositioning().repositioningBehaviour(this.getHitBox());
                spaceShipDirection = ( spaceShipDirection != Direction.STOP )? spaceShipDirection : new LeftRepositioning().repositioningBehaviour(this.getHitBox());
            } else {
                spaceShipDirection = new LeftRepositioning().repositioningBehaviour(this.getHitBox());
                spaceShipDirection = ( spaceShipDirection != Direction.STOP )? spaceShipDirection : new RightRepositioning().repositioningBehaviour(this.getHitBox());
            }
        } else if (this.getHitBox().y > 575){
            spaceShipDirection = Direction.STOP;
        }
        counter = ( counter == 0 )? 36 : counter - 1;
        int actualWidth = Arrays.stream(this.xpoints).max().getAsInt() + 36 * spaceShipDirection.getXDirection();

        //Sposta la navicella in giù e verso una delle 2 direzione sull'asse delle X
        for(int i = 0; i < this.ypoints.length; i++) {
            this.ypoints[i] += Direction.DOWN.getYDirection();
            if( actualWidth > 18 && actualWidth < GameScreen.screenWidthForObstacle){
                this.xpoints[i] += spaceShipDirection.getXDirection();
            } else {
                spaceShipDirection = Direction.reverseDirection(spaceShipDirection);
            }
        }
    }
}
