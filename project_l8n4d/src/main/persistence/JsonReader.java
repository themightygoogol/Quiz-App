package persistence;

import model.Question;
import model.Quiz;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.User;
import model.QuizSystem;
import org.json.*;

/**
 * Represents a reader that reads quiz system from JSON data stored in file
 */
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads quiz system from file and returns it;
    // throws IOException if an error occurs reading data from file
    public QuizSystem read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUsers(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses quiz system from JSON object and returns it
    private QuizSystem parseUsers(JSONObject jsonObject) {
        QuizSystem quizSystem = new QuizSystem(new ArrayList<>());
        quizSystem.removeUser("public");
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(quizSystem, nextUser);
        }
        return quizSystem;
    }

    // MODIFIES: quiz
    // EFFECTS: parses user from JSON object and adds it to quiz system
    private void addUser(QuizSystem quizSystem, JSONObject jsonObject) {

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONArray jsonArray = jsonObject.getJSONArray("quizzes");
        User user = new User(username, password, new ArrayList<>());

        for (Object json : jsonArray) {
            JSONObject nextQuiz = (JSONObject) json;
            addQuiz(user, nextQuiz);
        }

        quizSystem.addUser(user);
    }

    // MODIFIES: wr
    // EFFECTS: parses quiz from JSON object and adds them to user
    private void addQuiz(User user, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray jsonArray = jsonObject.getJSONArray("questions");
        Quiz quizToAdd = new Quiz(name);

        for (Object json : jsonArray) {
            JSONObject nextQuestion = (JSONObject) json;
            addQuestions(quizToAdd, nextQuestion);
        }
        user.addQuiz(quizToAdd);
    }

    // MODIFIES: wr
    // EFFECTS: parses question from JSON object and adds it to quiz
    private void addQuestions(Quiz quiz, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        Question theQuestionObject = new Question(question, answer);
        quiz.addQuestion(theQuestionObject);
    }
}