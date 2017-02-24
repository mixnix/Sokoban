import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by user_name on 24.02.2017.
 */
public class Board extends JPanel {
    Player player;

    public Board(){

        //init UI
        setFocusable(true);
        setBackground(Color.blue);
        setPreferredSize(new Dimension(1000,500));

        addKeyListener(new TAdapter());

        player = new Player(40,60);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(player.isVisible()){
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e){
            player.keyReleased(e);
        }
        @Override
        public void keyPressed(KeyEvent e){
            player.keyPressed(e);
            repaint();
        }
    }
}
