Problem Statement:

In classic arcade games, simplicity and engagement are key, yet many traditional implementations lack modularity, reusability, and scalability—making them difficult to maintain or extend. This project aims to recreate the iconic Pac-Man game using JavaFX for a modern, interactive GUI and MySQL for persistent score tracking, while applying fundamental software engineering principles and design patterns to ensure clean architecture, robust performance, and future adaptability. The goal is not only to develop a fully functional Pac-Man game, but also to demonstrate best practices in object-oriented design and MVC architecture through a well-structured, modular codebase that aligns with academic and real-world software standards.
 
Key Features:
Gameplay Features
	•	Player-controlled Pac-Man that moves across a custom-designed maze.
	•	Pellets and Power Pellets for scoring and temporary ghost vulnerability.
	•	Power-Up Mode with timer-based state change and visual feedback.
	•	Collision Detection between Pac-Man, pellets, and ghosts.
User Interface – Interactive game UI built using JavaFX
	•	Real-time score display
	•	Game canvas for maze, player, ghosts and pellets
	•	Start and scoreboard screens for user interaction
Database Integration
	•	MySQL backend to store and retrieve player scores.
	•	Top Scores display with player name, score, and timestamp.
	•	ScoreDAO class for clean and encapsulated DB operations.


Modularity and maintainability
	•	Well-structured package organization (model, view, controller, db, observer, factory).
	•	Easily extensible to add new entities or gameplay features.
	•	Reusable components through interface-driven and pattern-oriented design.
Software Design
	•	MVC Architecture Pattern: Separation of concerns between model, view, and controller for clean and maintainable code.
	•	Design Pattern Integration:
	•	Singleton: Ensures only one GameModel and ScoreDAO exists
	•	Factory: Created game entities like Player and Ghost dynamically
	•	Observer: Notifies when pacman collides with ghost
	•	DAO (Data Access Object): Handles database operation separately from business logic


Architecture Patterns

Model – View – Controller Pattern (MVC)

	•	Model:
Handles business logic and data management. So in the project:
	•	GameModel – Manages game logic like score, player state and collisions
	•	ScoreDAO – Interfaces with the database to insert and retrieve scores

	•	View:
Responsible for rendering the user interface. So in the project:
	•	GameView – Renders the Pacman game elements on the screen
	•	ScoreBoard, LeaderboardRow – Show score and leaderboard visually using JavaFX

	•	Controller:
Bridges the Model and View, handling user input and updating the model/view. So in the project:
	•	GameController – Processes key inputs, updates game state, and communicates between the  model and view 

This architecture makes the app more modular, testable, and maintainable.
	


Design Principles 

	•	Single Responsibility Principle:
Each class has a clear responsibility (GameModel, ScoreBoard, ScoreDAO, etc.).

	•	Open/Closed Principle:
Adding new views or DB features doesn’t require modifying core logic classes.

	•	Liskov Substitution Principle:
Any future subclass (like custom views or game elements) could be swapped safely.

	•	Dependency Inversion Principle:
Not heavily abstracted, but with DAO and interfaces, it's moving in that direction.

	•	Separation of Concerns:  Clean distinction between Model, View, and Controller layers, each doing its own job.



Design Patterns

	•	Observer Pattern:
Ensures the ScoreBoard updates in real-time as the game state changes.
GameModel notifies ScoreBoard about score and power mode changes.

	•	DAO Pattern (Data Access Object):
Separates low-level database access logic from business logic.
ScoreDAO is a textbook DAO that manages all interactions with the MySQL database.
All DB access into ScoreDAO is abstracted which makes the project more maintainable and testable.

	•	Singleton Pattern: The Singleton Pattern ensures that a class has only one instance throughout the application and  provides a global point of access to it.
In our Pac-Man project, the ScoreDAO class uses the Singleton pattern to ensure that only one database access object exists. This avoids redundant connections and enforces consistent  interaction with the score database.

	•	Factory Pattern: The Factory Pattern is used to encapsulate the creation of game entities like Player and Ghost,  providing a flexible and scalable way to manage object creation.
This pattern is applied via PlayerFactory and GhostFactory, which are utilized in the GameModel  class and the GameEntityFactory to dynamically generate instances based on the entity type (e.g., "pacman" or ghost types like "red" or "pink").

