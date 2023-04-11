package controller.guiController.upload;

import controller.guiController.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.data.Airport;
import model.data.DataType;
import model.database.SQLiteDatabase;
import model.loader.Loader;
import java.util.List;

/**
 * The controller class which contains the controls for data entry of a single Airport.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirportSingleEntryController {

  /** Initialize a loader object */
  private final Loader loader = Main.getLoader();

  @FXML TextField nameFld;
  @FXML TextField cityFld;
  @FXML TextField countryFld;
  @FXML TextField iataFld;
  @FXML TextField icaoFld;
  @FXML TextField timezoneFld;
  @FXML TextField latitudeFld;
  @FXML TextField longitudeFld;
  @FXML TextField altitudeFld;
  @FXML TextField dstFld;
  @FXML TextField dbtimezoneFld;
  @FXML TextField airportidFld;
  @FXML Button cancelButton;
  @FXML Button addButton;

  /** This method closes window when the 'Cancel' button is pushed. */
  public void closeWindow() {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  /**
   * This method presents the user with a confirmation box before uploading a file
   *
   * @param message
   */
  public void showConfirmDialog(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Confirm data entry upload");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /** This method loads the data entered for an aiport as a singular line. */
  public void addEntry() {
    String entryString = makeAirportString();

    try {
      String message = loader.loadLine(entryString, "Airport");

      List<DataType> data = loader.getParser().getData();
      String fileName = loader.getLineFileName("Airport");

      SQLiteDatabase database = new SQLiteDatabase();
      database.initialiseTable("Airport", fileName);
      while (data.remove(null)) ;
      database.addAirports((Airport) data.get(data.size() - 1));
      database.startCommite();

      showConfirmDialog(message);

      Stage stage = (Stage) addButton.getScene().getWindow();
      stage.close();

    } catch (Exception e) {
      Alert ErrorAlert = new Alert(Alert.AlertType.NONE);
      ErrorAlert.setAlertType(Alert.AlertType.ERROR);
      ErrorAlert.setContentText(e.getMessage());
      ErrorAlert.show();
    }
  }

  /**
   * This method compiles a string from the the entered data for an airport.
   *
   * @return airlineString.
   */
  public String makeAirportString() {
    String airportString = "";

    String airportID = airportidFld.getText();
    airportString += airportID + ",";

    String name = nameFld.getText();
    airportString += name + ",";

    String city = cityFld.getText();
    airportString += city + ",";

    String country = countryFld.getText();
    airportString += country + ",";

    String iata = iataFld.getText();
    airportString += iata + ",";

    String icao = icaoFld.getText();
    airportString += icao + ",";

    String latitude = latitudeFld.getText();
    airportString += latitude + ",";

    String longitude = longitudeFld.getText();
    airportString += longitude + ",";

    String altitude = altitudeFld.getText();
    airportString += altitude + ",";

    String timezone = timezoneFld.getText();
    airportString += timezone + ",";

    String DST = dstFld.getText();
    airportString += DST + ",";

    String DBtimezone = dbtimezoneFld.getText();
    airportString += DBtimezone;

    return airportString;
  }

  /** This method launches alert giving user information on expected format of airport entry. */
  public void airportHelp() {
    Alert ErrorAlert = new Alert(Alert.AlertType.NONE);
    ErrorAlert.setAlertType(Alert.AlertType.INFORMATION);
    ErrorAlert.setContentText(
        "Expected format of airport entry:\n"
            + "Airport name: a combination of letters and spaces\n"
            + "City: a combination of letters, numbers and spaces\n"
            + "Country: a combination of letters and spaces\n"
            + "IATA: 'null' if IATA is unknown, three letters or numbers otherwise\n"
            + "ICAO: 'null' if ICAO is unknown, four letters or numbers otherwise\n"
            + "Timezone: an integer between -12 and 14 indicating deviation from GMT\n"
            + "Latitude: a positive or negative decimal number\n"
            + "Longitude: a positive or negative decimal number\n"
            + "Altitude: a positive or negative integer\n"
            + "DST: single capital letter corresponding to daylight savings time. One of: 'E','A','S','O','Z','N' or 'U'\n"
            + "Database timezone: the name of the timezone, a combination of letters and no spaces e.g. 'Pacific/Auckland'\n"
            + "Airport ID: an integer, must be unique within the airport file");
    ErrorAlert.show();
  }
}
