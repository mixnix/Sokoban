import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by user_name on 24.02.2017.
 */
public class Board extends JPanel {

    /**
    * Obiekt przedstawiajacy gracza
     */
    Player player;

    /**
    * Lista ścian
     */
    ArrayList<Wall> walls = new ArrayList<Wall>();
    /**
     * Lista skrzynek
     */
    ArrayList<Box> boxes = new ArrayList<Box>();
    /**
     *lista pozycji koncowych
     */
    ArrayList<End_position> end_positions = new ArrayList<End_position>();
    /**
     * stala dystansu pomiedzy poszczegolnymi obiektami na planszy
     */
    private final int DISTANCE = 15;
    /**
     * stala oznaczajaca szerokosc poziomu
     */
    public int LEVEL_WIDTH = 0;
    /**
     * stala oznaczajaca wysokosc poziomu
     */
    public int LEVEL_HEIGHT = 0;


    /**
    * Konstruktor inicjujacy plansze gry
     */
    public Board(){

        //init UI
        setFocusable(true);
        setBackground(Color.blue);
        setPreferredSize(new Dimension(1000,500));

        addKeyListener(new TAdapter());


        loadBoardFromXML();
    }

    /**
     * Laduje level z pliku konfiguracyjnego
     */
    private void loadBoardFromXML(){
        int x = 0;
        int y = 0;
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
                if(LEVEL_WIDTH < x) LEVEL_WIDTH = x;
                x = 0;
            }
            LEVEL_HEIGHT = y;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Metoda rysujaca level i ponownie rysujaca po kazdej zmianie
     * @param g kontekst graficzny
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int screenWidth = this.getWidth();
        int screenHeight = this.getHeight();


        for(int i = 0; i < end_positions.size(); i++){
            End_position item = end_positions.get(i);
            g.setColor(Color.green);
            g.fillRect(item.getX()*screenWidth/LEVEL_WIDTH,item.getY()*screenHeight/LEVEL_HEIGHT,item.getImage().getWidth(null)*screenWidth/LEVEL_WIDTH,item.getImage().getWidth(null)*screenHeight/LEVEL_HEIGHT);
        }

        g.setColor(Color.white);
        g.fillRect(player.getX()*screenWidth/LEVEL_WIDTH,player.getY()*screenHeight/LEVEL_HEIGHT,player.getImage().getWidth(null)*screenWidth/LEVEL_WIDTH,player.getImage().getWidth(null)*screenHeight/LEVEL_HEIGHT);


        for(int i = 0; i < boxes.size(); i++){
            Box item = boxes.get(i);
            g.setColor(Color.yellow);
            g.fillRect(item.getX()*screenWidth/LEVEL_WIDTH,item.getY()*screenHeight/LEVEL_HEIGHT,item.getImage().getWidth(null)*screenWidth/LEVEL_WIDTH,item.getImage().getWidth(null)*screenHeight/LEVEL_HEIGHT);
        }
        for(int i = 0; i < walls.size(); i++){
            Wall item = walls.get(i);

            g.setColor(Color.red);
            g.fillRect(item.getX()*screenWidth/LEVEL_WIDTH,item.getY()*screenHeight/LEVEL_HEIGHT,item.getImage().getWidth(null)*screenWidth/LEVEL_WIDTH,item.getImage().getWidth(null)*screenHeight/LEVEL_HEIGHT);
        }
    }

    /**
     * prywatna klasa odpowiedzialna za obslugiwanie zdarzen
     */
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();


            if(key == KeyEvent.VK_UP){
                if(isPushableBoxCollision(Direction.UP)) {
                    Box box = getBoxToTheDirection(Direction.UP);
                    box.move(Direction.UP);
                    player.y += -DISTANCE;
                }else if(!isNextToWall(Direction.UP))
                    player.y += -DISTANCE;
            }

            if(key == KeyEvent.VK_LEFT){
                if(isPushableBoxCollision(Direction.LEFT)) {
                    Box box = getBoxToTheDirection(Direction.LEFT);
                    box.move(Direction.LEFT);
                    player.x += -DISTANCE;
                }else if(!isNextToWall(Direction.LEFT))
                   player.x += -DISTANCE;

            }

            if(key == KeyEvent.VK_RIGHT){
                if(isPushableBoxCollision(Direction.RIGHT)) {
                    Box box = getBoxToTheDirection(Direction.RIGHT);
                    box.move(Direction.RIGHT);
                    player.x += DISTANCE;
                }else if(!isNextToWall(Direction.RIGHT))
                    player.x += DISTANCE;
            }



            if(key == KeyEvent.VK_DOWN){
                if(isPushableBoxCollision(Direction.DOWN)) {
                    Box box = getBoxToTheDirection(Direction.DOWN);
                    box.move(Direction.DOWN);
                    player.y += DISTANCE;
                }else if(!isNextToWall(Direction.DOWN))
                    player.y += DISTANCE;
            }
            repaint();
        }
    }

    /**
     * kmetoda sprawdza czy gracz jest przy scianie
     * @param direction kierunek w kierunku ktorego sprawdzamy czy gracz jest przy scianie np Direction.UP czy gracz styka sie gornie ze sciana
     * @return zwraca true lub false w zaleznosci czy gracz jest przy scianei czy nie
     */
    private boolean isNextToWall(Direction direction){
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

    /**
     * Czy jest skrzynka w danym kierunku ktora da sie popchnac
     * @param direction kierunek w ktorym sprawdzamy czy ejst skrzynka
     * @return true - jest i da sie popchnac false - nie ma i nie da sie popchnac
     */
    private boolean isPushableBoxCollision(Direction direction){
        //checks if theres is box in called direction and if theres nothing behind it so it can be pushed
        //TODO I could do it more efficiently
        for(Box box : boxes){
            switch (direction){
                case DOWN:
                    boolean hej = isNothingBehind(Direction.DOWN, box);
                    if(player.getX()==box.getX() && player.getY()+DISTANCE == box.getY() && hej)
                        return true;
                    break;
                case UP:
                    if(player.getX()==box.getX() && player.getY()-DISTANCE == box.getY() && isNothingBehind(Direction.UP, box))
                        return true;
                    break;

                case LEFT:
                    if(player.getY()==box.getY() && player.getX()-DISTANCE == box.getX() && isNothingBehind(Direction.LEFT, box))
                        return true;
                    break;
                case RIGHT:
                    if(player.getY()==box.getY() && player.getX()+DISTANCE == box.getX() && isNothingBehind(Direction.RIGHT, box))
                        return true;
                    break;

            }
        }
        return false;
    }

    /**
     * zwraca skrzynke w danym kierunku
     * @param direction
     * @return
     */
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

    /**
     * czy za skrzynka jest cos jeszcze
     * @param direction
     * @param box
     * @return
     */
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
