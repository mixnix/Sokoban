import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by user_name on 21/05/2017.
 */
public class Constants {
    public static final String xmlConfigFile="Config\\config.xml";
    public static int menuWidth;
    public static int menuHeight;
    public static String menuTitle;
    public static String gameTitle;
    public static String jbNewGameText;
    public static String jbHighScoreText;
    public static String jbHelpText;
    public static String jbExitText;

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


        } catch(FileNotFoundException e){
            System.out.print("Problem w loadConstants"+e);
        } catch (Exception e){
            System.out.println("Problem w loadConstants"+e);
        }

    }
}
