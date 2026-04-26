package pt.isec.pa.deepsea.model;

import pt.isec.pa.deepsea.model.data.GameConfig;

import java.io.BufferedWriter;
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
     * Saves all logged messages to a text file, one message per line.
     * The file name is defined in {@link GameConfig#LOG_FILE_NAME}.
     *
     * @return true if saved successfully, false otherwise
     */
    public boolean saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GameConfig.LOG_FILE_NAME, true))) {
            for (String message : messages) {
                writer.write(message);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Returns the number of logged messages.
     *
     * @return message count
     */
    public int size() {
        return messages.size();
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