# Package: pt.isec.pa.deepsea

This package is the root package of the **Deep Sea Mining** application. It serves as the top-level entry point for the entire project, containing the main application class that launches the game.

Its main responsibilities are:
* Providing the application entry point through the `DeepSeaApp` class, which contains the `main` method responsible for starting the game.
* Acting as the root namespace that organises all sub-packages, including the model, UI, and resources.

The classes that belong in this package are:
* `DeepSeaApp` — the main class that initialises and launches the application

All game logic, data structures and interface components are delegated to the appropriate sub-packages (`model`, `model.data`, `ui`, etc.).