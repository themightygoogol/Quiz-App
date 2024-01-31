package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {
    private Quiz quiz;
    private User user;


    @BeforeEach
    void runBefore() {
        Question question = new Question("1+1", "2");
        quiz = new Quiz("quiz");
        quiz.addQuestion(question);
        user = new User("username","password", new ArrayList<>());
    }

    @Test
    void testConstructor() {
        assertEquals(0, user.getNumberOfQuizzes());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void testSetUsername() {
        user.setUsername("some other username");
        assertEquals("some other username", user.getUsername());
    }

    @Test
    void testSetPassword() {
        user.setPassword("some other password");
        assertEquals("some other password", user.getPassword());
    }

    @Test
    void testAddQuiz() {
        user.addQuiz(quiz);
        assertEquals(1, user.getNumberOfQuizzes());
    }

    @Test
    void testRemoveQuiz() {
        user.removeQuiz(quiz.getName());
        assertEquals(0, user.getNumberOfQuizzes());
        user.addQuiz(quiz);
        assertEquals(1, user.getNumberOfQuizzes());
        user.removeQuiz(quiz.getName());
        assertEquals(0, user.getNumberOfQuizzes());
        user.removeQuiz(quiz.getName());
        assertEquals(0, user.getNumberOfQuizzes());
    }

    @Test
    void testNonExistentQuiz() {
        user.addQuiz(quiz);
        assertNull(user.getQuiz("non existent quiz"));

    }
}