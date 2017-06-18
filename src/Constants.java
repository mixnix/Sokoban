import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by user_name on 21/05/2017.
 */
public class Constants {
    /**
     * plik zawierajacy wszystkie zmienne
     */
    public static final String xmlConfigFile="Config\\config.xml";
    /**
     * szerokość głównego menu
     */
    public static int menuWidth;
    /**
     * wysokość głównego menu
     */
    public static int menuHeight;
    /**
     * tytuł menu
     */
    public static String menuTitle;
    /**
     * tytuł gry
     */
    public static String gameTitle;
    /**
     * tekst przycisku nowej gry
     */
    public static String jbNewGameText;
    /**
     * tekst przycisku najwyższych wyników
     */
    public static String jbHighScoreText;
    /**
     * tekst przycisku pomocy
     */
    public static String jbHelpText;
    /**
     * tekst przycisku wyjscia
     */
    public static String jbExitText;

    public static String backButton;

    public static String xmlHighScoreFile;

    /**
     * laduje wszystkie zmienne z pliku
     */
    public static void loadConstants(){
        try {
            File xmlInputFile = new File(xmlConfigFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInputFile);
            doc.getDocumentElement().normalize();

            menuWidth = Integer.parseInt(doc.getElementsByTagName("menuWidth").item(0).getTextContent());
            menuHeight = Integer.parseInt(doc.getElementsByTagName("menuHeight").item(0).getTextContent());
            menuTitle = doc.getElementsByTagName("menuTitle").item(0).getTextContent();
            gameTitle = doc.getElementsByTagName("gameTitle").item(0).getTextContent();
            jbNewGameText = doc.getElementsByTagName("jbNewGameText").item(0).getTextContent();
            jbHighScoreText = doc.getElementsByTagName("jbHighScoreText").item(0).getTextContent();
            jbHelpText = doc.getElementsByTagName("jbHelpText").item(0).getTextContent();
            jbExitText = doc.getElementsByTagName("jbExitText").item(0).getTextContent();
            backButton = doc.getElementsByTagName("backButton").item(0).getTextContent();
            xmlHighScoreFile = doc.getElementsByTagName("xmlHighScoreFile").item(0).getTextContent();

        } catch(FileNotFoundException e){
            System.out.print("Problem w loadConstants w klasie Constants "+e);
        } catch (Exception e){
            System.out.println("Problem w loadConstants w klasie Constants "+e);
        }

    }
}
