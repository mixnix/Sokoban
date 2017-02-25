import javax.swing.*;
import java.awt.*;

/**
 * Created by user_name on 25.02.2017.
 */
public class Wall {
    private int x;
    private int y;
    protected int width;
    protected int height;
    protected Image image;

    public Wall(int x, int y){
        this.x = x;
        this.y = y;

        loadImage("wall.png");
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

    public Rectangle getBounds() { return new Rectangle(x,y,width,height);}
}
