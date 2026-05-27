package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.GameConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton class responsible for logging all operations performed
 * on the game data model.
 *
 * <p>Each message is automatically timestamped with the date and time
 * it was received. The log supports consultation, saving to a text file,
 * and clearing of messages.</p>
 *
 * @author Leonardo Matos Ferreira - 2024113113
 * @version v1.0.0 (Stage 2)
 */
public class DeepSeaLog {
    private static DeepSeaLog instance;
    private final List<String> messages;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String LOGS_DIRECTORY = "logs";

    /**
     * Private constructor — prevents external instantiation.
     */
    private DeepSeaLog() {
        this.messages = new ArrayList<>();
    }

    /**
     * Returns the single instance of DeepSeaLog.
     * Creates it on first call (lazy initialization).
     *
     * @return singleton instance
     */
    public static DeepSeaLog getInstance() {
        if (instance == null) {
            instance = new DeepSeaLog();
        }
        return instance;
    }

    /**
     * Returns the logs directory path.
     *
     * @return path to logs directory
     */
    public static String getLogsDirectory() {
        return LOGS_DIRECTORY;
    }

    /**
     * Logs a message, automatically associating the current date and time.
     *
     * @param message message to log
     */
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        messages.add("[" + timestamp + "] " + message);
    }

    /**
     * Returns an unmodifiable view of all logged messages.
     *
     * @return list of log messages
     */
    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    /**
     * Clears all logged messages.
     */
    public void clear() {
        messages.clear();
    }

    /**
     * Returns the number of logged messages.
     *
     * @return message count
     */
    public int size() {
        return messages.size();
    }

    /**
     * Saves the current log messages to a file in the logs directory.
     *
     * @return {@code true} if the file was written successfully
     */
    public boolean saveToFile() {
        File dir = new File(LOGS_DIRECTORY);
        if (!dir.exists() && !dir.mkdirs()) {
            return false;
        }

        File file = new File(dir, GameConfig.LOG_FILE_NAME);
        try (FileWriter writer = new FileWriter(file)) {
            for (String message : messages) {
                writer.write(message + System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append("\n");
        }
        return sb.toString();
    }
}