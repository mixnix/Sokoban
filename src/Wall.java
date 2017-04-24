/**
 * Created by user_name on 25.02.2017.
 */
public class Wall extends Props {


    /**
     * konsturktor, od bazowego rozni sie tylko tym, ze laduje obrazek
     * @param x
     * @param y
     */
    public Wall(int x, int y){
        super(x, y);
        //TODO delete line under this line probably useless
        this.destinationX = x;

        loadImage("wall.png");
        getImageDimension();
    }
}
