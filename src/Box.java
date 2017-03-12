/**
 * Created by user_name on 05/03/2017.
 */
public class Box extends Props {



    public Box(int x, int y){
        super(x, y);
        this.x = x;

        loadImage("box.PNG");
        getImageDimension();
    }

    public void move(Direction direction){
        switch (direction) {
            case LEFT:
                x -= 15;
            case RIGHT:
                x += 15;
            case UP:
                y -= 15;
            case DOWN:
                y += 15;

        }
    }
}

