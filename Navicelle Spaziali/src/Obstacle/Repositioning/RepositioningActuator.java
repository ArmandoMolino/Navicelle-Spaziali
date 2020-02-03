package Obstacle.Repositioning;

import Screen.GameScreen;
import Obstacle.Obstacle;
import Obstacle.Repositioning.Strategy.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;


/**
 * Questa classe viene utilizzata per applicare la corretta strategia per
 * il riposizionamento della navicella.
 */
public class RepositioningActuator {

    private static int counter = 0;

    /**
     * Sceglie la strategia adatta in base alle seguenti condizioni:
     * 1 - casualmente nel caso un ostacolo si trovi nel suo intorno (RandomRepositioning);
     * 2 - a sinistra se la maggioranza degli ostacoli si trovano alla sua destra (RightRepositioning);
     * 3 - a destra se la maggioranza degli ostacoli si trovano alla sua sinistra (LeftRepositioning);
     * 4 - verso il fuel più vicino se il carburante è al di sotto del 75% (TakeFuelRepositioning);
     * @return strategia di riposizionamento
     */
    public static Repositioning doRepositioning() {
        //intorno della navicella. Esso è un rettangolo di altezza = 2 * altezza hitbox ostacoli.
        Rectangle neighbourhood = new Rectangle( GameScreen.spaceShip.getHitBox().x,  GameScreen.spaceShip.getHitBox().y - 72, 34, 68);

        //ArrayList contenente gli ostacoli diversi da fuel
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(GameScreen.obstacles.stream()
                                            .filter(obstacle -> !obstacle.getClass().getName().contains("Fuel"))
                                            .collect(Collectors.toList()));
        if( !obstacles.isEmpty() ){
            for ( Obstacle asteroid : obstacles){
                if ( neighbourhood.contains(asteroid.getHitBox()) || neighbourhood.intersects(asteroid.getHitBox()) ){
                    counter = 36;
                    return new RandomRepositioning();
                }
            }
            if ( counter == 0 ){
            //significa che nella gameBoard c'è del fuel poichè obstacles contiene tutti gli ostacoli tranne il fuel
            // se c'è del fuel ed il carburante disponibile è al di sotto del 75%
                if( obstacles.size() < GameScreen.obstacles.size() && GameScreen.spaceShip.getActualFuel() < GameScreen.spaceShip.getFuelCapacity() * 0.75 ) {
                    return new TakeFuelRepositioning();
                } else {
                return rightOrLeftRepositioning();
                }
            } else {
                counter -=1;
            }
        }
        return new NoRepositioning();
    }

    private static Repositioning rightOrLeftRepositioning(){
        int nRightObst = GameScreen.obstacles.stream()
                .filter(obstacle -> ( obstacle.getHitBox().x > GameScreen.screenWidthForObstacle/2 + 18 ))
                .collect(Collectors.toList()).size();

        int nLeftObst = GameScreen.obstacles.stream()
                .filter(obstacle -> ( obstacle.getHitBox().x < GameScreen.screenWidthForObstacle/2 - 18 ))
                .collect(Collectors.toList()).size();

        if ( nRightObst < nLeftObst ){
            return new RightRepositioning();
        } else {
            return new LeftRepositioning();
        }
    }
}
