package controller.guiController.dataview;

import controller.analysis.Filterer;
import controller.guiController.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Storage;
import java.io.IOException;
import java.util.HashMap;

/**
 * This is the parent class of the filter pop up window classes for each datatype.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public abstract class FilterPopUpController implements Initializable {

  @FXML private Label errorText;

  /** Class to call filtering methods from. */
  protected Filterer filterer = Main.getFilterer();

  /** Class to get stored data from. */
  protected Storage storage = Main.getStorage();

  /**
   * This method gets the filter terms the user has entered and their types.
   *
   * @return A HashMap with filter types as keys and filter terms as values.
   */
  public abstract HashMap<String, String> getFilterTerms();

  /**
   * This method filters the data in storage by the given filter terms.
   *
   * @param filterTerms A HashMap with filter types as keys and filter terms as values.
   */
  public abstract void filterByDataType(HashMap<String, String> filterTerms);

  /**
   * This method returns the fxml file name which the class is the controller for.
   *
   * @return An fxml filename.
   */
  public abstract String getFXMLFilename();

  /**
   * This method displays the filter pop up window.
   *
   * @throws IOException if fmxl file cannot be opened.
   */
  public void display() throws IOException {
    final Stage filterPopUp = new Stage();
    filterPopUp.initModality(Modality.APPLICATION_MODAL);
    Parent root = FXMLLoader.load(getClass().getResource(getFXMLFilename()));
    Scene scene = new Scene(root);
    filterPopUp.setScene(scene);
    filterPopUp.showAndWait();
  }

  /**
   * This method calls getFilterTerms to get a hashmap of the filter types and terms. Calls
   * filterByDataType with the given filter types and terms in a hashmap, and sets the filterSuccess
   * attribute of the filterer to true. Displays an error message if any step goes wrong.
   */
  public void applyFilters() {
    errorText.setVisible(false);
    HashMap<String, String> filterTerms = getFilterTerms();
    if (!filterTerms.isEmpty()) {
      try {
        filterByDataType(filterTerms);
        filterer.setFilterSuccess(true);
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
      } catch (RuntimeException e) {
        if (e.getMessage() == null) {
          errorText.setText("Something went wrong.");
        } else {
          errorText.setText(e.getMessage());
        }
        errorText.setVisible(true);
      }
    } else {
      errorText.setText("Select at least one filter option.");
      errorText.setVisible(true);
    }
  }
}
