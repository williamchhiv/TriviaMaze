package controller;

import model.DatabaseManager;
import model.QuestionImporter;
import view.GameWindow;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.dropTable();
        DatabaseManager.createTable();
        QuestionImporter.importQuestions("questions.txt");
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
