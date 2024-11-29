package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainMenuScreen implements Screen  {

    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite titleSprite;
    private Sprite Gold_Pigs;
    private Sprite pig1;
    private Sprite pig2;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Main game;

    private ImageButton playButton;
    private ImageButton exitButton;
    private ImageButton settingsButton;

    private static final float VIRTUAL_WIDTH = 1600;
    private static final float VIRTUAL_HEIGHT = 900;

    public MainMenuScreen(Main game) {
        this.game = game;

        // Set up camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        // SpriteBatch for rendering
        batch = new SpriteBatch();

        // Load background image
        backgroundTexture = new Texture(Gdx.files.internal("mainmenu_background.png"));
        // Load title image
        Texture title = new Texture(Gdx.files.internal("Angry_birds.png"));
        titleSprite = new Sprite(title);
        titleSprite.setPosition(VIRTUAL_WIDTH / 2 - 450, VIRTUAL_HEIGHT - 340);
        titleSprite.setScale(1f);
        //Load gold pig
        Texture gold_Pig = new Texture(Gdx.files.internal("Gold_Pig.png"));
        Gold_Pigs = new Sprite(gold_Pig);
        Gold_Pigs.setPosition(VIRTUAL_WIDTH/2 - 690 , VIRTUAL_HEIGHT - 340);
        Gold_Pigs.setScale(1.2f);
        //Loading pig1 and pig2
        Texture p1 = new Texture(Gdx.files.internal("pig1.png"));
        pig1 = new Sprite(p1);
        pig1.setPosition(VIRTUAL_WIDTH/2 - 160 , VIRTUAL_HEIGHT - 800);
        pig1.setScale(1f);
        Texture p2 = new Texture(Gdx.files.internal("pig2.png"));
        pig2 = new Sprite(p2);
        pig2.setPosition(VIRTUAL_WIDTH/2 + 30 , VIRTUAL_HEIGHT - 810);
        pig2.setScale(1f);



        // Set up stage for buttons
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Create Play button with Sprite
        Texture playTexture = new Texture(Gdx.files.internal("play_button.png"));
        Sprite playSprite = new Sprite(playTexture);
        TextureRegionDrawable playDrawable = new TextureRegionDrawable(new TextureRegion(playSprite));
        playButton = new ImageButton(playDrawable);

        playButton.setPosition((VIRTUAL_WIDTH / 2 - 100) + 40, VIRTUAL_HEIGHT / 2 - 50); // Center it

        // Create Exit button with Sprite
        Texture exitTexture = new Texture(Gdx.files.internal("exit_button.png"));
        Sprite exitSprite = new Sprite(exitTexture);
        TextureRegionDrawable exitDrawable = new TextureRegionDrawable(new TextureRegion(exitSprite));
        exitButton = new ImageButton(exitDrawable);
        exitButton.setPosition(50, 50); // Bottom-left

        // Create Settings button with Sprite
        Texture settingsTexture = new Texture(Gdx.files.internal("settings_button.png"));
        Sprite settingsSprite = new Sprite(settingsTexture);
        TextureRegionDrawable settingsDrawable = new TextureRegionDrawable(new TextureRegion(settingsSprite));
        settingsButton = new ImageButton(settingsDrawable);
        settingsButton.setPosition(VIRTUAL_WIDTH - 150, 50); // Bottom-right

        // Add buttons to stage
        stage.addActor(playButton);
        stage.addActor(exitButton);
        stage.addActor(settingsButton);

        // Input handling for buttons
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (playButton.isPressed()) {
                    game.setScreen(new LevelScreen(game)); // Replace with actual game screen
                    System.out.println("Play clicked!");
                } else if (exitButton.isPressed()) {
                    Gdx.app.exit(); // Exit the game
                } else if (settingsButton.isPressed()) {
                    System.out.println("Settings clicked!"); // Open settings screen
                }
                return true;
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch.draw(Gold_Pigs, Gold_Pigs.getX() ,Gold_Pigs.getY(), Gold_Pigs.getOriginX() , Gold_Pigs.getOriginY() ,Gold_Pigs.getWidth() ,Gold_Pigs.getHeight() , Gold_Pigs.getScaleX() , Gold_Pigs.getScaleY() , 25);
        batch.draw(titleSprite, titleSprite.getX(), titleSprite.getY());
        batch.draw(pig1,pig1.getX(),pig1.getY());
        batch.draw(pig2, pig2.getX(), pig2.getY(), pig2.getOriginX(), pig2.getOriginY(), pig2.getWidth(), pig2.getHeight() ,pig2.getScaleX() ,pig2.getScaleY() , pig2.getRotation());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        stage.dispose();
    }
}
