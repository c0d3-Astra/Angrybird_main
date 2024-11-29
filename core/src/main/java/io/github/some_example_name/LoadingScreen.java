package io.github.some_example_name;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class LoadingScreen implements Screen {
    private SpriteBatch batch;
    private Sprite background;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;
    private Main game;

    private static final float VIRTUAL_WIDTH = 1600;
    private static final float VIRTUAL_HEIGHT = 900;

    private float timer;
    private boolean showMessage;

    private float fadeTimer;
    private float fadeDuration = 0.8f;
    private boolean fadingIn = true;

    public LoadingScreen(Main game) {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();
        Pixmap pixmap = new Pixmap(Gdx.files.internal("Loading.jpg"));
        Pixmap resizedPixmap = new Pixmap(1600, 900, pixmap.getFormat());
        resizedPixmap.drawPixmap(pixmap,
            0, 0, pixmap.getWidth(), pixmap.getHeight(),
            0, 0, resizedPixmap.getWidth(), resizedPixmap.getHeight());
        Texture backgroundTexture = new Texture(resizedPixmap);
        pixmap.dispose();
        resizedPixmap.dispose();
        background = new Sprite(backgroundTexture);
        background.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();
        timer = 0;
        fadeTimer = 0;
        showMessage = false;
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        timer += delta;
        if (timer > 2 && !showMessage) {
            showMessage = true;
            fadeTimer = 0;
        }
        float alpha = 1;
        if (showMessage) {
            fadeTimer += delta;
            if (fadingIn) {
                alpha = Math.min(1, fadeTimer / fadeDuration);
                if (alpha >= 1) {
                    fadingIn = false;
                    fadeTimer = 0;
                }
            } else {
                alpha = Math.max(0, 1 - fadeTimer / fadeDuration);
                if (alpha <= 0) {
                    fadingIn = true;
                    fadeTimer = 0;
                }
            }
        }
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.draw(batch);
        if (showMessage) {
            int x = (int) ((VIRTUAL_WIDTH - 30) / 2 - 150);
            int y = 80;
            String text = "Press Space To Continue";
            font.setColor(0, 0, 0, alpha);
            font.draw(batch, text, x - 1, y);
            font.draw(batch, text, x + 1, y);
            font.draw(batch, text, x, y - 1);
            font.draw(batch, text, x, y + 1);
            font.draw(batch, text, x - 1, y + 1);
            font.draw(batch, text, x + 1, y + 1);
            font.draw(batch, text, x - 1, y - 1);
            font.draw(batch, text, x + 1, y - 1);
            font.setColor(1, 1, 1, alpha);
            font.draw(batch, text, x, y);
        }
        batch.end();
        if (showMessage && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            System.out.println("Space pressed! Proceeding...");
            game.setScreen(new MainMenuScreen(game));
        }
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
        background.getTexture().dispose();
        font.dispose();
    }
}
