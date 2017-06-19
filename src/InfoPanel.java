import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by user_name on 19.06.2017.
 */
public class InfoPanel extends JPanel {
    /**
     * czas który upłynął w grze w sekundach
     */
    static int  nCzas = 0;
    /**
     * liczba ruchów które wykonał gracz
     */
    public static int nPosuniecia = 0;
    /**
     * czy gra jest zapauzowana czy nie
     */
    static boolean zapauzowany = true;
    /**
     * czas w milisekundach
     */
    static int licznik = 0;

    /**
     * etykieta wyswietlająca liczbę posunięć
     */
    public static JLabel posuniecia;
    /**
     * etykieta wyświetlająca czas w sekundach
     */
    public static JLabel czas;

    /**
     * uaktualnia etykiety
     */
    public static void uaktualnij(){
        posuniecia.setText(Integer.toString(nPosuniecia));
        if(!zapauzowany)
            licznik+=5;
        nCzas = (int)Math.floor(licznik/1000);
        czas.setText(Integer.toString(nCzas));
    }

    /**
     * inicjuje statyczne zmienne
     * @param glowneMenu
     */
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
