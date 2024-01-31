package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a question with a question and its respective answer
 */
public class Question implements Writable {
    private String question;
    private String answer;

    // REQUIRES: non-empty string for question and answer
    // EFFECTS: constructs a question with the given question and answer
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // REQUIRES: non-empty string for question
    // MODIFIES: this
    // EFFECTS: changes the question for the question object
    public void changeQuestion(String question) {
        this.question = question;
    }

    // REQUIRES: non-empty string for answer
    // MODIFIES: this
    // EFFECTS: changes the answer for the question object
    public void changeAnswer(String answer) {
        this.answer = answer;
    }

    // EFFECTS: returns the question for the question object
    public String getQuestion() {
        return question;
    }

    // EFFECTS: returns the answer for the question object
    public String getAnswer() {
        return answer;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        return json;
    }
}
