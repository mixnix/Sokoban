import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by user_name on 24.02.2017.
 */
public class Player extends Props{

    private int dx;
    private int dy;
    //zobacz czy bez konstruktora tez dziala

    public Player(int x, int y){
        super(x, y);

        loadImage("player.png");
        getImageDimension();
    }
}
