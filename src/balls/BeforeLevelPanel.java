package balls;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Matthias Delbar
 */

public class BeforeLevelPanel extends JPanel {

    private BallsPanel bp;
    private int lvlnr;

    public BeforeLevelPanel(int lvlnr) {
        super();

        this.lvlnr = lvlnr;

        if(lvlnr == 0) {
            showStart();
        }
        else if(lvlnr >= 12) {
            showEnd();
        }
        else {
            showBetween();
        }
    }

    private void showStart() {
        setBackground(Game.getRandomBgColor());
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setMinimumSize(new Dimension(Game.WIDTH, Game.HEIGHT));

        // Level number and goal
        int total = Game.levels[lvlnr][0];
        int goal = Game.levels[lvlnr][1];
        JLabel levelGoal = new JLabel("Level " + (lvlnr + 1) + ": Explode " + goal + " out of " + total + " balls.");
        levelGoal.setForeground(Color.WHITE);

        // Play button
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Level(lvlnr).start();
            }
        });

        // Current score
        JLabel currScore = new JLabel("Current score: " + Game.score);
        currScore.setForeground(Color.WHITE);

        // Add components
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(playButton, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(levelGoal)
                    .addComponent(currScore))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(levelGoal)
                .addGap(50, 50, 50)
                .addComponent(playButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(currScore)
                .addContainerGap(75, Short.MAX_VALUE))
        );
    }

    private void showBetween() {
        bp = (BallsPanel) Game.GAMEFRAME.getContentPane();
        setBackground(Game.getRandomBgColor());
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setMinimumSize(new Dimension(Game.WIDTH, Game.HEIGHT));

        int exploded = bp.getNumBallsExploded();
        int required = Game.levels[lvlnr - 1][1];
        boolean success = exploded >= required;

        // Update the game score
        if(success) {
            Game.score += exploded;
        }

        // Congratulations or Failure
        JLabel finishedMessage;
        String conclusion = (success) ? ("Congratulations! You got ") : ("Failure! You only got ");
        Color c = (success) ? (Color.GREEN) : (Color.RED);

        finishedMessage = new JLabel(conclusion + exploded + "/" + required);
        finishedMessage.setForeground(c);

        // Put target (for next or current level, depending on success)
        int total = (success) ? (Game.levels[lvlnr][0]) : (Game.levels[lvlnr-1][0]);
        int goal = (success) ? (Game.levels[lvlnr][1]) : (Game.levels[lvlnr-1][1]);
        int nr = (success) ? (lvlnr + 1) : (lvlnr);
        JLabel levelGoal = new JLabel("Level " + nr + ": Explode " + goal + " out of " + total + " balls.");
        levelGoal.setForeground(Color.WHITE);

        // Next level or Play again button
        JButton button = new JButton();
        button.setText((success) ? ("Next level") : ("Retry level"));
        ActionListener next = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new Level(lvlnr).start();
            }
        };
        ActionListener retry = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new Level(lvlnr - 1).start();
            }
        };
        button.addActionListener((success) ? (next) : (retry));


        // Current score
        JLabel currScore = new JLabel("Current score: " + Game.score);
        currScore.setForeground(Color.WHITE);

        // Add components
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(button, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(finishedMessage)
                    .addComponent(levelGoal)
                    .addComponent(currScore))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(finishedMessage)
                .addGap(50, 50, 50)
                .addComponent(levelGoal)
                .addGap(25, 25, 25)
                .addComponent(button, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(currScore)
                .addContainerGap(75, Short.MAX_VALUE))
        );
    }

    private void showEnd() {
        bp = (BallsPanel) Game.GAMEFRAME.getContentPane();
        setBackground(Game.getRandomBgColor());
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setMinimumSize(new Dimension(Game.WIDTH, Game.HEIGHT));

        int exploded = bp.getNumBallsExploded();
        int required = Game.levels[lvlnr - 1][1];
        boolean success = exploded >= required;

        if(!success) {
            showBetween();
            return;
        }

        // Update the game score
        Game.score += exploded;

        // Congratulations
        JLabel finishedMessage = new JLabel("Congratulations! You completed the game!");
        finishedMessage.setForeground(Color.GREEN);

        // Show total score
        JLabel totalScore = new JLabel("Total score: " + Game.score);
        totalScore.setForeground(Color.WHITE);

        // Play again button
        JButton button = new JButton();
        button.setText("Play again?");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BeforeLevelPanel blp = new BeforeLevelPanel(0);
                Game.GAMEFRAME.setContentPane(blp);
                Game.GAMEFRAME.pack();
            }
        });

        // Add components
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(button, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(finishedMessage)
                    .addComponent(totalScore))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(finishedMessage)
                .addGap(50, 50, 50)
                .addComponent(totalScore)
                .addGap(25, 25, 25)
                .addComponent(button, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );
    }
}
