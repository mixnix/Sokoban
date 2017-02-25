import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by user_name on 24.02.2017.
 */
public class Board extends JPanel {
    Player player;

    ArrayList<Wall> walls = new ArrayList<Wall>();

    private String level = "##########\n" +
            "#        #\n" +
            "#        #\n" +
            "#        #\n" +
            "#        #\n" +
            "#        #\n" +
            "#        #\n" +
            "#        #\n" +
            "#        #\n" +
            "##########";

    public Board(){

        //init UI
        setFocusable(true);
        setBackground(Color.blue);
        setPreferredSize(new Dimension(1000,500));

        addKeyListener(new TAdapter());

        player = new Player(40,60);

        int x = 10;
        int y = 10;
        int height = 0;
        int width = 0;
        int DISTANCE = 15;
        for(int i = 0; i < level.length(); i++){
            char item = level.charAt(i);

            if(item == '\n'){
                y += DISTANCE;
                if(width < x){
                    width = x;
                }
                x = 10;
            } else if(item == '#'){
                Wall wall = new Wall(x,y);
                walls.add(wall);
                x += DISTANCE;
            } else if(item == ' '){
                x+= DISTANCE;
            }
            height = y;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(player.isVisible()){
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        for(int i = 0; i < walls.size(); i++){
            Wall item = walls.get(i);

            g.drawImage(item.getImage(), item.getX(), item.getY(), this);
        }
    }

    private class TAdapter extends KeyAdapter {

        //TODO x and y should be private
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT){
                player.x += -15;
            }

            if(key == KeyEvent.VK_RIGHT){
                player.x += 15;
            }

            if(key == KeyEvent.VK_UP){
                player.y += -15;
            }

            if(key == KeyEvent.VK_DOWN){
                player.y += 15;
            }
            repaint();
        }


    }
}
