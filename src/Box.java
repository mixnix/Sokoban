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
        this.destinationX = x;

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
                destinationX -= 15;
                break;
            case RIGHT:
                destinationX += 15;
                break;
            case UP:
                destinationY -= 15;
                break;

            case DOWN:
                destinationY += 15;
                break;


        }
    }
}

