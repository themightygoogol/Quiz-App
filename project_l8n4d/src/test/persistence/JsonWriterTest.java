package persistence;

import model.Question;
import model.Quiz;
import model.QuizSystem;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            QuizSystem quizSystem = new QuizSystem(new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterBrandNewQuizSystem() {
        try {
            QuizSystem quizSystem = new QuizSystem(new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/testWriterBrandNewQuizSystem.json");
            writer.open();
            writer.write(quizSystem);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBrandNewQuizSystem.json");
            quizSystem = reader.read();
            assertEquals(1, quizSystem.getNumberOfUsers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralQuizSystem() {
        try {
            QuizSystem quizSystem = new QuizSystem(new ArrayList<>());
            Question question = new Question("1+1", "2");
            Quiz quiz = new Quiz("math quiz");
            quiz.addQuestion(question);
            User user = new User("username", "password", new ArrayList<>());
            user.addQuiz(quiz);
            quizSystem.addUser(user);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralQuizSystem.json");
            writer.open();
            writer.write(quizSystem);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralQuizSystem.json");
            quizSystem = reader.read();
            assertEquals(2, quizSystem.getNumberOfUsers());
            checkQuizSystem(quizSystem, user, quiz, question);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}