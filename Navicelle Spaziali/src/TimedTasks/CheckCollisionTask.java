package TimedTasks;

import Obstacle.Obstacle;
import Screen.GameScreen;
import Enum.Sound;
import Screen.Factory.AbstractScreenFactory;


import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.ConcurrentModificationException;

/**
 * Classe utilizzata per il controllo delle collisioni.
 * @see TimerTask
 */
public class CheckCollisionTask extends TimerTask {

    @Override
    public void run() {
        try {
            //crae una lista contenente tutti gli ostacoli che intersecano o sono contenuti
            //dal hitbox della navicella
            List<Obstacle> obstacles = GameScreen.obstacles.stream()
                    .filter(obstacle ->     GameScreen.spaceShip.getHitBox().intersects(obstacle.getHitBox()) ||
                                            GameScreen.spaceShip.getHitBox().contains(obstacle.getHitBox())
                    ).collect(Collectors.toList());


            obstacles.forEach(obstacle -> {

                if(!obstacle.getClass().getName().contains("Fuel")){
                    //Se l'ostacolo esaminato non è un carburante
                    if( AbstractScreenFactory.sound ){ //Avvia la clip dell'esplosione se i suoni sono attivi
                        Sound.EXPLOSION.playSound(0);
                    }
                    //Diminuisce la vita della navicella e se essa è inferiore ad 1 GameOver
                    GameScreen.spaceShip.setActualHeart(GameScreen.spaceShip.getActualHeart() - 1);
                    if(GameScreen.spaceShip.getActualHeart() < 1){
                        GameScreen.getInstance().gameOver();
                    }
                } else {
                    //Se l'ostacolo esaminato è un carburante
                    if( AbstractScreenFactory.sound ){ //Avvia la clip audio corrispettiva se i suoni sono attivi
                        Sound.PICKUP_FUEL.playSound(0);
                    }
                    GameScreen.spaceShip.reFull(); //Ricarica il carburante della navicella
                }
                //rimuove l'ostacolo esaminato
                GameScreen.obstacles.remove(obstacle);
            });

        } catch (ConcurrentModificationException | IndexOutOfBoundsException | NullPointerException ignored){ }

    }
}
