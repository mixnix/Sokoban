import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * Created by user_name on 21/05/2017.
 */
public class MenuWindow extends JFrame implements ActionListener, KoniecGryListener{
    /**
     * port na którym łączymy się z serwerem
     */
    private Socket socket;

    /**
     * okresla rozmiar okna menu
     */
    private Dimension menuSize;
    /**
     * przycisk zamykajacy program
     */
    private JButton jbExit;
    /**
     * przycisk rozpoczynajacy nowa gre
     */
    private JButton jbNewGame;
    /**
     * przycisk wyswietlajacy tablice najlepszych wynikow
     */
    private JButton jbHighScores;
    /**
     * przycisk wyswietlajacy pomoc
     */
    private JButton jbHelp;

    /**
     * panel na którym jest narysowane menu
     */
    private JPanel menuPanel;

    private String playerNick;

    private HelpPanel helpPanel;

    private HighScorePanel scorePanel;

    private Board board;

    private JPanel panelZGra;

    private InfoPanel infoPanel;

    /**
     * konstruktor ustawiajacy wszystkie komponenty na swoich miejscach
     * @param socket
     */
    public MenuWindow(Socket socket){
        this.socket = socket;
        menuSize = new Dimension(Constants.menuWidth, Constants.menuHeight);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(menuSize);
        setTitle(Constants.menuTitle);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        jbNewGame = new JButton(Constants.jbNewGameText);
        jbNewGame.setActionCommand("NewGame");
        jbNewGame.addActionListener(this);
        jbNewGame.setFocusable(false);
        jbHighScores = new JButton(Constants.jbHighScoreText);
        jbHighScores.setActionCommand("HighScore");
        jbHighScores.addActionListener(this);
        jbHighScores.setFocusable(false);
        jbHelp = new JButton(Constants.jbHelpText);
        jbHelp.setActionCommand("Help");
        jbHelp.addActionListener(this);
        jbHelp.setFocusable(false);
        jbExit = new JButton(Constants.jbExitText);
        jbExit.setActionCommand("Exit");
        jbExit.addActionListener(this);
        jbExit.setFocusable(false);






        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5,1,20,20));
        menuPanel.add(new JLabel(Constants.gameTitle, SwingConstants.CENTER));
        menuPanel.add(jbNewGame);
        menuPanel.add(jbHighScores);
        menuPanel.add(jbHelp);
        menuPanel.add(jbExit);
        add(menuPanel);





    }

    /**
     * metoda obslugujaca wydarzenia nacisniecia przyciskow
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent){

        String komenda = actionEvent.getActionCommand();
        switch (komenda){

            case "Help":
                helpPanel = new HelpPanel(this);
                this.remove(menuPanel);
                this.add(helpPanel);
                this.revalidate();
                this.repaint();
                break;
            case "Exit":
                try{
                    if(socket != null)
                        socket.close();
                    System.exit(0);
                } catch(IOException e){
                    System.out.println("Bląd podczas wychodzenia z gry");
                    System.err.println(e);
                }
            case "BackFromHelp":
                this.remove(helpPanel);
                helpPanel = null;
                this.add(menuPanel);
                this.revalidate();
                this.repaint();
                break;
            case "HighScore":
                scorePanel = new HighScorePanel(this);
                this.remove(menuPanel);
                this.add(scorePanel);
                this.revalidate();
                this.repaint();
                break;
            case "BackFromScores":
                this.remove(scorePanel);
                scorePanel = null;
                this.add(menuPanel);
                this.revalidate();
                this.repaint();
                break;
            case "NewGame":

                while(playerNick==null || playerNick.length()==0){
                    playerNick= JOptionPane.showInputDialog(this,Constants.playerNameDialogText,Constants.playerNameDialogTitle,JOptionPane.INFORMATION_MESSAGE);
                    if(playerNick==null){
                        break;
                    }
                }

                //tworze panel z gra zawierajacy dwie czesci, menu z pauza igre
                panelZGra = new JPanel();
                panelZGra.setLayout(new BorderLayout());

                board = new Board(this);
                this.addKeyListener(new OuterAdapter());
                this.remove(menuPanel);

                infoPanel = new InfoPanel(this);


                panelZGra.add(board, BorderLayout.CENTER);
                panelZGra.add(infoPanel, BorderLayout.SOUTH);

                //dodaje dwuczesciowy panel do glownego okna i przerysowywuje
                this.add(panelZGra);
                this.revalidate();
                this.repaint();

                break;

            case "Pauza":
                if(infoPanel.zapauzowany== false) infoPanel.zapauzowany=true;
                else infoPanel.zapauzowany=false;
                break;
        }
    }

    /**
     * klasa obslugujaca wyswietlanie panelu z pomocą
     */

    private class HelpPanel extends JPanel{
        private String title;
        private ArrayList<String> rules;

        public HelpPanel(ActionListener listener){
            setLayout(new BorderLayout());
            setPreferredSize(menuSize);
            rules = new ArrayList<>();

            loadHelpFromFile();
            add(createBackButton(listener), BorderLayout.SOUTH);
            add(createHelpLabel(),BorderLayout.CENTER);
            setVisible(true);
        }

        private void loadHelpFromFile(){
            try{
                File xmlInputFile = new File("Config\\help.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlInputFile);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("title");
                title = nList.item(0).getTextContent();
                nList = doc.getElementsByTagName("rules");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        rules.add(eElement.getTextContent());
                    }
                }
            }catch(Exception e){
                System.out.println("Błąd w wewnętrznej klasie MenuWindow->HelpPanel w metodzie loadHelpFromFile"+e);

            }
        }

        private JLabel createHelpLabel(){
            StringBuilder strB = new StringBuilder("<html><h1>");
            strB.append(title + "</h1><br>");
            int counter = 1;
            for (String rule : rules) {
                strB.append(counter + ". " + rule + "<br>");
                counter++;
            }
            strB.append("</htm>");
            String labelText = strB.toString();
            JLabel helpLabel1 = new JLabel(labelText);
            return helpLabel1;
        }

        private JButton createBackButton(ActionListener listener){
            JButton backToMainMenuBtn = new JButton(Constants.backButton);
            System.out.println(Constants.backButton);
            backToMainMenuBtn.setFocusable(false);
            backToMainMenuBtn.addActionListener(listener);
            backToMainMenuBtn.setActionCommand("BackFromHelp");
            return backToMainMenuBtn;
        }
    }

    private class HighScorePanel extends JPanel{
        private class ScorePair{
            private String name;
            private int moves;
            private int time;
             public ScorePair(String name, int moves, int time){
                 this.name=name;
                 this.moves=moves;
                 this.time=time;
             }
             public String getName(){ return  name; }
             public int getMoves() { return  moves; }
             public int getTime() { return time; }
        }

        ArrayList<ScorePair> highscoreList;

        public HighScorePanel(ActionListener listener){
            setLayout(new BorderLayout());
            setPreferredSize(menuSize);
            highscoreList = new ArrayList<>();
            loadHighScoresFromFile();
            add(createHighScoreTable(), BorderLayout.CENTER);
            add(createHighScoreLabel(), BorderLayout.NORTH);
            add(createBackToMenuButton(listener), BorderLayout.SOUTH);
            setVisible(true);

        }

        private void loadHighScoresFromFile() {
            try {

                File xmlInputFile = new File(Constants.xmlHighScoreFile);

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlInputFile);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("ScoreID");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        highscoreList.add(new ScorePair(eElement.getElementsByTagName("name").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("moves").item(0).getTextContent()), Integer.parseInt(eElement.getElementsByTagName("time").item(0).getTextContent())));
                    }
                }
            }  catch (Exception e) {
                System.out.println("Błąd w MenuWindow->HighScorePanel w metodzie loadHighScoresFromFile "+e);
            }
        }

        private JTable createHighScoreTable(){
            sortLists();
            saveHighscoresToFile();
            Vector<Vector> rows = new Vector<>();
            for(int i = 0; i < 10; i++){
                Vector<String> row = new Vector<>();
                String rowNumber = Integer.toString((i+1));
                row.add(rowNumber);
                row.add(highscoreList.get(i).getName());
                row.add(Integer.toString(highscoreList.get(i).getMoves()));
                row.add(Integer.toString(highscoreList.get(i).getTime()));
                rows.add(row);
            }
            Vector<String> columnsLabels = new Vector<>();
            columnsLabels.addElement("_");
            columnsLabels.addElement("nick");
            columnsLabels.addElement("moves");
            columnsLabels.addElement("time");

            JTable highScoreLabel = new JTable(rows, columnsLabels);
            highScoreLabel.setEnabled(false);
            highScoreLabel.getTableHeader().setReorderingAllowed(false);

            return highScoreLabel;
        }

        private JButton createBackToMenuButton(ActionListener menuListener) {
            JButton backToMainMenuBtn = new JButton(Constants.backButtonHighScores);
            backToMainMenuBtn.setFocusable(false);
            backToMainMenuBtn.addActionListener(menuListener);
            backToMainMenuBtn.setActionCommand("BackFromScores");
            return backToMainMenuBtn;
        }

        private JLabel createHighScoreLabel() {
            JLabel highScoreLbl = new JLabel("<html><br>"+"[numer wyniku, nick, ruchy, czas w sekundach]"+"<br><br></html>");
            highScoreLbl.setHorizontalAlignment(JLabel.CENTER);
            highScoreLbl.setVerticalAlignment(JLabel.CENTER);
            highScoreLbl.setFont(new Font("Arial", Font.PLAIN, 17));
            return highScoreLbl;
        }
        private void sortLists() {
            Collections.sort(highscoreList, new Comparator<ScorePair>() {
                @Override
                public int compare(ScorePair pair, ScorePair t1) {
                    if(pair.getMoves()>t1.getMoves()){
                        return 1;
                    }
                    if(pair.getMoves()<t1.getMoves())
                    {
                        return -1;
                    }
                    else
                        return 0;
                }
            });
        }

        private void saveHighscoresToFile(){
            try{
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("highscores");
                doc.appendChild(rootElement);
                for(int i=0;i<10;i++) {
                    if(highscoreList.size()<=i)
                        break;
                    Element ScoreID = doc.createElement("ScoreID");
                    rootElement.appendChild(ScoreID);
                    ScoreID.setAttribute("id", Integer.toString(i));
                    Element name = doc.createElement("name");
                    name.appendChild(doc.createTextNode(highscoreList.get(i).getName()));
                    ScoreID.appendChild(name);
                    Element moves = doc.createElement("moves");
                    moves.appendChild(doc.createTextNode(Integer.toString(highscoreList.get(i).getMoves())));
                    ScoreID.appendChild(moves);
                    Element time = doc.createElement("time");
                    time.appendChild(doc.createTextNode(Integer.toString(highscoreList.get(i).getTime())));
                    ScoreID.appendChild(time);
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("ConfigFiles\\highscores.xml"));
                transformer.transform(source, result);
            }
            catch (Exception e)
            {

            }
        }




    }

    public class OuterAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if(!InfoPanel.zapauzowany)
                board.adapter.keyPressed(e);
        }
    }

//    public static void end(){
//        Object[] options={ "play again","back to main menu", "nie wime co to"};
//        switch(JOptionPane.showOptionDialog(this, "cos", "coś 22",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1])) {
//            case JOptionPane.YES_OPTION:
//                break;
//            case JOptionPane.NO_OPTION:
//                break;
//            case JOptionPane.CANCEL_OPTION:
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void KoniecGry(){

        Object[] options={ "play again","back to main menu"};
        switch(JOptionPane.showOptionDialog(this, "Co chcesz zrobić", "Koniec gry",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1])) {
            case JOptionPane.YES_OPTION:

                break;
            case JOptionPane.NO_OPTION:
                this.remove(panelZGra);
                panelZGra = null;

                //napisz tu zapisywanie do najwyzszych wynikow
                this.add(menuPanel);
                this.revalidate();
                this.repaint();
                break;
                //zapisanie do najwyzszych wynikow

                //sczyszczenie zegara

            default:
                break;
        }
    }
}
