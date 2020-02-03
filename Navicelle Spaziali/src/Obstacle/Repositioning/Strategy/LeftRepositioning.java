package Obstacle.Repositioning.Strategy;

import Obstacle.Obstacle;
import Screen.GameScreen;
import Enum.Direction;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Questa classe viene utilizzata per attuare la strategia di
 * riposizionamento a sinistra.
 * @see Repositioning
 */
public class LeftRepositioning  implements Repositioning {

    /**
     * Questa funzione attua la seguente strategia:
     * - Se non ci sono ostacoli alla sinistra, vai a sinistra;
     * - Se ci sono ostacoli alla sinistra, non muoverti;
     * @param hitBox hit box della navicella
     * @return direzione
     */
    @Override
    public Direction repositioningBehaviour(Rectangle hitBox) {
        // rectangleL rappresenta l'area che puo occupare un ostacolo che collide con l'astronave sulla sinistra.
        Rectangle rectangle = getLeftRectangle(hitBox);

        //lista degli ostacoli, che sono diversi dal fuel, che sono contenuti o intersecano il rettangolo.
        List<Obstacle> obstacles = GameScreen.obstacles.stream()
                .filter(obstacle -> !obstacle.getClass().getName().contains("Fuel") &&
                        (obstacle.getHitBox().intersects(rectangle) ||
                                obstacle.getHitBox().contains(rectangle))
                ).collect(Collectors.toList());
        return (obstacles.isEmpty())? Direction.LEFT : Direction.STOP;
    }
}
