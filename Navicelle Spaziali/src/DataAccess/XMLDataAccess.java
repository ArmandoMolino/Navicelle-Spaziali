package DataAccess;

import org.xml.sax.SAXException;
import DataAccess.DataHandler.Handler;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;


public class XMLDataAccess implements DataAccess {

    public static Object loadData(File f){
        SAXParserFactory spf = SAXParserFactory.newInstance();
        Handler handler = new Handler();
        try {
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(f ,handler);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return handler;
    }

}

