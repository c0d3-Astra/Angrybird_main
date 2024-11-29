package io.github.some_example_name;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class GameUI {
    private Main game;
    private Stage stage;
    private ImageButton pauseButton;
    private boolean isPaused;

    private ShapeRenderer shapeRenderer;

    // Add boundaries
    private Body groundBody;
    private Body leftBoundary;
    private Body rightBoundary;

    public GameUI(Main game, World world, Stage stage) {
        this.game = game;
        this.stage = stage;

        // Initialize ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Create ground and boundaries
        createGround(world);
        createBoundaries(world);
    }

    private void createGround(World world) {
        // Set up ground body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(stage.getViewport().getWorldWidth() / 2 / 100f, 1);  // Centered X, near bottom Y
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Create ground body and shape
        groundBody = world.createBody(bodyDef);
        PolygonShape groundShape = new PolygonShape();
        float groundWidth = stage.getViewport().getWorldWidth() / 100f;
        float groundHeight = 1;  // 1 meter high
        groundShape.setAsBox(groundWidth / 2, groundHeight / 2);

        // Define ground fixture with high friction and no bounce
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 2f;
        fixtureDef.restitution = 0.0f;  // No bounce
        groundBody.createFixture(fixtureDef);
        groundShape.dispose();
    }

    private void createBoundaries(World world) {
        float screenWidth = stage.getViewport().getWorldWidth() / 100f;
        float screenHeight = stage.getViewport().getWorldHeight() / 100f;

        // Create static boundary bodies
        BodyDef boundaryDef = new BodyDef();
        boundaryDef.type = BodyDef.BodyType.StaticBody;

        // Left boundary
        boundaryDef.position.set(0, screenHeight / 2);
        leftBoundary = world.createBody(boundaryDef);
        EdgeShape leftEdge = new EdgeShape();
        leftEdge.set(0, -screenHeight / 2, 0, screenHeight / 2);
        leftBoundary.createFixture(leftEdge, 0.0f);
        leftEdge.dispose();

        // Right boundary
        boundaryDef.position.set(screenWidth, screenHeight / 2);
        rightBoundary = world.createBody(boundaryDef);
        EdgeShape rightEdge = new EdgeShape();
        rightEdge.set(0, -screenHeight / 2, 0, screenHeight / 2);
        rightBoundary.createFixture(rightEdge, 0.0f);
        rightEdge.dispose();
    }

    public float groundHeight() {
        return 1;  // 1 meter high
    }

    public void renderBackground() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw background rectangle
        shapeRenderer.setColor(0.15f, 0.15f, 0.2f, 1f);
        shapeRenderer.rect(0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());

        // Draw ground rectangle
        shapeRenderer.setColor(0.4f, 0.2f, 0.1f, 1f);
        shapeRenderer.rect(0, 0, stage.getViewport().getWorldWidth(), groundHeight() * 100 + 50);

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void togglePause() {
        isPaused = !isPaused;
        System.out.println(isPaused ? "Game Paused" : "Game Resumed");
    }

    public ImageButton getPauseButton() {
        return pauseButton;
    }

    public Body getGroundBody() {
        return groundBody;
    }
}
