package Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pig extends Actor {

    private static int textureIndex = 0;
    private static final String[] textures = {"normal.png", "king.png", "soldier.png"};

    private Texture texture;
    private Body body;

    // Conversion factor between Box2D meters and pixels
    private static final float SCALE = 100f;

    // Constructor with Box2D world
    public Pig(World world, float x, float y) {
        // Load the pig texture in sequence
        texture = loadTexture();

        // Create the Box2D physics body
        createPhysicsBody(world, x / SCALE, y / SCALE);

        // Set the size of the actor in pixels
        setSize(50f, 50f); // Increased visual size in pixels

        // Align the actor's position with the Box2D body
        update(0); // Initialize position correctly
    }

    // Load the next texture in sequence
    private Texture loadTexture() {
        Texture loadedTexture = new Texture(Gdx.files.internal(textures[textureIndex]));
        textureIndex = (textureIndex + 1) % textures.length; // Cycle through textures
        return loadedTexture;
    }

    // Method to create the physics body for the Pig
    private void createPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = createBodyDef(x, y);
        body = world.createBody(bodyDef);

        CircleShape shape = createShape();
        FixtureDef fixtureDef = createFixtureDef(shape);

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
    }

    // Create and return a BodyDef for the Pig
    private BodyDef createBodyDef(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y); // Position in Box2D meters
        bodyDef.fixedRotation = true; // Prevent rotation
        bodyDef.linearDamping = 0.5f; // Linear damping to slow down the Pig
        bodyDef.linearVelocity.set(0, 0); // Initial velocity
        return bodyDef;
    }

    // Create and return a CircleShape for the Pig's physics body
    private CircleShape createShape() {
        CircleShape shape = new CircleShape();
        shape.setRadius(25f / SCALE); // Radius in Box2D meters (matches 50px visual size)
        return shape;
    }

    // Create and return a FixtureDef with appropriate properties for the Pig
    private FixtureDef createFixtureDef(CircleShape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Density for mass
        fixtureDef.friction = 0.8f; // Friction to prevent slipping
        return fixtureDef;
    }

    // Method to set the Pig's position in the Box2D world
    public void setBodyPosition(float x, float y) {
        body.setTransform(x / SCALE, y / SCALE, body.getAngle());
    }

    // Method to draw the Pig on the screen
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), 50f, 50f); // Increased visual size
    }

    // Method to synchronize the Pig's Actor position with its Box2D body
    public void update(float deltaTime) {
        Vector2 bodyPosition = body.getPosition(); // Get position in Box2D meters
        setPosition(bodyPosition.x * SCALE - getWidth() / 2, bodyPosition.y * SCALE - getHeight() / 2);
    }

    // Method to dispose of the texture when done
    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return body;
    }
}
