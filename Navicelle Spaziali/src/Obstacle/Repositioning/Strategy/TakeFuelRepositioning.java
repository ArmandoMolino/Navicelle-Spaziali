package Obstacle.Repositioning.Strategy;

import Obstacle.Obstacle;
import Screen.GameScreen;
import Enum.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.Line2D;
import java.util.stream.Collectors;

/**
 * Questa classe viene utilizzata per attuare la strategia di
 * riposizionamento per effettuare il rifornimento di carburante.
 * @see Repositioning
 */
public class TakeFuelRepositioning  implements Repositioning {

    /**
     * Questa metodo attua la seguente strategia:
     * - Se nella schermata di gioco c'è del fuel, allora la navicella tende a
     *   muoversi verso il fuel la cui distanza, tra la linea (che parte dalla hit box
     *   della navicella e finisce alla coordinata y=0) ed il punto (x,y) della hitbox
     *   del fuel stesso, è minima.
     * @param hitBox hit box della navicella
     * @return direzione
     */
    @Override
    public Direction repositioningBehaviour(Rectangle hitBox) {
        //ArrayList contenete gli ostacoli di tipo fuel
        ArrayList<Obstacle> fuels = new ArrayList<Obstacle>(GameScreen.obstacles.stream()
                .filter(obstacle -> obstacle.getClass().getName().contains("Fuel"))
                .collect(Collectors.toList()));
        //linea che parte dalla hit box della navicella e finisce alla coordinata y=0
        Line2D line2D = new Line2D.Double(hitBox.x, hitBox.y, hitBox.x, 0);


        Obstacle closeFuelOnXAxis = fuels.get(0);
        double minDist = Double.MAX_VALUE;

        for(Obstacle fuel : fuels){
            double temp = line2D.ptLineDist(fuel.getHitBox().x, 0);
            if ( minDist > temp ){
                minDist = temp;
                closeFuelOnXAxis = fuel;
            }
        }
        //se il fuel si trova sulla stessa posizione x della navicella
        //essa non cambia direzione.
        if( closeFuelOnXAxis.getHitBox().x == hitBox.x )
            return Direction.STOP;
        //se la posizione x del fuel è maggiore della posizione x allora il fuel si trova a destra se no si trova a sinistra
        return ( closeFuelOnXAxis.getHitBox().x > hitBox.x )? new RightRepositioning().repositioningBehaviour(hitBox) : new LeftRepositioning().repositioningBehaviour(hitBox);
    }
}