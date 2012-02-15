package balls;




import java.util.Random;

/**
 * @author Matthias Delbar
 */

public class Movement {

    public double x, y;

    public Movement(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Movement() {
        Random r = new Random();
        // Horizontal movement
        x = (r.nextBoolean()) ? (1.0) : (-1.0);
        x *= r.nextDouble() * Game.MAXSPEED + 0.1;

        // Vertical movement
        y = (r.nextBoolean()) ? (1.0) : (-1.0);
        y *= r.nextDouble() * Game.MAXSPEED + 0.1;
    }
}
