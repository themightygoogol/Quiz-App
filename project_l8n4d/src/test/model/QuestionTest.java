package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the question class
public class QuestionTest {
    private Question question;

    @BeforeEach
    void runBefore() {
        question = new Question("1+1", "2");
    }

    @Test
    void testConstructor() {
        assertEquals("1+1", question.getQuestion());
        assertEquals("2", question.getAnswer());
    }

    @Test
    void testChangeQuestion() {
        question.changeQuestion("5+5");
        assertEquals("5+5", question.getQuestion());
        question.changeQuestion("2+5");
        assertEquals("2+5", question.getQuestion());
    }

    @Test
    void testChangeAnswer() {
        question.changeAnswer("5+5");
        assertEquals("5+5", question.getAnswer());
        question.changeAnswer("2+5");
        assertEquals("2+5", question.getAnswer());
    }
}
