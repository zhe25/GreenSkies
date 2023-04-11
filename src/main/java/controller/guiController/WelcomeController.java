package controller.guiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The Controller class which contains the controls for the Welcome page, the first page shown to
 * users.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class WelcomeController {
  /** The continue button. */
  @FXML public Button continueButton;
  /** The info button. */
  @FXML public Button InfoButton;

  /**
   * This method handles the event for continuing to the main screen of the application.
   *
   * @param event The event to handle.
   * @throws IOException
   */
  @FXML
  public void continueToMainScreen(ActionEvent event) throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root =
        FXMLLoader.load(
            getClass().getResource("/view/upload/upload.fxml")); // open the Upload Data page
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method handles the event for continuing to the info screen of the application.
   *
   * @param event The event to handle.
   * @throws IOException
   */
  @FXML
  public void toInfoScreen(ActionEvent event) throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/info.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }
}
