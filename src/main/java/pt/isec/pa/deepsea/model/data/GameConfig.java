package pt.isec.pa.deepsea.model.data;

public interface GameConfig {
    // SeaSurface Grid
    int DEFAULT_SEASURFACE_ROWS = 10;
    int DEFAULT_SEASURFACE_COLS = 10;

    // SeaTrench Grid
    int DEFAULT_SEATRENCH_ROWS = 14;
    int DEFAULT_SEATRENCH_COLS = 8;

    // SeaFloor Grid
    int DEFAULT_SEAFLOOR_ROWS = 12;
    int DEFAULT_SEAFLOOR_COLS = 12;

    // Ship
    int SHIP_MAX_FUEL = 1000;

    // Number of integrity points restored per 1 mineral when repairing a drone
    int REPAIR_INTEGRITY_PER_MINERAL = 10;
    // Minimum minerals required to perform any repair
    int MIN_REPAIR_COST = 1;
    // Price in minerals per 1 unit of ship fuel when buying fuel in the shop
    int FUEL_PRICE_PER_UNIT = 1;

    // Drone
    int MAX_DRONES = 5;
    int START_DRONES = 3;
    int DRONE_MAX_FUEL = 100;
    int DRONE_MAX_INTEGRITY = 100;
    int DRONE_PRICE = 25;

    // Upgrade prices
    int SHIP_FUEL_UPGRADE_PRICE_PER_UNIT = 2; // minerals per fuel unit
    int DRONE_FUEL_UPGRADE_PRICE_PER_UNIT = 1; // minerals per fuel unit
    int DRONE_HULL_UPGRADE_PRICE_PER_POINT = 2; // minerals per integrity point

    // Obstacles
    int MAX_OBSTACLES = 5;
    int MAX_MONSTERS = 3;
    int MAX_ARTIFACTS = (int) (DEFAULT_SEASURFACE_ROWS * DEFAULT_SEASURFACE_COLS);
    int MAX_CELL_ARTIFACTS = 4;

    // Puzzle
    int PUZZLE_SIZE = 4;
    int PUZZLE_MAX_MOVES = 2;
    int PUZZLE_SHUFFLE_MOVES = 1;

    // Consumos
    int FUEL_COST_SHIP_MOVE = 5;
    int FUEL_COST_DRONE_MOVE = 2;

    // Resourses
    int MAX_MINERAL_VALUE = 200;

    // --- Modo Defesa ---
    boolean DEFENSE_MODE = true;

    String LOG_FILE_NAME = "deepsea_log.txt";
    String BIN_FILE_NAME = "deepsea_file.dat";
}
