package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class QuizSystemTest {
    private User user;
    private QuizSystem quizSystem;


    @BeforeEach
    void runBefore() {
        Question question = new Question("1+1", "2");
        Quiz quiz = new Quiz("quiz");
        quiz.addQuestion(question);
        user = new User("username", "password", new ArrayList<>());
        quizSystem = new QuizSystem(new ArrayList<>());
    }

    @Test
    void testConstructor() {
        assertEquals(1, quizSystem.getNumberOfUsers());
        assertEquals("public", quizSystem.getUser(0).getUsername());
    }

    @Test
    void testAddUser() {
        quizSystem.addUser(user);
        assertEquals(2, quizSystem.getNumberOfUsers());
        assertEquals(user, quizSystem.getUser(1));
    }

    @Test
    void testFindUser() {
        assertNull(quizSystem.findUser("username"));
        quizSystem.addUser(user);
        assertEquals(user, quizSystem.findUser("username"));
    }


    @Test
    void testRemoveUser() {
        quizSystem.addUser(user);
        assertEquals(user, quizSystem.findUser("username"));
        quizSystem.removeUser(user.getUsername());
        assertNull(quizSystem.findUser("username"));
    }
}