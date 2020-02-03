package Screen;

import DataAccess.DataHandler.*;
import DataAccess.XMLDataAccess;
import Screen.Factory.AbstractScreenFactory;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Questa classe che contiene tutte l'informazioni relative alla creazione
 * della schermata delle opzioni.
 * @see Screen
 * @see ActionListener
 * @see JFrame
 */
public class OptionScreen extends JFrame implements Screen, ActionListener {

    private JLabel sound = new JLabel();
    private JLabel language = new JLabel();

    private JRadioButton soundON = new JRadioButton();
    private JRadioButton soundOFF = new JRadioButton();
    private ButtonGroup radioButtonGroup = new ButtonGroup();

    private JComboBox<String> languagesList = new JComboBox<>();

    private JPanel soundPanel = new JPanel();
    private JPanel languagePanel = new JPanel();
    private JPanel save_cancelPanel = new JPanel();

    private JButton save = new JButton();
    private JButton cancel = new JButton();

    private static OptionScreen instance;

    private OptionScreen() {}

    public static OptionScreen getInstance() {
        if (instance == null) {
            instance = new OptionScreen();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == save) {
            AbstractScreenFactory.currentLanguage = (String)languagesList.getSelectedItem();
            AbstractScreenFactory.sound = soundON.isSelected();
            this.reloadWithNewOption();
            WelcomeScreen.getInstance().loadPage();
            this.closeWindow();
        } else if (actionEvent.getSource() == cancel) {
            WelcomeScreen.getInstance().loadPage();
            this.closeWindow();
        }
    }

    //Questo metodo viene utilizzato per caricare tutti gli elementi nella schermata delle opzioni.
    @Override
    public void loadPage() {
        this.add(this.save_cancelPanel, BorderLayout.SOUTH);
        this.add(this.soundPanel, BorderLayout.NORTH);
        this.add(this.languagePanel, BorderLayout.CENTER);

        this.radioButtonGroup.add(this.soundOFF);
        this.radioButtonGroup.add(this.soundON);

        this.soundPanel.add(this.soundON, FlowLayout.LEFT);
        this.soundPanel.add(this.soundOFF, FlowLayout.LEFT);
        this.soundPanel.add(this.sound, FlowLayout.LEFT);

        this.languagePanel.add(this.languagesList, FlowLayout.LEFT);
        this.languagePanel.add(this.language, FlowLayout.LEFT);

        this.save_cancelPanel.add(this.save, FlowLayout.LEFT);
        this.save_cancelPanel.add(this.cancel, FlowLayout.LEFT);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    //Chiude la schermata
    @Override
    public void closeWindow() {
        this.dispose();
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
     * ricarica le informazioni nella schermata principale e nella schermati delle opzioni
     * con le nuove opzioni, cioè cambia la lingua del testo degli elementi grafici.
     */
    private void reloadWithNewOption(){
        Handler handler = (Handler)XMLDataAccess.loadData(new File("resurce\\languages.xml"));
        Languages language = handler.getLanguage(AbstractScreenFactory.currentLanguage);

        WelcomeScreen welcomeScreen = WelcomeScreen.getInstance();
        welcomeScreen.setSize(250,150);
        welcomeScreen.getStartGame().setText(language.getStartButton());
        welcomeScreen.getOptions().setText(language.getOptionButton());
        welcomeScreen.getMyTitle().setText(language.getTitle());
        welcomeScreen.getMyTitle().setFont(new Font("Sans-serif", Font.PLAIN, 22));

        this.getSoundOFF().setText(language.getNoLabel());
        this.getSoundON().setText(language.getYesLabel());
        this.getLanguage().setText(language.getLanguageLabel());
        this.getSound().setText(language.getSoundLabel());
        this.getSave().setText(language.getSaveButton());
        this.getCancel().setText(language.getCancelButton());

    }

    //Getter
    public JLabel getSound() {
        return sound;
    }
    public JRadioButton getSoundON() {
        return soundON;
    }
    public JRadioButton getSoundOFF() {
        return soundOFF;
    }
    public JLabel getLanguage() {
        return language;
    }
    public JComboBox<String> getLanguagesList() {
        return languagesList;
    }
    public JButton getSave() {
        return save;
    }
    public JButton getCancel() {
        return cancel;
    }
}