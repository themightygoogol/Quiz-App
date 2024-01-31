package persistence;

import model.QuizSystem;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            QuizSystem quizSystem = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterBrandNewQuizSystem.json");
        try {
            QuizSystem quizSystem = reader.read();
            assertEquals("public", quizSystem.getUser(0).getUsername());
            assertEquals(1, quizSystem.getNumberOfUsers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralQuizSystem.json");
        try {
            QuizSystem quizSystem = reader.read();
            assertEquals(2, quizSystem.getNumberOfUsers());
            ArrayList<User> users = quizSystem.getUsers();
            checkQuizSystem(quizSystem, users.get(1), users.get(1).getQuiz(0),
                    users.get(1).getQuiz(0).getQuestion(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}