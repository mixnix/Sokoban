import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by user_name on 24.02.2017.
 */
public class Board extends JPanel implements Runnable {

    /**
    * Obiekt przedstawiajacy gracza
     */
    Player player;

    /**
     * Dzieki temu watkowi sa animacje
     */
    private Thread animator;
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
     * stala mówiąca o tym czy jesteśmy w grze
     */
    public boolean ingame = true;


    public TAdapter getAdaper (){
        return new TAdapter();
    }

    /**
    * Konstruktor inicjujacy plansze gry
     */
    public Board(ActionListener mainMenu){



        KlasaInformujaca.iloscRuchowLvl1=0;
        KlasaInformujaca.timeLvl1=0;

        //init additional ingame menu



        //init UI
        setFocusable(true);
        setBackground(Color.blue);
        setPreferredSize(new Dimension(1000,500));

        addKeyListener(new TAdapter());


        loadBoardFromXML();
        setVisible(true);
    }

    /**
     * Laduje level z pliku konfiguracyjnego
     */
    private void loadBoardFromXML(){
        int x = 0;
        int y = 0;
        int width = 0;
        try {
            File inputFile = new File("Config\\level1.xml");
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
        if(ingame){

                int screenWidth = this.getWidth();
                int screenHeight = this.getHeight();


                for(int i = 0; i < end_positions.size(); i++){
                    End_position item = end_positions.get(i);
                    g.setColor(Color.green);
                    g.fillRect(item.getDestinationX()*screenWidth/LEVEL_WIDTH,item.getDestinationY()*screenHeight/LEVEL_HEIGHT,item.getImage().getWidth(null)*screenWidth/LEVEL_WIDTH,item.getImage().getWidth(null)*screenHeight/LEVEL_HEIGHT);
                }

                g.setColor(Color.white);
                g.fillRect(player.getCurrentX()*screenWidth/LEVEL_WIDTH,player.getCurrentY()*screenHeight/LEVEL_HEIGHT,player.getImage().getWidth(null)*screenWidth/LEVEL_WIDTH,player.getImage().getWidth(null)*screenHeight/LEVEL_HEIGHT);


                for(int i = 0; i < boxes.size(); i++){
                    Box item = boxes.get(i);
                    g.setColor(Color.yellow);
                    g.fillRect(item.getCurrentX()*screenWidth/LEVEL_WIDTH,item.getCurrentY()*screenHeight/LEVEL_HEIGHT,item.getImage().getWidth(null)*screenWidth/LEVEL_WIDTH,item.getImage().getWidth(null)*screenHeight/LEVEL_HEIGHT);
                }
                for(int i = 0; i < walls.size(); i++){
                    Wall item = walls.get(i);

                    g.setColor(Color.red);
                    g.fillRect(item.getDestinationX()*screenWidth/LEVEL_WIDTH,item.getDestinationY()*screenHeight/LEVEL_HEIGHT,item.getImage().getWidth(null)*screenWidth/LEVEL_WIDTH,item.getImage().getWidth(null)*screenHeight/LEVEL_HEIGHT);

            }

        }

    }

    /**
     * sprawdza czy wszystko juz zaanimowalo swoj ruch
     * @return
     */
    boolean isAllOnDestination(){

        if(player.getCurrentX()-player.getDestinationX()!=0 || player.getCurrentY()-player.getDestinationY()!=0)
            return false;

        for(int i = 0; i < boxes.size(); i++){
            Box item = boxes.get(i);
            if(item.getCurrentX()-item.getDestinationX()!=0 || item.getCurrentY()-item.getDestinationY()!=0)
                return false;
        }
        return true;
    }

    /**
     * zmienia pozycje wszystkich rzeczy o troche by sprawic wrazenie animacji
     */
    public void moveEverythingALittle(){
        //ponieważ wszystko idealnie jest zsynchronizowane to else nie wykona sie gdy current = getDestination
        if(player.getCurrentX()<player.getDestinationX())
            player.currentX += 1;
        else if(player.getCurrentX()>player.getDestinationX())
            player.currentX -= 1;

        if( player.getCurrentY()<player.getDestinationY())
            player.currentY += 1;
        else if( player.getCurrentY()>player.getDestinationY())
            player.currentY -= 1;

        for(int i = 0; i < boxes.size(); i++){
            Box item = boxes.get(i);
            if(item.getCurrentX()<item.getDestinationX())
                item.currentX += 1;
            else if(item.getCurrentX()>item.getDestinationX())
                item.currentX -= 1;

            if( item.getCurrentY()<item.getDestinationY())
                item.currentY += 1;
            else if ( item.getCurrentY()>item.getDestinationY())
                item.currentY -= 1;
        }
    }

    /**
     * prywatna klasa odpowiedzialna za obslugiwanie zdarzen
     */
    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();


            if(key == KeyEvent.VK_UP){
                if(isPushableBoxCollision(Direction.UP)) {
                    Box box = getBoxToTheDirection(Direction.UP);
                    box.move(Direction.UP);
                    player.destinationY += -DISTANCE;
                }else if(!isNextToWall(Direction.UP) && (getBoxToTheDirection(Direction.UP)==null))
                    player.destinationY += -DISTANCE;
            }

            if(key == KeyEvent.VK_LEFT){
                if(isPushableBoxCollision(Direction.LEFT)) {
                    Box box = getBoxToTheDirection(Direction.LEFT);
                    box.move(Direction.LEFT);
                    player.destinationX += -DISTANCE;
                }else if(!isNextToWall(Direction.LEFT) && (getBoxToTheDirection(Direction.LEFT)==null))
                   player.destinationX += -DISTANCE;

            }

            if(key == KeyEvent.VK_RIGHT){
                if(isPushableBoxCollision(Direction.RIGHT)) {
                    Box box = getBoxToTheDirection(Direction.RIGHT);
                    box.move(Direction.RIGHT);
                    player.destinationX += DISTANCE;
                }else if(!isNextToWall(Direction.RIGHT) && (getBoxToTheDirection(Direction.RIGHT)==null))
                    player.destinationX += DISTANCE;
            }



            if(key == KeyEvent.VK_DOWN){
                if(isPushableBoxCollision(Direction.DOWN)) {
                    Box box = getBoxToTheDirection(Direction.DOWN);
                    box.move(Direction.DOWN);
                    player.destinationY += DISTANCE;
                }else if(!isNextToWall(Direction.DOWN) && (getBoxToTheDirection(Direction.DOWN)==null))
                    player.destinationY += DISTANCE;
            }

            //checkForVictory();
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
     * tworzy watek i go rozpoczyna
     */
    @Override
    public void addNotify(){
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void run(){
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (true){
            if (!ingame) {
                System.exit(0);
                break;
            }
            repaint();
            if(!isAllOnDestination())
                moveEverythingALittle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = 5 - timeDiff;



            if(sleep < 0)
                sleep = 2;

            try{
                Thread.sleep(sleep);
            } catch (InterruptedException e){
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();

            if(checkForVictory())
                ingame = false;
        }
    }

    public boolean checkForVictory(){
        boolean[] winningTable = new boolean[end_positions.size()];
        for(int ij = 0; ij<winningTable.length; ij++)
            winningTable[ij]=false;

        for(int ij = 0; ij<end_positions.size();ij++) {
            for (Box box : boxes) {
                if(box.getCurrentY()==end_positions.get(ij).getCurrentY()&&box.getCurrentX()==end_positions.get(ij).getCurrentX())
                    winningTable[ij]=true;
            }
        }
        for(int ij = 0; ij<winningTable.length; ij++)
            if(winningTable[ij]==false)
                return false;

        return true;

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
                    if(player.getDestinationX()==box.getDestinationX() && player.getDestinationY()+DISTANCE == box.getDestinationY() && hej)
                        return true;
                    break;
                case UP:
                    if(player.getDestinationX()==box.getDestinationX() && player.getDestinationY()-DISTANCE == box.getDestinationY() && isNothingBehind(Direction.UP, box))
                        return true;
                    break;

                case LEFT:
                    if(player.getDestinationY()==box.getDestinationY() && player.getDestinationX()-DISTANCE == box.getDestinationX() && isNothingBehind(Direction.LEFT, box))
                        return true;
                    break;
                case RIGHT:
                    if(player.getDestinationY()==box.getDestinationY() && player.getDestinationX()+DISTANCE == box.getDestinationX() && isNothingBehind(Direction.RIGHT, box))
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
                    if(player.getDestinationX()==box.getDestinationX() && player.getDestinationY()+DISTANCE == box.getDestinationY())
                        return box;
                    break;
                case UP:
                    if(player.getDestinationX()==box.getDestinationX() && player.getDestinationY()-DISTANCE == box.getDestinationY())
                        return box;
                    break;
                case LEFT:
                    if(player.getDestinationY()==box.getDestinationY() && player.getDestinationX()-DISTANCE == box.getDestinationX())
                        return box;
                    break;
                case RIGHT:
                    if(player.getDestinationY()==box.getDestinationY() && player.getDestinationX()+DISTANCE == box.getDestinationX())
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
                    if(temp.getDestinationX()==box.getDestinationX() && box.getDestinationY()+DISTANCE == temp.getDestinationY())
                        return false;
                    break;
                case UP:
                    if(temp.getDestinationX()==box.getDestinationX() && box.getDestinationY()-DISTANCE == temp.getDestinationY())
                        return false;
                    break;
                case LEFT:
                    if(temp.getDestinationY()==box.getDestinationY() && box.getDestinationX()-DISTANCE == temp.getDestinationX())
                        return false;
                    break;
                case RIGHT:
                    if(temp.getDestinationY()==box.getDestinationY() && box.getDestinationX()+DISTANCE == temp.getDestinationX())
                        return false;
                    break;
            }
        }
        for(Wall temp : walls){
            switch (direction){
                case DOWN:
                    if(temp.getDestinationX()==box.getDestinationX() && box.getDestinationY()+DISTANCE == temp.getDestinationY())
                        return false;
                    break;
                case UP:
                    if(temp.getDestinationX()==box.getDestinationX() && box.getDestinationY()-DISTANCE == temp.getDestinationY())
                        return false;
                    break;
                case LEFT:
                    if(temp.getDestinationY()==box.getDestinationY() && box.getDestinationX()-DISTANCE == temp.getDestinationX())
                        return false;
                    break;
                case RIGHT:
                    if(temp.getDestinationY()==box.getDestinationY() && box.getDestinationX()+DISTANCE == temp.getDestinationX())
                        return false;
                    break;
            }
        }
        return true;
    }
}
