package balls;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * @author Matthias Delbar
 */

public class BallsPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private ArrayList<Ball> balls;
    private ArrayList<Ball> normalBalls;
    private ArrayList<Ball> explodingBalls;
    private Color bgcolor;
    private int mouseX, mouseY;
    private boolean clicked = false;
    private boolean end = false;
    private Level lvl;
    private int numBallsExploded = 0;

    
    public BallsPanel(Level lvl) {
        super();
        setMinimumSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setMaximumSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setOpaque(false);

        bgcolor = Game.getRandomBgColor();
        mouseX = mouseY = -1;
        this.lvl = lvl;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void init(int numBalls) {
        balls = new ArrayList<Ball>();
        normalBalls = new ArrayList<Ball>();
        explodingBalls = new ArrayList<Ball>();
        
        for(int i = 0; i < numBalls; i++) {
            Ball b = new Ball();
            balls.add(b);
            normalBalls.add(b);
        }
    }

    public void move() {
        for(Ball b : normalBalls) {
            b.move();
        }
    }

    public void explode() {
        for(int i = 0; i < explodingBalls.size(); i++) {
            Ball b = explodingBalls.get(i);
            b.explode();
            if(b.hasExploded()) {
                explodingBalls.remove(b);
                numBallsExploded++;
                i--;
            }
        }
    }

    private void checkForCollisions() {
        for(int i = 0; i < normalBalls.size(); i++) {
            Ball n = normalBalls.get(i);
            for(int j = 0; j < explodingBalls.size(); j++) {
                Ball e = explodingBalls.get(j);
                int radiusDist = n.getRadius() + e.getRadius();
                int dx = n.getxCoord() - e.getxCoord();
                int dy = n.getyCoord() - e.getyCoord();
                int ballDistSquared = dx * dx + dy * dy;
                if(ballDistSquared <= radiusDist * radiusDist) {
                    normalBalls.remove(n);
                    i--;
                    n.explode();
                    explodingBalls.add(0, n);
                    break;
                }
            }
        }
    }

    private boolean checkForEnd() {
        if(clicked && explodingBalls.isEmpty()) {
            return true;
        }
        return false;
    }

    public void actionPerformed(ActionEvent e) {
        explode();
        if(!checkForEnd()) {
            move();
            checkForCollisions();
            repaint();
        }
        else {
            lvl.stop();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Paint background
        g.setColor(bgcolor);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        // Paint balls
        for(int i = 0; i < balls.size(); i++) {
            drawBall(g, balls.get(i));
        }

        // Paint mouse circle
        drawMouse(g);
    }

    private void drawBall(Graphics g, Ball b) {
        g.setColor(b.getColor());
        g.fillOval(b.getxCoord() - b.getRadius(), b.getyCoord() - b.getRadius(), 2 * b.getRadius() + 1, 2 * b.getRadius() + 1);
    }

    private void drawMouse(Graphics g) {
        if(!clicked && mouseX >= 0 && mouseY >= 0) {
            g.setColor(Color.WHITE);
            g.drawOval(mouseX - Game.MOUSE_RADIUS, mouseY - Game.MOUSE_RADIUS, 2 * Game.MOUSE_RADIUS + 1, 2 * Game.MOUSE_RADIUS + 1);
        }
    }

    
    public void mouseClicked(MouseEvent e) {
        // Do nothing. Trigger on MousePressed
    }

    public void mousePressed(MouseEvent e) {
        if(!clicked) {
            clicked = true;
            Ball b = new Ball();
            b.setColor(Color.WHITE);
            b.setxCoord(e.getX());
            b.setyCoord(e.getY());
            b.explode();

            balls.add(b);
            explodingBalls.add(b);
            numBallsExploded--; // -1 because the click counts as an exploded ball
        }
    }

    public void mouseReleased(MouseEvent e) {
        // Do nothing. Trigger on MousePressed
    }

    public void mouseEntered(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseExited(MouseEvent e) {
        mouseX = -1;
        mouseY = -1;
    }

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public int getNumBallsExploded() {
        return numBallsExploded;
    }
}
