# Package: pt.isec.pa.deepsea.model.data

This package forms the core data model of the **Deep Sea Mining** game. It contains the central configuration interface and all data structures required to represent the game state.

Its main responsibilities are:
* Defining all game constants through the `GameConfig` interface, ensuring that values such as grid dimensions, fuel capacities, obstacle limits and game modes are configurable from a single location.
* Grouping and organising all sub-packages that represent the game's entities, grids, and components, including the surface grid, marine trenches, sea floor, drones, ship, and minigame.

The classes and interfaces that belong in this package are:
* Configuration interfaces (`GameConfig`)
* Top-level data classes that do not belong to a more specific sub-package

All gameplay logic is excluded from this package. It contains only data structures and their initialisation.