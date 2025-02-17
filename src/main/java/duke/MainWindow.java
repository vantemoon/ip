package duke;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.JPG"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Jarvis.JPG"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke duke) {
        this.duke = duke;
        greet();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        try {
            String input = userInput.getText();
            String response = duke.getResponse(input);
            if (response.equals("exit")) {
                Main.exit();
            }
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            userInput.clear();
        } catch (DukeException dukeException) {
            dialogContainer.getChildren().add(DialogBox.getDukeDialog(dukeException.getMessage(), dukeImage));
        }
    }

    /**
     * Greets the user.
     */
    @FXML
    private void greet() {
        String greetingMessage = duke.getResponse("greet");
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(greetingMessage, dukeImage));
    }
}
