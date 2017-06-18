import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by user_name on 21/05/2017.
 */
public class MenuWindow extends JFrame implements ActionListener{
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
    //oddzielna klasa odpowiedzialna za highscore
    //oddzielna klasa odpowiedzialna za pomoc


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
        getContentPane().add(menuPanel);

    }

    /**
     * metoda obslugujaca wydarzenia nacisniecia przyciskow
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        String komenda = actionEvent.getActionCommand();
        switch (komenda){
            case "NewGame":
                this.setVisible(false);
                SokobanExample.main(new String[0]);
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
        }
    }

    /**
     * klasa obslugujaca wyswietlanie panelu z pomocą
     */
    private class HelpPanel extends JPanel{
        private String title;

        private ArrayList<String> zasadyArray;

        public HelpPanel(ActionListener menuListener, Socket socket){
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(Constants.menuWidth, Constants.menuHeight));
            zasadyArray = new ArrayList<String>();
        }
    }
}
