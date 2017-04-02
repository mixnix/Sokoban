/**
 * Created by user_name on 05/03/2017.
 */
public class End_position extends Props {


    /**
     * konstruktor klasy dziedziczacej rozni sie tylko tym, ze laduje obrazek
     * @param x
     * @param y
     */
    public End_position(int x, int y){
        super(x, y);
        this.x = x;

        loadImage("end_position.PNG");
        getImageDimension();
    }
}

