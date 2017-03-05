import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
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

    private final int DISTANCE = 15;


    public Board(){

        //init UI
        setFocusable(true);
        setBackground(Color.blue);
        setPreferredSize(new Dimension(1000,500));

        addKeyListener(new TAdapter());


        loadBoardFromXML();
    }

    private void loadBoardFromXML(){
        int x = 10;
        int y = 10;
        int width = 0;
        try {
            File inputFile = new File("diagram.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("line");
            for(int n = 0; n < nList.getLength(); ++ n){
                Node nNode = nList.item(n);
                NodeList innerList = nNode.getChildNodes();
                for(int j = 0; j < innerList.getLength(); ++j){
                    Node innerNode = innerList.item(j);
                    if(innerNode.getTextContent().equals("wall")){
                        Wall wall = new Wall(x,y);
                        walls.add(wall);
                    } else if(innerNode.getTextContent().equals("empty_field")){
                    } else if(innerNode.getTextContent().equals("player")){
                        player = new Player(x,y);
                    }
                    x+= DISTANCE;
                }
                //next row
                y += DISTANCE;
                if(width < x){
                    width = x;
                }
                x = 10;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(player.getImage(), player.getX(), player.getY(), this);


        for(int i = 0; i < walls.size(); i++){
            Wall item = walls.get(i);

            g.drawImage(item.getImage(), item.getX(), item.getY(), this);
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT){
               if(!isWallCollision(Direction.LEFT))
                   player.x += -DISTANCE;
            }

            if(key == KeyEvent.VK_RIGHT){
                if(!isWallCollision(Direction.RIGHT))
                    player.x += DISTANCE;
            }

            if(key == KeyEvent.VK_UP){
                if(!isWallCollision(Direction.UP))
                    player.y += -DISTANCE;
            }

            if(key == KeyEvent.VK_DOWN){
                if(!isWallCollision(Direction.DOWN))
                    player.y += DISTANCE;
            }
            repaint();
        }
    }

    private boolean isWallCollision(Direction direction){
        switch (direction) {
            case LEFT:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (player.isLeftCollision(wall)) {
                        return true;
                    }
                }
                return false;
            case RIGHT:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (player.isRightCollision(wall)) {
                        return true;
                    }
                }
                return false;
            case UP:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (player.isUpCollision(wall)) {
                        return true;
                    }
                }
                return false;
            case DOWN:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (player.isDownCollision(wall)) {
                        return true;
                    }
                }
                return false;
            default:
                return false;
        }
    }
}
