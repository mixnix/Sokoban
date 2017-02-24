import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by user_name on 24.02.2017.
 */
public class Player {
    protected int x;
    protected int y;
    private int dx;
    private int dy;
    protected int width;
    protected int height;
    protected boolean visisble;
    protected Image image;
    //zobacz czy bez konstruktora tez dziala
    AlreadyMoved alreadyMoved = new AlreadyMoved();

    public Player(int x, int y){
        this.x = x;
        this.y = y;
        visisble = true;

        loadImage("player.png");
        getImageDimension();
    }

    protected void loadImage(String imageName){
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    protected void getImageDimension(){
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Image getImage() {return image;}

    public int getX(){return x;}
    public int getY(){return y;}

    public boolean isVisible(){return visisble;}

    public void setVisisble(Boolean visible) {this.visisble = visible;}

    //not needed because I won't be using collision detection
    public Rectangle getBounds() { return new Rectangle(x,y,width,height);}

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(!alreadyMoved.left && key == KeyEvent.VK_LEFT){
            x += -15;
            alreadyMoved.left = true;
        }

        if(!alreadyMoved.right && key == KeyEvent.VK_RIGHT){
            x += 15;
            alreadyMoved.right = true;
        }

        if(!alreadyMoved.up && key == KeyEvent.VK_UP){
            y += -15;
            alreadyMoved.up = true;
        }

        if(!alreadyMoved.down && key == KeyEvent.VK_DOWN){
            y += 15;
            alreadyMoved.down = true;
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT){
            alreadyMoved.left = false;
        }

        if(key == KeyEvent.VK_RIGHT){
            alreadyMoved.right = false;
        }

        if(key == KeyEvent.VK_UP){
            alreadyMoved.up = false;
        }

        if(key == KeyEvent.VK_DOWN){
            alreadyMoved.down = false;
        }
    }
}
