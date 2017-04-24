import javax.swing.*;
import java.awt.*;

/**
 * Created by user_name on 25.02.2017.
 */
public class Props {


    /**
     * obecna pozycja elementu w pionie
     */
    protected int currentX;
    /**
     * obecna pozycja elementu w poziomie
     */
    protected int currentY;

    /**
     * pozycja w poziomie elementu
     */
    protected int destinationX;
    /**
     * pozycja w pionie elementu
     */
    protected int destinationY;
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
     * @param destinationX
     * @param destinationY
     */
    public Props(int destinationX, int destinationY){
       this.destinationX = destinationX;
       this.destinationY = destinationY;
       currentX = destinationX;
       currentY = destinationY;
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
    public int getDestinationX(){return destinationX;}

    /**
     * zwraca pozycje w pionie elementu
     * @return
     */
    public int getDestinationY(){return destinationY;}

    /**
     * zwraca obecna pozycje w poziomie
     * @return
     */
    public int getCurrentX(){return currentX;}

    /**
     * zwraca obecna pozycje w pionie
     * @return
     */
    public int getCurrentY(){return currentY;}

    /**
     * sprawdza czy element ma kolizje z innym elementem w lewo
     * @param a
     * @return
     */
    public boolean isLeftCollision(Props a){
        if(this.getDestinationX()-15==a.getDestinationX() && this.getDestinationY()  == a.getDestinationY())
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
        if(this.getDestinationX()+15==a.getDestinationX() && this.getDestinationY() == a.getDestinationY())
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
        if(this.getDestinationY()-15==a.getDestinationY() && this.getDestinationX() == a.getDestinationX())
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
        if(this.getDestinationY()+15==a.getDestinationY() && this.getDestinationX() == a.getDestinationX())
            return true;
        else
            return false;
    }
}
