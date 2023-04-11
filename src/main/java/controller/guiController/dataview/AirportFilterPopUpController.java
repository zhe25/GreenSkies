package controller.guiController.dataview;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The controller for the pop up window in which user can select filters to apply to the list of
 * airports.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirportFilterPopUpController extends FilterPopUpController {

  @FXML private TextField countryField;
  @FXML private TextField cityField;
  @FXML private CheckBox countryCheckBox;
  @FXML private CheckBox cityCheckBox;

  /**
   * This method is inherited from the Initializable interface.
   *
   * @param url Not used.
   * @param resourceBundle Not used.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  /**
   * This method gets the country and active status which the user has inputed and inserts them into
   * a hashmap with the filter type as the key.
   *
   * @return A HashMap with filter type as the key and filter term as the value.
   */
  @Override
  public HashMap<String, String> getFilterTerms() {
    HashMap<String, String> filterTerms = new HashMap<>();
    if (countryCheckBox.isSelected()) {
      filterTerms.put("Country", countryField.getText());
    }
    if (cityCheckBox.isSelected()) {
      filterTerms.put("City", cityField.getText());
    }
    return filterTerms;
  }

  /**
   * This method calls the filterAirports method of the Filterer class with the given hashmap of
   * filter types and term.
   *
   * @param filterTerms a hashmap to pass into the filterAirports method.
   */
  public void filterByDataType(HashMap<String, String> filterTerms) {
    filterer.filterAirports(filterTerms, storage.getAirports());
  }

  /**
   * This method returns the fxml filename for the airport filter pop up controller.
   *
   * @return The fxml filename
   */
  @Override
  public String getFXMLFilename() {
    return "/view/dataview/airportFilterPopUp.fxml";
  }
}
