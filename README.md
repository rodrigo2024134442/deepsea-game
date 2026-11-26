# Deep Sea Mining

A deep sea mining game built in **Java 21 and JavaFX**. You command a research ship on the sea surface, deploy mining drones to the sea floor and the deep trench, mine minerals, retrieve artifacts, solve a puzzle mini-game, and spend your earnings in the shop on fuel, repairs, new drones and upgrades, while avoiding rocks, sea creatures and trench currents.
Developed for **Advanced Programming** at ISEC - Polytechnic University of Coimbra (2025/26).

## Highlights

- **State machine** for the game flow, with one state per phase: Surface, Bottom, Trench Descent, Trench Ascent, Puzzle Game and Shop (`GameContext`, `IGameState`, `GameStateAdapter`)
- **Design patterns**: State, Facade (`DeepSeaManager`), Observer (property change events to update the UI), Singleton (resource managers and game log), Factory and Adapter
- Strict separation between the **model** and the **JavaFX interface**: no game logic in the UI
- Several game views drawn on JavaFX canvases (surface, sea floor, trench, puzzle), a shop, and side panels for the ship, drones and commands
- Save and load games (serialisation) and a list of recent games
- Sound effects and video cut-scenes with JavaFX Media
- **JUnit 5** unit tests for the model, state machine, drones, entities and mini-game
- UML class diagram, state diagram and full report in [`reports/`](reports)

## Tech

Java 21, JavaFX 21 (controls, media), Maven, JUnit 5, UML

## Run

```
mvn clean javafx:run
```

Run the tests with `mvn test`.

## Authors

- Rodrigo Costa ([@rodrigo2024134442](https://github.com/rodrigo2024134442))
- [@joao2024126292](https://github.com/joao2024126292)
- [@leonardo2024113113](https://github.com/leonardo2024113113)