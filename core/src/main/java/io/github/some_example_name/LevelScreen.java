package io.github.some_example_name;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

public class LevelScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture levelBackground;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private ImageButton backButton;
    private ImageButton[] levelButtons;
    private Texture[] levelTextures = new Texture[5];

    private static final float VIRTUAL_WIDTH = 1600;
    private static final float VIRTUAL_HEIGHT = 900;

    public LevelScreen(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();
        batch = new SpriteBatch();
        levelBackground = new Texture(Gdx.files.internal("level_background_enhanced.jpg"));
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Texture backTexture = new Texture(Gdx.files.internal("back_button.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(new TextureRegion(backTexture));
        backButton = new ImageButton(backDrawable);
        backButton.setPosition(50, 50);
        stage.addActor(backButton);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                System.out.println("Back clicked!");
                return true;
            }
        });
        levelButtons = new ImageButton[5];
        float buttonSpacing = 80;
        float startX;
        for (int i = 0; i < levelButtons.length; i++) {
            levelTextures[i] = new Texture(Gdx.files.internal("lvl" + (i + 1) + ".png"));
            TextureRegionDrawable levelDrawable = new TextureRegionDrawable(new TextureRegion(levelTextures[i]));
            levelButtons[i] = new ImageButton(levelDrawable);
            stage.addActor(levelButtons[i]);
        }
        startX = (VIRTUAL_WIDTH - (levelButtons[0].getWidth() * levelButtons.length + buttonSpacing * (levelButtons.length - 1))) / 2;
        for (int i = 0; i < levelButtons.length; i++) {
            levelButtons[i].setPosition(
                startX + (i * (levelButtons[i].getWidth() + buttonSpacing)),
                VIRTUAL_HEIGHT / 2 - levelButtons[i].getHeight() / 2
            );
            final int level = i + 1;
            levelButtons[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("Level " + level + " clicked!");
                    if(true){
                        game.setScreen(new L1(game));
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(levelBackground, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
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
        levelBackground.dispose();
        stage.dispose();
        for (Texture texture : levelTextures) {
            if (texture != null) {
                texture.dispose();
            }
        }
    }
}
