package Screen.Factory;

import Screen.*;
import DataAccess.*;
import DataAccess.DataHandler.Handler;
import DataAccess.DataHandler.Languages;

import java.awt.*;
import java.io.File;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;


/**
 * Classe utilizzata per la costruzione dell'oggetto WelcomeScreenFactory
 * @see AbstractScreenFactory
 */
public class WelcomeScreenFactory extends AbstractScreenFactory {

    @Override
    protected Screen makeScreen() {
        Handler handler = (Handler)XMLDataAccess.loadData(new File("resurce\\languages.xml"));
        Languages language = handler.getLanguage(currentLanguage);
        WelcomeScreen welcomeScreen = WelcomeScreen.getInstance();

        welcomeScreen.setSize(250,150);
        welcomeScreen.getStartGame().setText(language.getStartButton());
        welcomeScreen.getOptions().setText(language.getOptionButton());
        welcomeScreen.getMyTitle().setText(language.getTitle());
        welcomeScreen.getMyTitle().setFont(new Font("Sans-serif", Font.PLAIN, 22));

        welcomeScreen.getStartGame().addActionListener(welcomeScreen);
        welcomeScreen.getOptions().addActionListener(welcomeScreen);
        welcomeScreen.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        welcomeScreen.centerWindow();

        return welcomeScreen;
    }
}
