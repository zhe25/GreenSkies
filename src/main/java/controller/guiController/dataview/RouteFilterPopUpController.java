package controller.guiController.dataview;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Pop up scene on which users can apply filters for the route table.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class RouteFilterPopUpController extends FilterPopUpController {

  @FXML private CheckBox airlineCheckBox;
  @FXML private CheckBox sourceCheckBox;
  @FXML private CheckBox destinationCheckBox;
  @FXML private TextField airlineField;
  @FXML private TextField sourceField;
  @FXML private TextField destinationField;

  /**
   * This method is inherited from the Initializable interface.
   *
   * @param url Not used.
   * @param resourceBundle Not used.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  /**
   * This method gets the filter terms the user has entered from the text fields and puts them into
   * a HashMap with the filter type as the key and the filter term as the value.
   *
   * @return A HashMap with filter types as keys and filter terms as values.
   */
  public HashMap<String, String> getFilterTerms() {
    HashMap<String, String> filterTerms = new HashMap<>();
    if (airlineCheckBox.isSelected()) {
      filterTerms.put("Airline", airlineField.getText());
    }
    if (sourceCheckBox.isSelected()) {
      filterTerms.put("Source", sourceField.getText());
    }
    if (destinationCheckBox.isSelected()) {
      filterTerms.put("Destination", destinationField.getText());
    }
    return filterTerms;
  }

  /**
   * This method calls the filterRoutes method of filterer with the given HashMap of filter types
   * and terms.
   *
   * @param filterTerms A HashMap with filter type as the key and filter term as the value.
   */
  @Override
  public void filterByDataType(HashMap<String, String> filterTerms) {
    filterer.filterRoutes(filterTerms, storage.getRoutes());
  }

  /**
   * This method returns the fxml filename for the route filter pop up controller.
   *
   * @return The fxml filename
   */
  @Override
  public String getFXMLFilename() {
    return "/view/dataview/routeFilterPopUp.fxml";
  }
}
