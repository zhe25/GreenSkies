package controller.guiController;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import java.util.HashSet;
import java.util.Optional;

/**
 * This class deals with the methods for alert pop up windows.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AlertPopUp {

  /**
   * This method displays a warning to the user before they delete an entry or file.
   *
   * @param dataType the type of entry or file that the user is trying to delete.
   * @return the option the user chooses (ok to delete, cancel to go back).
   */
  public static Optional<ButtonType> showDeleteAlert(String dataType) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Remove");
    alert.setHeaderText(
        "Are you sure you want to delete selected " + dataType + "? It can not be undone.");
    // alert.setContentText(null);
    alert.initStyle(StageStyle.UTILITY);
    return alert.showAndWait();
  }

  /**
   * This method displays a warning to the user when they are about to add routes to their history
   * which contain airports that are not present in the airport file.
   *
   * @param invalidAirports a HashSet of the airports which are not in the airport file.
   * @return the option the user chooses (ok to proceed, cancel to abandon).
   */
  public static Optional<ButtonType> showAirportAlert(HashSet<String> invalidAirports) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm add to history");
    StringBuilder headerText =
        new StringBuilder(
            "Some of your selected routes contain airports that are not contained in the airport file.\n"
                + "Carbon emissions will not be calculated for these routes. Are you sure you want to proceed?\n\n"
                + "Invalid airports are: ");
    for (String airport : invalidAirports) {
      headerText.append(airport).append(", ");
    }
    alert.setHeaderText(headerText.toString());
    alert.initStyle(StageStyle.UTILITY);
    return alert.showAndWait();
  }

  /**
   * This method displays a warning to the user when they are about to select more than two route in
   * history to display on google map.
   *
   * @return the option the user chooses (ok to proceed, cancel to abandon).
   */
  public static Optional<ButtonType> showGoogleMapAlert() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Google Map");
    StringBuilder headerText = new StringBuilder("Please select only one route for google Map.");
    alert.setHeaderText(headerText.toString());
    alert.initStyle(StageStyle.UTILITY);
    return alert.showAndWait();
  }
}
