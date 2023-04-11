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
import model.data.Airline;
import model.data.Route;
import model.database.SQLiteDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the airline data view.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirlineDataViewController extends DataViewController {

  @FXML private TableView<Airline> tableView;
  @FXML private TableColumn<Route, Boolean> addColumn;
  @FXML private TableColumn<Airline, Integer> airlineIDColumn;
  @FXML private TableColumn<Airline, String> airlineNameColumn;
  @FXML private TableColumn<Airline, String> airlineAliasColumn;
  @FXML private TableColumn<Airline, String> airlineIATAColumn;
  @FXML private TableColumn<Airline, String> ICAOColumn;
  @FXML private TableColumn<Airline, String> callsignColumn;
  @FXML private TableColumn<Airline, String> countryColumn;
  @FXML private TableColumn<Airline, Boolean> activeStatusColumn;

  /** The database object. */
  private SQLiteDatabase database = new SQLiteDatabase();

  /** Initialize the strings in the searchTypes list. */
  private final ObservableList<String> searchTypes =
      FXCollections.observableArrayList("Name", "Country", "IATA", "ICAO");

  ObservableList<Airline> airlines;
  /**
   * This method initializes the controller class.
   *
   * @param url The URL used.
   * @param rb The resource bundle used.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    addColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
    airlineIDColumn.setCellValueFactory(new PropertyValueFactory<>("airlineID"));
    airlineNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    airlineAliasColumn.setCellValueFactory(new PropertyValueFactory<>("airlineAlias"));
    airlineIATAColumn.setCellValueFactory(new PropertyValueFactory<>("IATA"));
    ICAOColumn.setCellValueFactory(new PropertyValueFactory<>("ICAO"));
    callsignColumn.setCellValueFactory(new PropertyValueFactory<>("callsign"));
    countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    activeStatusColumn.setCellValueFactory(new PropertyValueFactory<>("activeStatus"));

    if (storage.getAirlines() != null) {
      for (Airline airline : storage.getAirlines()) {
        airline.initCheckBox();
      }
      airlines = FXCollections.observableList(storage.getAirlines());
      tableView.setItems(airlines);
      searchTypeSelection.setItems(searchTypes); // Setup choice boxes
    }
  }
  /**
   * This method calls searchAirlines method from searcher class and upldates table to display
   * results of search.
   *
   * @param searchTerm The term to search for matches to.
   * @param searchType The type of attribute to check for matches.
   */
  public void searchByDataType(String searchTerm, String searchType) {
    ArrayList<Airline> results =
        Searcher.searchAirlines(searchTerm, searchType, storage.getAirlines());
    tableView.setItems(FXCollections.observableList(results));
  }

  /** This method clears search bar and display all airlines in table view. */
  @Override
  public void clearSearch() {
    errorText.setVisible(false);
    searchBar.setText(null);
    tableView.setItems(FXCollections.observableList(storage.getAirlines()));
  }

  /**
   * This method displays the filter pop up box. If filtering is successful displays the filtered
   * airline data in the tableview when the pop up is closed.
   *
   * @throws IOException If AirlineFilterPopUpController fxml file cannot be opened.
   */
  public void filterOptions() throws IOException {
    errorText.setVisible(false);
    AirlineFilterPopUpController filterPopUp = new AirlineFilterPopUpController();
    filterer.setFilterSuccess(false);
    filterPopUp.display();
    if (filterer.getFilterSuccess()) {
      tableView.setItems(FXCollections.observableList(filterer.getFilteredAirlines()));
    }
  }

  public void removeSelected() {
    errorText.setVisible(false);
    if (getAnySelected()) {
      Optional<ButtonType> result = AlertPopUp.showDeleteAlert("airline(s)");
      if (result.isPresent() && result.get() == ButtonType.OK) {
        airlines.removeIf(airline -> airline.getSelect().isSelected());
        database.deleteFile(storage.getCurrentAirlineFile(), "Airline");
        database.initialiseTable("Airline", storage.getCurrentAirlineFile());
        database.updateAirlineTable(storage.getAirlines());
      }
    } else {
      errorText.setText("No airlines selected");
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
    for (Airline airline : airlines) {
      if (airline.getSelect().isSelected()) {
        selected = true;
        break;
      }
    }
    return selected;
  }
}
