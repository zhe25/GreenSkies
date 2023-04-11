package controller.analysis;

import model.data.*;
import model.loader.Loader;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit test for AirportStatsCalculator class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class RouteStatsCalculatorTest {

  private Storage storage;
  private Loader loader;
  private RouteStatsCalculator routeStatsCalculator;

  @Before
  public void setUp() {
    storage = new Storage();
    loader = new Loader(storage);
    routeStatsCalculator = new RouteStatsCalculator();
    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/GenerateReportTest.csv", "Route");
    } catch (FileSystemException | FileNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // --------------------------------- Testing for updateMostEmissionsRoute

  /**
   * Verify that when updateMostEmissionsRoute is called with a route and there are no routes in the
   * history, then the route with the most emissions is the route that was added.
   */
  @Test
  public void updateMostEmissionsRouteFirstEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRoute = new Route("2H", 1654, "IKT", 2937, "ODO", 8944, "", 0, "AN4".split(" "));
    expectedResults.add(testRoute);
    testRoute.setEmissions(10000);
    routeStatsCalculator.updateMostEmissionsRoute(testRoute);
    assertEquals(expectedResults, routeStatsCalculator.getMostEmissionsRoutes());
  }

  /**
   * Verify that when updateMostEmissionsRoute is called with a route and it has more emissions,
   * then the most emissions route in the history is set to the added route.
   */
  @Test
  public void updateMostEmissionsRouteAddMoreEmissionsEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteMoreEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLessEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMoreEmissions.setEmissions(12600);
    testRouteLessEmissions.setEmissions(163);
    expectedResults.add(testRouteMoreEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteLessEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteMoreEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getMostEmissionsRoutes());
  }

  /**
   * Verify that when updateMostEmissionsRoute is called with a route and it has less emissions,
   * then the most emissions route in the history is remains the same.
   */
  @Test
  public void updateMostEmissionsRouteAddLessEmissionsEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteMoreEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLessEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMoreEmissions.setEmissions(12600);
    testRouteLessEmissions.setEmissions(163);
    expectedResults.add(testRouteMoreEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteMoreEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteLessEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getMostEmissionsRoutes());
  }

  /**
   * Verify that when updateMostEmissionsRoute is called with a route and it is already in the most
   * emissions routes, then the most emissions route in the history remains the same.
   */
  @Test
  public void updateMostEmissionsRouteAddSameEmissionsSameRouteEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    testRouteEmissions.setEmissions(20000);
    expectedResults.add(testRouteEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getMostEmissionsRoutes());
  }

  /**
   * Verify that when updateMostEmissionsRoute is called with a route and it has the same most
   * emissions but isn't in the most emissions routes array, then the route is added to the most
   * emissions route array.
   */
  @Test
  public void updateMostEmissionsRouteAddSameEmissionsDiffRouteEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteDiffEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteEmissions.setEmissions(20000); // 12600 km
    testRouteDiffEmissions.setEmissions(20000); // 12600 km
    expectedResults.add(testRouteEmissions);
    expectedResults.add(testRouteDiffEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteDiffEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getMostEmissionsRoutes());
  }

  /**
   * Verify that when updateMostEmissionsRoute is called with a route and it has NaN emissions then
   * the most emissions route in the history is not changed to this added route. This test also uses
   * the FlightAnalyser class to check it works as expected here.
   */
  @Test
  public void updateMostEmissionsRouteAddNaNEmissionsEntryUseFlighAnalyserTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    List<DataType> providedAirports = new ArrayList<>();
    providedAirports.add(
        new Airport(
            2937,
            "Goroka",
            "Goroka",
            "Papua New Guinea",
            "GKA",
            "AYGA",
            -6.081689,
            145.391881,
            5282,
            10,
            "U",
            "Pacific/Port_Moresby"));
    providedAirports.add(
        new Airport(
            8944,
            "Narsarsuaq",
            "Narssarssuaq",
            "Greenland",
            "UAK",
            "BGBW",
            61.160517,
            -45.425978,
            112,
            -3,
            "E",
            "America/Godthab"));
    providedAirports.add(
        new Airport(
            4253,
            "Nuuk",
            "Godthaab",
            "Greenland",
            "GOH",
            "BGGH",
            64.190922,
            -51.678064,
            283,
            -3,
            "E",
            "America/Godthab"));
    providedAirports.add(
        new Airport(
            6436,
            "Akureyri",
            "Akureyri",
            "Iceland",
            "AEY",
            "BIAR",
            65.659994,
            -18.072703,
            6,
            0,
            "N",
            "Atlantic/Reykjavik"));

    storage.setData(providedAirports, "Airport", "airporttest.csv");
    Route testRouteEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteNaNEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    FlightAnalyser flightAnalyser =
        new FlightAnalyser(testRouteNaNEmissions, testRouteEmissions, storage);
    testRouteEmissions.setEmissions(flightAnalyser.getPath2Emission()); // 12600 km
    testRouteNaNEmissions.setEmissions(flightAnalyser.getPath1Emission()); // NaN
    expectedResults.add(testRouteEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteNaNEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getMostEmissionsRoutes());
  }

  /**
   * Verify that when updateMostEmissionsRoute is called with a route that has NaN emissions and
   * then with a route that has emissions that the NaN route gets ignored and the most emissions
   * route is the second route (not NaN emissions).
   */
  @Test
  public void updateMostEmissionsRouteMoreEmissionsEntryTest() throws SQLException {
    ArrayList<Route> expectedResults = new ArrayList<>();
    List<DataType> providedAirports = new ArrayList<>();
    providedAirports.add(
        new Airport(
            2937,
            "Goroka",
            "Goroka",
            "Papua New Guinea",
            "GKA",
            "AYGA",
            -6.081689,
            145.391881,
            5282,
            10,
            "U",
            "Pacific/Port_Moresby"));
    providedAirports.add(
        new Airport(
            8944,
            "Narsarsuaq",
            "Narssarssuaq",
            "Greenland",
            "UAK",
            "BGBW",
            61.160517,
            -45.425978,
            112,
            -3,
            "E",
            "America/Godthab"));
    providedAirports.add(
        new Airport(
            4253,
            "Nuuk",
            "Godthaab",
            "Greenland",
            "GOH",
            "BGGH",
            64.190922,
            -51.678064,
            283,
            -3,
            "E",
            "America/Godthab"));
    providedAirports.add(
        new Airport(
            6436,
            "Akureyri",
            "Akureyri",
            "Iceland",
            "AEY",
            "BIAR",
            65.659994,
            -18.072703,
            6,
            0,
            "N",
            "Atlantic/Reykjavik"));

    storage.setData(providedAirports, "Airport", "airporttest.csv");
    Route testRouteEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteNaNEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    FlightAnalyser flightAnalyser =
        new FlightAnalyser(testRouteNaNEmissions, testRouteEmissions, storage);
    testRouteEmissions.setEmissions(flightAnalyser.getPath2Emission()); // 12600 km
    testRouteNaNEmissions.setEmissions(flightAnalyser.getPath1Emission()); // NaN
    expectedResults.add(testRouteEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteNaNEmissions);
    routeStatsCalculator.updateMostEmissionsRoute(testRouteEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getMostEmissionsRoutes());
  }

  // --------------------------------- Testing for updateLeastEmissionsRoute

  /**
   * Verify that when updateLeastEmissionsRoute is called with a route and there are no routes in
   * the history, then the route with the least emissions is the route that was added.
   */
  @Test
  public void updateLeastEmissionsRouteFirstEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRoute = new Route("2H", 1654, "IKT", 2937, "ODO", 8944, "", 0, "AN4".split(" "));
    expectedResults.add(testRoute);
    testRoute.setEmissions(3425353);
    routeStatsCalculator.updateLeastEmissionsRoute(testRoute);
    assertEquals(expectedResults, routeStatsCalculator.getLeastEmissionsRoutes());
  }

  /**
   * Verify that when updateLeastEmissionsRoute is called with a route and it has more emissions,
   * then the less emissions route in the history is not changed.
   */
  @Test
  public void updateLeastEmissionsRouteAddMoreEmissionsEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteMoreEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLessEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMoreEmissions.setEmissions(12600);
    testRouteLessEmissions.setEmissions(163);
    expectedResults.add(testRouteLessEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteLessEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteMoreEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getLeastEmissionsRoutes());
  }

  /**
   * Verify that when updateLeastEmissionsRoute is called with a route and it has less emissions,
   * then the less emissions route in the history is updated to the added route.
   */
  @Test
  public void updateLeastEmissionsRouteAddLessEmissionsEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteMoreEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLessEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMoreEmissions.setEmissions(12600);
    testRouteLessEmissions.setEmissions(163);
    expectedResults.add(testRouteLessEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteMoreEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteLessEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getLeastEmissionsRoutes());
  }

  /**
   * Verify that when updateLeastEmissionsRoute is called with a route and it is already in the
   * least emissions routes, then the least emissions route in the history remains the same.
   */
  @Test
  public void updateLeastEmissionsRouteAddSameEmissionsSameRouteEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    testRouteEmissions.setEmissions(20000);
    expectedResults.add(testRouteEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getLeastEmissionsRoutes());
  }

  /**
   * Verify that when updateLeastEmissionsRoute is called with a route and it has the same most
   * emissions but isn't in the most emissions routes array, then the route is added to the most
   * emissions route array.
   */
  @Test
  public void updateLeastEmissionsRouteAddSameEmissionsDiffRouteEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteDiffEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteEmissions.setEmissions(20000);
    testRouteDiffEmissions.setEmissions(20000);
    expectedResults.add(testRouteEmissions);
    expectedResults.add(testRouteDiffEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteDiffEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getLeastEmissionsRoutes());
  }

  /**
   * Verify that when updateLeastEmissionsRoute is called with a route and it has NaN emissions then
   * the most emissions route in the history is not changed to this added route. This test also uses
   * the FlightAnalyser class to check it works as expected here.
   */
  @Test
  public void updateLeastEmissionsRouteAddNaNEmissionsEntryUseFlighAnalyserTest()
      throws SQLException {
    ArrayList<Route> expectedResults = new ArrayList<>();
    List<DataType> providedAirports = new ArrayList<>();
    providedAirports.add(
        new Airport(
            2937,
            "Goroka",
            "Goroka",
            "Papua New Guinea",
            "GKA",
            "AYGA",
            -6.081689,
            145.391881,
            5282,
            10,
            "U",
            "Pacific/Port_Moresby"));
    providedAirports.add(
        new Airport(
            8944,
            "Narsarsuaq",
            "Narssarssuaq",
            "Greenland",
            "UAK",
            "BGBW",
            61.160517,
            -45.425978,
            112,
            -3,
            "E",
            "America/Godthab"));
    providedAirports.add(
        new Airport(
            4253,
            "Nuuk",
            "Godthaab",
            "Greenland",
            "GOH",
            "BGGH",
            64.190922,
            -51.678064,
            283,
            -3,
            "E",
            "America/Godthab"));
    providedAirports.add(
        new Airport(
            6436,
            "Akureyri",
            "Akureyri",
            "Iceland",
            "AEY",
            "BIAR",
            65.659994,
            -18.072703,
            6,
            0,
            "N",
            "Atlantic/Reykjavik"));

    storage.setData(providedAirports, "Airport", "airporttest.csv");
    Route testRouteNaNEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    FlightAnalyser flightAnalyser =
        new FlightAnalyser(testRouteNaNEmissions, testRouteEmissions, storage);
    testRouteEmissions.setEmissions(flightAnalyser.getPath2Emission()); // 12600 km
    testRouteNaNEmissions.setEmissions(flightAnalyser.getPath1Emission()); // NaN
    expectedResults.add(testRouteEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteNaNEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getLeastEmissionsRoutes());
  }

  /**
   * Verify that when updateLeastEmissionsRoute is called with a route that has NaN emissions and
   * then with a route that has emissions that the NaN route gets ignored and the most emissions
   * route is the second route (not NaN emissions).
   */
  @Test
  public void updateLeastEmissionsRouteMoreEmissionsUseFlighAnalyserEntryTest()
      throws SQLException {
    ArrayList<Route> expectedResults = new ArrayList<>();
    List<DataType> providedAirports = new ArrayList<>();
    providedAirports.add(
        new Airport(
            2937,
            "Goroka",
            "Goroka",
            "Papua New Guinea",
            "GKA",
            "AYGA",
            -6.081689,
            145.391881,
            5282,
            10,
            "U",
            "Pacific/Port_Moresby"));
    providedAirports.add(
        new Airport(
            8944,
            "Narsarsuaq",
            "Narssarssuaq",
            "Greenland",
            "UAK",
            "BGBW",
            61.160517,
            -45.425978,
            112,
            -3,
            "E",
            "America/Godthab"));
    providedAirports.add(
        new Airport(
            4253,
            "Nuuk",
            "Godthaab",
            "Greenland",
            "GOH",
            "BGGH",
            64.190922,
            -51.678064,
            283,
            -3,
            "E",
            "America/Godthab"));
    providedAirports.add(
        new Airport(
            6436,
            "Akureyri",
            "Akureyri",
            "Iceland",
            "AEY",
            "BIAR",
            65.659994,
            -18.072703,
            6,
            0,
            "N",
            "Atlantic/Reykjavik"));

    storage.setData(providedAirports, "Airport", "airporttest.csv");
    Route testRouteNaNEmissions =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteEmissions =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    FlightAnalyser flightAnalyser =
        new FlightAnalyser(testRouteNaNEmissions, testRouteEmissions, storage);
    testRouteEmissions.setEmissions(flightAnalyser.getPath2Emission()); // 12600 km
    testRouteNaNEmissions.setEmissions(flightAnalyser.getPath1Emission()); // NaN
    expectedResults.add(testRouteEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteNaNEmissions);
    routeStatsCalculator.updateLeastEmissionsRoute(testRouteEmissions);
    assertEquals(expectedResults, routeStatsCalculator.getLeastEmissionsRoutes());
  }

  // --------------------------------- Testing for updateMostDistanceRoute

  /**
   * Verify that when updateMostDistanceRoute is called with a route and there are no routes in the
   * history, then the route of the most distance is the route that was added.
   */
  @Test
  public void updateMostDistanceRouteFirstEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRoute = new Route("2H", 1654, "IKT", 2937, "ODO", 8944, "", 0, "AN4".split(" "));
    expectedResults.add(testRoute);
    testRoute.setDistance(12333333);
    routeStatsCalculator.updateMostDistanceRoute(testRoute);
    assertEquals(expectedResults, routeStatsCalculator.getMostDistanceRoutes());
  }

  /**
   * Verify that when updateMostDistanceRoute is called with a route and it is of more distance,
   * then the more emissions route in the history is changed to this route.
   */
  @Test
  public void updateMostDistanceRouteAddMoreDistanceEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteMoreDistance =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLessDistance =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMoreDistance.setDistance(29394389);
    testRouteLessDistance.setDistance(309328);
    expectedResults.add(testRouteMoreDistance);
    routeStatsCalculator.updateMostDistanceRoute(testRouteLessDistance);
    routeStatsCalculator.updateMostDistanceRoute(testRouteMoreDistance);
    assertEquals(expectedResults, routeStatsCalculator.getMostDistanceRoutes());
  }

  /**
   * Verify that when updateMostDistanceRoute is called with a route and it is of less distance,
   * then the most distance route is not changed.
   */
  @Test
  public void updateMostDistanceRouteAddLessDistanceEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteMoreDistance =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLessDistance =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMoreDistance.setDistance(43243224);
    testRouteLessDistance.setDistance(3232223);
    expectedResults.add(testRouteMoreDistance);
    routeStatsCalculator.updateMostDistanceRoute(testRouteMoreDistance);
    routeStatsCalculator.updateMostDistanceRoute(testRouteLessDistance);
    assertEquals(expectedResults, routeStatsCalculator.getMostDistanceRoutes());
  }

  /**
   * Verify that when updateMostDistanceRoute is called with a route and it is already in the most
   * distance routes, then the most distance routes in the history remains the same.
   */
  @Test
  public void updateMostDistanceRouteAddSameDistanceSameRouteEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteDistance =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    testRouteDistance.setDistance(700080);
    expectedResults.add(testRouteDistance);
    routeStatsCalculator.updateMostDistanceRoute(testRouteDistance);
    routeStatsCalculator.updateMostDistanceRoute(testRouteDistance);
    assertEquals(expectedResults, routeStatsCalculator.getMostDistanceRoutes());
  }

  /**
   * Verify that when updateMostDistanceRoute is called with a route and it has the same most
   * distance but isn't in the most distance routes array, then the route is added to the most
   * distance route array.
   */
  @Test
  public void updateMostDistanceRouteAddSameEmissionsDiffRouteEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteDistance =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteDiffDistance =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteDistance.setDistance(20000);
    testRouteDiffDistance.setDistance(20000);
    expectedResults.add(testRouteDistance);
    expectedResults.add(testRouteDiffDistance);
    routeStatsCalculator.updateMostDistanceRoute(testRouteDistance);
    routeStatsCalculator.updateMostDistanceRoute(testRouteDiffDistance);
    assertEquals(expectedResults, routeStatsCalculator.getMostDistanceRoutes());
  }

  // --------------------------------- Testing for updateLeastDistanceRoute

  /**
   * Verify that when updateLeastDistanceRoute is called with a route and there are no routes in the
   * history, then the route of the least distance is the route that was added.
   */
  @Test
  public void updateLeastDistanceRouteFirstEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRoute = new Route("2H", 1654, "IKT", 2937, "ODO", 8944, "", 0, "AN4".split(" "));
    expectedResults.add(testRoute);
    testRoute.setDistance(2432828);
    routeStatsCalculator.updateLeastDistanceRoute(testRoute);
    assertEquals(expectedResults, routeStatsCalculator.getLeastDistanceRoutes());
  }

  /**
   * Verify that when updateLeastDistanceRoute is called with a route and it is of less distance,
   * then the less emissions route in the history is changed to this route.
   */
  @Test
  public void updateLeastDistanceRouteAddLessDistanceEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteMoreDistance =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLessDistance =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMoreDistance.setDistance(29394389);
    testRouteLessDistance.setDistance(309328);
    expectedResults.add(testRouteLessDistance);
    routeStatsCalculator.updateLeastDistanceRoute(testRouteMoreDistance);
    routeStatsCalculator.updateLeastDistanceRoute(testRouteLessDistance);
    assertEquals(expectedResults, routeStatsCalculator.getLeastDistanceRoutes());
  }

  /**
   * Verify that when updateLeastDistanceRoute is called with a route and it is of more distance,
   * then the most less route is not changed.
   */
  @Test
  public void updateLessDistanceRouteAddMoreDistanceEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteMoreDistance =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLessDistance =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMoreDistance.setDistance(43243224);
    testRouteLessDistance.setDistance(3232223);
    expectedResults.add(testRouteLessDistance);
    routeStatsCalculator.updateLeastDistanceRoute(testRouteLessDistance);
    routeStatsCalculator.updateLeastDistanceRoute(testRouteMoreDistance);
    assertEquals(expectedResults, routeStatsCalculator.getLeastDistanceRoutes());
  }

  /**
   * Verify that when updateLeastDistanceRoute is called with a route and it is already in the least
   * distance routes, then the least distance routes in the history remains the same.
   */
  @Test
  public void updateLeastDistanceRouteAddSameDistanceSameRouteEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteDistance =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    testRouteDistance.setDistance(34727229);
    expectedResults.add(testRouteDistance);
    routeStatsCalculator.updateLeastDistanceRoute(testRouteDistance);
    routeStatsCalculator.updateLeastDistanceRoute(testRouteDistance);
    assertEquals(expectedResults, routeStatsCalculator.getLeastDistanceRoutes());
  }

  /**
   * Verify that when updateLeastDistanceRoute is called with a route and it has the same most
   * distance but isn't in the most distance routes array, then the route is added to the most
   * distance route array.
   */
  @Test
  public void updateLeastDistanceRouteAddSameDistanceDiffRouteEntryTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    Route testRouteDistance =
        new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteDiffDistance =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteDistance.setDistance(20000);
    testRouteDiffDistance.setDistance(20000);
    expectedResults.add(testRouteDistance);
    expectedResults.add(testRouteDiffDistance);
    routeStatsCalculator.updateLeastDistanceRoute(testRouteDistance);
    routeStatsCalculator.updateLeastDistanceRoute(testRouteDiffDistance);
    assertEquals(expectedResults, routeStatsCalculator.getLeastDistanceRoutes());
  }

  // --------------------------------- Testing for updateMostTravelledMostRoute

  /**
   * Verify that when updateMostTravelledRoute is called when there is one route in the flight
   * history, then the most travelled route is this single route.
   */
  @Test
  public void updateMostTravelledRouteOneTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    Route testRoute = new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    testRoute.setTimesTaken(5);
    expectedResults.add(testRoute);
    testRoutes.add(testRoute);
    routeStatsCalculator.updateMostTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getMostTravelledRoutes());
  }

  /**
   * Verify that when updateMostTravelledRoute is called when there are two routes in the flight
   * history and one is more travelled than the other, then the most travelled route is the only
   * route in the mostTravelled Route array.
   */
  @Test
  public void updateMostTravelledMostRouteTwoTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    Route testRouteMost = new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLess =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteLess.setTimesTaken(5);
    testRouteMost.setTimesTaken(9);
    expectedResults.add(testRouteMost);
    testRoutes.add(testRouteMost);
    testRoutes.add(testRouteLess);
    routeStatsCalculator.updateMostTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getMostTravelledRoutes());
  }

  /**
   * Verify that when updateMostTravelledRoute is called when there are multiple routes in history
   * i.e. 10 and one of them is of the most travelled, then that route is the only route added to
   * the most travelled routes array.
   */
  @Test
  public void updateMostTravelledMostRouteTenDiffOneMostTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    Route testRouteMost = new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRoute2 = new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute3 = new Route("2C", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute4 = new Route("2D", 2777, "GBRF", 2242, "BIAR", 6436, "", 3, "NH7".split(" "));
    Route testRoute5 = new Route("2E", 1744, "GFRG", 2727, "BIAR", 6436, "", 1, "NH7".split(" "));
    Route testRoute6 = new Route("2F", 2424, "SWVR", 5858, "BIAR", 6436, "", 0, "NH7".split(" "));
    Route testRoute7 = new Route("2G", 2775, "SDD", 7557, "BIAR", 6436, "", 3, "NH7".split(" "));
    Route testRoute8 = new Route("2H", 9898, "VSV", 5578, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute9 = new Route("2I", 2782, "SVDE", 5257, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteMost.setTimesTaken(10);
    testRoute2.setTimesTaken(2);
    testRoute3.setTimesTaken(3);
    testRoute4.setTimesTaken(4);
    testRoute5.setTimesTaken(5);
    testRoute6.setTimesTaken(6);
    testRoute7.setTimesTaken(7);
    testRoute8.setTimesTaken(8);
    testRoute9.setTimesTaken(9);
    expectedResults.add(testRouteMost);
    testRoutes.add(testRoute2);
    testRoutes.add(testRoute3);
    testRoutes.add(testRoute4);
    testRoutes.add(testRoute5);
    testRoutes.add(testRoute6);
    testRoutes.add(testRoute7);
    testRoutes.add(testRoute8);
    testRoutes.add(testRoute9);
    testRoutes.add(testRouteMost);
    routeStatsCalculator.updateMostTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getMostTravelledRoutes());
  }

  /**
   * Verify that when updateMostTravelledRoute is called when there are multiple routes in history
   * i.e. 10 and more than one of them e.g. 3 is of the most travelled, then those routes are the
   * only routes added to the most travelled routes array.
   */
  @Test
  public void updateMostTravelledMostRouteTenDiffThreeMostTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    Route testRoute1 = new Route("2A", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRoute2 = new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute3 = new Route("2C", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute4 = new Route("2D", 2777, "GBRF", 2242, "BIAR", 6436, "", 3, "NH7".split(" "));
    Route testRoute5 = new Route("2E", 1744, "GFRG", 2727, "BIAR", 6436, "", 1, "NH7".split(" "));
    Route testRoute6 = new Route("2F", 2424, "SWVR", 5858, "BIAR", 6436, "", 0, "NH7".split(" "));
    Route testRoute7 = new Route("2G", 2775, "SDD", 7557, "BIAR", 6436, "", 3, "NH7".split(" "));
    Route testRoute8 = new Route("2H", 9898, "VSV", 5578, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute9 = new Route("2I", 2782, "SVDE", 5257, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRoute1.setTimesTaken(8);
    testRoute2.setTimesTaken(2);
    testRoute3.setTimesTaken(9);
    testRoute4.setTimesTaken(4);
    testRoute5.setTimesTaken(9);
    testRoute6.setTimesTaken(6);
    testRoute7.setTimesTaken(7);
    testRoute8.setTimesTaken(8);
    testRoute9.setTimesTaken(9);
    expectedResults.add(testRoute9);
    expectedResults.add(testRoute5);
    expectedResults.add(testRoute3);
    testRoutes.add(testRoute1);
    testRoutes.add(testRoute2);
    testRoutes.add(testRoute3);
    testRoutes.add(testRoute4);
    testRoutes.add(testRoute5);
    testRoutes.add(testRoute6);
    testRoutes.add(testRoute7);
    testRoutes.add(testRoute8);
    testRoutes.add(testRoute9);
    routeStatsCalculator.updateMostTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getMostTravelledRoutes());
  }

  /**
   * Verify that when updateMostTravelledRoute is called when there are no routes in history that
   * there are no routes in the most travelled routes array.
   */
  @Test
  public void updateMostTravelledRouteNoRoutesTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    routeStatsCalculator.updateMostTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getMostTravelledRoutes());
  }

  // --------------------------------- Testing for updateLeastTravelledMostRoute

  /**
   * Verify that when updateMostTravelledRoute is called when there is one route in the flight
   * history, then the least travelled route is this single route.
   */
  @Test
  public void updateLeastTravelledMostRouteOneTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    Route testRoute = new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    testRoute.setTimesTaken(5);
    expectedResults.add(testRoute);
    testRoutes.add(testRoute);
    routeStatsCalculator.updateLeastTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getLeastTravelledRoutes());
  }

  /**
   * Verify that when updateLeastTravelledRoute is called when there are two routes in the flight
   * history and one is less travelled than the other, then the least travelled route is the only
   * route in the least travelled routes array.
   */
  @Test
  public void updateLeastTravelledMostRouteTwoTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    Route testRouteMost = new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRouteLess =
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteLess.setTimesTaken(86);
    testRouteMost.setTimesTaken(233);
    expectedResults.add(testRouteLess);
    testRoutes.add(testRouteMost);
    testRoutes.add(testRouteLess);
    routeStatsCalculator.updateLeastTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getLeastTravelledRoutes());
  }

  /**
   * Verify that when updateMostTravelledRoute is called when there are multiple routes in history
   * i.e. 10 and one of them is of the least travelled, then that route is the only route added to
   * the least travelled routes array.
   */
  @Test
  public void updateLeastTravelledMostRouteTenDiffOneMostTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    Route testRouteLeast = new Route("2H", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRoute2 = new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute3 = new Route("2C", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute4 = new Route("2D", 2777, "GBRF", 2242, "BIAR", 6436, "", 3, "NH7".split(" "));
    Route testRoute5 = new Route("2E", 1744, "GFRG", 2727, "BIAR", 6436, "", 1, "NH7".split(" "));
    Route testRoute6 = new Route("2F", 2424, "SWVR", 5858, "BIAR", 6436, "", 0, "NH7".split(" "));
    Route testRoute7 = new Route("2G", 2775, "SDD", 7557, "BIAR", 6436, "", 3, "NH7".split(" "));
    Route testRoute8 = new Route("2H", 9898, "VSV", 5578, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute9 = new Route("2I", 2782, "SVDE", 5257, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRouteLeast.setTimesTaken(1);
    testRoute2.setTimesTaken(2);
    testRoute3.setTimesTaken(3);
    testRoute4.setTimesTaken(4);
    testRoute5.setTimesTaken(5);
    testRoute6.setTimesTaken(6);
    testRoute7.setTimesTaken(7);
    testRoute8.setTimesTaken(8);
    testRoute9.setTimesTaken(9);
    expectedResults.add(testRouteLeast);
    testRoutes.add(testRoute2);
    testRoutes.add(testRoute3);
    testRoutes.add(testRoute4);
    testRoutes.add(testRoute5);
    testRoutes.add(testRoute6);
    testRoutes.add(testRoute7);
    testRoutes.add(testRoute8);
    testRoutes.add(testRoute9);
    testRoutes.add(testRouteLeast);
    routeStatsCalculator.updateLeastTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getLeastTravelledRoutes());
  }

  /**
   * Verify that when updateLeastTravelledRoute is called when there are multiple routes in history
   * i.e. 10 and more than one of them e.g. 3 is of the least travelled, then those routes are the
   * only routes added to the least travelled routes array.
   */
  @Test
  public void updateLeastTravelledMostRouteTenDiffThreeMostTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    Route testRoute1 = new Route("2A", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" "));
    Route testRoute2 = new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute3 = new Route("2C", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute4 = new Route("2D", 2777, "GBRF", 2242, "BIAR", 6436, "", 3, "NH7".split(" "));
    Route testRoute5 = new Route("2E", 1744, "GFRG", 2727, "BIAR", 6436, "", 1, "NH7".split(" "));
    Route testRoute6 = new Route("2F", 2424, "SWVR", 5858, "BIAR", 6436, "", 0, "NH7".split(" "));
    Route testRoute7 = new Route("2G", 2775, "SDD", 7557, "BIAR", 6436, "", 3, "NH7".split(" "));
    Route testRoute8 = new Route("2H", 9898, "VSV", 5578, "BIAR", 6436, "", 4, "NH7".split(" "));
    Route testRoute9 = new Route("2I", 2782, "SVDE", 5257, "BIAR", 6436, "", 4, "NH7".split(" "));
    testRoute1.setTimesTaken(8);
    testRoute2.setTimesTaken(2);
    testRoute3.setTimesTaken(9);
    testRoute4.setTimesTaken(2);
    testRoute5.setTimesTaken(9);
    testRoute6.setTimesTaken(6);
    testRoute7.setTimesTaken(7);
    testRoute8.setTimesTaken(8);
    testRoute9.setTimesTaken(9);
    expectedResults.add(testRoute4);
    expectedResults.add(testRoute2);
    testRoutes.add(testRoute1);
    testRoutes.add(testRoute2);
    testRoutes.add(testRoute3);
    testRoutes.add(testRoute4);
    testRoutes.add(testRoute5);
    testRoutes.add(testRoute6);
    testRoutes.add(testRoute7);
    testRoutes.add(testRoute8);
    testRoutes.add(testRoute9);
    routeStatsCalculator.updateLeastTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getLeastTravelledRoutes());
  }

  /**
   * Verify that when updateLeastTravelledRoute is called when there are no routes in history that
   * there are no routes in the least travelled routes array.
   */
  @Test
  public void updateLeastTravelledRouteNoRoutesTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    ArrayList<Route> testRoutes = new ArrayList<>();
    routeStatsCalculator.updateLeastTravelledRoute(testRoutes);
    assertEquals(expectedResults, routeStatsCalculator.getLeastTravelledRoutes());
  }

  // --------------------------------- Testing for resetRoutesArrays()

  /**
   * Verify that when resetRoutesArrays is called when there are no values in the arrays, then they
   * remain empty.
   */
  @Test
  public void resetRoutesArraysEmptyTest() {
    ArrayList<Route> expectedLeastTravelledRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostTravelledRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> expectedLeastEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostDistanceRoutes = new ArrayList<>();
    ArrayList<Route> expectedLeastDistanceRoutes = new ArrayList<>();

    routeStatsCalculator.resetRoutesArrays();

    assertEquals(expectedLeastTravelledRoutes, routeStatsCalculator.getLeastTravelledRoutes());
    assertEquals(expectedMostTravelledRoutes, routeStatsCalculator.getMostTravelledRoutes());
    assertEquals(expectedMostEmissionsRoutes, routeStatsCalculator.getMostEmissionsRoutes());
    assertEquals(expectedLeastEmissionsRoutes, routeStatsCalculator.getLeastEmissionsRoutes());
    assertEquals(expectedMostDistanceRoutes, routeStatsCalculator.getMostDistanceRoutes());
    assertEquals(expectedLeastDistanceRoutes, routeStatsCalculator.getLeastDistanceRoutes());
  }

  /**
   * Verify that when resetRoutesArrays is called when there is a single value in each array, that
   * the arrays become empty.
   */
  @Test
  public void resetRoutesArraysOneValueTest() {
    ArrayList<Route> testLeastTravelledRoutes = new ArrayList<>();
    ArrayList<Route> testMostTravelledRoutes = new ArrayList<>();
    ArrayList<Route> testMostEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> testLeastEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> testMostDistanceRoutes = new ArrayList<>();
    ArrayList<Route> testLeastDistanceRoutes = new ArrayList<>();

    testLeastTravelledRoutes.add(
        new Route("2A", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" ")));
    testMostTravelledRoutes.add(
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testMostEmissionsRoutes.add(
        new Route("2C", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testLeastEmissionsRoutes.add(
        new Route("2D", 2777, "GBRF", 2242, "BIAR", 6436, "", 3, "NH7".split(" ")));
    testMostDistanceRoutes.add(
        new Route("2E", 1744, "GFRG", 2727, "BIAR", 6436, "", 1, "NH7".split(" ")));
    testLeastDistanceRoutes.add(
        new Route("2F", 2424, "SWVR", 5858, "BIAR", 6436, "", 0, "NH7".split(" ")));

    ArrayList<Route> expectedLeastTravelledRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostTravelledRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> expectedLeastEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostDistanceRoutes = new ArrayList<>();
    ArrayList<Route> expectedLeastDistanceRoutes = new ArrayList<>();

    routeStatsCalculator.resetRoutesArrays();

    assertEquals(expectedLeastTravelledRoutes, routeStatsCalculator.getLeastTravelledRoutes());
    assertEquals(expectedMostTravelledRoutes, routeStatsCalculator.getMostTravelledRoutes());
    assertEquals(expectedMostEmissionsRoutes, routeStatsCalculator.getMostEmissionsRoutes());
    assertEquals(expectedLeastEmissionsRoutes, routeStatsCalculator.getLeastEmissionsRoutes());
    assertEquals(expectedMostDistanceRoutes, routeStatsCalculator.getMostDistanceRoutes());
    assertEquals(expectedLeastDistanceRoutes, routeStatsCalculator.getLeastDistanceRoutes());
  }

  /**
   * Verify that when resetRoutesArrays is called when there is are multiple values in each array,
   * that the arrays become empty.
   */
  @Test
  public void resetRoutesArraysMultiValuesTest() {
    ArrayList<Route> testLeastTravelledRoutes = new ArrayList<>();
    ArrayList<Route> testMostTravelledRoutes = new ArrayList<>();
    ArrayList<Route> testMostEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> testLeastEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> testMostDistanceRoutes = new ArrayList<>();
    ArrayList<Route> testLeastDistanceRoutes = new ArrayList<>();

    testLeastTravelledRoutes.add(
        new Route("2A", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" ")));
    testMostTravelledRoutes.add(
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testMostEmissionsRoutes.add(
        new Route("2C", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testLeastEmissionsRoutes.add(
        new Route("2D", 2777, "GBRF", 2242, "BIAR", 6436, "", 3, "NH7".split(" ")));
    testMostDistanceRoutes.add(
        new Route("2E", 1744, "GFRG", 2727, "BIAR", 6436, "", 1, "NH7".split(" ")));
    testLeastDistanceRoutes.add(
        new Route("2F", 2424, "SWVR", 5858, "BIAR", 6436, "", 0, "NH7".split(" ")));
    testLeastTravelledRoutes.add(
        new Route("2F", 2424, "SWVR", 5858, "BIAR", 6436, "", 0, "NH7".split(" ")));
    testMostTravelledRoutes.add(
        new Route("2G", 2775, "SDD", 7557, "BIAR", 6436, "", 3, "NH7".split(" ")));
    testMostEmissionsRoutes.add(
        new Route("2H", 9898, "VSV", 5578, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testLeastEmissionsRoutes.add(
        new Route("2I", 2782, "SVDE", 5257, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testMostDistanceRoutes.add(
        new Route("2A", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" ")));
    testLeastDistanceRoutes.add(
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" ")));

    ArrayList<Route> expectedLeastTravelledRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostTravelledRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> expectedLeastEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostDistanceRoutes = new ArrayList<>();
    ArrayList<Route> expectedLeastDistanceRoutes = new ArrayList<>();

    routeStatsCalculator.resetRoutesArrays();

    assertEquals(expectedLeastTravelledRoutes, routeStatsCalculator.getLeastTravelledRoutes());
    assertEquals(expectedMostTravelledRoutes, routeStatsCalculator.getMostTravelledRoutes());
    assertEquals(expectedMostEmissionsRoutes, routeStatsCalculator.getMostEmissionsRoutes());
    assertEquals(expectedLeastEmissionsRoutes, routeStatsCalculator.getLeastEmissionsRoutes());
    assertEquals(expectedMostDistanceRoutes, routeStatsCalculator.getMostDistanceRoutes());
    assertEquals(expectedLeastDistanceRoutes, routeStatsCalculator.getLeastDistanceRoutes());
  }

  /**
   * Verify that when resetRoutesArrays is called when there is a mixture of values in each array,
   * that the arrays become empty.
   */
  @Test
  public void resetRoutesArraysVaryingNumOfValuesTest() {
    ArrayList<Route> testLeastTravelledRoutes = new ArrayList<>();
    ArrayList<Route> testMostTravelledRoutes = new ArrayList<>();
    ArrayList<Route> testMostEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> testLeastEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> testMostDistanceRoutes = new ArrayList<>();
    ArrayList<Route> testLeastDistanceRoutes = new ArrayList<>();

    testLeastTravelledRoutes.add(
        new Route("2A", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" ")));
    testMostEmissionsRoutes.add(
        new Route("2C", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testLeastEmissionsRoutes.add(
        new Route("2D", 2777, "GBRF", 2242, "BIAR", 6436, "", 3, "NH7".split(" ")));
    testMostDistanceRoutes.add(
        new Route("2E", 1744, "GFRG", 2727, "BIAR", 6436, "", 1, "NH7".split(" ")));
    testLeastDistanceRoutes.add(
        new Route("2F", 2424, "SWVR", 5858, "BIAR", 6436, "", 0, "NH7".split(" ")));
    testMostEmissionsRoutes.add(
        new Route("2H", 9898, "VSV", 5578, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testLeastEmissionsRoutes.add(
        new Route("2I", 2782, "SVDE", 5257, "BIAR", 6436, "", 4, "NH7".split(" ")));
    testMostDistanceRoutes.add(
        new Route("2A", 1654, "GKA", 2937, "UAK", 8944, "", 0, "AN4".split(" ")));
    testLeastDistanceRoutes.add(
        new Route("2B", 5336, "BGGH", 4253, "BIAR", 6436, "", 4, "NH7".split(" ")));

    ArrayList<Route> expectedLeastTravelledRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostTravelledRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> expectedLeastEmissionsRoutes = new ArrayList<>();
    ArrayList<Route> expectedMostDistanceRoutes = new ArrayList<>();
    ArrayList<Route> expectedLeastDistanceRoutes = new ArrayList<>();

    routeStatsCalculator.resetRoutesArrays();

    assertEquals(expectedLeastTravelledRoutes, routeStatsCalculator.getLeastTravelledRoutes());
    assertEquals(expectedMostTravelledRoutes, routeStatsCalculator.getMostTravelledRoutes());
    assertEquals(expectedMostEmissionsRoutes, routeStatsCalculator.getMostEmissionsRoutes());
    assertEquals(expectedLeastEmissionsRoutes, routeStatsCalculator.getLeastEmissionsRoutes());
    assertEquals(expectedMostDistanceRoutes, routeStatsCalculator.getMostDistanceRoutes());
    assertEquals(expectedLeastDistanceRoutes, routeStatsCalculator.getLeastDistanceRoutes());
  }
}
