package controller.guiController;

import controller.analysis.FlightAnalyser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.data.Airport;
import model.data.Route;
import org.controlsfx.control.textfield.TextFields;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the analyse page.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AnalyseController extends SideNavBarController implements Initializable {

  @FXML public TextField pathSource1;
  @FXML public TextField pathDestination1;
  @FXML public TextField pathSource2;
  @FXML public TextField pathDestination2;
  @FXML private Label errorText;

  /** A hash set to store some airport data. */
  private HashSet<String> airports = new HashSet<>();

  /**
   * This method is required for Initializable interface load the analyse.fxml and display all the
   * components.
   *
   * @param primaryStage The primary stage used.
   * @throws IOException
   */
  public void start(Stage primaryStage) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("/view/analyse.fxml"));
    primaryStage.setTitle("Welcome");
    primaryStage.setScene(new Scene(root, 1024, 640));
    primaryStage.show();
  }

  /**
   * This method binds the list of airports code with textfield and show like combobox.
   *
   * @param url The provided url.
   * @param resourceBundle The provided resoure bundle.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    for (Airport airport : Main.getStorage().getAirports()) {
      if (!airport.getIATA().equals("")) {
        airports.add(airport.getIATA());
      } else if (!airport.getICAO().equals("")) {
        airports.add(airport.getICAO());
      }
    }
    TextFields.bindAutoCompletion(pathSource1, airports);
    TextFields.bindAutoCompletion(pathDestination1, airports);
    TextFields.bindAutoCompletion(pathSource2, airports);
    TextFields.bindAutoCompletion(pathDestination2, airports);
  }

  /**
   * This method checks if all data has been entered correctly. If it has, it calls the analyse
   * method to analyse the routes and display the results.
   *
   * @throws IOException
   */
  public void analyseButton() throws IOException {
    errorText.setVisible(false);
    if (pathSource1.getText().isEmpty()
        || pathSource2.getText().isEmpty()
        || pathDestination1.getText().isEmpty()
        || pathDestination2.getText().isEmpty()) {
      errorText.setText("Please enter information into all fields to proceed.");
      errorText.setVisible(true);
    } else if (pathSource1.getText().equals(pathDestination1.getText())
        || pathSource2.getText().equals(pathDestination2.getText())) {
      errorText.setText("Source cannot be the same as destination.");
      errorText.setVisible(true);
    } else {
      analyse();
    }
  }

  /**
   * This method calculates distance and emissions by using two routes.
   *
   * @throws IOException
   */
  @FXML
  private void analyse() throws IOException {

    ArrayList<String> path1 = new ArrayList<>();
    ArrayList<String> path2 = new ArrayList<>();

    storage.getAnalyseDistanceResult().clear();
    storage.getAnalyseEmissionResult().clear();

    String[] equipment1 = {"abc"};
    String[] equipment2 = {"abc"};
    int check = 0;
    if (airports.contains(pathSource1.getText())
        && airports.contains(pathDestination1.getText())
        && airports.contains(pathSource2.getText())
        && airports.contains(pathDestination2.getText())) {

      check = 1;
    }

    if (check == 1) {
      path1.add((String) pathSource1.getText());
      path1.add((String) pathDestination1.getText());
      path2.add((String) pathSource2.getText());
      path2.add((String) pathDestination2.getText());

      Route route1 =
          new Route(
              null,
              0,
              (String) pathSource1.getText(),
              0,
              (String) pathDestination1.getText(),
              0,
              null,
              0,
              equipment1);
      Route route2 =
          new Route(
              null,
              0,
              (String) pathSource2.getText(),
              0,
              (String) pathDestination2.getText(),
              0,
              null,
              0,
              equipment2);
      FlightAnalyser analyser = new FlightAnalyser(route1, route2, Main.getStorage());

      storage.setAnalyseDistanceResult(analyser.getTotalDistancePath1());
      storage.setAnalyseDistanceResult(analyser.getTotalDistancePath2());
      storage.setAnalyseEmissionResult(analyser.getPath1Emission());
      storage.setAnalyseEmissionResult(analyser.getPath2Emission());
      storage.setComparedRoutes(
          new Pair(
              String.format("%s - %s", pathSource1.getText(), pathDestination1.getText()),
              String.format("%s - %s", pathSource2.getText(), pathDestination2.getText())));

      Stage stage1 = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/view/analyseResult.fxml"));
      Scene scene = new Scene(root);
      stage1.setScene(scene);
      stage1.show();
    } else {
      errorText.setText("Invalid airport code.");
      errorText.setVisible(true);
    }
  }

  /** This method resets the text for each of the paths. */
  @FXML
  public void cleanText() {
    pathSource1.clear();
    pathSource2.clear();
    pathDestination1.clear();
    pathDestination2.clear();
    errorText.setVisible(false);
  }
}
