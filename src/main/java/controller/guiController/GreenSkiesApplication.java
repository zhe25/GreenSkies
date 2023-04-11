package controller.guiController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The controller class which contains the controls for starting the application.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class GreenSkiesApplication extends Application {

  /** The main window for the application */
  private static Stage primaryStage;

  /**
   * This method starts the application's GUI
   *
   * @param primaryStage The primary stage used.
   * @throws IOException This throws an IOException.
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    this.primaryStage = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("/view/welcome.fxml"));
    primaryStage.setTitle("Green Skies");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * The method returns the primary stage of the application.
   *
   * @return the stage for the application's main window.
   */
  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  /**
   * This method is where the application is called from.
   *
   * @param args The passed in arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
