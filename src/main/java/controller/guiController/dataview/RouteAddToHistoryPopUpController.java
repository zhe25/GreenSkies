package controller.guiController.dataview;

import controller.analysis.FlightAnalyser;
import controller.analysis.GeneralStatsCalculator;
import controller.analysis.RouteStatsCalculator;
import controller.guiController.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.data.Route;
import model.data.Storage;
import model.database.SQLiteDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the route data view.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class RouteAddToHistoryPopUpController implements Initializable {

  public Button setAllPassengerBtn;
  @FXML private TableView<Route> tableView;
  @FXML private TableColumn<Route, Integer> passengerNumber;
  @FXML private TableColumn<Route, String> airlineNameColumn;
  @FXML private TableColumn<Route, String> sourceAirportColumn;
  @FXML private TableColumn<Route, String> destinationAirportColumn;
  @FXML private TableColumn<Route, String> codeShareColumn;
  @FXML private TableColumn<Route, Integer> numOfStopsColumn;
  @FXML private TableColumn<Route, String> equipmentColumn;
  @FXML private Button confirmBtn;
  @FXML private Button cancelBtn;

  /** The GeneralStatsCalculator to generate reports about flight history. */
  private final GeneralStatsCalculator generalStatsCalculator = Main.getGeneralStatsCalculator();
  /** The RouteStatsCalculator to generate route stats for the reports about flight history. */
  private final RouteStatsCalculator routeStatsCalculator = Main.getRouteStatsCalculator();

  /** The class from which stored data can be accessed. */
  private final Storage storage = Main.getStorage();

  /** List of routes to be added to history. */
  private ObservableList<Route> tempRoute;

  /** The database for adding history to history table in database. */
  private SQLiteDatabase database = new SQLiteDatabase();

  /**
   * This method displays the content for the history.
   *
   * @throws IOException This throws an IOException.
   */
  public void display() throws IOException {

    Parent root =
        FXMLLoader.load(getClass().getResource("/view/dataview/routeAddToHistoryPopUp.fxml"));
    Stage addPopUp = new Stage();
    addPopUp.setTitle("Add To History ");
    addPopUp.initModality(Modality.APPLICATION_MODAL);
    addPopUp.setScene(new Scene(root));
    addPopUp.show();
  }

  /**
   * This method initializes the controller class. The checkboxes are added to each record.
   *
   * @param url The URL used.
   * @param rb The resource bundle used.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    passengerNumber.setCellValueFactory(new PropertyValueFactory<>("timesTaken"));
    passengerNumber.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    passengerNumber.setOnEditCommit(
        routeIntegerCellEditEvent -> {
          Route routeChanged =
              routeIntegerCellEditEvent
                  .getTableView()
                  .getItems()
                  .get(routeIntegerCellEditEvent.getTablePosition().getRow());
          routeChanged.setTimesTaken(routeIntegerCellEditEvent.getNewValue());
        });
    airlineNameColumn.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
    sourceAirportColumn.setCellValueFactory(new PropertyValueFactory<>("sourceAirport"));
    destinationAirportColumn.setCellValueFactory(new PropertyValueFactory<>("destinationAirport"));
    codeShareColumn.setCellValueFactory(new PropertyValueFactory<>("codeShare"));
    numOfStopsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfStops"));
    equipmentColumn.setCellValueFactory(new PropertyValueFactory<>("firstEquipment"));

    tempRoute = FXCollections.observableArrayList(Main.getStorage().getTempRoutes());
    tableView.setEditable(true);
    tableView.setItems(tempRoute);
  }

  /**
   * This method called when user clicked on the confirm button. It will check for route that is
   * already inside history. If so, it will modify the number of passengers. If not, the route will
   * be added to history
   */
  public void confirm() {
    for (Route route : Main.getStorage().getTempRoutes()) {
      if (route.getTimesTaken() <= 0) {
        // Have not edit number of passenger => invalid history
        continue; // drop

      } else {
        int index = Main.getStorage().getHistory().indexOf(route);
        if (index != -1) {
          Main.getStorage()
              .getHistory()
              .get(index)
              .setTimesTaken(storage.getHistory().get(index).getTimesTaken());
        } else {
          Main.getStorage().getHistory().add(route);
        }
        route.getSelect().setSelected(false);
        updateReportStats(route);
      }
    }
    database.updateHistoryTable(storage.getHistory());

    tempRoute.clear();
    Stage stage = (Stage) cancelBtn.getScene().getWindow();
    stage.close();
  }

  /**
   * This methods updates the calculations of the stats calculator classes to include the route
   * which has just been added to history.
   *
   * @param route Route which has been added to history.
   */
  public void updateReportStats(Route route) {

    FlightAnalyser flightAnalyser = new FlightAnalyser(route, storage);
    route.setEmissions(flightAnalyser.getPath1Emission());
    route.setDistance(flightAnalyser.getTotalDistancePath1());
    generalStatsCalculator.updateTotalDistance(route);
    generalStatsCalculator.updateTotalEmissions(route);
    storage.addToHistorySrcAirports(route.getSourceAirport());
    storage.addToHistoryDestAirports(route.getDestinationAirport());
    routeStatsCalculator.updateLeastDistanceRoute(route);
    routeStatsCalculator.updateMostDistanceRoute(route);
    routeStatsCalculator.updateMostEmissionsRoute(route);
    routeStatsCalculator.updateLeastEmissionsRoute(route);
  }

  /** This method closes the stage without adding any routes to history. */
  public void cancel() {
    for (Route route : Main.getStorage().getTempRoutes()) {
      route.setTimesTaken(0);
    }
    Stage stage = (Stage) cancelBtn.getScene().getWindow();
    stage.close();
  }

  /** This method sets the times taken for each of the routes in tempRoutes to 1. */
  public void setAllPassengerTo1() {
    for (Route route : Main.getStorage().getTempRoutes()) {
      route.setTimesTaken(1);
    }
    tableView.refresh();
  }
}
