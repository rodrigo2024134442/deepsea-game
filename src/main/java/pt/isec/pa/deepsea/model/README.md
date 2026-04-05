# Package: pt.isec.pa.deepsea.model

This package represents the top-level model layer of the **Deep Sea Mining** game. It acts as the main entry point for the game's data model, bridging the data structures and the rest of the application.

Its main responsibilities are:
* Initialising and aggregating all core game components through the `Game` class, which serves as the central access point to the game state.
* Providing a clean interface between the data model and the application's other layers (such as the UI), ensuring that no gameplay logic leaks into the interface layer.

The classes and interfaces that belong in this package are:
* The `Game` class, which initialises and provides access to the surface grid and the ship
* State management classes (to be implemented in future stages)

All user interface logic is excluded from this package — it contains only the model entry point and its direct dependencies.