import javax.swing.*;
import java.awt.*;

/**
 * Created by user_name on 24.02.2017.
 */
public class SokobanExample extends JFrame {
    /**
     * inicjuje okno
     */
    public SokobanExample(){
        add(new Board());
        setSize(1000,500);

        setTitle("Sokoban");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * tworzy watek z gra i go wywoluje
     * @param args
     */
    public static void glownaMetoda(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SokobanExample ex = new SokobanExample();
                ex.setVisible(true);
            }
        });
    }
}
