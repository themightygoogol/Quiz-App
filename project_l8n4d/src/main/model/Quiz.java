package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/**
 * Represents a quiz having a name and array of questions
 */
public class Quiz implements Writable {

    private final ArrayList<Question> questions;
    private String name;

    // REQUIRES: name must not be empty
    // EFFECTS: constructs an empty quiz with the given name
    public Quiz(String name) {
        questions = new ArrayList<>();
        this.name = name;
    }

    // REQUIRES: non-empty string for name
    // MODIFIES: this
    // EFFECTS: changes the name of the quiz
    public void changeName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds imputed question to the quiz and adds event to event log
    public void addQuestion(Question question) {
        questions.add(question);

        EventLog.getInstance().logEvent(new Event("Question " + question.getQuestion() + " added to " + name
                + "."));
    }

    // REQUIRES: 0 <= index < number of questions in quiz and non-empty string for newQuestion and newAnswer
    // MODIFIES: this
    // EFFECTS: sets new question at indicated index and adds event to event log
    public void modifyQuestion(int index, String newQuestion, String newAnswer) {
        Question question = new Question(newQuestion, newAnswer);
        questions.set(index, question);

        EventLog.getInstance().logEvent(new Event("Question " + index + " modified."));
    }

    // REQUIRES: 0 <= index < number of questions in quiz
    // MODIFIES: this
    // EFFECTS: removes question at index indicated and adds event to event log
    public void removeQuestion(int index) {
        questions.remove(index);

        EventLog.getInstance().logEvent(new Event("Question " + index + " removed."));

    }

    // REQUIRES: index is a positive integer
    // EFFECTS: returns true if 0 <= index < number of questions in quiz and false otherwise
    public boolean indexExistsInQuestions(int index) {
        for (int i = 0; i < this.getNumberOfQuestions(); i++) {
            if (i == index) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: 0 <= index < number of questions in quiz
    // EFFECTS: returns question at index indicated
    public Question getQuestion(int index) {
        return questions.get(index);
    }

    // EFFECTS: returns number of questions in the quiz
    public int getNumberOfQuestions() {
        return questions.size();
    }

    // EFFECTS: returns name of the quiz
    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("questions", questionsToJson());
        return json;
    }

    // EFFECTS: returns question in this quiz as a JSON array
    private JSONArray questionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Question q : questions) {
            jsonArray.put(q.toJson());
        }
        return jsonArray;
    }
}