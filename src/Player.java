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

}
