package model;

/**
 * The QuestionShortAnswer class represents a short answer question.
 * It extends the AbstractQuestion class and sets the question type to short answer.
 */
public class QuestionShortAnswer extends AbstractQuestion {

    /**
     * Constructs a QuestionShortAnswer object with the specified question, choices, and answer.
     *
     * @param theQuestion The text of the question.
     * @param theChoices  The possible choices for the question (usually empty for short answer questions).
     * @param theAnswer   The correct answer for the question.
     */
    public QuestionShortAnswer(final String theQuestion, final String[] theChoices, final String theAnswer) {
        super(QUESTIONTYPE.shortAnswer, theQuestion, theChoices, theAnswer);
    }

}
