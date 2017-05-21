import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

/**
 * Created by user_name on 21/05/2017.
 */
public class MenuWindow extends JFrame implements ActionListener{
    private Socket socket;
    private boolean isConnected;
    private Dimension menuSize;
    private JButton jbExit;
    private JButton jbNewGame;
    private JButton jbHighScores;
    private JButton jbHelp;

    private JPanel menuPanel;
    //oddzielna klasa odpowiedzialna za highscore
    //oddzielna klasa odpowiedzialna za pomoc




    public MenuWindow(Socket socket){
        this.socket = socket;
        isConnected = socket == null ? false : true;
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

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        String komenda = actionEvent.getActionCommand();
        switch (komenda){
            case "NewGame":
                this.setVisible(false);
                SokobanExample.main(new String[0]);
        }
    }
}