package model;

/**
 * The AbstractedQuestion class represents a myQuestion with multiple myChoices and an myAnswer.
 * It implements the Question interface.
 */
public class AbstractQuestion implements Question {
    private final QUESTIONTYPE myType;
    private final String myQuestion;
    private final String[] myChoices;
    private final String myAnswer;

    /**
     * Constructs an AbstractedQuestion with the specified myType, subject, myQuestion, myChoices, and myAnswer.
     *
     * @param myType the myType of the myQuestion
     * @param myQuestion the myQuestion text
     * @param myChoices the possible myChoices for the myQuestion
     * @param myAnswer the correct myAnswer for the myQuestion
     * @throws IllegalArgumentException if any of the parameters are null
     */
    protected AbstractQuestion(QUESTIONTYPE myType, String myQuestion, String[] myChoices, String myAnswer) {
        if (myType == null || myQuestion == null || myChoices == null || myAnswer == null) {
            throw new IllegalArgumentException("The myQuestion parameters cannot be null.");
        }
        this.myType = myType;
        this.myQuestion = myQuestion;
        this.myChoices = myChoices;
        this.myAnswer = myAnswer;
    }

    /**
     * Returns the myType of myQuestion.
     *
     * @return the myType of myQuestion
     */
    public QUESTIONTYPE getType() {
        return myType;
    }

    /**
     * Returns the text of the myQuestion.
     *
     * @return the text of this myQuestion
     */
    public String getQuestion() {
        return myQuestion;
    }

    /**
     * Returns the possible myChoices for the myQuestion.
     *
     * @return an array of possible myChoices for this myQuestion
     */
    public String[] getChoices() {
        return myChoices;
    }

    /**
     * Returns the answer for myQuestion.
     *
     * @return an answer for myQuestion
     */

    public String getAnswer() {
        return myAnswer;
    }

    /**
     * Returns a hash code value for the myQuestion.
     *
     * @return a hash code value for the myQuestion
     */
    @Override
    public int hashCode() {
        int result = java.util.Objects.hash(myType, myQuestion, myAnswer);
        result = 31 * result + java.util.Arrays.hashCode(myChoices);
        return result;
    }
}