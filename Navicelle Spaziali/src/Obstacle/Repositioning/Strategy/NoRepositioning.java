package Obstacle.Repositioning.Strategy;

import Enum.Direction;

import java.awt.*;

/**
 * Questa classe viene utilizzata per attuare la strategia di
 * non riposizionamento.
 * @see Repositioning
 */

public class NoRepositioning implements Repositioning {

    @Override
    public Direction repositioningBehaviour(Rectangle hitBox) {
        return Direction.STOP;
    }
}