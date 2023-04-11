package controller.guiController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.data.Airport;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the map of airports page.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class MapOfRoutesController extends SideNavBarController {

  @FXML private WebView mapView;
  @FXML private WebEngine mapEngine;
  @FXML protected Label errorText;

  /** The airport that user selected to display in google map. */
  private HashMap<Integer, ArrayList<Airport>> mapAirport = new HashMap<>();

  /**
   * This method initializes the controller class.
   *
   * @param url The provided url.
   * @param resourceBundle The provided resource bundle.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    iniMap();
    mapAirport = Main.getStorage().getMapAirport();
  }

  /** This method initialises the google map. */
  public void iniMap() {
    mapEngine = mapView.getEngine();
    mapEngine.load(
        MapOfRoutesController.class.getResource("/view/googleMap.html").toExternalForm());
  }

  /** This method calls script to display the route on map. */
  public void displayRoute() {
    if (mapAirport.size() != 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[");
      mapAirport
          .get(0)
          .forEach(
              pos ->
                  stringBuilder.append(
                      String.format(
                          "{lat: %f, lng: %f}, ", pos.getLatitude(), pos.getLongitude())));
      stringBuilder.append("]");
      String scriptToExecute = "displayRoute(" + stringBuilder.toString() + ");";
      mapEngine.executeScript(scriptToExecute);
    }
  }

  public void showMap() {
    displayRoute();
  }
}
