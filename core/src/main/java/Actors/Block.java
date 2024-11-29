package Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Block extends Actor {
    private Texture texture;
    private Body body;

    public Block(World world, String texturePath, float x, float y, float width, float height) {
        super();
        texture = new Texture(Gdx.files.internal(texturePath));

        // Set the position and size of the block
        setPosition(x, y);
        setSize(width, height);

        // Create the Box2D body
        createBody(world, x, y, width, height);
    }

    private void createBody(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = createBodyDef(x, y);
        body = world.createBody(bodyDef);

        PolygonShape shape = createShape(width, height);
        FixtureDef fixtureDef = createFixtureDef(shape);

        body.createFixture(fixtureDef);
        body.setAngularDamping(0.1f);
        body.setUserData(this);
        shape.dispose();
    }

    private BodyDef createBodyDef(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Dynamic to interact with ground
        bodyDef.position.set(x / 100f, y / 100f); // Scale position to meters
        return bodyDef;
    }

    private PolygonShape createShape(float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 200f, height / 200f); // Scale size to meters (half-width and half-height)
        return shape;
    }

    private FixtureDef createFixtureDef(PolygonShape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; // Determines weight
        fixtureDef.friction = 300f; // Controls sliding
        fixtureDef.restitution = 0f; // No bounce
        return fixtureDef;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updatePosition();
        updateRotation();
    }

    private void updatePosition() {
        setPosition(body.getPosition().x * 100 - getWidth() / 2, body.getPosition().y * 100 - getHeight() / 2);
    }

    private void updateRotation() {
        setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        disposeTexture();
        disposeBody();
    }

    private void disposeTexture() {
        if (texture != null) {
            texture.dispose();
        }
    }

    private void disposeBody() {
        if (body != null) {
            body.getWorld().destroyBody(body);
        }
    }

    public void setFriction(float v) {
        body.getFixtureList().get(0).setFriction(v);
    }

    public void setDensity(float v) {
        body.getFixtureList().get(0).setDensity(v);
    }
}
