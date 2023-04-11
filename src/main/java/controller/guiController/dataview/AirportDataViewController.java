package controller.guiController.dataview;

import controller.analysis.Searcher;
import controller.guiController.AlertPopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.data.Airport;
import model.database.SQLiteDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the airport data view.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirportDataViewController extends DataViewController {

  @FXML private TableView<Airport> tableView;
  @FXML private TableColumn<Airport, Boolean> addColumn;
  @FXML private TableColumn<Airport, Integer> airportIDColumn;
  @FXML private TableColumn<Airport, String> nameColumn;
  @FXML private TableColumn<Airport, String> cityColumn;
  @FXML private TableColumn<Airport, String> countryColumn;
  @FXML private TableColumn<Airport, String> IATAColumn;
  @FXML private TableColumn<Airport, String> ICAOColumn;
  @FXML private TableColumn<Airport, Double> latitudeColumn;
  @FXML private TableColumn<Airport, Double> longitudeColumn;
  @FXML private TableColumn<Airport, Integer> altitudeColumn;
  @FXML private TableColumn<Airport, Float> timezoneColumn;
  @FXML private TableColumn<Airport, String> DSTColumn;
  @FXML private TableColumn<Airport, String> dataBaseTimeZoneColumn;

  /** The database object. */
  private SQLiteDatabase database = new SQLiteDatabase();

  /** Initialize the list of attribute to be added tp the searchTypes list */
  private final ObservableList<String> searchTypes =
      FXCollections.observableArrayList("Name", "Country", "IATA", "ICAO");

  ObservableList<Airport> airports;

  /**
   * This method initializes the controller class.
   *
   * @param url The URL used.
   * @param rb The resource bundle used.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    addColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
    airportIDColumn.setCellValueFactory(new PropertyValueFactory<>("airportID"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
    countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    IATAColumn.setCellValueFactory(new PropertyValueFactory<>("IATA"));
    ICAOColumn.setCellValueFactory(new PropertyValueFactory<>("ICAO"));
    latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
    longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
    altitudeColumn.setCellValueFactory(new PropertyValueFactory<>("altitude"));
    timezoneColumn.setCellValueFactory(new PropertyValueFactory<>("timezone"));
    DSTColumn.setCellValueFactory(new PropertyValueFactory<>("DST"));
    dataBaseTimeZoneColumn.setCellValueFactory(new PropertyValueFactory<>("dataBaseTimeZone"));
    if (storage.getAirports() != null) {
      for (Airport airport : storage.getAirports()) {
        airport.initCheckBox();
      }
      airports = FXCollections.observableList(storage.getAirports());
      tableView.setItems(airports);
      searchTypeSelection.setItems(searchTypes);
    }
  }
  /**
   * This method calls searchAirports method from searcher class and upldates table to display
   * results of search.
   */
  public void searchByDataType(String searchTerm, String searchType) {
    ArrayList<Airport> results =
        Searcher.searchAirports(searchTerm, searchType, storage.getAirports());
    tableView.setItems(FXCollections.observableList(results));
  }

  /** This method clears search bar and display all airports in table view. */
  @Override
  public void clearSearch() {
    errorText.setVisible(false);
    searchBar.setText(null);
    tableView.setItems(FXCollections.observableList(storage.getAirports()));
  }

  /**
   * This method opens the filter options pop up. If the user's filters are successfully applied,
   * set the table to display the filtered data when the pop up is closed.
   */
  public void filterOptions() throws IOException {
    errorText.setVisible(false);
    AirportFilterPopUpController filterPopUpController = new AirportFilterPopUpController();
    filterer.setFilterSuccess(false);
    filterPopUpController.display();
    if (filterer.getFilterSuccess()) {
      tableView.setItems(FXCollections.observableList(filterer.getFilteredAirports()));
    }
  }

  public void removeSelected() {
    errorText.setVisible(false);
    if (getAnySelected()) {
      Optional<ButtonType> result = AlertPopUp.showDeleteAlert("airport(s)");
      if (result.isPresent() && result.get() == ButtonType.OK) {
        airports.removeIf(airport -> airport.getSelect().isSelected());
        database.deleteFile(storage.getCurrentAirportFile(), "Airport");
        database.initialiseTable("Airport", storage.getCurrentAirportFile());
        database.updateAirportTable(airports);
      }
    } else {
      errorText.setText("No airports selected.");
      errorText.setVisible(true);
    }
  }

  /**
   * Check if at least one entry has been selected.
   *
   * @return true if any have been selected or false otherwise.
   */
  public boolean getAnySelected() {
    boolean selected = false;
    for (Airport airport : airports) {
      if (airport.getSelect().isSelected()) {
        selected = true;
        break;
      }
    }
    return selected;
  }
}
