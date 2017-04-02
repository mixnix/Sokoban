import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by user_name on 24.02.2017.
 */
public class Player extends Props{

    /**
     * konstruktor klasy dziedziczacej rozni sie tylko tym, ze laduje obrazek
     * @param x
     * @param y
     */
    public Player(int x, int y){
        super(x, y);

        loadImage("player.png");
        getImageDimension();
    }
}
