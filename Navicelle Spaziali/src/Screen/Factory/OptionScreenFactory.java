package Screen.Factory;

import Screen.*;
import DataAccess.*;
import Listener.CustomWindowListener;
import DataAccess.DataHandler.Handler;
import DataAccess.DataHandler.Languages;

import java.io.File;
import java.util.List;

/**
 * Classe utilizzata per la costruzione dell'oggetto OptionScreenFactory
 * @see AbstractScreenFactory
 */
public class OptionScreenFactory extends AbstractScreenFactory {

    @Override
    protected Screen makeScreen() {
        Handler handler = (Handler)XMLDataAccess.loadData(new File("resurce\\languages.xml"));
        Languages language = handler.getLanguage(currentLanguage);

        OptionScreen optionScreen = OptionScreen.getInstance();
        optionScreen.setSize(200,150);
        List<Languages> languagesList = handler.getLanguagesList();

        optionScreen.getSoundON().setSelected(true);

        optionScreen.getSoundOFF().setText(language.getNoLabel());
        optionScreen.getSoundON().setText(language.getYesLabel());

        optionScreen.getLanguage().setText(language.getLanguageLabel());
        optionScreen.getSound().setText(language.getSoundLabel());

        optionScreen.getSave().setText(language.getSaveButton());
        optionScreen.getCancel().setText(language.getCancelButton());

        for ( Languages langs : languagesList ){
            optionScreen.getLanguagesList().addItem(langs.getId());
        }

        optionScreen.getSave().addActionListener(optionScreen);
        optionScreen.getCancel().addActionListener(optionScreen);
        optionScreen.addWindowListener(new CustomWindowListener());

        optionScreen.centerWindow();

        return optionScreen;
    }
}
