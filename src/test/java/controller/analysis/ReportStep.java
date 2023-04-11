package controller.analysis;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.data.Route;
import model.data.Storage;
import model.database.SQLiteDatabase;
import model.loader.Loader;
import org.junit.Assert;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import controller.guiController.dataview.RouteAddToHistoryPopUpController;

/**
 * This class is the step for the cucumber tests for the report statistics methods.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class ReportStep {
  private String fileType;
  private String filePath;
  private Storage storage = new Storage();
  private Loader loader = new Loader(storage);
  private SQLiteDatabase database = new SQLiteDatabase();
  private GeneralStatsCalculator generalStatsCalculator = new GeneralStatsCalculator();
  private RouteAddToHistoryPopUpController routeAddToHistoryPopUpController =
      new RouteAddToHistoryPopUpController();
  private RouteStatsCalculator routeStatsCalculator = new RouteStatsCalculator();

  @Before
  private void setUp() throws FileNotFoundException, FileSystemException, SQLException {
    loader.loadFile("../seng202_project/src/test/java/TestFiles/airports.csv", "Airport");
    loader.loadFile("../seng202_project/src/test/java/TestFiles/routes.csv", "Route");
  }

  @Given(
      "The user has uploaded route data and airport data and added a route {string},{int},{string},{int},{string},{int},{string},{int},{string} and set the number of passengers to {int} into the flight history and is currently on the carbon emissions report page and the user has entered their goal as {int} kg")
  public void theAddedRouteToHistoryIs(
      String airline,
      int airlineID,
      String sourceAirport,
      int sourceAirportID,
      String destinationAirport,
      int destinationAirportID,
      String codeshare,
      int stops,
      String equipment,
      int timesTaken,
      int goal)
      throws FileNotFoundException, FileSystemException, SQLException {
    loader.loadFile("../seng202_project/src/test/java/TestFiles/airports.csv", "Airport");
    loader.loadFile("../seng202_project/src/test/java/TestFiles/routes.csv", "Route");
    Route historyRoute =
        new Route(
            airline,
            airlineID,
            sourceAirport,
            sourceAirportID,
            destinationAirport,
            destinationAirportID,
            codeshare,
            stops,
            equipment.split(" "));
    historyRoute.setTimesTaken(timesTaken);
    this.storage.addToHistory(historyRoute);
    this.generalStatsCalculator.setCarbonEmissionsGoal(goal);

    FlightAnalyser flightAnalyser = new FlightAnalyser(historyRoute, storage);
    historyRoute.setEmissions(flightAnalyser.getPath1Emission());
    historyRoute.setDistance(flightAnalyser.getTotalDistancePath1());
    generalStatsCalculator.updateTotalDistance(historyRoute);
    generalStatsCalculator.updateTotalEmissions(historyRoute);
    storage.addToHistorySrcAirports(historyRoute.getSourceAirport());
    storage.addToHistoryDestAirports(historyRoute.getDestinationAirport());
    routeStatsCalculator.updateLeastDistanceRoute(historyRoute);
    routeStatsCalculator.updateMostDistanceRoute(historyRoute);
    routeStatsCalculator.updateMostEmissionsRoute(historyRoute);
    routeStatsCalculator.updateLeastEmissionsRoute(historyRoute);
  }

  @When("The user clicks the generate report button")
  public void reportGeneratorButtonClicked() {}

  @Then("The total carbon emissions of {double} kg is displayed.")
  public void theTotalDisplayedIs(double expectedEmissions) {
    Assert.assertEquals(
        expectedEmissions, this.generalStatsCalculator.getTotalCarbonEmissions(), 0.01);
  }
}
