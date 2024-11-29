import Actors.Pig;
import Actors.RedBird;
import io.github.some_example_name.GameMananger;
import io.github.some_example_name.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.physics.box2d.*;

public class GameManagerTest {
    private GameMananger gameManager;
    private World mockWorld;

    private Pig mockPig;
    private RedBird mockBird;
    private Body mockBody;
    private Main mockGame;

    @BeforeEach
    public void setUp() {
        mockWorld = mock(World.class);  // Mock the World object
        // Mock the Stage
        mockGame = mock(Main.class);    // Mock the Main class
        gameManager = new GameMananger(mockGame, mockWorld, mockStage);

        // Create mock birds and pigs
        mockBird = mock(RedBird.class);
        mockPig = mock(Pig.class);

        // Set up mocked bodies
        mockBody = mock(Body.class);
        when(mockBird.getBody()).thenReturn(mockBody);
        when(mockPig.getBody()).thenReturn(mockBody);
    }

    @Test
    public void testScoreUpdateOnPigHit() {
        // Arrange: Initialize score
        int initialScore = GameMananger.score;

        // Simulate a collision with a pig
        gameManager.onBirdLaunched(); // Launch the bird
        gameManager.update(1.0f); // Update the game logic (e.g., the bird's interaction with a pig)

        // Simulate a collision where the pig is hit
        gameManager.getGameContactListener().handleCollision(mockBody, mockBody);

        // Assert: Score should have been updated
        assertEquals(initialScore + 100, GameMananger.score, "Score should be updated after pig hit");
    }

    @Test
    public void testGameStateAfterBirdLaunch() {
        // Arrange: Set up initial game state
        gameManager.onBirdLaunched(); // Launch the first bird

        // Act: Check for the next bird state
        gameManager.onBirdLaunched(); // Launch another bird

        // Assert: Ensure the next bird is being launched, and the state transitions as expected
        assertEquals(1, gameManager.getCurrentBirdIndex(), "The current bird index should increment after each bird launch");
    }

    // Example for handling the collision between a bird and a block
    @Test
    public void testScoreUpdateOnBlockHit() {
        // Arrange: Set initial score
        int initialScore = GameMananger.score;

        // Simulate a collision with a block
        gameManager.onBirdLaunched(); // Launch the bird
        gameManager.update(1.0f); // Update the game logic (e.g., the bird's interaction with a block)

        // Simulate a collision where the block is hit
        gameManager.getGameContactListener().handleCollision(mockBody, mockBody);

        // Assert: Score should have been updated after block hit
        assertEquals(initialScore + 20, GameMananger.score, "Score should be updated after block hit");
    }
}
