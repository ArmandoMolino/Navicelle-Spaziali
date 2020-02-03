package DataAccess.DataHandler;


/**
 * Classe contenente le stringhe, in una data lingua, per tutti
 * gli elementi grafici presenti sui vari Screen.
 */
public class Languages {
    String id;

    //Welcome screen element
    private String title;
    private String startButton;
    private String optionButton;

    //Option screen element
    private String languageLabel;
    private String soundLabel;
    private String saveButton;
    private String cancelButton;
    private String yesLabel;
    private String noLabel;


    //Getter e Setter
    public String getTitle() {
        return title;
    }
    void setTitle(String title) {
        this.title = title;
    }

    public String getStartButton() {
        return startButton;
    }
    void setStartButton(String startButton) {
        this.startButton = startButton;
    }

    public String getOptionButton() {
        return optionButton;
    }
    void setOptionButton(String optionButton) {
        this.optionButton = optionButton;
    }

    public String getId() {
        return id;
    }
    void setId(String id) {
        this.id = id;
    }

    public String getLanguageLabel() {
        return languageLabel;
    }
    void setLanguageLabel(String languageLabel) {
        this.languageLabel = languageLabel;
    }

    public String getSoundLabel() {
        return soundLabel;
    }
    void setSoundLabel(String soundLabel) {
        this.soundLabel = soundLabel;
    }

    public String getSaveButton() {
        return saveButton;
    }
    void setSaveButton(String saveButton) {
        this.saveButton = saveButton;
    }

    public String getCancelButton() {
        return cancelButton;
    }
    void setCancelButton(String cancelButton) {
        this.cancelButton = cancelButton;
    }

    public String getYesLabel() {
        return yesLabel;
    }
    void setYesLabel(String yesLabel) {
        this.yesLabel = yesLabel;
    }

    public String getNoLabel() {
        return noLabel;
    }
    void setNoLabel(String noLabel) {
        this.noLabel = noLabel;
    }
}
