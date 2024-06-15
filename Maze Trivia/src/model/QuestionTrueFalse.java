package model;

/**
 * The QuestionTrueFalse class represents a true/false question.
 * It extends the AbstractQuestion class and sets the question type to true/false.
 */
public class QuestionTrueFalse extends AbstractQuestion {

    /**
     * Constructs a QuestionTrueFalse object with the specified question, choices, and answer.
     *
     * @param theQuestion The text of the question.
     * @param theChoices  The possible choices for the question (usually "True" and "False").
     * @param theAnswer   The correct answer for the question.
     */
    public QuestionTrueFalse(final String theQuestion, final String[] theChoices, final String theAnswer) {
        super(QUESTIONTYPE.trueFalse, theQuestion, theChoices, theAnswer);
    }

}
