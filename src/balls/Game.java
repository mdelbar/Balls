package balls;




import java.awt.Color;
import java.util.Random;
import javax.swing.JFrame;

/**
 * @author Matthias Delbar
 */

public class Game {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int MAXSPEED = 2;
    public static final int DEFAULT_RADIUS = 7;
    public static final int EXPLOSION_RADIUS = 37;
    public static final int MOUSE_RADIUS = 5;
    public static final int FPS = 60;
    public static final int EXPLOSION_HOLD_TIME = 60;

    public static Level currentLevel;
    public static int score = 0;
    public static Random r = new Random();

    // Format: {numballs, target}
    public static final int[][] levels = {
        {5,1},
        {10,2},
        {15,3},
        {20,5},
        {25,7},
        {30,10},
        {35,15},
        {40,21},
        {45,27},
        {50,33},
        {55,44},
        {60,55}
    };

    public static JFrame GAMEFRAME = null;


    public static Color getRandomBgColor() {
        return new Color(r.nextInt(51), r.nextInt(51), r.nextInt(51));
    }
}
