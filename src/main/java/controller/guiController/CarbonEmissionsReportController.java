package controller.guiController;

import controller.analysis.AirportStatsCalculator;
import controller.analysis.GeneralStatsCalculator;
import controller.analysis.RouteStatsCalculator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.data.Route;
import model.data.Storage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the carbon emissions report page.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class CarbonEmissionsReportController extends SideNavBarController {

  @FXML private TextField displayTotalEmissionsField;
  @FXML private TextField displayTotalDistanceTravelledField;
  @FXML private TextField displayMostEmissionsRouteField;
  @FXML private TextField displayLeastEmissionsRouteField;
  @FXML private TextField displayMostDistanceRouteField;
  @FXML private TextField displayLeastDistanceRouteField;
  @FXML private TextField displayMostVisitedSourceAirportField;
  @FXML private TextField displayLeastVisitedSourceAirportField;
  @FXML private TextField displayMostVisitedDestinationAirportField;
  @FXML private TextField displayLeastVisitedDestinationAirportField;
  @FXML private TextField displayCarbonEmissionGoalField;
  @FXML private TextField displayTreeOffsetField;
  @FXML private TextField carbonEmissionGoalField;
  @FXML private TextArea displayStatusCommentField;

  /** This reportGenerator for the application. */
  private GeneralStatsCalculator generalStatsCalculator;
  /** A string of the most emission routes. */
  private String MostEmissionsRouteString = "";
  /** A string of the least emission routes. */
  private String LeastEmissionsRouteString;
  /** A string of the most distance routes. */
  private String MostDistanceRouteString;
  /** A string of the least distance routes. */
  private String LeastDistanceRouteString;
  /** A string of the most visited source airports. */
  private String MostVisitedSourceAirportString;
  /** A string of the least visited source airports. */
  private String LeastVisitedSourceAirportString;
  /** A string of the most visited destination airports. */
  private String MostVisitedDestAirportString;
  /** A string of the least visited destination airports. */
  private String LeastVisitedDestAirportString;
  /** A string of the number of trees required to offset the provided amount of carbon emissions. */
  private String numOfTreesString;
  /** The RouteStatsCalculator used in the application. */
  private RouteStatsCalculator routeStatsCalculator;
  /** The AirportStatsCalculator used in the application. */
  private AirportStatsCalculator airportStatsCalculator;

  public CarbonEmissionsReportController() {}

  /**
   * This method is the initializer for this class.
   *
   * @param url The provided url.
   * @param resourceBundle The provided resource bundle.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.generalStatsCalculator = Main.getGeneralStatsCalculator();
    this.routeStatsCalculator = Main.getRouteStatsCalculator();
    this.airportStatsCalculator = Main.getAirportStatsCalculator();

  }

  /**
   * This method displays the carbon emissions report information based on the inputted goal,
   * duration and current flight history records.
   */
  @FXML
  private void generateReportData() {

    if (storage.getHistory().size() == 0) {
      Alert ErrorAlert = new Alert(Alert.AlertType.NONE);
      ErrorAlert.setAlertType(Alert.AlertType.ERROR);
      ErrorAlert.setContentText("You have not selected any data to be in your Flight History");
      ErrorAlert.show();
    } else {
      updateTravelledAndVisited();
      setUpData();

      try {
        displayCarbonEmissionGoalField.setText(String.valueOf(Double.parseDouble(carbonEmissionGoalField.getText())));
        generalStatsCalculator.setCarbonEmissionsGoal(Double.parseDouble(carbonEmissionGoalField.getText()));

        displayTotalEmissionsField.setText(
            String.format("%.2f", generalStatsCalculator.getTotalCarbonEmissions()));
        displayTotalDistanceTravelledField.setText(
            String.format("%.2f", generalStatsCalculator.getTotalDistanceTravelled()));
        displayMostEmissionsRouteField.setText(MostEmissionsRouteString);
        displayLeastEmissionsRouteField.setText(LeastEmissionsRouteString);
        displayMostDistanceRouteField.setText(MostDistanceRouteString);
        displayLeastDistanceRouteField.setText(LeastDistanceRouteString);
        displayMostVisitedSourceAirportField.setText(MostVisitedSourceAirportString);
        displayLeastVisitedSourceAirportField.setText(LeastVisitedSourceAirportString);
        displayMostVisitedDestinationAirportField.setText(MostVisitedDestAirportString);
        displayLeastVisitedDestinationAirportField.setText(LeastVisitedDestAirportString);
        displayTreeOffsetField.setText(numOfTreesString);
        generalStatsCalculator.createCarbonEmissionsComment();
        displayStatusCommentField.setText(generalStatsCalculator.getCarbonEmissionsComment());
      } catch (NumberFormatException e) {
        carbonEmissionGoalField.setText("");
        carbonEmissionGoalField.setPromptText(
            "NO GOAL WAS ENTERED. PLEASE ENTER A GOAL AS A DOUBLE.");
      }
    }
  }

  /** This method clears all fields in the report generator page to be empty. */
  @FXML
  private void clearReportData() {
    displayCarbonEmissionGoalField.setText("");
    carbonEmissionGoalField.setText("");
    displayTotalEmissionsField.setText("");
    displayTotalDistanceTravelledField.setText("");
    displayMostEmissionsRouteField.setText("");
    displayMostEmissionsRouteField.setText("");
    displayLeastEmissionsRouteField.setText("");
    displayMostDistanceRouteField.setText("");
    displayLeastEmissionsRouteField.setText("");
    displayLeastDistanceRouteField.setText("");
    displayMostVisitedSourceAirportField.setText("");
    displayLeastVisitedSourceAirportField.setText("");
    displayMostVisitedDestinationAirportField.setText("");
    displayLeastVisitedDestinationAirportField.setText("");
    displayTreeOffsetField.setText("");
    displayStatusCommentField.setText("");
  }

  /** This method sets up the data for the report to display the statistics as strings. */
  public void setUpData() {
    generalStatsCalculator.calculateOffsetTrees();
    this.MostEmissionsRouteString =
        RoutesArrayToString(routeStatsCalculator.getMostEmissionsRoutes());
    this.LeastEmissionsRouteString =
        RoutesArrayToString(routeStatsCalculator.getLeastEmissionsRoutes());
    this.MostDistanceRouteString =
        RoutesArrayToString(routeStatsCalculator.getMostDistanceRoutes());
    this.LeastDistanceRouteString =
        RoutesArrayToString(routeStatsCalculator.getLeastDistanceRoutes());
    this.MostVisitedSourceAirportString =
        CombineAirportsToOneString(airportStatsCalculator.getMostVisitedSrcAirports());
    this.LeastVisitedSourceAirportString =
        CombineAirportsToOneString(airportStatsCalculator.getLeastVisitedSrcAirports());
    this.MostVisitedDestAirportString =
        CombineAirportsToOneString(airportStatsCalculator.getMostVisitedDestAirports());
    this.LeastVisitedDestAirportString =
        CombineAirportsToOneString(airportStatsCalculator.getLeastVisitedDestAirports());
    numOfTreesToString(generalStatsCalculator.getTreesToGrow());
    generalStatsCalculator.createCarbonEmissionsComment();
  }

  /**
   * This methods takes an array containing either routes with most or least emissions or most or
   * least distance and produces a string of its routes' AirlineIDs.
   *
   * @param arrayToConvert Either the most or least emissions or most or least distance array that
   *     needs to be converted to a String.
   * @return resultString The string of the array's routes' AirlineIDs.
   */
  public String RoutesArrayToString(ArrayList<Route> arrayToConvert) {
    String resultString = "";
    StringBuilder resultStringBuilder = new StringBuilder(resultString);
    for (int i = 0; i < arrayToConvert.size(); i++) {
      if (arrayToConvert.get(i).getAirlineID() >= 0) {
        String routeString =
            arrayToConvert.get(i).getSourceAirport()
                + " to "
                + arrayToConvert.get(i).getDestinationAirport();
        if (i == arrayToConvert.size() - 1) {
          resultStringBuilder.append(routeString);
        } else {
          resultStringBuilder.append(routeString).append(", ");
        }
      }
    }
    resultString = resultStringBuilder.toString();
    return resultString;
  }

  /**
   * This methods takes an array containing either source or destination airports that are most or
   * least visited and produces a string of its routes' AirlineIDs.
   *
   * @param airportArrayToConvert Either the most or least visited source or destination airports
   *     array that needs to be converted to a String.
   * @return resultString The string of the array's airport names.
   */
  public String CombineAirportsToOneString(ArrayList<String> airportArrayToConvert) {
    String resultString = "";
    StringBuilder resultStringBuilder = new StringBuilder(resultString);
    for (int i = 0; i < airportArrayToConvert.size(); i++) {
      if (i == airportArrayToConvert.size() - 1) {
        resultStringBuilder.append(airportArrayToConvert.get(i));
      } else {
        resultStringBuilder.append(airportArrayToConvert.get(i)).append(", ");
      }
    }
    resultString = resultStringBuilder.toString();
    return resultString;
  }

  /**
   * This method converts the number of trees to offset emissions to a string that has been ceiled.
   *
   * @param trees The number of trees to offset as a double.
   */
  public void numOfTreesToString(Double trees) {
    this.numOfTreesString = String.format("%.0f", Math.ceil(trees));
  }

  /**
   * This methods updates all the most and least travelled route(s) arrays and visited airport(s)
   * arrays at once.
   */
  public void updateTravelledAndVisited() {
    Storage storage = Main.getStorage();
    routeStatsCalculator.updateLeastTravelledRoute(storage.getHistory());
    routeStatsCalculator.updateMostTravelledRoute(storage.getHistory());
    airportStatsCalculator.updateMostVisitedSrcAirports(storage.getHistorySrcAirports());
    airportStatsCalculator.updateLeastVisitedSrcAirports(storage.getHistorySrcAirports());
    airportStatsCalculator.updateMostVisitedDestAirports(storage.getHistoryDestAirports());
    airportStatsCalculator.updateLeastVisitedDestAirports(storage.getHistoryDestAirports());
  }

  public void resetReport() {
    routeStatsCalculator.resetRoutesArrays();
    airportStatsCalculator.resetAirportArrays();
  }
}
