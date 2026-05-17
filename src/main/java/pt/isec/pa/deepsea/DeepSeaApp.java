package pt.isec.pa.deepsea;

import javafx.application.Application;
import pt.isec.pa.deepsea.ui.MainJFX;

/**
 * Entry point of the Deep Sea Mining application.
 * Launches the JavaFX runtime, which in turn instantiates {@link MainJFX}.
 *
 * <p>Kept separate from {@link MainJFX} so the JavaFX runtime is only
 * initialised when this class is the actual entry point — useful for
 * unit tests that want to instantiate the model without JavaFX.</p>
 */
public class DeepSeaApp {
    public static void main(String[] args) {
        Application.launch(MainJFX.class, args);
    }
}