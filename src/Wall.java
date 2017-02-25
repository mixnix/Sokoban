import javax.swing.*;
import java.awt.*;

/**
 * Created by user_name on 25.02.2017.
 */
public class Wall extends Props {



    public Wall(int x, int y){
        super(x, y);
        this.x = x;

        loadImage("wall.png");
        getImageDimension();
    }
}
