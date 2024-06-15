package model;

/**
 * The QuestionMultipleChoice class represents a multiple-choice question.
 * It extends the AbstractQuestion class and sets the question type to multiple choice.
 */
public class QuestionMultipleChoice extends AbstractQuestion {

    /**
     * Constructs a QuestionMultipleChoice object with the specified question, choices, and answer.
     *
     * @param theQuestion The text of the question.
     * @param theChoices  The possible choices for the question.
     * @param theAnswer   The correct answer for the question.
     */
    public QuestionMultipleChoice(final String theQuestion, final String[] theChoices, final String theAnswer) {
        super(QUESTIONTYPE.multipleChoice, theQuestion, theChoices, theAnswer);
    }

}
