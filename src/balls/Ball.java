package balls;




import java.awt.Color;
import java.util.Random;

/**
 * @author Matthias Delbar
 */

public class Ball {

    private double x, y;
    private int xCoord, yCoord, radius;
    private Color color;
    private Movement movement;
    private Random r = new Random();
    
    private boolean exploding = false;
    private boolean growing = false;
    private boolean shrinking = false;
    private boolean hasExploded = false;
    private int waitCount = 0;

    public Ball() {
        radius = Game.DEFAULT_RADIUS;
        xCoord = r.nextInt(1 + radius + (Game.WIDTH - 2*radius));
        yCoord = r.nextInt(1 + radius + (Game.HEIGHT - 2*radius));
        x = xCoord;
        y = yCoord;
        // Weird color picker so the color won't overlap with the BallsPanel bgcolor (rg.nextInt(50))
        color = new Color(r.nextInt(206) + 50, r.nextInt(206) + 50, r.nextInt(206) + 50);
        movement = new Movement();
    }

    public void move() {
        // Calculate the new X & Y coordinates
        x += movement.x;
        y += movement.y;
        xCoord = (int) Math.round(x);
        yCoord = (int) Math.round(y);

        // Check for horizontal collision
        if(x + radius > Game.WIDTH) {
            x = Game.WIDTH - radius;
            movement.x *= -1;
        }
        else if(x - radius < 0) {
            x = 0 + radius;
            movement.x *= -1;
        }

        // Check for vertical collision
        if(y + radius > Game.HEIGHT) {
            y = Game.HEIGHT - radius;
            movement.y *= -1;
        }
        else if(y - radius < 0) {
            y = 0 + radius;
            movement.y *= -1;
        }
    }

    public void stop() {
        movement.x = 0;
        movement.y = 0;
    }

    public void explode() {
        if(exploding) {
            if(growing) {
                if(radius < Game.EXPLOSION_RADIUS) {
                    radius += 1;
                }
                else if(waitCount < Game.EXPLOSION_HOLD_TIME) {
                    waitCount++;
                }
                else {
                    shrinking = true;
                    growing = false;
                }
            }
            else if(shrinking) {
                if(radius > 0) {
                    radius = Math.max(radius - 5, 0);
                }
                else {
                    exploding = false;
                    growing = false;
                    shrinking = false;
                    hasExploded = true;
                }
            }
        }
        else {
            stop();
            exploding = true;
            growing = true;
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 128);
        }
    }

    public boolean isExploding() {
        return exploding;
    }

    public boolean hasExploded() {
        return hasExploded;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
        this.x = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
        this.y = yCoord;
    }
    
}
