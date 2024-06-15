package view;

import model.Maze;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * RoomUI class represents the user interface for the rooms in the Trivia Maze game.
 * It handles displaying the current room, the question, and the navigation buttons.
 */
public class RoomUI extends JPanel {
    private final JLabel myRoomNumberLabel;
    private final JLabel myQuestionLabel;
    private final JButton myButton1;
    private final JButton myButton2;
    private final JButton myButton3;
    private final JButton myButton4;
    private final JTextField myShortAnswerField;
    private final Maze myMaze;
    private final JButton myUpButton;
    private final JButton myDownButton;
    private final JButton myLeftButton;
    private final JButton myRightButton;
    private final GameWindow myGameWindow;

    private String myPendingDirection = null;

    /**
     * Constructor for the RoomUI class.
     *
     * @param theMaze       The maze object.
     * @param theGameWindow The game window object.
     */
    public RoomUI(final Maze theMaze, final GameWindow theGameWindow) {
        this.myMaze = theMaze;
        this.myGameWindow = theGameWindow;

        // Initialize components with smaller font
        final Font largeFont = new Font("Arial", Font.BOLD, 24);
        final Font gridFont = new Font("Arial", Font.BOLD, 24);
        final Font buttonFont = new Font("Arial", Font.BOLD, 14);

        // Custom Colors
        final Color backgroundColor = new Color(60, 63, 65);
        final Color textColor = new Color(187, 187, 187);
        final Color buttonColor = new Color(75, 110, 175);
        final Color buttonTextColor = new Color(255, 255, 255);

        // Set background color
        setBackground(backgroundColor);

        myRoomNumberLabel = new JLabel();
        myRoomNumberLabel.setFont(largeFont);
        myRoomNumberLabel.setForeground(textColor);
        updateRoomNumber();

        myQuestionLabel = new JLabel();
        myQuestionLabel.setFont(largeFont);
        myQuestionLabel.setForeground(textColor);

        final JPanel gridPanel = new JPanel(new GridLayout(5, 5, 3, 3));
        gridPanel.setPreferredSize(new Dimension(500, 500));
        gridPanel.setMinimumSize(new Dimension(300, 300));

        for (int i = 0; i < 25; i++) {
            final JPanel roomPanel = new JPanel(new BorderLayout());
            roomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            roomPanel.setBackground(Color.WHITE);
            roomPanel.setPreferredSize(new Dimension(60, 60));
            gridPanel.add(roomPanel);

            final JLabel numberLabel = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            numberLabel.setFont(gridFont);
            roomPanel.add(numberLabel, BorderLayout.CENTER);
        }

        // Arrow Buttons Panel
        final JPanel arrowPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        final Dimension arrowButtonSize = new Dimension(60, 60);

        myUpButton = createArrowButton("↑", "UP", arrowButtonSize);
        myDownButton = createArrowButton("↓", "DOWN", arrowButtonSize);
        myLeftButton = createArrowButton("←", "LEFT", arrowButtonSize);
        myRightButton = createArrowButton("→", "RIGHT", arrowButtonSize);

        gbc.gridx = 1;
        gbc.gridy = 0;
        arrowPanel.add(myUpButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        arrowPanel.add(myLeftButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        arrowPanel.add(myRightButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        arrowPanel.add(myDownButton, gbc);

        // Create buttons explicitly
        myButton1 = new JButton("");
        myButton2 = new JButton("");
        myButton3 = new JButton("");
        myButton4 = new JButton("");

        // Add action listeners for multiple choice buttons
        myButton1.addActionListener(e -> handleAnswer(myButton1.getText()));
        myButton2.addActionListener(e -> handleAnswer(myButton2.getText()));
        myButton3.addActionListener(e -> handleAnswer(myButton3.getText()));
        myButton4.addActionListener(e -> handleAnswer(myButton4.getText()));

        // Set button properties
        final JButton[] buttons = {myButton1, myButton2, myButton3, myButton4};
        for (final JButton button : buttons) {
            button.setFont(buttonFont);
            button.setBackground(buttonColor);
            button.setForeground(buttonTextColor);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            button.setFocusPainted(false); // Disable the focus border
            final Dimension buttonSize = new Dimension(200, 50);
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
        }

        myShortAnswerField = new JTextField();
        myShortAnswerField.setFont(buttonFont);
        myShortAnswerField.setPreferredSize(new Dimension(300, 30));
        myShortAnswerField.setMinimumSize(new Dimension(300, 30));
        myShortAnswerField.setVisible(false); // Initially hidden
        myShortAnswerField.addActionListener(e -> shortAnswerHandle(myShortAnswerField.getText()));

        // Set up GroupLayout
        final GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        // Create horizontal group
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(myRoomNumberLabel))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(myQuestionLabel))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(gridPanel)
                                .addGap(10)
                                .addComponent(arrowPanel))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(myButton1)
                                .addGap(5)
                                .addComponent(myButton2)
                                .addGap(10)
                                .addComponent(myButton3)
                                .addGap(5)
                                .addComponent(myButton4))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(myShortAnswerField)
                                .addGap(10))
        );

        // Create vertical group
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(myRoomNumberLabel)
                        .addGap(10)
                        .addComponent(myQuestionLabel)
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gridPanel)
                                .addComponent(arrowPanel))
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(myButton1)
                                .addComponent(myButton2)
                                .addComponent(myButton3)
                                .addComponent(myButton4))
                        .addGap(10)
                        .addComponent(myShortAnswerField)
                        .addGap(10)
        );

        updateNavigationButtons();
    }

    /**
     * Creates an arrow button for navigating the maze.
     *
     * @param theText      The text to display on the button.
     * @param theDirection The direction the button represents.
     * @param theSize      The size of the button.
     * @return The created JButton.
     */
    private JButton createArrowButton(final String theText, final String theDirection, final Dimension theSize) {
        final JButton button = new JButton(theText);
        button.setPreferredSize(theSize);
        button.setMinimumSize(theSize);
        button.setMaximumSize(theSize);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.addActionListener(e -> attemptMove(theDirection));
        return button;
    }

    /**
     * Handles the event when a short answer is submitted.
     *
     * @param theText The text of the answer submitted.
     */
    private void shortAnswerHandle(final String theText) {
        final Question currentQuestion = myMaze.getCurrentQuestion();
        if (currentQuestion != null) {
            final boolean isCorrect = currentQuestion.getAnswer().equalsIgnoreCase(theText);
            if (isCorrect) {
                JOptionPane.showMessageDialog(this, "Correct! You have moved to the next room.");
                clearQuestionUI();
                movePendingDirection();
                updateRoomNumber();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect! The door is now locked.");
                myMaze.lockCurrentDoor(Arrays.toString(myMaze.getDirectionFromRoomNumber(myMaze.getTargetRoomNumber(myPendingDirection))));
                clearQuestionUI();
            }
            updateNavigationButtons();
            checkGameState();
        } else {
            JOptionPane.showMessageDialog(this, "No active question.");
        }
    }

    /**
     * Attempts to move in the specified direction.
     *
     * @param theDirection The direction to move.
     */
    private void attemptMove(final String theDirection) {
        this.myPendingDirection = theDirection;
        final int targetRoomNumber = myMaze.getTargetRoomNumber(theDirection);
        if (myMaze.attemptMove(targetRoomNumber)) {
            movePendingDirection();
            updateRoomNumber();
            updateNavigationButtons();
        } else if (myMaze.isDirectionBlocked(theDirection)) {
            JOptionPane.showMessageDialog(this, "This door is now locked.");
            checkGameState();
        } else {
            final Question currentQuestion = myMaze.getCurrentQuestion();
            if (currentQuestion != null) {
                final String questionType = currentQuestion.getType().name();
                final String questionText = currentQuestion.getQuestion();
                final String[] choices = currentQuestion.getChoices();
                updateQuestionUI(questionType, questionText, choices);
                setArrowButtonsEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "No active question.");
            }
        }
    }

    /**
     * Handles the event when an answer button is pressed.
     *
     * @param theAnswer The answer text.
     */
    private void handleAnswer(final String theAnswer) {
        final Question currentQuestion = myMaze.getCurrentQuestion();
        if (currentQuestion != null) {
            final boolean isCorrect = currentQuestion.getAnswer().equalsIgnoreCase(theAnswer);
            if (isCorrect) {
                JOptionPane.showMessageDialog(this, "Correct! You have moved to the next room.");
                clearQuestionUI();
                movePendingDirection();
                updateRoomNumber();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect! The door is now locked.");
                myMaze.lockCurrentDoor(myPendingDirection);
                clearQuestionUI();
            }
            updateNavigationButtons();
            checkGameState();
        } else {
            JOptionPane.showMessageDialog(this, "No active question.");
        }
    }

    /**
     * Moves to the pending direction if set.
     */
    private void movePendingDirection() {
        if (myPendingDirection != null) {
            final int targetRoomNumber = myMaze.getTargetRoomNumber(myPendingDirection);
            myMaze.move(targetRoomNumber, true);
            myPendingDirection = null;
        }
        setArrowButtonsEnabled(true);
    }

    /**
     * Clears the question UI elements.
     */
    private void clearQuestionUI() {
        myQuestionLabel.setText("");
        myButton1.setVisible(false);
        myButton2.setVisible(false);
        myButton3.setVisible(false);
        myButton4.setVisible(false);
        myShortAnswerField.setVisible(false);
        revalidate();
        repaint();
    }

    /**
     * Updates the room number label with the current room number.
     */
    public void updateRoomNumber() {
        final int currentRoomNumber = myMaze.getCurrentRoomNumber();
        myRoomNumberLabel.setText("Room " + currentRoomNumber);
    }

    /**
     * Updates the question UI elements based on the question type and text.
     *
     * @param theQuestionType The type of the question.
     * @param theQuestionText The text of the question.
     * @param theChoices      The choices for the question.
     */
    private void updateQuestionUI(final String theQuestionType, final String theQuestionText, final String[] theChoices) {
        myQuestionLabel.setText("Question: " + theQuestionText);
        switch (theQuestionType) {
            case "multipleChoice" -> {
                myShortAnswerField.setVisible(false);
                myButton1.setVisible(true);
                myButton2.setVisible(true);
                myButton3.setVisible(true);
                myButton4.setVisible(true);
                myButton1.setText(theChoices.length > 0 ? theChoices[0] : "");
                myButton2.setText(theChoices.length > 1 ? theChoices[1] : "");
                myButton3.setText(theChoices.length > 2 ? theChoices[2] : "");
                myButton4.setText(theChoices.length > 3 ? theChoices[3] : "");
            }
            case "shortAnswer" -> {
                myButton1.setVisible(false);
                myButton2.setVisible(false);
                myButton3.setVisible(false);
                myButton4.setVisible(false);
                myShortAnswerField.setVisible(true);
                myShortAnswerField.setText("");
            }
            case "trueFalse" -> {
                myShortAnswerField.setVisible(false);
                myButton1.setVisible(true);
                myButton2.setVisible(true);
                myButton3.setVisible(false);
                myButton4.setVisible(false);
                myButton1.setText("True");
                myButton2.setText("False");
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Updates the navigation buttons based on the possible moves.
     */
    private void updateNavigationButtons() {
        final boolean canMoveUp = myMaze.canMoveUp();
        final boolean canMoveDown = myMaze.canMoveDown();
        final boolean canMoveLeft = myMaze.canMoveLeft();
        final boolean canMoveRight = myMaze.canMoveRight();

        myUpButton.setEnabled(canMoveUp);
        myDownButton.setEnabled(canMoveDown);
        myLeftButton.setEnabled(canMoveLeft);
        myRightButton.setEnabled(canMoveRight);
    }

    /**
     * Enables or disables the arrow buttons.
     *
     * @param theEnabled True to enable the buttons, false to disable.
     */
    private void setArrowButtonsEnabled(final boolean theEnabled) {
        myUpButton.setEnabled(theEnabled);
        myDownButton.setEnabled(theEnabled);
        myLeftButton.setEnabled(theEnabled);
        myRightButton.setEnabled(theEnabled);
    }

    /**
     * Checks the game state for win or loss conditions.
     */
    private void checkGameState() {
        if (myMaze.isAtExit()) {
            final int choice = JOptionPane.showOptionDialog(this, "Congratulations! You've reached the exit. What would you like to do?", "You Win!",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new Object[]{"Play Again", "Start Menu", "Exit"}, null);

            switch (choice) {
                case JOptionPane.YES_OPTION -> myGameWindow.restartGame();
                case JOptionPane.NO_OPTION -> myGameWindow.showStartMenu();
                case JOptionPane.CANCEL_OPTION -> System.exit(0);
                default -> {
                }
            }
        } else if (!myMaze.isPathToEnd()) {
            final int choice = JOptionPane.showOptionDialog(this, "There is no path to the end. The game is over. What would you like to do?", "Game Over",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null,
                    new Object[]{"Play Again", "Start Menu", "Exit"}, null);

            switch (choice) {
                case JOptionPane.YES_OPTION -> myGameWindow.restartGame();
                case JOptionPane.NO_OPTION -> myGameWindow.showStartMenu();
                case JOptionPane.CANCEL_OPTION -> System.exit(0);
                default -> {
                }
            }
        }
    }
}
