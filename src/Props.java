import javax.swing.*;
import java.awt.*;

/**
 * Created by user_name on 25.02.2017.
 */
public class Props {

    /**
     * pozycja w poziomie elementu
     */
    protected int x;
    /**
     * pozycja w pionie elementu
     */
    protected int y;
    /**
     * szerokosc elementu
     */
    protected int width;
    /**
     * dlugosc elementu
     */
    protected int height;
    /**
     * obrazek elementu
     */
    protected Image image;

    /**
     * konsturktor inicjujacy pozycje w pionie i w poziomie
     * @param x
     * @param y
     */
    public Props(int x, int y){
       this.x = x;
       this.y = y;
    }

    /**
     * laduje obrazek elementu
     * @param imageName
     */
    protected void loadImage(String imageName){
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    /**
     * laduje rozmiary elementu z obrazka
     */
    protected void getImageDimension(){
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    /**
     * zwraca obrazek
     * @return
     */
    public Image getImage() {return image;}

    /**
     * zwraca pozycje w poziomie elementu
     * @return
     */
    public int getX(){return x;}

    /**
     * zwraca pozycje w pionie elementu
     * @return
     */
    public int getY(){return y;}


    /**
     * sprawdza czy element ma kolizje z innym elementem w lewo
     * @param a
     * @return
     */
    public boolean isLeftCollision(Props a){
        if(this.getX()-15==a.getX() && this.getY()  == a.getY())
            return true;
        else
            return false;
    }
    /**
     * sprawdza czy element ma kolizje z innym elementem w prawo
     * @param a
     * @return
     */
    public boolean isRightCollision(Props a){
        if(this.getX()+15==a.getX() && this.getY() == a.getY())
            return true;
        else
            return false;
    }
    /**
     * sprawdza czy element ma kolizje z innym elementem w gore
     * @param a
     * @return
     */
    public boolean isUpCollision(Props a){
        if(this.getY()-15==a.getY() && this.getX() == a.getX())
            return true;
        else
            return false;
    }
    /**
     * sprawdza czy element ma kolizje z innym elementem w dol
     * @param a
     * @return
     */
    public boolean isDownCollision(Props a){
        if(this.getY()+15==a.getY() && this.getX() == a.getX())
            return true;
        else
            return false;
    }
}
