package Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RedBird extends Actor {

    private static int textureIndex = 0;
    private static final String[] textures = {"RED.png", "White.png", "Silver.png"};

    private Texture redBirdTexture;
    private Body body; // Box2D body for the red bird
    private float birdRadius; // To store the radius
    private boolean isLaunched = false; // To track if the bird has been launched

    public RedBird(World world) {
        // Load the bird texture in sequence
        redBirdTexture = loadTexture();

        // Define the bird radius
        birdRadius = 25f; // Adjust size as needed (25 pixels radius = 50 pixels diameter)

        // Create the Box2D body
        createBody(world);
    }

    // Load the next texture in sequence
    private Texture loadTexture() {
        Texture texture = new Texture(Gdx.files.internal(textures[textureIndex]));
        textureIndex = (textureIndex + 1) % textures.length; // Cycle through textures
        return texture;
    }

    // Create the Box2D body for the RedBird
    private void createBody(World world) {
        BodyDef bodyDef = createBodyDef();
        body = world.createBody(bodyDef);

        // Define a circular shape and fixture for the body
        CircleShape shape = createShape();
        FixtureDef fixtureDef = createFixtureDef(shape);

        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose(); // Dispose the shape after creating the fixture
    }

    // Create and return a BodyDef for the RedBird
    private BodyDef createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // The bird will be affected by gravity
        bodyDef.position.set(2f, -1f); // Initial position in Box2D world units
        bodyDef.fixedRotation = false; // Allow rotation
        bodyDef.linearDamping = 0.4f; // Linear damping to slow down the bird
        return bodyDef;
    }

    // Create and return a CircleShape for the RedBird's physics body
    private CircleShape createShape() {
        CircleShape shape = new CircleShape();
        shape.setRadius(birdRadius / 100f); // Convert pixels to Box2D units
        return shape;
    }

    // Create and return a FixtureDef with appropriate properties for the RedBird
    private FixtureDef createFixtureDef(CircleShape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Density affects how mass is calculated
        fixtureDef.restitution = 0.6f; // Bounciness
        fixtureDef.friction = 0.5f; // Surface friction
        return fixtureDef;
    }

    // Method to set the RedBird's position in the Box2D world
    public void setBodyPosition(float x, float y) {
        body.setTransform(x, y, body.getAngle()); // Set the position and maintain the current angle of the body
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the red bird texture based on its Box2D body position
        batch.draw(
            redBirdTexture,
            body.getPosition().x * 100 - birdRadius,
            body.getPosition().y * 100 - birdRadius,
            birdRadius * 2, birdRadius * 2
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta); // Call the parent class' act method
    }

    // Dispose the texture when no longer needed
    public void dispose() {
        redBirdTexture.dispose();
    }

    public Body getBody() {
        return body; // Getter for the Box2D body
    }

    public float getRadius() {
        return birdRadius; // Getter for the bird radius
    }

    public boolean isLaunched() {
        return isLaunched; // Getter for the launched state
    }

    public void setLaunched(boolean launched) {
        isLaunched = launched; // Setter for the launched state
    }

    public void setStatic() {
        body.setType(BodyDef.BodyType.StaticBody); // Set the body type to Static
    }

    public void setDynamic() {
        body.setType(BodyDef.BodyType.DynamicBody); // Set the body type to Dynamic
    }
}
