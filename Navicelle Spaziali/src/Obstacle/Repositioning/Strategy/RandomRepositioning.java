package Obstacle.Repositioning.Strategy;

import Obstacle.Obstacle;
import Screen.GameScreen;
import Enum.Direction;

import java.awt.*;
import java.util.ConcurrentModificationException;

/**
 * Questa classe viene utilizzata per attuare la strategia di
 * riposizionamento randomico.
 * @see Repositioning
 */
public class RandomRepositioning implements Repositioning {


    /**
     * Questa funzione attua la seguente strategia:
     * - Se un ostacolo si trova nel intorno della navicella restituisce una direzione casuale
     * @param hitBox hit box della navicella
     * @return direzione
     */
    @Override
    public Direction repositioningBehaviour(Rectangle hitBox) {
        // rectangleL rappresenta l'area che puo occupare un ostacolo che collide con l'astronave sulla sinistra.
        Rectangle rectangleL = getLeftRectangle(hitBox);

        // rectangleR rappresenta l'area che puo occupare un ostacolo che collide con l'astronave sulla destra.
        Rectangle rectangleR = getRightRectangle(hitBox);

        //variabili booleane usate per sapere se spostandosi a destra o a sinistra
        //puo esserci una collisione.
        boolean rightCollision = false;
        boolean leftCollision = false;
        try {
            for( Obstacle obstacle : GameScreen.obstacles ){
                if ( rectangleR.contains(obstacle.getHitBox()) || rectangleR.intersects(obstacle.getHitBox())) {
                    rightCollision = true;
                } else if ( rectangleL.contains(obstacle.getHitBox()) || rectangleL.intersects(obstacle.getHitBox())){
                    leftCollision = true;
                }
            }
        } catch (ConcurrentModificationException | IndexOutOfBoundsException | NullPointerException ignored){ }

        //sceglie in modo randomico la direzione
        int choose = (int) (Math.random() * 2);
        if(choose == 0 && !rightCollision){
            //se choose è uguale a 0 e non avvengono collisioni spostandoci a destra
            return Direction.RIGHT;
        }
        if ( !leftCollision ) {
            //se choose non è uguale a 0 e non avvengono collisioni spostandoci a sinistra
            return Direction.LEFT;
        } else {
            return Direction.STOP;
        }
    }
}
