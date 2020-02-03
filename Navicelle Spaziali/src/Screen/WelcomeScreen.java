package Screen;

import Screen.Factory.GameScreenFactory;
import Screen.Factory.OptionScreenFactory;
import Screen.Factory.WelcomeScreenFactory;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Questa classe che contiene tutte l'informazioni relative alla creazione
 * della schermata principale.
 * @see Screen
 * @see ActionListener
 * @see JFrame
 */
public class WelcomeScreen extends JFrame implements Screen, ActionListener {

    private JLabel myTitle = new JLabel();

    private JPanel settingsPanel = new JPanel();
    private JPanel TitlePanel = new JPanel();

    private JButton startGame = new JButton();
    private JButton options = new JButton();

    private static WelcomeScreen instance;

    public static void main(String[] args) {
        new OptionScreenFactory();
        new WelcomeScreenFactory();
        new GameScreenFactory();
        WelcomeScreen.getInstance().loadPage();
    }

    private WelcomeScreen() {
    }

    public static WelcomeScreen getInstance() {
        if (instance == null) {
            instance = new WelcomeScreen();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.startGame ) {
            this.closeWindow();
            GameScreen.getInstance().loadPage();
        } else if (actionEvent.getSource() == this.options) {
            this.closeWindow();
            OptionScreen.getInstance().loadPage();
        }
    }

    //Questo metodo viene utilizzato per caricare tutti gli elementi nella schermata principale.
    @Override
    public void loadPage() {

        this.add(this.TitlePanel, BorderLayout.NORTH);
        this.add(this.settingsPanel, BorderLayout.CENTER);

        this.TitlePanel.add(this.myTitle, FlowLayout.LEFT);

        this.settingsPanel.add(this.options, FlowLayout.LEFT);
        this.settingsPanel.add(this.startGame, FlowLayout.LEFT);

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

    //Getter
    public JLabel getMyTitle(){ return myTitle; }
    public JButton getStartGame() {
        return startGame;
    }
    public JButton getOptions() {
        return options;
    }
}