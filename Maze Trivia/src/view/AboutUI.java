package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The AboutUI class is the About window in the application.
 *
 */
public class AboutUI extends JFrame {

    private final JPanel myContainer;

    /**
     * Constructs a new AboutUI window.
     * Sets up the window properties and initializes the UI components.
     */
    public AboutUI() {
        super("About");
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
     * Creates and configures the UI components for the AboutUI window.
     * Sets the layout and adds the text content.
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
                        <u>Spring 2024 - Group 12</u>
                    </h2>

                    <div class="name-list">
                        <h1>Made by:</h1>
                        <p>William Chhiv</p>
                        <p>Ashley Timko</p>
                        <p>Bao Trinh</p>
                    </div>
                <br><br>
                <div class="equation">
                        <h1>Trivia Maze</h1>
                        <h2>This group assignment is about making\s
                        a trivia maze using GUI and working from scratch.</h2>
                        <br>
                </div>
                </body>
                </html>
                """);
        myContainer.add(creditsTextLabel);
    }
}
