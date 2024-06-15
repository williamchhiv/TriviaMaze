package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * RuleUI class represents the user interface for displaying the rules of the game.
 */
public class RuleUI extends JFrame {

    private final JPanel myContainer;

    /**
     * Constructor for the RuleUI class.
     * Initializes the window settings and calls createUI to set up the UI components.
     */
    public RuleUI() {
        super("Rules");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        myContainer = new JPanel();
        this.add(myContainer);

        createUI();
    }

    /**
     * Sets up the user interface components for the rules window.
     */
    private void createUI() {
        myContainer.setPreferredSize(this.getPreferredSize());
        myContainer.setLayout(new BoxLayout(myContainer, BoxLayout.Y_AXIS));

        final JLabel creditsTextLabel = new JLabel();
        creditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        creditsTextLabel.setText("""
                <html>
                <style>
                    body {
                      font-family: Verdana, sans-serif;
                    }
                    .name-list {
                        padding-left: 10px;
                        padding-right: 10px;
                        font-size: 12px;
                    }
                    .equation {
                        padding-left: 10px;
                        padding-right: 10px;
                        font-size: 10px;
                    }
                </style>

                <body>
                    <h2 style="text-align: center;">
                        <u>Information about the Trivia Maze Game!</u>
                    </h2>

                    <div class="game-rules">
                        <h1>Game Information</h1>
                        <p>The Trivia Maze is a 5 x 5 game where the player\s
                        must navigate through entrance to exit.</p>
                    </div>
                <br><br>
                <div class="equation">
                        <h1>Game Rules</h1>
                        <h2>The maze is composed of rooms. Each room has doors connected to the adjacent rooms.\s
                        In order for the user to pass through a door, they must correctly answer a question.\s
                        There are 3 categories of questions asked consisted of Multiple Choice, Short Answer and True/False.\s
                        If the user is unable to answer a question correctly, that door is then locked permanently.\s
                        If you can make it to the end of the maze, at room 25, you win.\s
                        If the user is unable to make it from the entrance to the exit (due to locked doors), the game is lost.</h2>
                        <br>
                </div>
                </body>
                </html>
                """);
        myContainer.add(creditsTextLabel);
    }
}
