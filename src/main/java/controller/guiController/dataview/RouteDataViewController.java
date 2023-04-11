package controller.guiController.dataview;

import controller.analysis.GeneralStatsCalculator;
import controller.analysis.Searcher;
import controller.guiController.AlertPopUp;
import controller.guiController.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.data.Airport;
import model.data.Route;
import model.database.SQLiteDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the route data view.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class RouteDataViewController extends DataViewController {

  @FXML private TableView<Route> tableView;
  @FXML private TableColumn<Route, Boolean> addColumn;
  @FXML private TableColumn<Route, String> airlineNameColumn;
  @FXML private TableColumn<Route, String> sourceAirportColumn;
  @FXML private TableColumn<Route, String> destinationAirportColumn;
  @FXML private TableColumn<Route, String> codeShareColumn;
  @FXML private TableColumn<Route, Integer> numOfStopsColumn;
  @FXML private TableColumn<Route, String> equipmentColumn;
  @FXML private Button selectAllBtn;
  @FXML private Button deselectAllBtn;
  @FXML private Button AddToHistoryButton;
  @FXML private Button removeBtn;

  /** The database object. */
  private SQLiteDatabase database = new SQLiteDatabase();

  /** The types of search which can be performed on routes. */
  private final ObservableList<String> searchTypes =
      FXCollections.observableArrayList("Airline", "Source", "Destination");

  /** Class to generate reports on history. */
  private GeneralStatsCalculator generalStatsCalculator;

  /** Pop up to launch when adding route to history. */
  private final RouteAddToHistoryPopUpController addPopUp = new RouteAddToHistoryPopUpController();

  private ObservableList<Route> routes;

  private HashSet<String> airports;
  /**
   * Initializes the controller class. The checkboxes are added to each record.
   *
   * @param url The URL used.
   * @param rb The resource bundle used.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    this.generalStatsCalculator = Main.getGeneralStatsCalculator();
    addColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
    airlineNameColumn.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
    sourceAirportColumn.setCellValueFactory(new PropertyValueFactory<>("sourceAirport"));
    destinationAirportColumn.setCellValueFactory(new PropertyValueFactory<>("destinationAirport"));
    codeShareColumn.setCellValueFactory(new PropertyValueFactory<>("codeShare"));
    numOfStopsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfStops"));
    equipmentColumn.setCellValueFactory(new PropertyValueFactory<>("firstEquipment"));

    if (storage.getRoutes() != null) {
      for (Route route : storage.getRoutes()) {
        route.initCheckBox();
      }
      routes = FXCollections.observableList(storage.getRoutes());
      tableView.setItems(routes);
      tableView.setEditable(true);
      searchTypeSelection.setItems(searchTypes); // Setup choice boxes
    }

    airports = new HashSet<>();
    for (Airport airport : Main.getStorage().getAirports()) {
      if (!airport.getIATA().equals("")) {
        airports.add(airport.getIATA());
      }
      if (!airport.getICAO().equals("")) {
        airports.add(airport.getICAO());
      }
    }
  }

  /**
   * This method calls searchRoutes method from searcher class and upldates table to display results
   * of search.
   */
  public void searchByDataType(String searchTerm, String searchType) {
    ArrayList<Route> results = Searcher.searchRoutes(searchTerm, searchType, storage.getRoutes());
    tableView.setItems(FXCollections.observableList(results));
  }

  /**
   * This method adds the selected routes to the flight history. The total distance, total
   * emissions, least distance, most distance , least emissions , most emissions , least travelled
   * and most travelled route are updated.
   */
  public void addDataToHistory() throws IOException {
    errorText.setVisible(false);
    boolean selected = getAnySelected();
    if (selected) {
      if (!Main.getStorage().getTempRoutes().isEmpty()) {
        Main.getStorage().getTempRoutes().clear();
      }

      for (Route route : Main.getStorage().getRoutes()) {
        if (route.getSelect().isSelected()) {
          Main.getStorage().getTempRoutes().add(route);
        }
      }

      checkAirports();
    } else {
      errorText.setText("No routes selected.");
      errorText.setVisible(true);
    }
  }

  /**
   * This method creates a warning pop up to tell the user that route(s) they are trying to add to
   * their history does not have associated airport(s) in the current airport file.
   *
   * @throws IOException if the pop up cannot be launched.
   * @param invalidAirports a set of airport codes contained in the routes but not in the airport
   *     file.
   */
  public void airportWarning(HashSet<String> invalidAirports) throws IOException {
    Optional<ButtonType> result = AlertPopUp.showAirportAlert(invalidAirports);
    if (result.isPresent() && result.get() == ButtonType.OK) {
      addPopUp.display();
    }
  }

  /**
   * This method checks if all the airports from the routes that the user is trying to add to their
   * history are contained in the airport file. If they are not, asks the user for confirmation
   * before proceeding.
   *
   * @throws IOException If the warning pop up cannot be launched.
   */
  public void checkAirports() throws IOException {
    HashSet<String> invalidAirports = new HashSet<>();
    for (Route route : storage.getTempRoutes()) {
      if (!airports.contains(route.getDestinationAirport())) {
        invalidAirports.add(route.getDestinationAirport());
      }
      if (!airports.contains(route.getSourceAirport())) {
        invalidAirports.add(route.getSourceAirport());
      }
    }
    if (invalidAirports.size() > 0) {
      airportWarning(invalidAirports);
    } else {
      addPopUp.display();
    }
  }

  /** This method clears the search bar and displays all routes in table view. */
  @Override
  public void clearSearch() {
    errorText.setVisible(false);
    searchBar.setText(null);
    tableView.setItems(FXCollections.observableList(storage.getRoutes()));
  }

  /**
   * This method launches the filter pop up box. If filtering is successful displays filtered routes
   * in table view.
   *
   * @throws IOException If fxml file cannot be launched.
   */
  public void filterOptions() throws IOException {
    errorText.setVisible(false);
    RouteFilterPopUpController filterPopUp = new RouteFilterPopUpController();
    filterer.setFilterSuccess(false);
    filterPopUp.display();
    if (filterer.getFilterSuccess()) {
      tableView.setItems(FXCollections.observableList(filterer.getFilteredRoutes()));
    }
  }

  /** This method removes all routes that have been selected by the user from the current file. */
  public void removeSelected() {
    errorText.setVisible(false);
    if (getAnySelected()) {
      Optional<ButtonType> result = AlertPopUp.showDeleteAlert("route(s)");
      if (result.isPresent() && result.get() == ButtonType.OK) {
        routes.removeIf(route -> route.getSelect().isSelected());
        database.deleteFile(storage.getCurrentRouteFile(), "Route");
        database.initialiseTable("Route", storage.getCurrentRouteFile());
        database.updateRoute(storage.getRoutes());
      }
    } else {
      errorText.setText("No routes selected.");
      errorText.setVisible(true);
    }
  }

  /**
   * Check if at least one entry has been selected.
   *
   * @return true if any have been selected or false otherwise.
   */
  public boolean getAnySelected() {
    for (Route route : routes) {
      if (route.getSelect().isSelected()) {
        return true;
      }
    }
    return false;
  }
}
