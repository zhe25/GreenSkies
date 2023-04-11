package controller.guiController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.data.Storage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the analyseResult page.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AnalyseResultController implements Initializable {

  @FXML public Text DistanceRoute1;
  @FXML public Text DistanceRoute2;
  @FXML public Text EmissionRoute1;
  @FXML public Text EmissionRoute2;
  @FXML public Text DistanceDifference;
  @FXML public Text EmissionDifference;
  @FXML private Button BackButton;
  @FXML private Text route1DistanceHeading;
  @FXML private Text route2DistanceHeading;
  @FXML private Text route1EmissionsHeading;
  @FXML private Text route2EmissionsHeading;

  /**
   * This method is required for Initializable interface show distance and emissions of each route
   *
   * @param url The provided url.
   * @param resourceBundle The provided resource bundle.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Storage storage = Main.getStorage();
    route1DistanceHeading.setText(
        String.format("Distance of route 1 (%s):", storage.getComparedRoutes().getKey()));
    route2DistanceHeading.setText(
        String.format("Distance of route 2 (%s):", storage.getComparedRoutes().getValue()));
    route1EmissionsHeading.setText(
        String.format("Emissions of route 1 (%s):", storage.getComparedRoutes().getKey()));
    route2EmissionsHeading.setText(
        String.format("Emissions of route 2 (%s):", storage.getComparedRoutes().getValue()));
    if (storage.getAnalyseDistanceResult().size() == 0
        || storage.getAnalyseEmissionResult().size() == 0) {

      DistanceRoute1.setText("NaN");
      DistanceRoute2.setText("NaN");
      EmissionRoute1.setText("NaN");
      EmissionRoute2.setText("NaN");
    } else {
      String DistanceRoute1Text = String.format("%.2f", storage.getAnalyseDistanceResult().get(0));
      DistanceRoute1.setText(DistanceRoute1Text);

      String DistanceRoute2Text = String.format("%.2f", storage.getAnalyseDistanceResult().get(1));
      DistanceRoute2.setText(DistanceRoute2Text);

      String EmissionRoute1Text = String.format("%.2f", storage.getAnalyseEmissionResult().get(0));
      EmissionRoute1.setText(EmissionRoute1Text);

      String EmissionRoute2Text = String.format("%.2f", storage.getAnalyseEmissionResult().get(1));
      EmissionRoute2.setText(EmissionRoute2Text);
    }

    String DistanceDifferenceText =
        String.format(
            "%.2f",
            Math.abs(
                storage.getAnalyseDistanceResult().get(0)
                    - storage.getAnalyseDistanceResult().get(1)));
    DistanceDifference.setText(DistanceDifferenceText);
    String EmissionDifferenceText =
        String.format(
            "%.2f",
            Math.abs(
                storage.getAnalyseEmissionResult().get(0)
                    - storage.getAnalyseEmissionResult().get(1)));
    EmissionDifference.setText(EmissionDifferenceText);
  }

  /**
   * This method is to return back to the AnalysePage.
   *
   * @throws IOException This throws and IOException.
   */
  public void GoBackToAnalyse() throws IOException {

    Stage stage = (Stage) BackButton.getScene().getWindow();
    stage.close();
  }
}
