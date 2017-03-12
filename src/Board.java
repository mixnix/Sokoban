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
    ArrayList<Box> boxes = new ArrayList<Box>();
    ArrayList<End_position> end_positions = new ArrayList<End_position>();

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
                    } else if(innerNode.getTextContent().equals("box")){
                        Box box = new Box(x,y);
                        boxes.add(box);
                    } else if(innerNode.getTextContent().equals("end_position")){
                        End_position end_position = new End_position(x,y);
                        end_positions.add(end_position);
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

        for(int i = 0; i < end_positions.size(); i++){
            End_position item = end_positions.get(i);

            g.drawImage(item.getImage(), item.getX(), item.getY(), this);
        }
        for(int i = 0; i < boxes.size(); i++){
            Box item = boxes.get(i);

            g.drawImage(item.getImage(), item.getX(), item.getY(), this);
        }
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
                if(isPushableBoxCollision(Direction.LEFT)) {
                    Box box = getBoxToTheDirection(Direction.LEFT);
                    box.move(Direction.LEFT);
                    player.x += -DISTANCE;
                }else if(!isNotMovable(Direction.LEFT))
                   player.x += -DISTANCE;

            }

            if(key == KeyEvent.VK_RIGHT){
                if(isPushableBoxCollision(Direction.RIGHT)) {
                    Box box = getBoxToTheDirection(Direction.RIGHT);
                    box.move(Direction.RIGHT);
                    player.x += DISTANCE;
                }else if(!isNotMovable(Direction.RIGHT))
                    player.x += DISTANCE;
            }

            if(key == KeyEvent.VK_UP){
                if(isPushableBoxCollision(Direction.UP)) {
                    Box box = getBoxToTheDirection(Direction.UP);
                    box.move(Direction.UP);
                    player.y += -DISTANCE;
                }else if(!isNotMovable(Direction.UP))
                    player.y += -DISTANCE;
            }

            if(key == KeyEvent.VK_DOWN){
                if(isPushableBoxCollision(Direction.DOWN)) {
                    Box box = getBoxToTheDirection(Direction.DOWN);
                    box.move(Direction.DOWN);
                    player.y += DISTANCE;
                }else if(!isNotMovable(Direction.DOWN))
                    player.y += DISTANCE;
            }
            repaint();
        }
    }

    private boolean isNotMovable(Direction direction){
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
    private boolean isPushableBoxCollision(Direction direction){
        //checks if theres is box in called direction and if theres nothing behind it so it can be pushed
        //TODO I could do it more efficiently
        for(Box box : boxes){
            switch (direction){
                case DOWN:
                    boolean hej = isNothingBehind(Direction.DOWN, box);
                    if(player.getX()==box.getX() && player.getY()+DISTANCE == box.getY() && hej)
                        return true;
                case UP:
                    if(player.getX()==box.getX() && player.getY()-DISTANCE == box.getY() && isNothingBehind(Direction.UP, box))
                        return true;
                case LEFT:
                    if(player.getY()==box.getY() && player.getX()-DISTANCE == box.getX() && isNothingBehind(Direction.LEFT, box))
                        return true;
                case RIGHT:
                    if(player.getY()==box.getY() && player.getX()+DISTANCE == box.getX() && isNothingBehind(Direction.RIGHT, box))
                        return true;
            }
        }
        return false;
    }

    private Box getBoxToTheDirection(Direction direction){
        for(Box box : boxes){
            switch (direction){
                case DOWN:
                    if(player.getX()==box.getX() && player.getY()+DISTANCE == box.getY())
                        return box;
                    break;
                case UP:
                    if(player.getX()==box.getX() && player.getY()-DISTANCE == box.getY())
                        return box;
                    break;
                case LEFT:
                    if(player.getY()==box.getY() && player.getX()-DISTANCE == box.getX())
                        return box;
                    break;
                case RIGHT:
                    if(player.getY()==box.getY() && player.getX()+DISTANCE == box.getX())
                        return box;
                    break;
            }
        }
        return null;
    }

    private boolean isNothingBehind(Direction direction, Box box){
        //TODO wall detection not only boxes
        for(Box temp : boxes){
            switch (direction){
                case DOWN:
                    if(temp.getX()==box.getX() && box.getY()+DISTANCE == temp.getY())
                        return false;
                    break;
                case UP:
                    if(temp.getX()==box.getX() && box.getY()-DISTANCE == temp.getY())
                        return false;
                    break;
                case LEFT:
                    if(temp.getY()==box.getY() && box.getX()-DISTANCE == temp.getX())
                        return false;
                    break;
                case RIGHT:
                    if(temp.getY()==box.getY() && box.getX()+DISTANCE == temp.getX())
                        return false;
                    break;
            }
        }
        return true;
    }
}
