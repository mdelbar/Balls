package balls;




import javax.swing.Timer;

/**
 * @author Matthias Delbar
 */

public class Level {

    private int lvlnr;
    private Timer timer;
    private int delay = 1000 / Game.FPS;

    public Level(int levelNumber) {
        this.lvlnr = levelNumber;
        
        timer = new Timer(delay, null);
    }

    public void start() {
        BallsPanel bp = new BallsPanel(this);
        bp.init(Game.levels[lvlnr][0]);
        Game.GAMEFRAME.setContentPane(bp);
        Game.GAMEFRAME.pack();
        timer = new Timer(delay, bp);
        timer.start();
    }

    public void stop() {
        timer.stop();
        BeforeLevelPanel blp = new BeforeLevelPanel(lvlnr + 1);
        Game.GAMEFRAME.setContentPane(blp);
        Game.GAMEFRAME.pack();
    }
}
