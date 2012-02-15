package balls;




import javax.swing.JFrame;

/**
 * @author Matthias Delbar
 */

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame f = new JFrame("Balls");
        f.setResizable(true); // TODO: Make false
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        Game.GAMEFRAME = f;
        f.setContentPane(new BeforeLevelPanel(0));
        f.pack();
//        new Level(1).start();
    }
    
    

}
