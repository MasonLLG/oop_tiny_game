package game.oracleutil;

import java.util.List;
import java.util.Objects;

/**
 * Represents a single riddle with a question, a list of possible answers,
 * and the index of the correct answer.
 * Provides convenient accessors and JSON‐style payload generation/parsing
 */
public class Riddle {

    private final String question;
    private final List<String> possibleAnswers;
    private final int correctAnswerIndex;

    /**
     * Constructs a new Riddle.
     *
     * @param question            the text of the riddle question
     * @param possibleAnswers     a non‐empty list of possible answer strings
     * @param correctAnswerIndex  the 0‐based index in {@code possibleAnswers}
     *                            of the correct answer
     * @throws IllegalArgumentException if {@code possibleAnswers} is empty or
     *                                  {@code correctAnswerIndex} is out of range
     * @throws NullPointerException     if {@code question} or {@code possibleAnswers}
     *                                  is null
     */
    public Riddle(String question, List<String> possibleAnswers, int correctAnswerIndex) {
        this.question = Objects.requireNonNull(question, "question must not be null");
        this.possibleAnswers = List.copyOf(Objects.requireNonNull(possibleAnswers, "possibleAnswers must not be null"));
        if (possibleAnswers.isEmpty()) {
            throw new IllegalArgumentException("possibleAnswers must contain at least one element");
        }
        if (correctAnswerIndex < 0 || correctAnswerIndex >= possibleAnswers.size()) {
            throw new IllegalArgumentException(
                    "correctAnswerIndex out of range: " + correctAnswerIndex);
        }
        this.correctAnswerIndex = correctAnswerIndex;
    }

    /**
     * @return the riddle question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return an unmodifiable list of all possible answers
     */
    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    /**
     * @return the 0‐based index of the correct answer in {@link #getPossibleAnswers()}
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    /**
     * @return the correct answer string, or {@code null} if out of range
     */
    public String getCorrectAnswer() {
        if (correctAnswerIndex >= 0 && correctAnswerIndex < possibleAnswers.size()) {
            return possibleAnswers.get(correctAnswerIndex);
        }
        return null;
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }

    @Override
    public String toString() {
        return "Riddle{question='" + question + "', possibleAnswers=" + possibleAnswers +
                ", correctIndex=" + correctAnswerIndex + "}";
    }
}
