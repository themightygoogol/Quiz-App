package persistence;

import model.Question;
import model.Quiz;
import model.QuizSystem;
import model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkQuizSystem(QuizSystem quizSystem, User user, Quiz quizName, Question question) {
        assertEquals(question.getQuestion(),
                quizSystem.findUser(user.getUsername()).getQuiz(quizName.getName()).getQuestion(0).getQuestion());
        assertEquals(question.getAnswer(),
                quizSystem.findUser(user.getUsername()).getQuiz(quizName.getName()).getQuestion(0).getAnswer());
    }
}
