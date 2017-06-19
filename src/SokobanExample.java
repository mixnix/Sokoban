import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user_name on 24.02.2017.
 */
public class SokobanExample extends JFrame implements ActionListener {
    /**
     * inicjuje okno
     */
    public SokobanExample(){
        //add(new Board(this));
        setSize(1000,500);

        setTitle("Sokoban");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * tworzy watek z gra i go wywoluje
     * @param args
     */
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SokobanExample ex = new SokobanExample();
                ex.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){

    }
}
