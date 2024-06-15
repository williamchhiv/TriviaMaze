package view;

import model.GameState;
import model.Maze;
import model.MazeFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GameWindow extends JFrame {

    private final CardLayout myCardLayout;
    private final JPanel myMainPanel;
    private Maze myMaze;
    private GameState myGameState;

    public GameWindow() {
        setTitle("Trivia Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setResizable(false);

        myMaze = MazeFactory.createMaze();
        myGameState = new GameState(myMaze);

        myCardLayout = new CardLayout();
        myMainPanel = new JPanel(myCardLayout);

        myMainPanel.add(createMainMenuPanel(), "Main Menu");
        myMainPanel.add(new RoomUI(myMaze, this), "Game");

        setJMenuBar(createMenuBar());

        add(myMainPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainMenuPanel() {
        final JPanel panel = new JPanel(new BorderLayout());

        final URL imageUrl = getClass().getResource("Mazebackground.png");
        if (imageUrl == null) {
            System.out.println("Image not found");
            return panel;
        }
        final ImageIcon image = new ImageIcon(imageUrl);
        final JLabel imageLabel = new JLabel(image);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        final JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        final JLabel titleLabel = new JLabel("Trivia Maze", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        final JButton newGameButton = new JButton("New Game");
        final JButton loadGameButton = new JButton("Load Game");
        final JButton exitButton = new JButton("Exit");

        final Font buttonFont = new Font("Arial", Font.BOLD, 25);
        newGameButton.setFont(buttonFont);
        loadGameButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        newGameButton.setFocusable(false);
        loadGameButton.setFocusable(false);
        exitButton.setFocusable(false);

        newGameButton.setMargin(new Insets(20, 50, 20, 50));
        loadGameButton.setMargin(new Insets(20, 50, 20, 50));
        exitButton.setMargin(new Insets(20, 50, 20, 50));

        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton.addActionListener(e -> restartGame());
        loadGameButton.addActionListener(e -> loadGame());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 20)));
        buttonPanel.add(loadGameButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 20)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        panel.add(imagePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(titleLabel, BorderLayout.NORTH);

        return panel;
    }

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();

        final JMenu gameMenu = getjMenu();

        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem aboutMenuItem = new JMenuItem("About");
        final JMenuItem rulesMenuItem = new JMenuItem("Rules");
        aboutMenuItem.addActionListener(e -> new AboutUI());
        helpMenu.add(aboutMenuItem);
        rulesMenuItem.addActionListener(e -> new RuleUI());
        helpMenu.add(rulesMenuItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JMenu getjMenu() {
        final JMenu gameMenu = new JMenu("File");
        final JMenuItem saveGameStatus = new JMenuItem("Save Game");
        saveGameStatus.addActionListener(e -> saveGame());

        final JMenuItem loadSavedGame = new JMenuItem("Load Game");
        loadSavedGame.addActionListener(e -> loadGame());

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));

        gameMenu.add(saveGameStatus);
        gameMenu.add(loadSavedGame);
        gameMenu.add(exitMenuItem);
        return gameMenu;
    }

    public void restartGame() {
        myMaze = MazeFactory.createMaze();
        myGameState = new GameState(myMaze);
        myMainPanel.add(new RoomUI(myMaze, this), "Game");
        myCardLayout.show(myMainPanel, "Game");
    }

    public void showStartMenu() {
        myCardLayout.show(myMainPanel, "Main Menu");
    }

    private void saveGame() {
        final JFileChooser fileChooser = new JFileChooser();
        final int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                GameState.saveGame(myGameState, fileChooser.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(this, "Game saved successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to save game.", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    private void loadGame() {
        final JFileChooser fileChooser = new JFileChooser();
        final int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                myGameState = GameState.loadGame(fileChooser.getSelectedFile().getPath());
                myMaze = myGameState.maze();
                // Update the UI with the loaded game state
                myMainPanel.add(new RoomUI(myMaze, this), "Game");
                myCardLayout.show(myMainPanel, "Game"); // Show game panel
                JOptionPane.showMessageDialog(this, "Game loaded successfully.");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load game.", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }
}
