package pt.isec.pa.deepsea.model.data;

public interface GameConfig {
    // SeaSurface Grid
    int DEFAULT_SEASURFACE_ROWS = 10;
    int DEFAULT_SEASURFACE_COLS = 10;

    // SeaTrench Grid
    int DEFAULT_SEATRENCH_ROWS = 10;
    int DEFAULT_SEATRENCH_COLS = 10;

    // SeaFloor Grid
    int DEFAULT_SEAFLOOR_ROWS = 10;
    int DEFAULT_SEAFLOOR_COLS = 10;

    // Ship
    int SHIP_MAX_FUEL = 1000;

    // Drone
    int MAX_DRONES = 3;
    int DRONE_MAX_FUEL = 200;
    int DRONE_MAX_INTEGRITY = 100;

    // Obstacles
    int MAX_OBSTACLES = 5;
    int MAX_MONSTERS = 3;
    int MAX_ARTIFACTS = (int) (DEFAULT_SEASURFACE_ROWS * DEFAULT_SEASURFACE_COLS * 1.5);
    int MAX_CELL_ARTIFACTS = 4;

    // Puzzle
    int PUZZLE_SIZE = 4;
    int PUZZLE_MAX_MOVES = 1;
    int PUZZLE_SHUFFLE_MOVES = 10;

    // Consumos
    int FUEL_COST_SHIP_MOVE = 5;
    int FUEL_COST_DRONE_MOVE = 2;

    // Resourses
    int MAX_MINERAL_VALUE = 20;

    // --- Modo Defesa ---
    boolean DEFENSE_MODE = false;

    String LOG_FILE_NAME = "deepsea_log.txt";
}
