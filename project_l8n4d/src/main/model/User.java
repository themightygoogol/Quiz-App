package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;


/**
 * Represents an account with a username, password and a list of quizzes
 */
public class User implements Writable {
    private final ArrayList<Quiz> quizzes;
    private String username;
    private String password;

    // EFFECTS: creates a new user with imputed username, password and list of quizzes
    public User(String username, String password, ArrayList<Quiz> quizzes) {
        this.quizzes = quizzes;
        this.username = username;
        this.password = password;
    }


    // EFFECTS: returns the username for this user
    public String getUsername() {
        return username;
    }

    // MODIFIES: this
    // EFFECTS: changes the username for this user
    public void setUsername(String username) {
        this.username = username;
    }


    // EFFECTS: returns the password for this user
    public String getPassword() {
        return password;
    }

    // MODIFIES: this
    // EFFECTS: changes the password for this user
    public void setPassword(String password) {
        this.password = password;
    }


    // MODIFIES: this
    // EFFECTS: adds quiz to this user and adds event to event log
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        EventLog.getInstance().logEvent(new Event("Quiz " + quiz.getName() + " added to " + username + "."));
    }

    // REQUIRES: quizToRemove must be the name of a quiz this user has
    // MODIFIES: this
    // EFFECTS: removes the quiz with the imputed quiz name from the user and adds event to event log
    public void removeQuiz(String quizToRemove) {
        for (int i = 0; i < quizzes.size(); i++) {
            if (quizzes.get(i).getName().equals(quizToRemove)) {
                quizzes.remove(i);
            }
        }
        EventLog.getInstance().logEvent(new Event("Quiz " + quizToRemove + " removed from " + username + "."));
    }

    // REQUIRES: quizName must be the name of a quiz in quizzes
    // EFFECTS: returns the quiz with the imputed name
    public Quiz getQuiz(String quizName) {
        for (int i = 0; i < quizzes.size(); i++) {
            if (quizzes.get(i).getName().equals(quizName)) {
                return quizzes.get(i);
            }
        }
        return null;
    }

    // REQUIRES: 0 <= i < quizzes.size();
    // EFFECTS: returns quiz at the imputed index
    public Quiz getQuiz(int i) {
        return quizzes.get(i);
    }

    // EFFECTS: returns the size of this quiz
    public int getNumberOfQuizzes() {
        return quizzes.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("quizzes", quizzesToJson());
        return json;
    }

    // EFFECTS: returns quizzes in this user as a JSON array
    private JSONArray quizzesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Quiz quiz : quizzes) {
            jsonArray.put(quiz.toJson());
        }
        return jsonArray;
    }

}

