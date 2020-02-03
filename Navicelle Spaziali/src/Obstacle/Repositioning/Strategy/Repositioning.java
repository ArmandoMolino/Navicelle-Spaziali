package Obstacle.Repositioning.Strategy;

import Enum.Direction;

import java.awt.*;

public interface Repositioning {

    Direction repositioningBehaviour(Rectangle hitBox);

    /**
     * Crea la hit box dell'astronave traslata di una posizione a destra.
     * @param hitBox hit box dell'astronave
     * @return hit box dell'astronave traslata di una posizione a destra
     */
    default Rectangle getRightRectangle(Rectangle hitBox){
        Rectangle rectangle = new Rectangle(hitBox);
        rectangle.height += 5;
        rectangle.y -= 5;
        rectangle.x += 36;
        return rectangle;
    }

    /**
     * Crea la hit box dell'astronave traslata di una posizione a sinistra.
     * @param hitBox hit box della astronave
     * @return hit box della astronave traslata di una posizione a sinistra
     */
    default Rectangle getLeftRectangle(Rectangle hitBox){
        Rectangle rectangle = new Rectangle(hitBox);
        rectangle.height += 5;
        rectangle.y -= 5;
        rectangle.x -= 36;
        return rectangle;
    }
}
