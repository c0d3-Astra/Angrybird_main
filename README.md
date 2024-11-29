# AngryMain2

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

# Angry Birds Tower Game

## Overview
The Angry Birds Tower Game is a simplified version of the popular Angry Birds game. It involves randomly generated towers made of blocks, where pigs are placed on top of some towers. The objective is to launch birds at the towers to knock down the blocks and hit the pigs. The game uses Box2D physics for collision detection and LibGDX for game development.

## Features
- **Random Level Generation**: Each level is generated randomly with varying tower heights, block textures, and pig placements.
- **Box2D Physics**: Uses Box2D for realistic physics and collision detection.
- **Multiple Towers**: The game generates between 5 to 9 towers per level.
- **Multiple Block Textures**: Towers are built using randomly chosen block textures such as wood, ice, and stone.
- **Pigs on Towers**: Pigs appear randomly on top of towers, and the player needs to hit them to score points.
- **Bird Launching**: The player launches birds at towers to knock them over and hit pigs.

## Requirements
- **LibGDX**: The game is developed using the LibGDX game development framework.
- **Box2D**: Box2D is used for handling the physics of the game (collision detection, gravity, etc.).

## How to Run the Game

1. Clone the repository or download the source files.
2. Set up the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse).
3. Make sure you have LibGDX properly configured. If you don't have it set up, follow the official LibGDX setup instructions: https://libgdx.com/dev/
4. Run the `Main` class to start the game.

### Controls:
- **Mouse Drag**: Click and drag to aim.
- **Mouse Release**: Release to launch the bird.

## Game Flow
1. **Game Start**: When the game begins, a random number of towers (between 5 and 9) is generated.
2. **Tower Creation**: Each tower is constructed with a random height (between 1 and 8 blocks) and a random texture (wood, ice, or stone).
3. **Pig Placement**: Pigs are randomly placed on the towers, with a chance of up to 3 pigs in the game.
4. **Bird Launch**: The player aims and launches birds at the towers.
5. **Physics Interaction**: Box2D handles the physics interactions when birds hit blocks or pigs.
6. **Game Over**: The game continues until all pigs are hit or the player decides to quit.

## Random Tower Generation Logic

### Tower Creation:
1. A random number of towers (between 5 and 9) is generated.
2. Each tower is placed horizontally with random heights ranging from 1 to 8 blocks.
3. A random texture is selected for each block in the tower, which can be wood, ice, or stone.
4. Each tower has a possibility of having a pig placed on top (up to 3 pigs in total).

### Physics:
- The towers, blocks, and pigs are placed in the Box2D world.
- Blocks are stacked vertically, and each has its own physics body for collision detection.
- Pigs are also placed in the Box2D world and are tracked for scoring.


## Key Classes

- **GameManager**: Handles level generation, creating towers, pigs, and launching birds. This is where the random tower creation occurs.
- **Block**: Represents individual blocks that make up the towers. Each block is given a random texture and placed in the world.
- **Pig**: Represents a pig placed on top of towers. Pigs are randomly added to towers during the level generation.
- **Bird**: Represents the bird that the player launches at the towers. The bird uses physics to interact with the world.

## Customization
- **Tower Generation**: Modify the `createTower` method in the `GameManager` class to change the randomization parameters for tower heights, block types, or pig placement.
- **Textures**: You can replace the `wood1.png`, `ice1.png`, and `stone1.png` files in the `/assets` folder with custom textures.
- **Bird Physics**: You can adjust the behavior of the bird by modifying the `Bird.java` class, changing the launch velocity, or how the bird interacts with towers.

## Troubleshooting
- **Error: Missing LibGDX setup**: Ensure that you have properly set up LibGDX and Box2D. Follow the official LibGDX setup guide if you run into issues.
- **No Image Assets**: If images like `wood1.png`, `ice1.png`, or `stone1.png` are missing, download or create placeholder images and place them in the `/assets` folder.

## License
This project is open-source and free to use. You can modify and distribute it as per your needs.

## Acknowledgements
- LibGDX: The primary framework used for this game.
- Box2D: Physics engine for handling the game’s physics interactions.
- Angry Birds: Inspiration for the game concept and mechanics.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

# AngryMan2

AngryMan2 is a Java-based game developed using the libGDX framework. This project includes multiple screens such as the main menu, level selection, and loading screens. The game is designed to be compatible with GraalVM for native image generation and uses the HyperLap2D extension for scene management.

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Code Explanation](#code-explanation)

## Getting Started

To get a local copy up and running, follow these simple steps.
- Use gradle to build and run the repo.

### Prerequisites

- Java 11
- Gradle
- GraalVM (if you want to build a native image)

## Project Structure

- `core`: Contains the core game logic and assets.
- `lwjgl3`: Contains the desktop launcher and configurations for LWJGL3.
- `assets`: Contains game assets like images, sounds, and fonts.

## Dependencies

The project uses the following dependencies:

- [libGDX](https://libgdx.badlogicgames.com/)
- [GraalVM](https://www.graalvm.org/)
- [Ashley](https://github.com/libgdx/ashley)
- [Box2DLights](https://github.com/libgdx/box2dlights)
- [HyperLap2D](https://github.com/rednblackgames/libgdx-hyperlap2d)

Dependencies are managed using Gradle. You can find them in the `build.gradle` files located in the root, `core`, and `lwjgl3` directories.

## Code Explanation

### Main Class

The `Main` class extends `Game` and sets the initial screen to `LoadingScreen`.

```java
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
    }
}
```

### LevelScreen Class

The `LevelScreen` class implements `Screen` and manages the level selection screen. It uses `Scene2D` for UI elements like buttons.

```java
public class LevelScreen implements Screen {
    // Initialization and setup code
    public LevelScreen(Main game) {
        // Setup camera, viewport, batch, and stage
        // Add buttons and listeners
    }

    @Override
    public void render(float delta) {
        // Clear screen, update camera, and draw stage
    }

    @Override
    public void dispose() {
        // Dispose resources
    }
}
```

### L1 Class

The `L1` class extends `ScreenAdapter` and uses HyperLap2D for scene management. It loads scenes and assets using `SceneLoader` and `AssetManager`.

```java
public class L1 extends ScreenAdapter {
    public L1(Main game) {
        // Initialize camera, batch, and scene loader
    }

    @Override
    public void show() {
        // Load assets and scenes
    }

    @Override
    public void render(float delta) {
        // Render the scene and handle input
    }

    @Override
    public void dispose() {
        // Dispose resources
    }
}
```

## Every Component is used as texture and sprites
- Rest project is handle with Hyperlap2d a Scene manager for Working game. 

