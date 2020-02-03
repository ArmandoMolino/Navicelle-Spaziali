package Screen.Factory;

import Screen.*;
import Listener.CustomWindowListener;

/**
 * Classe utilizzata per la costruzione dell'oggetto GameScreen
 * @see AbstractScreenFactory
 */
public class GameScreenFactory extends AbstractScreenFactory {

    @Override
    protected Screen makeScreen() {
        GameScreen gameScreen = GameScreen.getInstance();
        gameScreen.setSize(1200,750);

        gameScreen.centerWindow();
        gameScreen.setResizable(false);
        gameScreen.addWindowListener(new CustomWindowListener());
        return gameScreen;
    }
}
