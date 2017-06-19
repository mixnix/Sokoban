import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by user_name on 19.06.2017.
 */
public class InfoPanel extends JPanel {
    int nCzas = 0;
    public static int nPosuniecia = 0;
    boolean zapauzowany = true;

    public static JLabel posuniecia;
    public JLabel czas;
    public static void uaktualnij(){
        posuniecia.setText(Integer.toString(nPosuniecia));
    }

    public InfoPanel(ActionListener glowneMenu){
        setLayout(new GridLayout(1,3));

        JButton pauza = new JButton("pauza");
        pauza.setFocusable(false);
        pauza.addActionListener(glowneMenu);
        pauza.setActionCommand("Pauza");

        czas = new JLabel(Integer.toString(nCzas));
        posuniecia = new JLabel(Integer.toString(nPosuniecia));

        add(pauza);
        add(czas);
        add(posuniecia);



    }
}
