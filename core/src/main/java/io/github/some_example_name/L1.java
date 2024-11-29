package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class L1 implements Screen {

    private Main game;
    private GameUI gameUI;
    private GameMananger gameMananger;
    private BitmapFont font;
    private Stage stage;
    private Stage pauseStage;
    private World world;
    private SpriteBatch batch;
    private Box2DDebugRenderer debugRenderer;
    public static int score;

    public L1(Main game) {
        this.game = game;
        score = 0;

        // Set up the camera
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);

        // Set up the stages
        this.stage = new Stage(new ScreenViewport(camera));
        this.pauseStage = new Stage(new ScreenViewport(camera));

        // Create the world with gravity
        world = new World(new Vector2(0, -9.8f), true);

        // Initialize debug renderer for physics debugging
        debugRenderer = new Box2DDebugRenderer();

        // Initialize the UI and game entities
        gameUI = new GameUI(game, world, stage);
        gameMananger = new GameMananger(game,world, stage);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();
        batch = new SpriteBatch();
        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        // Any setup needed when this screen is shown
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a background color
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Check if the game is paused
        if (gameUI.isPaused()) {
            pauseStage.act(delta);
            pauseStage.draw();
        } else {
            // Step the physics world
            world.step(1 / 60f, 8, 3);

            // Render background
            gameUI.renderBackground();

            // Debug draw for Box2D
            debugRenderer.render(world, stage.getCamera().combined);

            // Update game entities
            gameMananger.update(delta);

            // Update and draw the stage
            stage.act(delta);
            stage.draw();
            batch.begin();
            font.draw(batch, "Score: " + score, 50, Gdx.graphics.getHeight() - 50);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        pauseStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Pause logic if needed
    }

    @Override
    public void resume() {
        // Resume logic if needed
    }

    @Override
    public void hide() {
        // Cleanup or state saving when this screen is hidden
    }

    @Override
    public void dispose() {
        // Dispose resources
        stage.dispose();
        pauseStage.dispose();
        gameUI.dispose();
        gameMananger.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
