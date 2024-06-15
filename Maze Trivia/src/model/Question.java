package model;

import java.io.Serializable;

/**
 * The Question interface represents a general question with a type, text, choices, and an answer.
 * It extends Serializable to allow question objects to be serialized.
 */
public interface Question extends Serializable {

    /**
     * Enumeration representing the different types of questions.
     */
    enum QUESTIONTYPE {
        multipleChoice, trueFalse, shortAnswer
    }

    /**
     * Gets the type of the question.
     *
     * @return the question type
     */
    QUESTIONTYPE getType();

    /**
     * Gets the text of the question.
     *
     * @return the question text
     */
    String getQuestion();

    /**
     * Gets the possible choices for the question.
     *
     * @return an array of choices
     */
    String[] getChoices();

    /**
     * Gets the correct answer for the question.
     *
     * @return the correct answer
     */
    String getAnswer();
}
