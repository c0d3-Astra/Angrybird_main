package io.github.some_example_name;

import Actors.Block;
import Actors.Pig;
import Actors.RedBird;
import Actors.Slingshot;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameMananger {
    private Stage gameStage;
    private List<RedBird> birdList;
    private List<Pig> pigList;
    private Map<Body, Actor> bodyToActorMap = new HashMap<>();
    private List<Body> bodiesPendingRemoval = new ArrayList<>();
    private Slingshot slingshot;
    private int activeBirdIndex = 0;
    private Main game;

    public GameMananger(Main game, World world, Stage stage) {
        this.game = game;
        this.gameStage = stage;

        // Set gravity for the world
        world.setGravity(new Vector2(0, -5f));
        world.setContactListener(new GameContactListener());

        // Initialize birds
        birdList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RedBird bird = new RedBird(world);
            bird.setStatic();
            bodyToActorMap.put(bird.getBody(), bird);
            bird.setPosition(2.5f + i * 0.2f, 3f);  // Set initial bird position
            birdList.add(bird);
        }

        // Initialize Slingshot
        slingshot = new Slingshot(world, new Vector2(2f, 0.2f), birdList.get(activeBirdIndex), this);
        stage.addActor(slingshot);
        for (RedBird bird : birdList) {
            stage.addActor(bird);
        }

        prepareBirdQueue();

        // Define block textures for towers
        String[] blockTextures = { "wood1.png", "ice1.png", "stone1.png" };

        // Initialize pigs and randomize the tower setup
        pigList = new ArrayList<>();
        Random random = new Random();
        int numberOfTowers = random.nextInt(5) + 5;  // Random number of towers between 5 and 9

        float startX = 800;  // Starting X position for towers
        float startY = 350;  // Starting Y position for towers
        int pigsAdded = 0;
        for (int i = 0; i < numberOfTowers; i++) {
            boolean placePigOnTop = pigsAdded < 3 || random.nextBoolean();
            if (placePigOnTop) {
                pigsAdded++;
            }
            createTower(world, stage, startX += 60, startY, blockTextures, placePigOnTop);
        }
    }

    private void createTower(World world, Stage stage, float xPosition, float yPosition, String[] blockTextures, boolean addPigOnTop) {
        Random random = new Random();
        int towerHeight = random.nextInt(8) + 1;  // Random tower height between 1 and 8

        float blockWidth = 40f;  // Block width
        float blockHeight = 40f; // Block height

        float towerTopY = yPosition;  // Track the top Y position of the tower

        for (int i = 0; i < towerHeight; i++) {
            float blockY = yPosition + i * blockHeight;  // Stack blocks vertically

            // Randomly select a texture for the block
            String texturePath = blockTextures[random.nextInt(blockTextures.length)];

            Block block = new Block(world, texturePath, xPosition, blockY, blockWidth, blockHeight);
            bodyToActorMap.put(block.getBody(), block);
            block.setDensity(0.2f);  // Low density for stability
            block.setFriction(0.3f); // Lower friction for sliding
            stage.addActor(block);

            towerTopY = blockY;  // Update the tower's top Y position
        }

        // Optionally add a pig on top of the tower
        if (addPigOnTop && towerHeight > 0) {
            Pig pig = new Pig(world, xPosition, towerTopY + blockHeight);
            bodyToActorMap.put(pig.getBody(), pig);
            pig.setSize(35f, 35f);  // Set pig size
            pigList.add(pig);
            stage.addActor(pig);
        }
    }

    public void update(float deltaTime) {
        // Update pigs
        for (Pig pig : pigList) {
            pig.update(deltaTime);
        }

        // Temporary list to track pigs to remove
        List<Pig> pigsToRemove = new ArrayList<>();

        // Remove bodies marked for deletion
        for (Body body : bodiesPendingRemoval) {
            Actor actor = bodyToActorMap.get(body);
            if (actor != null) {
                gameStage.getActors().removeValue(actor, true);  // Remove actor from the stage
                bodyToActorMap.remove(body);  // Remove mapping from body to actor

                // If the actor is a Pig, remove it from the pig list
                if (actor instanceof Pig) {
                    pigsToRemove.add((Pig) actor);  // Add the pig to the removal list
                }
            }
            body.getWorld().destroyBody(body);  // Destroy the body in the world
        }

        // Remove pigs from the pig list
        pigList.removeAll(pigsToRemove);

        // Clear the removal queue
        bodiesPendingRemoval.clear();
    }

    public void onBirdLaunched() {
        // Mark the current bird as launched
        birdList.get(activeBirdIndex).setLaunched(true);

        // Move to the next bird in the list if the current bird is launched
        while (activeBirdIndex < birdList.size() - 1 && birdList.get(activeBirdIndex).isLaunched()) {
            activeBirdIndex++;
        }

        // If the last bird has been launched, prevent launching further birds
        if (activeBirdIndex == birdList.size() - 1 && birdList.get(activeBirdIndex).isLaunched()) {
            activeBirdIndex++;
        }

        // Delay to check the game state after bird launch
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                evaluateGameStateAfterBirdLaunch();
            }
        }, 3f);  // Delay by 3 seconds
    }

    private void evaluateGameStateAfterBirdLaunch() {
        int remainingBirds = Math.max(0, birdList.size() - activeBirdIndex);
        int remainingPigs = pigList.size();

        System.out.println("Game State Check...");
        System.out.println("Remaining Birds: " + remainingBirds);
        System.out.println("Remaining Pigs: " + remainingPigs);

        if (remainingPigs == 0) {
            System.out.println("+++++++++++++You Win!++++++++++++");
            game.setScreen(new EndScreen(game, 1));
        } else if (remainingBirds == 0) {
            System.out.println("++++++++++++++You Lose!+++++++++++++");
            game.setScreen(new EndScreen(game, 2));
        } else {
            // Prepare the next bird for the slingshot if birds are still available
            prepareBirdQueue();
            birdList.get(activeBirdIndex).setBodyPosition(2.5f, 3f);  // Reset bird position
            slingshot.setBird(birdList.get(activeBirdIndex));
            System.out.println("=============Next bird is ready!===============");
        }
    }

    private void prepareBirdQueue() {
        if (activeBirdIndex < birdList.size() && !birdList.get(activeBirdIndex).isLaunched()) {
            birdList.get(activeBirdIndex).setBodyPosition(2.5f, 3f);  // Reset bird position
        }
    }

    public void dispose() {
        for (RedBird bird : birdList) {
            bird.dispose();
        }
        for (Pig pig : pigList) {
            pig.dispose();
        }
        slingshot.dispose();
    }

    private class GameContactListener implements ContactListener {
        @Override
        public void beginContact(Contact contact) {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();
            handleCollision(fixtureA, fixtureB);
        }

        @Override
        public void endContact(Contact contact) {
            // Not used
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
            // Not used
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
            // Not used
        }

        private void handleCollision(Fixture fixtureA, Fixture fixtureB) {
            Object userDataA = fixtureA.getBody().getUserData();
            Object userDataB = fixtureB.getBody().getUserData();

            // Collision with a pig
            if ((userDataA instanceof RedBird && userDataB instanceof Pig) ||
                (userDataB instanceof RedBird && userDataA instanceof Pig)) {
                Body pigBody = userDataA instanceof Pig ? fixtureA.getBody() : fixtureB.getBody();
                bodiesPendingRemoval.add(pigBody);
                L1.score += 100;
                System.out.println("Bird hits the pig!");
            }

            // Collision with a block
            if ((userDataA instanceof RedBird && userDataB instanceof Block) ||
                (userDataB instanceof RedBird && userDataA instanceof Block)) {
                Body blockBody = userDataA instanceof Block ? fixtureA.getBody() : fixtureB.getBody();
                bodiesPendingRemoval.add(blockBody);
                L1.score += 20;
                System.out.println("Bird hits the block!");
            }
        }
    }
}
