package DataAccess.DataHandler;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.ArrayList;


/**
 * Questa classe è un handler
 * @see  DefaultHandler
 */
public class Handler extends DefaultHandler {

    private List<Languages> languagesList = null;
    private Languages language = null;
    private StringBuilder data = null;

    //valori booleani per i campi di Languages
    private boolean bTitle;
    private boolean bStartButton;
    private boolean bOptionButton;
    private boolean bLanguageLabel;
    private boolean bSoundLabel;
    private boolean bSaveButton;
    private boolean bCancelButton;
    private boolean bYes;
    private boolean bNo;

    public List<Languages> getLanguagesList() {
        return languagesList;
    }


    /**
     * Ritorna l'elemento Languages nella lista languagesList avente lo stesso id di input
     * @param key id della lingua selezionata (Es. 'ita' per la lingua italiana)
     * @return l'elemento Languages corrispondente alla lingua selezionata se è stata trovata
     */
    public Languages getLanguage(String key) {
        for(Languages lang : languagesList){
            if( lang.id.equals(key)) {
                return  lang;
            }
        }
        return null;
    }


    /**
     * Fonte: https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html#endElement(java.lang.String,%20java.lang.String,%20java.lang.String)
     * Receive notification of the start of an element.
     * By default, do nothing.
     * Application writers may override this method in a subclass to take specific actions at the start of each element
     * (such as allocating a new tree node or writing output to a file).
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
     * @param localName The qualified name (with prefix), or the empty string if qualified names are not available.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
     * @param attributes The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("Language")) {
            // prende l'id dei vari oggetti presenti nel file languages.xml
            String id = attributes.getValue("id");

            // crea l'oggetto di tipo Languages e gli setta l'id
            language = new Languages();
            language.setId(id);

            // inizializza la lista
            if (languagesList == null)
                languagesList = new ArrayList<>();
            // setta i valori booleani per i campi,
            // questi valori saranno usati per settare le variabili
            // dell'oggetto Languages
        } else if (qName.equalsIgnoreCase("title")) {
            bTitle = true;
        } else if (qName.equalsIgnoreCase("startButton")) {
            bStartButton = true;
        } else if (qName.equalsIgnoreCase("optionButton")) {
            bOptionButton = true;
        } else if (qName.equalsIgnoreCase("languageLabel")) {
            bLanguageLabel = true;
        } else if (qName.equalsIgnoreCase("soundLabel")) {
            bSoundLabel = true;
        } else if (qName.equalsIgnoreCase("saveButton")) {
            bSaveButton = true;
        } else if (qName.equalsIgnoreCase("cancelButton")) {
            bCancelButton = true;
        } else if (qName.equalsIgnoreCase("yes")) {
            bYes = true;
        } else if (qName.equalsIgnoreCase("no")) {
            bNo = true;
        }
        // crea il contenitore dei dati
        data = new StringBuilder();
    }


    /**
     * Fonte: https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html#endElement(java.lang.String,%20java.lang.String,%20java.lang.String)
     * Receive notification of the end of an element.
     * By default, do nothing.
     * Application writers may override this method in a subclass to take specific actions at the end of each element
     * (such as finalising a tree node or writing output to a file).
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed
     * @param localName The local name (without prefix), or the empty string if Namespace processing is not being performed
     * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (bTitle) {
            //aggiunge il titolo
            language.setTitle(data.toString());
            bTitle = false;

        } else if (bStartButton) {
            //aggiunge il testo del StartButton
            language.setStartButton(data.toString());
            bStartButton = false;

        } else if (bOptionButton) {
            //aggiunge il testo del OptionButton
            language.setOptionButton(data.toString());
            bOptionButton = false;

        } else if (bLanguageLabel) {
            //aggiunge il testo del LanguageLabel
            language.setLanguageLabel(data.toString());
            bLanguageLabel = false;

        } else if (bSoundLabel) {
            //aggiunge il testo del SoundLabel
            language.setSoundLabel(data.toString());
            bSoundLabel = false;

        } else if (bSaveButton) {
            //aggiunge il testo del SaveButton
            language.setSaveButton(data.toString());
            bSaveButton = false;

        } else if (bCancelButton) {
            //aggiunge il testo del CancelButton
            language.setCancelButton(data.toString());
            bCancelButton = false;

        } else if (bYes) {
            //aggiunge il testo del YesLabel
            language.setYesLabel(data.toString());
            bYes = false;

        } else if (bNo) {
            //aggiunge il testo del NoLabel
            language.setNoLabel(data.toString());
            bYes = false;
        }
        if (qName.equalsIgnoreCase("Language")) {
            languagesList.add(language);
        }
    }

    /**
     * Fonte: https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html#endElement(java.lang.String,%20java.lang.String,%20java.lang.String)
     * Receive notification of character data inside an element.
     * By default, do nothing. Application writers may override this method to take specific actions
     * for each chunk of character data (such as adding the data to a node or buffer, or printing it to a file).
     * @param ch The characters.
     * @param start The start position in the character array.
     * @param length The number of characters to use from the character array.
     */
    @Override
    public void characters(char[] ch, int start, int length){
        data.append(new String(ch, start, length));
    }

}
