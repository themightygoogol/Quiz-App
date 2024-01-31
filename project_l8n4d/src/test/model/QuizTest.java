package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the quiz class
public class QuizTest {
    private Question question;
    private Quiz quiz;

    @BeforeEach
    void runBefore() {
        question = new Question("1+1", "2");
        quiz = new Quiz("quiz");
        quiz.addQuestion(question);
    }

    @Test
    void testConstructor() {
        assertEquals(  1, quiz.getNumberOfQuestions());
        assertEquals("quiz", quiz.getName());
        Quiz mathQuiz = new Quiz("math quiz");
        assertEquals(0, mathQuiz.getNumberOfQuestions());
        assertEquals("math quiz", mathQuiz.getName());
    }

    @Test
    void testChangeName() {
        quiz.changeName("some other quiz");
        assertEquals("some other quiz", quiz.getName());
    }

    @Test
    void testAddQuestion() {
        question = new Question("2+2", "4");
        quiz.addQuestion(question);
        assertEquals(2, quiz.getNumberOfQuestions());
        assertEquals("2+2", quiz.getQuestion(1).getQuestion());
        assertEquals("4", quiz.getQuestion(1).getAnswer());
    }

    @Test
    void testModifyQuestion() {
        quiz.modifyQuestion(0, "2+2", "4");
        assertEquals("2+2", quiz.getQuestion(0).getQuestion());
        assertEquals("4", quiz.getQuestion(0).getAnswer());
    }

    @Test
    void testRemoveQuestion() {
        quiz.removeQuestion(0);
        assertEquals(0, quiz.getNumberOfQuestions());
    }

    @Test
    void testNumOfQuestions() {
        assertEquals(1, quiz.getNumberOfQuestions());
    }

    @Test
     void testIndexExistsInQuestions() {
        assertTrue(quiz.indexExistsInQuestions(0));
        assertFalse(quiz.indexExistsInQuestions(1));
    }
}
