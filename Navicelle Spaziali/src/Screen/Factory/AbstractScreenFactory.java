package Screen.Factory;

import Screen.Screen;


/**
 * @see Screen
 */
public abstract class AbstractScreenFactory {

    //Variabili utili per settare la lingua ed il suono nei vari Screen
    public static String currentLanguage = "ita";
    public static boolean sound = true;

    AbstractScreenFactory(){
        makeScreen();
    }

    //Factory method
    abstract protected Screen makeScreen();
}


