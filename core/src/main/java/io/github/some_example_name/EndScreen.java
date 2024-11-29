package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.Main;

public class EndScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Sprite playButtonSprite;
    private Sprite exitButtonSprite;
    private Sprite backgroundSprite;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float VIRTUAL_WIDTH = 1600;
    private static final float VIRTUAL_HEIGHT = 900;

    public EndScreen(Main game, int screenType) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        // Set background texture based on screenType
        String backgroundImage = (screenType == 1) ? "win.jpg" : "lose.png";
        backgroundSprite = new Sprite(new Texture(Gdx.files.internal(backgroundImage)));
        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Load appropriate button textures
        loadButtons(screenType);

        // Set input processor for touch handling
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                camera.unproject(touchPos);
                handleButtonPress(touchPos);
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });

        // Load font
        font = createFont();
    }

    // Method to load buttons based on screenType (win/lose)
    private void loadButtons(int screenType) {
        String playButtonImage = (screenType == 1) ? "play_finish.png" : "replay_finish.png";
        playButtonSprite = new Sprite(new Texture(Gdx.files.internal(playButtonImage)));
        playButtonSprite.setSize(300, 100);
        playButtonSprite.setPosition(VIRTUAL_WIDTH / 2 - playButtonSprite.getWidth() - 20, VIRTUAL_HEIGHT / 2 - 300);

        exitButtonSprite = new Sprite(new Texture(Gdx.files.internal("exit_finish.png")));
        exitButtonSprite.setSize(300, 100);
        exitButtonSprite.setPosition(VIRTUAL_WIDTH / 2 + 20, VIRTUAL_HEIGHT / 2 - 300);
    }

    // Method to create font
    private BitmapFont createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    // Method to handle button presses
    private void handleButtonPress(Vector3 touchPos) {
        if (playButtonSprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
            System.out.println("Play/replay button pressed");
            game.setScreen(new L1(game));
        } else if (exitButtonSprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
            System.out.println("Exit button pressed");
            game.setScreen(new LevelScreen(game));
        }
    }

    @Override
    public void show() {
        // No need for implementation in this case
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        backgroundSprite.draw(batch);
        playButtonSprite.draw(batch);
        exitButtonSprite.draw(batch);
        font.draw(batch, "Score: " + L1.score, 50, VIRTUAL_HEIGHT - 50);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Not used
    }

    @Override
    public void resume() {
        // Not used
    }

    @Override
    public void hide() {
        // Not used
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundSprite.getTexture().dispose();
        playButtonSprite.getTexture().dispose();
        exitButtonSprite.getTexture().dispose();
        font.dispose();
    }
}
