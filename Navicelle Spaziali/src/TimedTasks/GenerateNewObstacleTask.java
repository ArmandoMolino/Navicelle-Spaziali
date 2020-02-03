package TimedTasks;

import Obstacle.Fuel;
import Obstacle.Asteroid;
import Obstacle.Obstacle;
import Screen.GameScreen;
import Obstacle.EnemySpaceShip;
import Obstacle.ObstacleAdapter;

import java.util.TimerTask;
import java.util.ConcurrentModificationException;

/**
 * Classe utilizzata per generare nuovi ostacoli.
 * @see TimerTask
 */
public class GenerateNewObstacleTask extends TimerTask {

    @Override
    public void run() {
        ObstacleAdapter newObstacle;
        boolean intersect = false;
        int randomStartXPos = 36 * (int)(Math.random() * 31);
        int randomObstacle = (int)(Math.random() * 1000);

        //Probabilita:
        // - Carburante = 1.50%;
        // - Navicella nemica = 48.50%;
        // - Asteroide = 50%;
        if ( randomObstacle < 15) {
            newObstacle = new Fuel(Obstacle.getPolyXArray(randomStartXPos, Fuel.getXArray()), Obstacle.getPolyYArray(-50, Fuel.getYArray()), Fuel.getXArray().length);
        } else if (randomObstacle < 50) {
            newObstacle = new EnemySpaceShip(Obstacle.getPolyXArray(randomStartXPos + 16, EnemySpaceShip.getXArray()), Obstacle.getPolyYArray(-50, EnemySpaceShip.getYArray()), EnemySpaceShip.getXArray().length);
        } else {
            newObstacle = new Asteroid(Obstacle.getPolyXArray(randomStartXPos, Asteroid.getXArray()), Obstacle.getPolyYArray(-50, Asteroid.getYArray()), Asteroid.getXArray().length);
        }
        try {
            //Se l'ostacolo si interseca o è contenuto nel hitbox di un altro ostacolo esso non viene aggiunto
            for (Obstacle obst : GameScreen.obstacles) {
                if( newObstacle.intersects(obst.getHitBox()) || newObstacle.contains(obst.getHitBox()) ){
                    intersect = true;
                    break;
                }
            }
        } catch (ConcurrentModificationException ignored) { }
        if(!intersect){
            GameScreen.obstacles.add(newObstacle);
        }
    }
}
