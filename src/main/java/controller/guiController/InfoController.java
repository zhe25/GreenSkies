package controller.guiController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The controller class which contains the controls for the help page on the welcome page.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class InfoController {

  @FXML public Button BackButton;

  /**
   * This method is used to return to the welcome page via the back button on the upload page.
   *
   * @throws IOException This throws an IOException.
   */
  @FXML
  public void BackToPreviousWindow() throws IOException {
    Stage stage1 = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/welcome.fxml"));
    Scene scene = new Scene(root);
    stage1.setScene(scene);
  }
}
