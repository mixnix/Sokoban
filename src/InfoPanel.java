import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by user_name on 19.06.2017.
 */
public class InfoPanel extends JPanel {
    static int  nCzas = 0;
    public static int nPosuniecia = 0;
    boolean zapauzowany = true;
    static int licznik = 0;

    public static JLabel posuniecia;
    public static JLabel czas;
    public static void uaktualnij(){
        posuniecia.setText(Integer.toString(nPosuniecia));
        licznik+=5;
        nCzas = (int)Math.floor(licznik/1000);
        czas.setText(Integer.toString(nCzas));
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
