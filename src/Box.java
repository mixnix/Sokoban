/**
 * Created by user_name on 05/03/2017.
 */
public class Box extends Props {


    /**
     * konstruktor klasy dziedziczacej rozni sie tylko tym, ze laduje obrazek
     * @param x
     * @param y
     */
    public Box(int x, int y){
        super(x, y);
        this.x = x;

        loadImage("box.PNG");
        getImageDimension();
    }

    /**
     * metoda zmieniajaca pozycje skrzynki, "porzuszajaca" nia
     * @param direction
     */
    public void move(Direction direction){
        switch (direction) {
            case LEFT:
                x -= 15;
                break;
            case RIGHT:
                x += 15;
                break;
            case UP:
                y -= 15;
                break;

            case DOWN:
                y += 15;
                break;


        }
    }
}

