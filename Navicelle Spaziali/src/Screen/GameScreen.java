package Screen;

import Obstacle.*;
import TimedTasks.*;
import Enum.Sound;
import Screen.Factory.AbstractScreenFactory;
import Obstacle.Repositioning.RepositioningActuator;


import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.Clip;
import java.util.stream.Collectors;


/**
 * Questa classe che contiene tutte l'informazioni relative alla creazione
 * della schermata e all'esecuzione del gioco.
 * @see JFrame
 * @see Screen
 * @see GameDrawingPanel
 * @see UtilityDrawingPanel
 */
public class GameScreen extends JFrame implements Screen {

    private static Timer timer;
    private static GameScreen instance;

    public static final int screenWidthForObstacle = 1110;

    //Vettore contenente tutti gli ostacoli che sono presenti nella schermata di gioco
    public static Vector<Obstacle> obstacles = new Vector<>();
    public static SpaceShip spaceShip;


    private GameScreen() { }

    public static GameScreen getInstance() {
        if (instance == null) {
            instance = new GameScreen();
            return instance;
        } else {
            return instance;
        }
    }

    /**
     * Questo metodo viene utilizzato per caricare tutti gli elementi nella schermata di gioco
     * e schedula le varie azioni, che dovranno essere eseguite ogni tot millisecondi, utili per
     * l'esecuzione del gioco.
     */
    @Override
    public void loadPage() {
        GameDrawingPanel gamePanel = new GameDrawingPanel();
        UtilityDrawingPanel utilityPanel = new UtilityDrawingPanel();
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(utilityPanel, BorderLayout.EAST);


        int screenWidthForObstacle = 1110;
        spaceShip = new SpaceShip(
                Obstacle.getPolyXArray( ((screenWidthForObstacle / 2)), SpaceShip.getXArray()),
                Obstacle.getPolyYArray( GameScreen.getInstance().getSize().height - 60, SpaceShip.getYArray()),
                SpaceShip.getXArray().length);
        timer = new Timer();

        //avvia la musica del gioco
        if( AbstractScreenFactory.sound )
            Sound.TITLE_MUSIC.playSound(Clip.LOOP_CONTINUOUSLY);


        //Diminuisce il fuel ogni secondo
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                GameScreen.spaceShip.decreseFuel();
                if(GameScreen.spaceShip.getActualFuel() == 0){
                    GameScreen.getInstance().gameOver();
                }
            }
        }, 0L, 1000);

        //Muove gli ostacoli ogni 7 millisecondi
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    for (Obstacle obstacle : GameScreen.obstacles) {
                        obstacle.move();
                    }
                } catch (ConcurrentModificationException | IndexOutOfBoundsException  ignored){ }
            }
        }, 0L, 7L);

        //Genera nuovi Obstacle ogni 70 millisecondi
        timer.scheduleAtFixedRate(new GenerateNewObstacleTask(), 0L, 70L);

        //Controlla le collisioni ogni millisecondo
        timer.scheduleAtFixedRate(new CheckCollisionTask(), 0L, 1L);

        //Compie il Repaint della Gameboard ogni 8 millisecondi
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GameScreen.getInstance().repaint();
            }
        }, 0L, 8L);

        //Muove l'astronave ogni 7 millisecondi dopo 4500 millisecondi
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spaceShip.move(RepositioningActuator.doRepositioning().repositioningBehaviour(spaceShip.getHitBox()));
            }
        }, 4500L, 7L);

        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Chiude la schermata di gioco, rimuove tutti gli ostacoli ed arresta tutte
     * le varie azioni schedulate e le clip audio in esecuzione.
     */
    @Override
    public void closeWindow() {
        this.dispose();
        timer.cancel();
        obstacles.clear();
        Sound.TITLE_MUSIC.stopSound();
    }

    //Centra la schermata di gioco.
    @Override
    public void centerWindow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = this.getSize().width;
        int height = this.getSize().height;
        int x = (dim.width - width) / 2;
        int y = (dim.height - height) / 2;
        this.setLocation(x, y);
    }

    /**
     * Avvia la clip audio del game over, se i suoni sono stati attivati,
     * e aspetta 30000 millisecondi prima di chiudere la schermata di gioco
     */
    public void gameOver(){
        try {
            this.repaint();
            if( AbstractScreenFactory.sound ){
                Sound.TITLE_MUSIC.stopSound();
                Sound.GAME_OVER.playSound(0);
            }
            Thread.sleep(3000);
        } catch (InterruptedException ignored) { }
        GameScreen.getInstance().closeWindow();
        WelcomeScreen.getInstance().loadPage();
    }

}

/**
 * Classe contenente i metodi per la visualizzazione grafica dei vari elementi di gioco.
 * @see JPanel
 */
class GameDrawingPanel extends JPanel {

