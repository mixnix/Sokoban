import javax.swing.*;
import java.awt.*;

/**
 * Created by user_name on 25.02.2017.
 */
public class Props {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image image;

    public Props(int x, int y){
       this.x = x;
       this.y = y;
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


    public boolean isLeftCollision(Props a){
        if(this.getX()-15==a.getX() && this.getY()  == a.getY())
            return true;
        else
            return false;
    }

    public boolean isRightCollision(Props a){
        if(this.getX()+15==a.getX() && this.getY() == a.getY())
            return true;
        else
            return false;
    }

    public boolean isUpCollision(Props a){
        if(this.getY()-15==a.getY() && this.getX() == a.getX())
            return true;
        else
            return false;
    }

    public boolean isDownCollision(Props a){
        if(this.getY()+15==a.getY() && this.getX() == a.getX())
            return true;
        else
            return false;
    }
}
