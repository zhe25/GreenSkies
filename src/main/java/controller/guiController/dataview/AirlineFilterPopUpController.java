package controller.guiController.dataview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * A pop up window in which the user can select filters to apply to the list of Routes.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirlineFilterPopUpController extends FilterPopUpController {

  @FXML private CheckBox countryCheckBox;
  @FXML private CheckBox activeCheckBox;
  @FXML private TextField countryField;
  @FXML private ChoiceBox<String> activeSelection;

  /** The two options for the active status of an airline. */
  private final ObservableList<String> activeOptions =
      FXCollections.observableArrayList("True", "False");

  /**
   * This method initializes activeSelection to give the options "True" or "False".
   *
   * @param url Not used.
   * @param resourceBundle Not used.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    activeSelection.setItems(activeOptions);
    activeSelection.setValue("True");
  }

  /**
   * This method gets the filter terms the user has entered in each textfield or choicebox and put
   * them into a hashmap with the type of filter as the key.
   *
   * @return A hashmap with filter types as keys and filter terms as values.
   */
  @Override
  public HashMap<String, String> getFilterTerms() {
    HashMap<String, String> filterTerms = new HashMap<>();
    if (countryCheckBox.isSelected()) {
      filterTerms.put("Country", countryField.getText());
    }
    if (activeCheckBox.isSelected()) {
      filterTerms.put("Active status", activeSelection.getValue());
    }
    return filterTerms;
  }

  /**
   * This method calls the filterAirlines method of filterer with the given hashmap of filter types
   * and terms.
   *
   * @param filterTerms A hasmap with filter types as keys and filter terms as values.
   */
  @Override
  public void filterByDataType(HashMap<String, String> filterTerms) {
    filterer.filterAirlines(filterTerms, storage.getAirlines());
  }

  /**
   * This method returns for the airline filter pop up.
   *
   * @return The name of the fxml file
   */
  @Override
  public String getFXMLFilename() {
    return "/view/dataview/airlineFilterPopUp.fxml";
  }
}