    @Override
    public void paint(Graphics g) {
        //elimina tutti gli ostacoli che si trovano fuori dallo schermo
        GameScreen.obstacles.removeIf(obstacle -> obstacle.getHitBox().y > 750);


        //Colora il background della schermata di nero
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.black);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        //Disegna i vari ostacolo ostacoli
        try {
            GameScreen.obstacles.forEach(obstacle -> {
                //Se l'ostacolo è un asteroide imposta il colore a grigio
                //Se l'ostacolo è un carburante imposta il colore a giallo
                //Se l'ostacolo è una navicella imposta il colore a blu
                if(obstacle.getClass().getName().contains("Asteroid")) {
                    g2D.setPaint(Color.GRAY);
                } else if(obstacle.getClass().getName().contains("Fuel")){
                    g2D.setPaint(Color.YELLOW);
                } else {
                    g2D.setPaint(Color.BLUE);
                }
                g2D.fillPolygon((Polygon) obstacle);
                g2D.draw((Shape) obstacle);
                //g2D.draw(obstacle.getHitBox());

            });
        } catch (ConcurrentModificationException ignored) {}

        //In base alla vita della nostra navicella imposta il suo colore.
        // - Rosso 3 vite
        // - Magenta 2 vite
        // - Ciano 1 vita
        // - Bianco 0 vite (GameOver)
        switch (GameScreen.spaceShip.getActualHeart()){
            case 3:
                g2D.setPaint(Color.RED);
                break;
            case 2:
                g2D.setPaint(Color.MAGENTA);
                break;
            case 1:
                g2D.setPaint(Color.CYAN);
                break;
            default: g2D.setPaint(Color.WHITE);
        }
        g2D.fillPolygon(GameScreen.spaceShip);
        g2D.draw(GameScreen.spaceShip);
        /*
        g2D.draw(GameScreen.spaceShip.getHitBox());
        g2D.draw(new Rectangle(GameScreen.spaceShip.getHitBox().x - 36,GameScreen.spaceShip.getHitBox().y - 5,GameScreen.spaceShip.getHitBox().width,GameScreen.spaceShip.getHitBox().height + 5));
        g2D.draw(new Rectangle(GameScreen.spaceShip.getHitBox().x + 36,GameScreen.spaceShip.getHitBox().y - 5,GameScreen.spaceShip.getHitBox().width,GameScreen.spaceShip.getHitBox().height + 5));
        g2D.draw(new Rectangle( GameScreen.spaceShip.getHitBox().x,  GameScreen.spaceShip.getHitBox().y - 72, 34, 68));
        */
    }
}

/**
 * Classe contenente i metodi per la visualizzazione grafica dei vari elementi di gioco.
 * @see JPanel
 */
class UtilityDrawingPanel extends JPanel {

    UtilityDrawingPanel() {
        setPreferredSize( new Dimension( 70, getHeight() ) );
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        //Colora il background della schermata di nero
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        //Lista contenente i cuori che rappresentano la vita della nostra navicella
        ArrayList<Heart> hearts = new ArrayList<>();

        //In base alla vita della nostra navicella aggiunge cuori alla lista.
        // - 3 cuori 3 vite
        // - 2 cuori 2 vite
        // - 1 cuore 1 vita
        // - 0 cuori 0 vite
        switch (GameScreen.spaceShip.getActualHeart()){
            case 3:
                hearts.add(new Heart(Obstacle.getPolyXArray(0, Heart.getXArray()), Obstacle.getPolyYArray(100,Heart.getYArray() ), Heart.getXArray().length));
            case 2:
                hearts.add(new Heart(Obstacle.getPolyXArray(0, Heart.getXArray()), Obstacle.getPolyYArray(75,Heart.getYArray() ), Heart.getXArray().length));
            case 1:
                hearts.add(new Heart(Obstacle.getPolyXArray(0, Heart.getXArray()), Obstacle.getPolyYArray(50,Heart.getYArray() ), Heart.getXArray().length));
                break;
            default:
        }
        //crea i rettangoli utilizzati per visualizzare la quantità di carburante della navicella
        Rectangle fuelRectangle =  new Rectangle( getWidth() - 15, getHeight()/6, 5, 502);
        Rectangle ActualFuelRectangle =  new Rectangle( getWidth() - 14, getHeight()/6 + 1 + (GameScreen.spaceShip.getFuelCapacity() - GameScreen.spaceShip.getActualFuel()) * 2, 3, GameScreen.spaceShip.getActualFuel() * 2);

        //rettangoli utilizzati per visualizzare la quantità i vari checkpoint di carburante della navicella.
        Rectangle fuel75 =  new Rectangle( getWidth() - 15, getHeight()/6, 4, 502 - 376); //75%
        Rectangle fuel50 =  new Rectangle( getWidth() - 15, getHeight()/6, 4, 502 - 251); //50%
        Rectangle fuel25 =  new Rectangle( getWidth() - 15, getHeight()/6, 4, 502 - 125); //25%

        //Disegna i vari elementi
        g2D.setPaint(Color.WHITE);
        g2D.fill(fuelRectangle);

        g2D.setPaint(Color.RED);
        g2D.fill(ActualFuelRectangle);

        g2D.setPaint(Color.WHITE);
        g2D.draw(fuel25);
        g2D.draw(fuel50);
        g2D.draw(fuel75);

        g2D.setPaint(Color.RED);
        if(!hearts.isEmpty()){
            for(Heart heart : hearts){
                g2D.draw(heart);
                g2D.fillPolygon(heart);
            }
        }
    }
}