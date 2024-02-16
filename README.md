# Mini games
## Overview
This project, developed as part of a university course, encompasses a suite of two classic games: Nim and Connect Four. 
Each game is designed to allow 1 vs 1 gameplay between real players or against an artificially intelligent (AI) opponent developed as part of the project. 
The primary goal was to implement the logic for each game, extending given base code to create fully interactive and engaging games.

## Features
- **Two Game Modes:** The project includes two distinct games, Nim and Connect Four, each with unique strategies and gameplay mechanics.
- **Versatile Player Options:** Players can choose to compete against another person or face an AI opponent, demonstrating the project's flexibility and adaptability.
- **Custom AI Logic:** The AI for each game was meticulously developed to provide a challenging yet beatable opponent, showcasing the application of basic AI principles.
- **Interactive User Interface:** A simple yet functional interface allows players to easily interact with the game, choose their moves, and view the game state.

## Structure
The project is structured into several Java classes, each serving a specific role in the game's functionality:

- `Controleur.java`: Acts as the main controller for the game suite, managing game selection and player interactions.
- `ControleurNim.java` & `ControleurPuissance4.java`: Specialized controllers for Nim and Connect Four, respectively, handling game-specific logic and flow.
- `CoupGrille.java`, `CoupNim.java`, `CoupInvalideException.java`: Define the moves available in each game and manage invalid move exceptions.
- `Grille.java`, `Tas.java`: Represent the game board for Connect Four and the collection of objects for Nim, respectively.
- `Joueur.java`: Abstracts a player in the game, with extensions for human and AI players.
- `Ihm.java`: Manages the input and output, providing an interface between the game and the player.

## Running the Games
Ensure Java is installed, then compile and run `Main.java` to start:
```
javac Main.java
java Main
```

Choose your game and opponent type via the command line prompts.

## Development and Contributions
This project was a collaborative effort in a university setting, focusing on Java programming, object-oriented design, and basic AI.
Contributions varied, emphasizing teamwork and individual learning experiences.
