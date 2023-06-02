import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.addPlayer("Player1");
        game.addPlayer("Player2");
        game.addPlayer("Player3");
    }

    @Test
    public void testCorrectAnswer() {
        // Roll the dice and answer correctly
        game.diceRoll(4);
        assertTrue(game.isCorrectAnswer());
    }

    @Test
    public void testIncorrectAnswer() {
        // Roll the dice and answer incorrectly
        game.diceRoll(4);
        assertFalse(game.isCorrectAnswer());
    }

    @Test
    public void testChangingQuestionCategory() {
        // Roll the dice and check if the correct category question is asked
        game.diceRoll(4);
        String currentCategory = game.getCurrentCategory();
        String question = game.getNextQuestion(currentCategory);
        assertTrue(question.startsWith(currentCategory));
    }

    @Test
    public void testRollMethod() {
        // Test the roll method in the game class
        game.diceRoll(3);
        int currentPlayerIndex = game.getCurrentPlayerIndex();
        int currentLocation = game.getPlayerLocation(currentPlayerIndex);
        assertEquals(3, currentLocation);
    }
}
