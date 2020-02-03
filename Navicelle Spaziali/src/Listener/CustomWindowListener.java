package Listener;

import Screen.GameScreen;
import Screen.OptionScreen;
import Screen.WelcomeScreen;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;


/**
 * Listener utilizzato per passare alla schermata principale quando la
 * schermata di gioco o quella delle opzioni vengono chiuse.
 * @see WindowAdapter
 * @see WelcomeScreen
 * @see GameScreen
 * @see OptionScreen
 */
public class CustomWindowListener extends WindowAdapter {

    /**
     * viene invocata quando una window sta in fase di chiusura.
     * @param e WindowEvent
     */
    @Override
    public void windowClosing(WindowEvent e) {
        WelcomeScreen.getInstance().loadPage();
        if(GameScreen.getInstance().isActive()){
            GameScreen.getInstance().closeWindow();
        } else {
            OptionScreen.getInstance().closeWindow();
        }
    }
}
